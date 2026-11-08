package daamky.hud;

import daamky.gui.Modules;
import daamky.render.SdfRect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2f;





public final class Souls {
    private static LivingEntity target;
    private static float fade, time;
    private static long lastNs;

    private Souls() {}

    private static float clamp01(float v) { return Math.max(0f, Math.min(1f, v)); }


    public static float[] project(Vec3d p, Vec3d cam, float yaw, float pitch, float fovDeg, int sw, int sh) {
        double yr = Math.toRadians(yaw), pr = Math.toRadians(pitch);
        Vec3d f = new Vec3d(-Math.sin(yr) * Math.cos(pr), -Math.sin(pr), Math.cos(yr) * Math.cos(pr));
        Vec3d r = new Vec3d(-Math.cos(yr), 0, -Math.sin(yr));
        Vec3d u = r.crossProduct(f);
        Vec3d d = p.subtract(cam);
        double z = d.dotProduct(f);
        if (z < 0.05) return null;
        double th = Math.tan(Math.toRadians(fovDeg) / 2.0), asp = sw / (double) sh;
        double nx = d.dotProduct(r) / z / (th * asp), ny = d.dotProduct(u) / z / th;
        return new float[]{(float) ((nx + 1) * 0.5 * sw), (float) ((1 - ny) * 0.5 * sh), (float) (sh / (2.0 * th * z))};
    }

    public static void render(Matrix3x2f m, float tickProgress) {
        MinecraftClient mc = MinecraftClient.getInstance();
        long now = System.nanoTime();
        float dt = lastNs == 0 ? 0.016f : Math.min(0.05f, (now - lastNs) / 1e9f);
        lastNs = now;
        LivingEntity aura = daamky.modules.combat.AttackAura.target();
        boolean auraEsp = aura != null && daamky.modules.combat.AttackAura.espOn();
        if (mc.player == null || mc.world == null || (!auraEsp && (Modules.TARGET_ESP == null || !Modules.TARGET_ESP.enabled))) { fade = 0; target = null; return; }

        Entity looked = auraEsp ? aura : mc.targetedEntity;
        if (looked instanceof LivingEntity le && le != mc.player && le.isAlive()) target = le;
        boolean active = looked == target && target != null;
        fade = clamp01(fade + (active ? dt / 0.25f : -dt / 0.35f));
        if (fade <= 0.003f || target == null) return;
        time += dt;

        int sw = mc.getWindow().getScaledWidth(), sh = mc.getWindow().getScaledHeight();
        Vec3d cam = mc.player.getCameraPosVec(tickProgress);
        float yaw = mc.player.getYaw(tickProgress), pitch = mc.player.getPitch(tickProgress);
        float fov = mc.options.getFov().getValue();
        Vec3d c = target.getLerpedPos(tickProgress);
        float h = target.getHeight(), w = target.getWidth();
        float ease = fade * fade * (3f - 2f * fade);
        int alpha = (int) (255 * ease);


        final int ORBS = 4, SEG = 14;
        double t = System.currentTimeMillis() / 1000.0;
        double spin = t * 3.0 % (Math.PI * 2);
        double a0 = 0.0, a1 = Math.toRadians(60), da = (a1 - a0) / SEG;
        double yOff = h / 2.0 + 0.2;
        double radius = w;
        float sizeK = Modules.SOUL_SIZE.value, spdK = Modules.SOUL_SPEED.value;
        spin = (t * 3.0 * spdK) % (Math.PI * 2);
        for (int o = 0; o < ORBS; o++) {
            double ph = t * spdK + o * 15;
            double off = o * (Math.PI / 2);
            for (int i = 0; i <= SEG; i++) {
                double ang = a0 + i * da;
                double a2 = ang + spin + off;
                Vec3d p = new Vec3d(c.x + radius * Math.cos(a2), c.y + Math.sin(ph + ang + o) * 0.7 + yOff, c.z + radius * Math.sin(a2));
                float[] s = project(p, cam, yaw, pitch, fov, sw, sh);
                if (s == null) continue;
                float side = 0.4f * (0.5f + i / (float) SEG) * sizeK * s[2];
                if (side < 1f) continue;
                int col = daamky.modules.render.JumpCircles.grad(i / (float) SEG * 0.5f + o * 0.25f);
                int argb = ((int) (alpha * 0.85f) << 24) | col;
                if (!daamky.render.Img.draw(m, "glow.png", s[0] - side / 2, s[1] - side / 2, side, side, argb))
                    SdfRect.glow(m, s[0] - side / 4, s[1] - side / 4, side / 2, side / 2, side / 4, side / 3, argb);
            }
        }
    }

    private static int lerp(int a, int b, float f) {
        int r = (int) (((a >> 16) & 255) * (1 - f) + ((b >> 16) & 255) * f);
        int g = (int) (((a >> 8) & 255) * (1 - f) + ((b >> 8) & 255) * f);
        int bl = (int) ((a & 255) * (1 - f) + (b & 255) * f);
        return r << 16 | g << 8 | bl;
    }
}
