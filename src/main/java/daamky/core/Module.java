package daamky.core;

import daamky.gui.Modules;
import net.minecraft.client.MinecraftClient;






public abstract class Module extends Modules.Mod {
    public static final int COMBAT = 0, MOVEMENT = 1, RENDER = 2, PLAYER = 3, MISC = 4, CONFIGS = 5, THEMES = 6;
    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    boolean prev;


    private final Modules.Mod proto;
    private final java.util.Set<Modules.Setting> used = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());

    protected Module(String name, String desc, int category) {
        super(name, desc, category, false);
        Modules.Mod p = null;
        for (Modules.Mod m : Modules.ALL) if (m != this && m.name.equalsIgnoreCase(name)) { p = m; break; }
        proto = p;
        if (p != null) settings.addAll(p.settings);
    }


    @SuppressWarnings("unchecked")
    private <S extends Modules.Setting> S reuse(Class<S> c, String n, java.util.function.Predicate<S> ok) {
        if (proto == null) return null;
        for (Modules.Setting s : proto.flat())
            if (c.isInstance(s) && s.name.equals(n) && !used.contains(s) && ok.test((S) s)) { used.add(s); return (S) s; }
        return null;
    }


    private <S extends Modules.Setting> S place(S mine) {
        if (proto != null) for (Modules.Setting s : proto.flat())
            if (s.getClass() == mine.getClass() && s.name.equals(mine.name) && !used.contains(s) && swap(settings, s, mine)) { used.add(s); return mine; }
        return add(mine);
    }

    private static boolean swap(java.util.List<Modules.Setting> list, Modules.Setting old, Modules.Setting nu) {
        for (int i = 0; i < list.size(); i++) {
            Modules.Setting s = list.get(i);
            if (s == old) { list.set(i, nu); return true; }
            if (s instanceof Modules.Group g && swap(g.children, old, nu)) return true;
        }
        return false;
    }

    protected <S extends Modules.Setting> S add(S s) { settings.add(s); return s; }

    protected Modules.Section section(String n) { return proto != null ? new Modules.Section(n) : add(new Modules.Section(n)); }
    protected Modules.Bool bool(String n, boolean v) {
        Modules.Bool b = reuse(Modules.Bool.class, n, x -> true);
        return b != null ? b : place(new Modules.Bool(n, v));
    }
    protected Modules.Mode mode(String n, int i, String... m) {
        Modules.Mode md = reuse(Modules.Mode.class, n, x -> java.util.Arrays.equals(x.modes, m));
        return md != null ? md : place(new Modules.Mode(n, i, m));
    }
    protected Modules.Slider slider(String n, float v, float min, float max, float step, String suffix) {
        Modules.Slider sl = reuse(Modules.Slider.class, n, x -> true);
        return sl != null ? sl : place(new Modules.Slider(n, v, min, max, step, suffix));
    }

    protected void onEnable() {}
    protected void onDisable() {}

    protected void onTick() {}

    public final boolean on() { return enabled && mc.player != null && mc.world != null; }
}
