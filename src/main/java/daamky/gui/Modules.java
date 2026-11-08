package daamky.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;






public final class Modules {




    public static abstract class Setting {
        public final String name;
        private BooleanSupplier visible = () -> true;
        private final List<Runnable> listeners = new ArrayList<>(1);
        Setting(String name) { this.name = name; }

        public boolean isVisible() { return visible.getAsBoolean(); }


        @SuppressWarnings("unchecked")
        public <T extends Setting> T showIf(BooleanSupplier cond) { this.visible = cond; return (T) this; }


        @SuppressWarnings("unchecked")
        public <T extends Setting> T showWhen(Mode m, String... values) {
            this.visible = () -> {
                for (String v : values) if (m.get().equals(v)) return true;
                return false;
            };
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public <T extends Setting> T onChange(Runnable r) { listeners.add(r); return (T) this; }
        protected void changed() { for (Runnable r : listeners) r.run(); }
    }

    public static final class Section extends Setting {
        public Section(String name) { super(name); }
    }

    public static final class Bool extends Setting {
        public boolean value;
        public Bool(String name, boolean v) { super(name); value = v; }
        public void set(boolean v) { if (v != value) { value = v; changed(); } }

        public boolean on() { return value && isVisible(); }
    }

    public static final class Mode extends Setting {
        public final String[] modes;
        public int index;

        public boolean open;
        public Mode(String name, int i, String... modes) { super(name); this.modes = modes; this.index = i; }
        public String get() { return modes[index]; }
        public void select(int i) { i = Math.max(0, Math.min(modes.length - 1, i)); if (i != index) { index = i; changed(); } }
        public void select(String m) { for (int i = 0; i < modes.length; i++) if (modes[i].equals(m)) { select(i); return; } }
        public void next() { select((index + 1) % modes.length); }
        public void prev() { select((index + modes.length - 1) % modes.length); }
    }


    public static final class Slider extends Setting {
        public final float min, max, step;
        public final String suffix;
        public float value;
        public Slider(String name, float v, float min, float max, float step, String suffix) {
            super(name); this.min = min; this.max = max; this.step = step; this.suffix = suffix; this.value = v;
        }

        public void set(float v) {
            v = Math.round((v - min) / step) * step + min;
            v = Math.max(min, Math.min(max, v));
            if (v != value) { value = v; changed(); }
        }
        public float frac() { return (value - min) / (max - min); }
        public String text() {
            String n = step >= 1f ? String.valueOf(Math.round(value))
                    : String.format(java.util.Locale.ROOT, step >= 0.1f ? "%.1f" : "%.2f", value);
            return n + suffix;
        }
    }


    public static final class Group extends Setting {
        public final List<Setting> children = new ArrayList<>();
        public boolean open = true;
        public Group(String name, Setting... kids) { super(name); children.addAll(List.of(kids)); }
        @Override public boolean isVisible() {
            if (!super.isVisible()) return false;
            for (Setting c : children) if (c.isVisible()) return true;
            return children.isEmpty();
        }
    }


    public static final class Button extends Setting {
        public final Runnable action;
        public Button(String name, Runnable action) { super(name); this.action = action; }
        public void press() { action.run(); changed(); }
    }


    public static List<Setting> rows(Mod m) {
        List<Setting> out = new ArrayList<>();
        collect(m.settings, out);
        return out;
    }
    private static void collect(List<Setting> in, List<Setting> out) {
        for (Setting s : in) {
            if (!s.isVisible()) continue;
            out.add(s);
            if (s instanceof Group g && g.open) collect(g.children, out);
        }
    }

    public static List<Setting> flat(Mod m) {
        List<Setting> out = new ArrayList<>();
        flatten(m.settings, out);
        return out;
    }
    private static void flatten(List<Setting> in, List<Setting> out) {
        for (Setting s : in) { out.add(s); if (s instanceof Group g) flatten(g.children, out); }
    }

    public static class Mod {
        public final String name, desc;
        public final int category;
        public boolean enabled;

        public int key = -1;
        public final List<Setting> settings = new ArrayList<>();
        protected Mod(String name, String desc, int category, boolean on) {
            this.name = name; this.desc = desc; this.category = category; this.enabled = on;
        }
        public boolean hasSettings() { return !settings.isEmpty(); }
        public List<Setting> flat() { return Modules.flat(this); }
    }

    public static final List<Mod> ALL = new ArrayList<>();
    public static Mod TARGET_ESP;
    public static final Slider SOUL_SIZE = new Slider("Размер", 1.0f, 0.5f, 2.0f, 0.1f, "x");
    public static final Slider SOUL_SPEED = new Slider("Скорость", 1.0f, 0.3f, 3.0f, 0.1f, "x");


    public static final Bool WM_ON = new Bool("Включено", true);
    public static final Bool WM_LOWER = new Bool("Маленькие буквы", false);
    public static final Slider WM_SIZE = new Slider("Размер текста", 8, 6, 14, 1, "");
    public static final Bool ARR_ON = new Bool("Включено", true);
    public static final Bool ARR_GLOW = new Bool("Свечение", true);
    public static final Bool ARR_LOWER = new Bool("Маленькие буквы", false);
    public static final Bool SV_ON = new Bool("Включено", true);
    public static final Bool TH_ON = new Bool("Включено", true);
    public static final Bool TH_TEST = new Bool("Тестовый режим", false);
    public static final Bool PO_ON = new Bool("Включено", true);
    public static final Bool CD_ON = new Bool("Включено", true);
    public static final Bool KB_ON = new Bool("Включено", true);
    public static final Mode ENGINE = new Mode("Режим", 0, "MSDF", "SDF");
    public static final Mode WEIGHT = new Mode("Толщина", 1, "Тонкий", "Обычный", "Жирный");
    public static final Mode DEMO_SIZE = new Mode("Размер", 1, "8", "10", "14", "20");

    public static final Mod HUD = add(2, "HUD", "Watermark, список модулей, Target HUD и панели", true);
    public static final Mod TEXT = add(2, "Text Engine", "Режим рендера и толщина шрифта", true);
    public static final Mod DEMO = add(2, "Font Demo", "Тест кириллицы и размеров текста", false);

    static {
        HUD.settings.add(new Section("Watermark"));
        HUD.settings.add(WM_ON);
        HUD.settings.add(WM_LOWER);
        HUD.settings.add(WM_SIZE);
        HUD.settings.add(new Section("Список модулей"));
        HUD.settings.add(ARR_ON);
        HUD.settings.add(ARR_GLOW);
        HUD.settings.add(ARR_LOWER);
        HUD.settings.add(new Section("Target HUD"));
        HUD.settings.add(TH_ON);
        HUD.settings.add(TH_TEST);
        HUD.settings.add(new Section("Staff Viewer"));
        HUD.settings.add(SV_ON);
        HUD.settings.add(new Section("Potions"));
        HUD.settings.add(PO_ON);
        HUD.settings.add(new Section("Cooldowns"));
        HUD.settings.add(CD_ON);
        HUD.settings.add(new Section("Keybinds"));
        HUD.settings.add(KB_ON);
        TEXT.settings.add(new Section("Рендер"));
        TEXT.settings.add(ENGINE);
        TEXT.settings.add(WEIGHT);
        DEMO.settings.add(new Section("Тест"));
        DEMO.settings.add(DEMO_SIZE);


        Mod waterSpeed = add(1, "Water Speed", "Увеличивает скорость в воде", true);
        Mod autoSprint = add(1, "Auto Sprint", "Автоматически включает спринт", true);
        Mod blocksLift = add(1, "Blocks Lift", "Позволяет подниматься в блоках", true);
        Mod noSlow = add(1, "No Slow", "Убирает замедление при использовании предмета", true);
        Mod spider = add(1, "Spider", "Позволяет взбираться по стенам", false);
        add(1, "Strafe", "Позволяет не замедляться при движении в стороны", true);
        Mod speed = add(1, "Speed", "Увеличивает скорость игрока", true);
        Mod timer = add(1, "Timer", "Ускоряет время игры", false);
        Mod flight = add(1, "Flight", "Позволяет летать без креатив режима", true);

        cfg(waterSpeed, new Mode("Режим", 0, "Ванильный", "Dolphin", "Matrix"), new Slider("Скорость", 1.2f, 1f, 3f, 0.1f, "x"));
        cfg(autoSprint, new Bool("Только вперёд", true), new Bool("Сохранять спринт", false));
        cfg(blocksLift, new Mode("Режим", 0, "Обычный", "Быстрый"), new Bool("Только с блоком", true));
        cfg(noSlow, new Mode("Режим", 0, "Ванильный", "Grim", "Reset"), new Bool("Еда", true), new Bool("Щит", true));
        cfg(spider, new Mode("Режим", 0, "Обычный", "Быстрый", "Grim"), new Slider("Скорость", 0.4f, 0.1f, 2f, 0.1f, ""));
        cfg(speed, new Mode("Режим", 0, "Ванильный", "Grim", "Matrix"), new Slider("Скорость", 1.5f, 0.5f, 5f, 0.1f, "x"), new Bool("Авто-прыжок", true));
        cfg(timer, new Mode("Режим", 0, "Обычный", "Адаптивный"), new Slider("Множитель", 1.5f, 0.5f, 5f, 0.1f, "x"), new Bool("Только в движении", false));
        cfg(flight, new Mode("Режим", 0, "Ванильный", "Glide", "Motion"), new Slider("Скорость", 1f, 0.1f, 5f, 0.1f, ""), new Bool("Анти-кик", true));


        Mod aura = add(0, "Attack Aura", "Автоматически атакует ближайшие цели", true);
        aura.settings.add(new Section("Цели"));
        aura.settings.add(new Mode("Выбор", 0, "Голые игроки", "Игроки", "Мобы", "Все"));
        aura.settings.add(new Mode("Сортировка", 0, "Умная", "Дистанция", "Здоровье", "Угол"));
        aura.settings.add(new Section("Дистанция"));
        aura.settings.add(new Slider("Атака", 3.0f, 2f, 6f, 0.1f, " бл."));
        aura.settings.add(new Slider("Поиск", 4.5f, 3f, 8f, 0.1f, " бл."));
        aura.settings.add(new Section("Обходы"));
        aura.settings.add(new Mode("Режим", 0, "Funtime", "Matrix", "Grim", "Vanilla"));
        aura.settings.add(new Mode("Обход спринта", 0, "Funtime", "Legit", "Выкл"));
        aura.settings.add(new Section("Коррекция движения"));
        aura.settings.add(new Mode("Режим", 0, "Незаметный", "Свободный", "Выкл"));
        aura.settings.add(new Bool("Таргетить если у цели лук, арбалет или трезубец", false));
        aura.settings.add(new Bool("Бить через стены", false));
        aura.settings.add(new Bool("Только критами", true));
        aura.settings.add(new Section("Ротация"));
        aura.settings.add(new Mode("Тип", 0, "Плавная", "Резкая", "Снап"));
        aura.settings.add(new Bool("Не бить при использовании предмета", true));

        cfg(add(0, "Auto Explosion", "Автоматически взрывает кристаллы после установки", true), new Bool("Только свои", false));
        cfg(add(0, "Friend Protect", "Не наносит урон друзьям", true), new Bool("Лучшие друзья", true));
        cfg(add(0, "Auto GApple", "Автоматически ест яблоко при определённом здоровье", true), new Slider("Здоровье", 8, 1, 20, 1, " HP"));
        cfg(add(0, "Auto Totem", "Автоматически берёт тотем в руку", true), new Bool("Только при низком HP", true));
        cfg(add(0, "Tape Mouse", "Автоматически нажимает левую или правую кнопку мыши", false), new Mode("Кнопка", 0, "Левая", "Правая"));
        cfg(add(0, "Aim Assist", "Отводит прицел от окружающих целей", true), new Slider("Сила", 50, 0, 100, 1, "%"), new Slider("Радиус", 4f, 1f, 8f, 0.5f, " бл."), new Bool("Только в бою", true));
        cfg(add(0, "Keep Sprint", "Не дает сбросить спринт при ударе", false), new Bool("Только в движении", true));
        cfg(add(0, "No Entity Trace", "Позволяет бить сквозь существо на линии прицела", false), new Bool("Только мобы", true));
        cfg(add(0, "Auto Swap", "Автоматически меняет предмет в правой руке", false), new Mode("Предмет", 0, "Щит", "Тотем", "Яблоко"));
        cfg(add(0, "No Hit Delay", "Убирает задержки при ударе", false), new Mode("Режим", 0, "Обычный", "Быстрый"));
        cfg(add(0, "Super Bow", "Увеличивает урон с лука", false), new Mode("Сила", 0, "Малая", "Средняя", "Большая"));
        cfg(add(0, "Bow Spam", "Позволяет быстро стрелять из лука", false), new Slider("Задержка", 50, 0, 200, 10, " мс"));
        cfg(add(0, "Fast Swap", "Меняет предмет в основной руке", false), new Bool("Быстрый режим", true));
        cfg(add(0, "Auto Pot", "Автоматически кидает зелья", false), new Bool("Только при низком HP", true));
        cfg(add(0, "Velocity", "Уменьшает получаемое отбрасывание", true), new Mode("Режим", 0, "Обычный", "Grim"));
        cfg(add(0, "Criticals", "Наносит критический урон при каждом ударе", false), new Mode("Режим", 0, "Прыжок", "Пакеты"));


        TARGET_ESP = add(2, "Target ESP", "Души вокруг цели под прицелом", true);
        cfg(TARGET_ESP, SOUL_SIZE, SOUL_SPEED);
        cfg(add(2, "ESP", "Подсвечивает игроков сквозь стены", true), new Mode("Стиль", 0, "Рамка", "Контур", "Заливка"));
        add(2, "Full Bright", "Максимальная яркость без зелья", true);
        cfg(add(2, "Nametags", "Показывает увеличенные ники игроков", true), new Bool("Здоровье", true), new Bool("Броня", true));
        cfg(add(2, "Tracers", "Рисует линии к игрокам", false), new Bool("Только игроки", true));
        add(2, "No Render", "Отключает лишние эффекты и частицы", false);


        cfg(add(3, "Auto Tool", "Выбирает лучший инструмент для блока", true), new Bool("Возвращать слот", true));
        add(3, "Fast Place", "Убирает задержку при установке блоков", false);
        cfg(add(3, "No Fall", "Убирает урон от падения", true), new Mode("Режим", 0, "Пакеты", "Земля"));
        add(3, "Auto Respawn", "Автоматически возрождает после смерти", true);
        cfg(add(3, "Chest Stealer", "Быстро забирает предметы из сундуков", false), new Slider("Задержка", 50, 0, 200, 10, " мс"));
        add(3, "Inventory Move", "Позволяет двигаться с открытым инвентарём", true);


        cfg(add(4, "Auto Fish", "Автоматически ловит рыбу", false), new Bool("Авто-заброс", true));
        add(4, "Auto Armor", "Автоматически надевает лучшую броню", true);
        add(4, "Auto Leave", "Выходит с сервера при низком здоровье", false);
        cfg(add(4, "Pearl Click", "Бросает жемчуг средней кнопкой мыши", true), new Bool("Возвращать слот", true));
        add(4, "Anti AFK", "Не даёт кикнуть за бездействие", false);
        add(4, "Auto Reconnect", "Переподключается после отключения", false);


        CelestialSettings.load();
    }


    private static void cfg(Mod m, Setting... s) {
        m.settings.add(new Section("Основное"));
        for (Setting x : s) m.settings.add(x);
    }


    static Mod addCard(int cat, String name, String desc) { return add(cat, name, desc, false); }

    private static Mod add(int cat, String name, String desc, boolean on) {
        Mod m = new Mod(name, desc, cat, on);
        ALL.add(m);
        return m;
    }

    public static boolean useMsdf() { return ENGINE.index == 0; }

    public static float weight() {
        return switch (WEIGHT.index) { case 0 -> 0f; case 1 -> 0.03f; default -> 0.07f; };
    }

    public static boolean demoOn() { return DEMO.enabled; }
    public static float demoSize() { return Float.parseFloat(DEMO_SIZE.get()); }
    public static boolean wmOn() { return HUD.enabled && WM_ON.value; }
}
