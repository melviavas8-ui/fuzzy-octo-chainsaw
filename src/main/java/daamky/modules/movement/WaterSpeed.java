package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;



public class WaterSpeed extends Module {
    private final Modules.Mode mode;

    public WaterSpeed() {
        super("Water Speed", "Увеличивает скорость в воде", MOVEMENT);
        mode = mode("Режим", 0, "Funtime", "Vulcan");
    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (!p.isTouchingWater() || !MoveUtil.isMoving()) return;
        double k = mode.get().equals("Funtime") ? 1.06 : 1.03;
        Vec3d v = p.getVelocity();
        p.setVelocity(v.x * k, v.y, v.z * k);
    }
}
