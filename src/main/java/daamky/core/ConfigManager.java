package daamky.core;

import com.google.gson.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import daamky.gui.Modules;
import net.fabricmc.loader.api.FabricLoader;





public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private ConfigManager() {}

    private static Path auto() { return FabricLoader.getInstance().getConfigDir().resolve("daamky.json"); }
    private static Path dir() { return FabricLoader.getInstance().getConfigDir().resolve("daamky"); }
    private static Path slot(String name) { return dir().resolve(name + ".json"); }

    public static void save() { write(auto()); }
    public static void load() { read(auto()); }

    public static boolean saveSlot(String name) {
        try { Files.createDirectories(dir()); } catch (Exception e) { return false; }
        return write(slot(name));
    }
    public static boolean loadSlot(String name) { return Files.exists(slot(name)) && read(slot(name)); }
    public static boolean deleteSlot(String name) { try { return Files.deleteIfExists(slot(name)); } catch (Exception e) { return false; } }
    public static boolean exists(String name) { return Files.exists(slot(name)); }

    public static List<String> slots() {
        List<String> out = new ArrayList<>();
        try (var s = Files.list(dir())) {
            s.forEach(p -> { String n = p.getFileName().toString(); if (n.endsWith(".json")) out.add(n.substring(0, n.length() - 5)); });
        } catch (Exception ignored) {}
        return out;
    }

    private static boolean write(Path file) {
        try {
            JsonObject root = new JsonObject();
            for (Modules.Mod m : Modules.ALL) {
                JsonObject o = new JsonObject();
                o.addProperty("enabled", m.enabled);
                o.addProperty("key", m.key);
                JsonObject s = new JsonObject();
                List<Modules.Setting> flat = m.flat();
                for (int i = 0; i < flat.size(); i++) {
                    Modules.Setting st = flat.get(i);
                    String k = i + ":" + st.name;
                    if (st instanceof Modules.Group g) s.addProperty(k, g.open);
                    else if (st instanceof Modules.Bool b) s.addProperty(k, b.value);
                    else if (st instanceof Modules.Mode md) s.addProperty(k, md.index);
                    else if (st instanceof Modules.Slider sl) s.addProperty(k, sl.value);
                }
                o.add("settings", s);
                root.add(m.name, o);
            }
            Files.writeString(file, GSON.toJson(root));
            return true;
        } catch (Exception e) {
            System.err.println("[daamky] config save failed: " + e);
            return false;
        }
    }

    private static boolean read(Path f) {
        try {
            if (!Files.exists(f)) return false;
            JsonObject root = JsonParser.parseString(Files.readString(f)).getAsJsonObject();
            for (Modules.Mod m : Modules.ALL) {
                if (!root.has(m.name) || m.category >= 5 && m.name.equals("Configs")) continue;
                JsonObject o = root.getAsJsonObject(m.name);
                if (o.has("enabled")) m.enabled = o.get("enabled").getAsBoolean();
                if (o.has("key")) m.key = o.get("key").getAsInt();
                if (!o.has("settings")) continue;
                JsonObject s = o.getAsJsonObject("settings");
                List<Modules.Setting> flat = m.flat();
                for (int i = 0; i < flat.size(); i++) {
                    Modules.Setting st = flat.get(i);
                    String k = i + ":" + st.name;
                    if (!s.has(k)) continue;
                    if (st instanceof Modules.Group g) g.open = s.get(k).getAsBoolean();
                    else if (st instanceof Modules.Bool b) b.set(s.get(k).getAsBoolean());
                    else if (st instanceof Modules.Mode md) md.select(s.get(k).getAsInt());
                    else if (st instanceof Modules.Slider sl) sl.set(s.get(k).getAsFloat());
                }
            }
            ModuleManager.syncSoundState();
            return true;
        } catch (Exception e) {
            System.err.println("[daamky] config load failed: " + e);
            return false;
        }
    }
}
