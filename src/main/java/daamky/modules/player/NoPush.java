package daamky.modules.player;

import daamky.core.Module;
import daamky.gui.Modules;


public class NoPush extends Module {
    private static NoPush inst;
    private final Modules.Bool blocks, entities, fishing, water, bubbles, border;

    public NoPush() {
        super("No Push", "Игнорирует отталкивание от\nчего-либо", PLAYER);
        section("Игнорировать");
        blocks = bool("Блоки", true);
        entities = bool("Сущности", true);
        fishing = bool("Удочки", false);
        water = bool("Воду", false);
        bubbles = bool("Пузыри", false);
        border = bool("Границу", false);
        inst = this;
    }

    public static boolean blocks() { return inst != null && inst.on() && inst.blocks.value; }
    public static boolean entities() { return inst != null && inst.on() && inst.entities.value; }
    public static boolean water() { return inst != null && inst.on() && inst.water.value; }
}
