package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;






public class NoSlow extends Module {
    private static NoSlow inst;
    public final Modules.Mode mode;

    public NoSlow() {
        super("No Slow", "Убирает замедление при\nиспользовании предмета", MOVEMENT);
        mode = mode("Режим", 0, "Funtime", "SpookyTime", "ReallyWorld", "Vanilla");
        inst = this;
    }

    public static boolean active() { return inst != null && inst.on(); }
}
