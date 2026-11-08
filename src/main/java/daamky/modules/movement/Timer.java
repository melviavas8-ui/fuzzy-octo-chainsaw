package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import net.minecraft.client.world.ClientWorld;






public class Timer extends Module {
    private final Modules.Slider speed, drain;
    private final Modules.Bool smart, addMoving;
    private float charge = 100f;

    public Timer() {
        super("Timer", "Ускоряет время игры", MOVEMENT);
        speed = slider("Скорость", 1.5f, 0.5f, 5f, 0.1f, "x");
        smart = bool("Умный режим", true);
        drain = slider("Скорость убывания", 1f, 0.1f, 5f, 0.1f, "");
        addMoving = bool("Добавлять в движении", false);
    }

    private void rate(float mult) {
        ClientWorld w = mc.world;
        if (w != null) w.getTickManager().setTickRate(20f * mult);
    }

    @Override protected void onDisable() { rate(1f); charge = 100f; }

    @Override protected void onTick() {
        float mult = speed.value;
        if (smart.value) {
            boolean moving = MoveUtil.isMoving();
            if (moving && charge > 0f) { charge = Math.max(0f, charge - drain.value); }
            else { charge = Math.min(100f, charge + (moving && addMoving.value ? 0.5f : 1f)); mult = 1f; }
            if (charge <= 0f) mult = 1f;
        }
        rate(mult);
    }
}
