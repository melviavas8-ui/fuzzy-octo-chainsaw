package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import java.util.List;






public class Speed extends Module {
    private final Modules.Mode mode;

    public Speed() {
        super("Speed", "Увеличивает скорость игрока", MOVEMENT);
        mode = mode("Режим", 0, "Vanilla", "Collision", "Matrix", "Grim");
    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (!MoveUtil.isMoving() || p.isTouchingWater() || p.isInLava() || p.isGliding()) return;
        switch (mode.get()) {
            case "Vanilla" -> {
                if (p.isOnGround()) p.jump();
                else MoveUtil.setSpeed(p, Math.max(MoveUtil.hSpeed(p), 0.26));
            }
            case "Collision", "Grim" -> {
                Box b = p.getBoundingBox().expand(1.0);
                List<Entity> list = mc.world.getOtherEntities(p, b, e -> e.isAlive() && !e.isSpectator());
                if (list.isEmpty()) return;
                double add = 0.08 * Math.min(list.size(), 3);
                MoveUtil.setSpeed(p, MoveUtil.hSpeed(p) + add);
            }
            case "Matrix" -> {
                if (p.isOnGround()) p.jump();
                else if (p.fallDistance > 0.1f && p.fallDistance < 0.9f) MoveUtil.setSpeed(p, MoveUtil.hSpeed(p) * 1.04);
            }
        }
    }
}
