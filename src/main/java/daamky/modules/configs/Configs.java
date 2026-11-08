package daamky.modules.configs;

import daamky.core.ConfigManager;
import daamky.core.Module;
import daamky.gui.Modules;





public class Configs extends Module {
    private final Modules.Mode slot;
    private final Modules.Bool save, load, delete;

    public Configs() {
        super("Configs", "Сохранение и загрузка конфигов\n(config/daamky/<слот>.json)", CONFIGS);
        section("Слот");
        slot = mode("Слот", 0, "default", "legit", "rage", "pvp", "build");
        section("Действия");
        save = bool("Сохранить", false);
        load = bool("Загрузить", false);
        delete = bool("Удалить", false);
        enabled = true;
    }

    @Override protected void onTick() {
        String n = slot.get();
        if (save.value) { save.value = false; ConfigManager.saveSlot(n); }
        if (load.value) { load.value = false; ConfigManager.loadSlot(n); enabled = true; }
        if (delete.value) { delete.value = false; ConfigManager.deleteSlot(n); }
    }
}
