package daamky.modules.player;

import daamky.core.Module;
import daamky.gui.Modules;


public class AutoRespawn extends Module {
    public final Modules.Bool message;

    public AutoRespawn() {
        super("Auto Respawn", "Автоматически респавнит вас\nпосле смерти", PLAYER);
        message = bool("Сообщение после респавна", false);
    }

    @Override protected void onTick() {
        if (mc.player.isDead()) mc.player.requestRespawn();
    }
}
