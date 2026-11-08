package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;






public class BlocksLift extends Module {
    private final Modules.Mode mode;

    public BlocksLift() {
        super("Blocks Lift", "Позволяет подниматься в блоках", MOVEMENT);
        mode = mode("Режим", 0, "Packet", "Glide", "Matrix");
    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (!p.isInsideWall()) return;
        if (mode.get().equals("Glide") || mode.get().equals("Matrix")) {
            Vec3d v = p.getVelocity();
            p.setVelocity(v.x, 0.12, v.z);
        }
    }
}
