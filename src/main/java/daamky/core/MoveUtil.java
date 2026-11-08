package daamky.core;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public final class MoveUtil {
    private MoveUtil() {}

    private static float fwd() {
        var o = MinecraftClient.getInstance().options;
        return (o.forwardKey.isPressed() ? 1f : 0f) - (o.backKey.isPressed() ? 1f : 0f);
    }

    private static float side() {
        var o = MinecraftClient.getInstance().options;
        return (o.leftKey.isPressed() ? 1f : 0f) - (o.rightKey.isPressed() ? 1f : 0f);
    }

    public static boolean isMoving() { return fwd() != 0 || side() != 0; }

    public static double hSpeed(ClientPlayerEntity p) {
        Vec3d v = p.getVelocity();
        return Math.hypot(v.x, v.z);
    }


    public static void setSpeed(ClientPlayerEntity p, double speed) {
        float f = fwd(), s = side();
        Vec3d v = p.getVelocity();
        if (f == 0 && s == 0) { p.setVelocity(0, v.y, 0); return; }
        if (f != 0 && s != 0) { f *= 0.70710678f; s *= 0.70710678f; }
        double r = Math.toRadians(p.getYaw());
        double sin = Math.sin(r), cos = Math.cos(r);
        p.setVelocity((-sin * f + cos * s) * speed, v.y, (cos * f + sin * s) * speed);
    }
}
