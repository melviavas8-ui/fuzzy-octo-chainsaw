package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;







public class Flight extends Module {
    private final Modules.Mode mode;
    private final Modules.Slider speed, levSpeed;

    public Flight() {
        super("Flight", "Позволяет летать без креатив\nрежима", MOVEMENT);
        mode = mode("Режим", 0, "Vanilla", "Levitation Boost", "Jump");
        speed = slider("Скорость", 2f, 0.5f, 10f, 0.1f, "");
        levSpeed = slider("Скорость", 1f, 0.6f, 5f, 0.1f, "");
    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        double sp = speed.value;
        switch (mode.get()) {
            case "Vanilla" -> {
                MoveUtil.setSpeed(p, sp);
                double y = mc.options.jumpKey.isPressed() ? sp * 0.5 : mc.options.sneakKey.isPressed() ? -sp * 0.5 : 0;
                Vec3d v = p.getVelocity();
                p.setVelocity(v.x, y, v.z);
            }
            case "Levitation Boost" -> {
                if (p.hasStatusEffect(net.minecraft.entity.effect.StatusEffects.LEVITATION)) {
                    Vec3d v = p.getVelocity();
                    double ls = levSpeed.value;
                    p.setVelocity(v.x, v.y * (1.0 + 0.05 * ls), v.z);
                    MoveUtil.setSpeed(p, 0.2 * ls);
                }
            }
            case "Jump" -> {
                if (p.getVelocity().y < -0.1 && p.fallDistance > 0.5f) {
                    Vec3d v = p.getVelocity();
                    p.setVelocity(v.x, 0.42 * Math.min(sp, 1.0), v.z);
                    p.fallDistance = 0;
                }
            }
        }
    }
}
