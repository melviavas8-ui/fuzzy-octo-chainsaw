package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;



public class Spider extends Module {
    private final Modules.Mode mode;
    private int t;

    public Spider() {
        super("Spider", "Позволяет взбираться по стенам", MOVEMENT);
        mode = mode("Режим", 0, "Grim", "Matrix", "Funtime");
    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (!p.horizontalCollision) { t = 0; return; }
        t++;
        Vec3d v = p.getVelocity();
        switch (mode.get()) {
            case "Grim" -> { if (t % 2 == 0) p.setVelocity(v.x, 0.42, v.z); }
            case "Matrix" -> { if (p.isOnGround() || t % 3 == 0) p.setVelocity(v.x, 0.42, v.z); }
            default -> p.setVelocity(v.x, 0.2, v.z);
        }
    }
}
