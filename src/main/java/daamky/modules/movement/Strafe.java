package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;



public class Strafe extends Module {
    public Strafe() { super("Strafe", "Позволяет не замедляться при\nдвижении в стороны", MOVEMENT); }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (p.isOnGround() || p.isTouchingWater() || p.isGliding() || !MoveUtil.isMoving()) return;
        double s = MoveUtil.hSpeed(p);
        if (s > 0.05) MoveUtil.setSpeed(p, s);
    }
}
