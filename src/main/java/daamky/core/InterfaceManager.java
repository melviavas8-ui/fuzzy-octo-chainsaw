package daamky.core;

import daamky.gui.DaamkyClickGui;
import daamky.gui.CelestialLegacyClickGui;
import daamky.render.Theme;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

/** Owns the selectable Daamky/legacy interface and its local chat command. */
public final class InterfaceManager {
    public static final int CELESTIAL_LEGACY = 1;
    public static final int DAAMKY = 2;

    private static int mode = DAAMKY;
    private static boolean initialized;

    private InterfaceManager() {}

    public static void init() {
        if (initialized) return;
        initialized = true;
        load();
        applyTheme();
        ClientSendMessageEvents.ALLOW_CHAT.register(InterfaceManager::onChatMessage);
    }

    public static int mode() { return mode; }
    public static boolean isLegacy() { return mode == CELESTIAL_LEGACY; }

    public static Screen createClickGui() {
        return isLegacy() ? new CelestialLegacyClickGui() : new DaamkyClickGui();
    }

    public static void setMode(int next) {
        if (next != CELESTIAL_LEGACY && next != DAAMKY) return;
        mode = next;
        applyTheme();
        save();
    }

    private static void applyTheme() {
        Theme.useLegacy(isLegacy());
    }

    private static boolean onChatMessage(String message) {
        if (message == null) return true;
        String input = message.trim();
        String lower = input.toLowerCase(Locale.ROOT);
        if (!lower.equals(".itest") && !lower.startsWith(".itest ")) return true;

        MinecraftClient.getInstance().execute(() -> execute(input));
        return false;
    }

    private static void execute(String input) {
        String[] args = input.trim().split("\\s+");
        if (args.length == 3 && args[1].equalsIgnoreCase("set")) {
            if (args[2].equals("1")) {
                setMode(CELESTIAL_LEGACY);
                notifyUser("Interface Test: Celestial Legacy включён.");
                return;
            }
            if (args[2].equals("2")) {
                setMode(DAAMKY);
                notifyUser("Interface Test: Daamky Interface включён.");
                return;
            }
        }

        notifyUser("Использование: .itest set 1 — Legacy; .itest set 2 — Daamky.");
    }

    private static void notifyUser(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.inGameHud != null) {
            client.inGameHud.getChatHud().addMessage(Text.literal("[Daamky] " + message));
        }
    }

    private static Path file() {
        return net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir()
                .resolve("daamky").resolve("interface.txt");
    }

    private static void load() {
        try {
            Path file = file();
            if (!Files.exists(file)) return;
            String value = Files.readString(file).trim();
            if (value.equals("1") || value.equals("2")) mode = Integer.parseInt(value);
        } catch (Exception e) {
            System.err.println("[daamky] could not load interface preference: " + e.getMessage());
            mode = DAAMKY;
        }
    }

    private static void save() {
        try {
            Path file = file();
            Files.createDirectories(file.getParent());
            Files.writeString(file, Integer.toString(mode));
        } catch (Exception e) {
            System.err.println("[daamky] could not save interface preference: " + e.getMessage());
        }
    }
}
