package daamky.modules.render;

import java.util.ArrayList;
import java.util.List;
import daamky.core.Module;
import daamky.hud.Souls;
import daamky.render.SdfRect;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2f;








public class JumpCircles extends Module {
    private static JumpCircles inst;

    private final daamky.gui.Modules.Slider radius, glowRadius, speed;
    private static final class Circle { final double x, y, z; final long born; Circle(double x, double y, double z, long b) { this.x = x; this.y = y; this.z = z; born = b; } }
    private final List<Circle> circles = new ArrayList<>();
    private boolean wasOnGround = true;

    public JumpCircles() {
        super("Jump Circles", "Добавляет анимацию прыжка под\nвами", RENDER);
        radius = slider("Радиус", 1.5f, 1.0f, 2.0f, 0.1f, "");
        glowRadius = slider("Радиус свечения", 1.0f, 1.0f, 2.0f, 0.1f, "");
        speed = slider("Скорость", 0.3f, 0.3f, 1.0f, 0.1f, "");
        inst = this;
    }

    @Override protected void onDisable() { circles.clear(); }

    @Override
    protected void onTick() {
        ClientPlayerEntity p = mc.player;
        boolean ground = p.isOnGround();

        if (wasOnGround && !ground && p.getVelocity().y > 0.05 && !p.isTouchingWater() && !p.isClimbing())
            circles.add(new Circle(p.getX(), p.getY() + 0.02, p.getZ(), System.nanoTime()));
        wasOnGround = ground;
    }

    public static void render(Matrix3x2f m, float tickProgress) {
        JumpCircles j = inst;
        if (j == null || !j.on() || j.circles.isEmpty()) return;
        j.draw(m, tickProgress);
    }

    private void draw(Matrix3x2f m, float tickProgress) {
        int sw = mc.getWindow().getScaledWidth(), sh = mc.getWindow().getScaledHeight();
        Vec3d cam = mc.player.getCameraPosVec(tickProgress);
        float yaw = mc.player.getYaw(tickProgress), pitch = mc.player.getPitch(tickProgress);
        float fov = mc.options.getFov().getValue();
        long now = System.nanoTime();
        float life = 0.6f / speed.value;
        float rMax = radius.value, glow = glowRadius.value;

        circles.removeIf(c -> (now - c.born) / 1e9f >= life);
        float spin = (now / 1e9f) * 0.6f;
        for (Circle c : circles) {
            float p = (now - c.born) / 1e9f / life;
            float e = 1f - (1f - p) * (1f - p) * (1f - p);
            float r = Math.max(0.05f, rMax * e);
            float fade = (float) Math.pow(1f - p, 1.5);


            float[] o = Souls.project(new Vec3d(c.x, c.y, c.z), cam, yaw, pitch, fov, sw, sh);
            float[] ax = Souls.project(new Vec3d(c.x + 1, c.y, c.z), cam, yaw, pitch, fov, sw, sh);
            float[] az = Souls.project(new Vec3d(c.x, c.y, c.z + 1), cam, yaw, pitch, fov, sw, sh);
            if (o == null || ax == null || az == null) continue;

            for (int layer = 0; layer < 2; layer++) {
                float half = r / 0.80f * (layer == 0 ? 1f : 1f + 0.12f * glow);
                Matrix3x2f t = new Matrix3x2f(m).mul(new Matrix3x2f(ax[0] - o[0], ax[1] - o[1], az[0] - o[0], az[1] - o[1], o[0], o[1]));
                t.scale(half);
                int col = grad(spin + layer * 0.33f);
                int a = (int) (255 * fade * (layer == 0 ? 0.95f : 0.45f));
                daamky.render.Img.draw(t, "jump_ring.png", -1f, -1f, 2f, 2f, (a << 24) | col);
            }
        }
    }


    public static int grad(float t) {
        t = t - (float) Math.floor(t);
        float[][] k = {SdfRect.GA, SdfRect.GB, SdfRect.GC, SdfRect.GA};
        float f = t * 3f; int i = Math.min(2, (int) f); f -= i;
        float[] a = k[i], b = k[i + 1];
        int r = (int) ((a[0] + (b[0] - a[0]) * f) * 255), g = (int) ((a[1] + (b[1] - a[1]) * f) * 255), bl = (int) ((a[2] + (b[2] - a[2]) * f) * 255);
        return (r & 255) << 16 | (g & 255) << 8 | (bl & 255);
    }
}
