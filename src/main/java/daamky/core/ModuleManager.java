package daamky.core;

import java.util.ArrayList;
import java.util.List;
import daamky.gui.Modules;
import daamky.modules.movement.*;
import daamky.modules.player.*;
import daamky.modules.render.JumpCircles;
import daamky.modules.combat.AttackAura;
import daamky.modules.themes.ClickGuiTheme;
import daamky.modules.configs.Configs;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public final class ModuleManager {
    public static final List<Module> MODULES = new ArrayList<>();
    private static int saveTimer, warm;

    private static final java.util.Map<Modules.Mod, Boolean> SOUND_STATE = new java.util.IdentityHashMap<>();

    private ModuleManager() {}

    public static void init() {

        register(new AttackAura());

        register(new AutoSprint(), new Speed(), new Strafe(), new Flight(), new WaterSpeed(), new Spider(),
                new BlocksLift(), new Timer(), new NoSlow());

        register(new AutoRespawn(), new AutoTool(), new AutoLeave(), new NoPush());

        register(new JumpCircles());

        register(new ClickGuiTheme(), new Configs());

        ConfigManager.load();
        ClientTickEvents.START_CLIENT_TICK.register(ModuleManager::tick);
        ClientLifecycleEvents.CLIENT_STOPPING.register(mc -> ConfigManager.save());
    }


    private static void register(Module... ms) {
        for (Module m : ms) {
            Modules.ALL.removeIf(x -> x.name.equalsIgnoreCase(m.name));
            Modules.ALL.add(m);
            MODULES.add(m);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T get(Class<T> c) {
        for (Module m : MODULES) if (m.getClass() == c) return (T) m;
        return null;
    }


    private static void tickSounds() {
        for (Modules.Mod m : Modules.ALL) {
            Boolean last = SOUND_STATE.put(m, m.enabled);
            if (last != null && last != m.enabled && warm > 40) Sounds.play(m.enabled ? Sounds.ENABLE : Sounds.DISABLE, 0.6f);
        }
    }


    public static void syncSoundState() {
        for (Modules.Mod m : Modules.ALL) SOUND_STATE.put(m, m.enabled);
    }

    private static void tick(MinecraftClient mc) {
        for (Module m : MODULES) {
            try {
                if (m.enabled != m.prev) {
                    m.prev = m.enabled;
                    if (m.enabled) m.onEnable(); else m.onDisable();
                }
                if (m.enabled && mc.player != null && mc.world != null) m.onTick();
            } catch (Throwable t) {
                System.err.println("[daamky] module " + m.name + " failed: " + t);
            }
        }
        tickSounds();
        if (mc.player != null) warm++; else warm = 0;
        if (++saveTimer >= 1200) { saveTimer = 0; ConfigManager.save(); }
    }
}
