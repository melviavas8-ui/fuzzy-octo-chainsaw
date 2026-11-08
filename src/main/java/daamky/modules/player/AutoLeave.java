package daamky.modules.player;

import daamky.core.Module;
import daamky.gui.Modules;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;





public class AutoLeave extends Module {
    private final Modules.Bool lowHp, nearPlayer, staffOnline, staffSpec, banInChat;
    private final Modules.Slider hp, dist;
    private final Modules.Mode method;

    public AutoLeave() {
        super("Auto Leave", "Избегает определенных условий", PLAYER);
        section("Ливать если");
        lowHp = bool("Мало ХП", true);
        hp = slider("Количество ХП", 6, 1, 20, 1, " HP");
        nearPlayer = bool("Рядом игрок", false);
        dist = slider("Дистанция до игрока", 10, 1, 64, 1, " бл.");
        staffOnline = bool("Staff в сети", false);
        staffSpec = bool("Staff в спеке", false);
        banInChat = bool("Бан в чате", false);
        section("Метод лива");
        method = mode("Метод лива", 0, "Отключение", "Перезаход", "Сообщение");
    }

    @Override protected void onTick() {
        String reason = null;
        if (lowHp.value && mc.player.getHealth() <= hp.value) reason = "У вас мало ХП ";
        if (reason == null && nearPlayer.value) {
            for (PlayerEntity p : mc.world.getPlayers())
                if (p != mc.player && mc.player.distanceTo(p) <= dist.value) { reason = "Рядом был игрок " + p.getName().getString(); break; }
        }
        if (reason == null) return;
        enabled = false;
        switch (method.get()) {
            case "Сообщение" -> mc.player.networkHandler.sendChatCommand("hub");
            default -> mc.getNetworkHandler().getConnection().disconnect(Text.literal(reason));
        }
    }
}
