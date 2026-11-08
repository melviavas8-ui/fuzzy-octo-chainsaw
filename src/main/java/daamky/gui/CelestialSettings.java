package daamky.gui;

import java.util.ArrayList;
import java.util.List;
import daamky.gui.Modules.*;







final class CelestialSettings {
    private CelestialSettings() {}


    private static final java.util.Set<String> KEEP = java.util.Set.of("hud", "text engine", "font demo", "target esp");


    static final class B {
        final Mod mod;
        B(Mod mod) { this.mod = mod; }
        Mode mode(String n, int i, String... m) { return new Mode(n, i, m); }
        Bool bool(String n, boolean v) { return new Bool(n, v); }
        Slider slider(String n, float v, float min, float max, float step) { return new Slider(n, v, min, max, step, ""); }
        Group group(String n, Setting... kids) { return new Group(n, kids); }
        Button button(String n, Runnable r) { return new Button(n, r); }
        void show(Setting... s) { mod.settings.clear(); mod.settings.addAll(List.of(s)); }
    }

    private static B mod(int cat, String name, String desc) {
        for (Mod m : Modules.ALL) {
            if (m.name.equalsIgnoreCase(name)) {
                if (KEEP.contains(name.toLowerCase())) return new B(new Mod(name, desc, cat, false) {});
                m.settings.clear();
                return new B(m);
            }
        }
        Mod m = Modules.addCard(cat, name, desc);
        return new B(m);
    }

    static void load() {
        m0();
        m1();
        m2();
        m3();
        m4();
        m5();
        m6();
        m7();
        m8();
        m9();
        m10();
        m11();
        m12();
        m13();
        m14();
        m15();
        m16();
        m17();
        m18();
        m19();
        m20();
        m21();
        m22();
        m23();
        m24();
        m25();
        m26();
        m27();
        m28();
        m29();
        m30();
        m31();
        m32();
        m33();
        m34();
        m35();
        m36();
        m37();
        m38();
        m39();
        m40();
        m41();
        m42();
        m43();
        m44();
        m45();
        m46();
        m47();
        m48();
        m49();
        m50();
        m51();
        m52();
        m53();
        m54();
        m55();
        m56();
        m57();
        m58();
        m59();
        m60();
        m61();
        m62();
        m63();
        m64();
        m65();
        m66();
        m67();
        m68();
        m69();
        m70();
        m71();
        m72();
        m73();
        m74();
        m75();
        m76();
        m77();
        m78();
        m79();
        m80();
        m81();
        m82();
        m83();
        m84();
        m85();
        m86();
        m87();
        m88();
        m89();
        m90();
        m91();
        m92();
        m93();
        m94();
        m95();
        m96();
        m97();
    }

    private static void m0() {

        var b = mod(0, "Anti Bot", "Детектит НПС/ботов от античита");
        var s1 = b.mode("Режим", 0, "Matrix", "ReallyWorld");
        b.show(s1);
    }

    private static void m1() {

        var b = mod(0, "Attack Aura", "Автоматически бьет окружающих вас существ");

        var s1 = b.mode("Сортировка", 0, "Умная", "Поле зрения", "Дистанция", "Здоровье");
        var s2 = b.mode("Режим", 0, "Funtime", "Matrix", "Snap", "SpookyTime");
        var s3 = b.mode("Обход спринта", 0, "Funtime", "Legit", "Snap", "SpookyTime");
        var s4 = b.mode("Режим", 0, "Незаметный", "Строгий", "Таргет", "Свободный");
        var s5 = b.bool("Таргетить если у цели лук, арбалет или трезубец", false);
        var s6 = b.bool("Заходить за спину цели", true);
        var s7 = b.slider("Дистанция за спиной", 1.0f, 0.5f, 2.5f, 0.1f);
        var s8 = b.slider("Дистанция удара", 3.0f, 1.0f, 6.0f, 0.1f);
        var s9 = b.slider("Дистанция наводки", 0.0f, 0.0f, 32.0f, 0.5f);
        var s10 = b.bool("Только криты", true);
        var s11 = b.bool("Бить через стены", true);
        var s12 = b.bool("Ломать щит", true);
        var s13 = b.bool("Бить через щит", true);
        var s14 = b.bool("Останавливать при использовании предмета", false);
        var s15 = b.bool("Останавливать при открытом контейнере", false);
        var s16 = b.bool("Только с пробелом", false);

        var s17 = b.bool("Дистанция под Grim", false);
        var s18 = b.bool("Убирать интерполяцию у энтити", false);
        var s19 = b.bool("Убирать взаимодействие с блоками и энтити", true);
        var s20 = b.group("Другие", s17, s18, s19);
        var s21 = b.group("Визуалы");
        var s22 = b.group("Криты", s16);
        var s23 = b.group("Удары", s10, s11, s12, s13, s14, s15);
        var s24 = b.group("Дистанции", s8, s9);
        var s25 = b.group("Коррекция движения", s4, s5, s6, s7);
        var s26 = b.group("Обходы", s2, s3);
        var s27 = b.group("Цели", s1);



        s7.showIf(() -> s6.on());
        s15.showIf(() -> s10.on());
        s16.showIf(() -> s10.on());


        b.show(s27, s26, s25, s24, s23, s22, s21, s20);
    }

    private static void m2() {

        var b = mod(0, "Auto Explosion", "Автоматически взрывает кристалл после установки обсидиана");
        var s1 = b.bool("Смотреть на кристалл", true);
        var s2 = b.bool("Коррекция движения", true);
        s2.showIf(() -> s1.on());
        b.show(s1, s2);
    }

    private static void m3() {

        var b = mod(0, "Auto GApple", "Автоматически ест золотое яблоко при определённом здоровье");
        var s1 = b.slider("Здоровье", 15.0f, 5.0f, 20.0f, 0.5f);
        b.show(s1);
    }

    private static void m4() {

        var b = mod(0, "Auto Pot", "Автоматически кидает зелья");
        var s1 = b.bool("Только в ПВП", false);
        var s2 = b.bool("Авто. выключение", false);
        var s3 = b.bool("Силу", true);
        var s4 = b.bool("Скорость", true);
        var s5 = b.bool("Защиту от огня", true);
        b.show(s1, s2, s3, s4, s5);
    }

    private static void m5() {

        var b = mod(0, "Auto Swap", "Автоматически меняет предмет в правой руке");
        var s1 = b.mode("Режим", 0, "Умный", "Бинд");
        var s2 = b.mode("Менять с", 0, "Щит", "Гепл", "Шар", "Руна", "Сфера", "Тотем");
        var s3 = b.mode("Менять на", 0, "Гепл", "Щит", "Шар", "Руна", "Сфера", "Тотем");

        var s4 = b.slider("Здоровье", 16.5f, 1.0f, 19.5f, 0.5f);
        var s5 = b.bool("Проверять кристалл", true);





        b.show(s1, s2, s3, s4, s5);
    }

    private static void m6() {

        var b = mod(0, "Auto Totem", "Автоматически берет тотем в руку");
        var s1 = b.bool("Сохранять зачарованные", true);
        var s2 = b.bool("Не менять шар", true);
        var s3 = b.slider("Здоровье", 5.0f, 1.0f, 20.0f, 0.5f);
        var s4 = b.bool("Счетчик тотемов", true);
        var s5 = b.bool("Кристалл", true);
        var s6 = b.slider("Радиус до кристалла", 0.0f, 0.0f, 0.0f, 0.0f);
        var s7 = b.bool("Обсидиан", false);
        var s8 = b.slider("Радиус до обсидиана", 0.0f, 0.0f, 0.0f, 0.0f);
        var s9 = b.bool("Якорь", false);
        var s10 = b.slider("Радиус до якоря", 0.0f, 0.0f, 0.0f, 0.0f);
        var s11 = b.bool("Динамит", false);
        var s12 = b.slider("Радиус до динамита", 0.0f, 0.0f, 0.0f, 0.0f);
        var s13 = b.bool("Падение", false);
        var s14 = b.slider("Дальность падения", 0.0f, 0.0f, 0.0f, 0.0f);
        b.show(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14);
    }

    private static void m7() {

        var b = mod(0, "Bow Spam", "Позволяет быстро стрелять из лука");
        var s1 = b.slider("Задержка", 2.0f, 2.0f, 5.0f, 1.0f);
        b.show(s1);
    }

    private static void m8() {

        var b = mod(0, "Criticals", "Позволяет всегда бить критическами ударами");

    }

    private static void m9() {

        var b = mod(0, "Fast Swap", "Меняет предмет в основной руке");

    }

    private static void m10() {

        var b = mod(0, "Friend Protect", "Отменяет урон по друзьям");

    }

    private static void m11() {

        var b = mod(0, "Hit Sounds", "Воспроизводит звуки при ударе");
        var s1 = b.mode("Выбор звука", 0, "UwU", "Бонк", "Стоны", "Маркер");
        var s2 = b.slider("Питч звука", 1.0f, 0.1f, 2.0f, 0.1f);
        b.show(s1, s2);
    }

    private static void m12() {

        var b = mod(0, "Hitboxes", "Увеличивает хитбокс у энтити");

        var s1 = b.slider("Размер", 0.2f, 0.1f, 2.0f, 0.1f);
        b.show(s1);
    }

    private static void m13() {

        var b = mod(0, "Keep Sprint", "Не дает сбросить спринт при ударе");
        var s1 = b.mode("Режим", 0, "Vanilla", "SpookyTime");
        b.show(s1);
    }

    private static void m14() {

        var b = mod(0, "Popka", ":D");

    }

    private static void m15() {

        var b = mod(0, "Super Bow", "Увеличивает урон с лука");
        var s1 = b.slider("Сила выстрела", 50.0f, 10.0f, 100.0f, 10.0f);
        b.show(s1);
    }

    private static void m16() {

        var b = mod(0, "Tape Mouse", "Автоматически нажимает левую или правую кнопку мыши");
        var s1 = b.mode("Режим", 0, "ЛКМ", "ПКМ");
        var s2 = b.slider("Задержка в секундах", 0.5f, 0.0f, 60.0f, 0.5f);
        b.show(s1, s2);
    }

    private static void m17() {

        var b = mod(0, "Trigger Bot", "Автоматически бьет существо если оно на прицеле");

        var s1 = b.mode("Обход спринта", 0, "Funtime", "Legit");

        var s2 = b.bool("Только криты", true);
        var s3 = b.bool("Останавливать при использовании предмета", false);
        var s4 = b.bool("Останавливать при открытом контейнере", false);
        var s5 = b.bool("Только с пробелом", false);
        var s6 = b.group("Криты", s5);
        var s7 = b.group("Удары", s2, s3, s4);
        var s8 = b.group("Визуалы");
        var s9 = b.group("Обходы", s1);
        var s10 = b.group("Цели");


        b.show(s10, s9, s8, s7, s6);
    }

    private static void m18() {

        var b = mod(0, "Velocity", "Редактирует ваше откидывание");
        var s1 = b.mode("Режим", 0, "Vanilla", "Custom", "Jump", "JumpReset");

        b.show(s1);
    }

    private static void m19() {

        var b = mod(1, "Auto Sprint", "Автоматически включает спринт");
        var s1 = b.bool("Голод", false);
        var s2 = b.bool("Слепоту", true);
        var s3 = b.bool("Приседание", true);
        var s4 = b.bool("Ползание", true);
        var s5 = b.bool("Использование предметов", true);
        var s6 = b.bool("Горизонтальную коллизию", true);
        var s7 = b.button("SpookyTime", () -> { s1.set(false); s2.set(true); s3.set(true); s4.set(true); s5.set(true); s6.set(true); });
        var s8 = b.button("Funtime", () -> { s1.set(false); s2.set(true); s3.set(true); s4.set(true); s5.set(true); s6.set(true); });
        var s9 = b.button("Grim", () -> { s1.set(false); s2.set(false); s3.set(false); s4.set(false); s5.set(false); s6.set(false); });
        var s10 = b.group("Игнорировать", s1, s2, s3, s4, s5, s6);
        var s11 = b.group("Авто. конфигурации", s7, s8, s9);
        b.show(s11, s10);
    }

    private static void m20() {

        var b = mod(1, "Blocks Lift", "Позволяет подниматься в блоках");
        var s1 = b.mode("Режим", 0, "Packet", "Glide", "Matrix");
        b.show(s1);
    }

    private static void m21() {

        var b = mod(1, "Flight", "Позволяет летать без креатив режима");
        var s1 = b.mode("Режим", 0, "Vanilla", "Levitation Boost", "Jump");
        var s2 = b.slider("Скорость", 2.0f, 0.5f, 10.0f, 0.1f);
        var s3 = b.slider("Скорость", 1.0f, 0.6f, 5.0f, 0.1f);
        s2.showWhen(s1, "Vanilla");
        s3.showWhen(s1, "Levitation Boost");
        b.show(s1, s2, s3);
    }

    private static void m22() {

        var b = mod(1, "No Slow", "Убирает замедление при использовании предмета");
        var s1 = b.mode("Режим", 0, "Funtime", "SpookyTime", "ReallyWorld", "Vanilla");
        b.show(s1);
    }

    private static void m23() {

        var b = mod(1, "Speed", "Увеличивает скорость игрока");
        var s1 = b.mode("Режим", 0, "Vanilla", "Collision", "Matrix", "Grim");

        b.show(s1);
    }

    private static void m24() {

        var b = mod(1, "Spider", "Позволяет взбираться по стенам");
        var s1 = b.mode("Режим", 0, "Grim", "Matrix", "Funtime");
        b.show(s1);
    }

    private static void m25() {

        var b = mod(1, "Strafe", "Позволяет не замедляться при движении в стороны");

    }

    private static void m26() {

        var b = mod(1, "Super Firework", "Увеличивает скорость при использовании фейерверка");
        var s1 = b.slider("Скорость по XZ", 1.6f, 1.5f, 2.0f, 0.01f);
        var s2 = b.slider("Скорость по Y", 1.6f, 1.5f, 2.0f, 0.01f);


        b.show(s1, s2);
    }

    private static void m27() {

        var b = mod(1, "Timer", "Ускоряет время игры");
        var s1 = b.slider("Скорость", 1.8f, 1.1f, 10.0f, 0.1f);
        var s2 = b.bool("Умный режим", true);
        var s3 = b.slider("Скорость убывания", 1.0f, 0.5f, 3.0f, 0.1f);
        var s4 = b.slider("Добавлять в движении", 0.0f, 0.0f, 1.0f, 0.05f);
        s3.showIf(() -> s2.on());
        s4.showIf(() -> s2.on());
        b.show(s1, s2, s3, s4);
    }

    private static void m28() {

        var b = mod(1, "Water Speed", "Увеличивает скорость в воде");
        var s1 = b.mode("Режим", 0, "Funtime", "Vulcan");
        b.show(s1);
    }

    private static void m29() {

        var b = mod(2, "2D ESP", "WallHack на сущностей");


        var s1 = b.slider("Ширина", 1.0f, 0.5f, 2.0f, 0.5f);

        b.show(s1);
    }

    private static void m30() {

        var b = mod(2, "Array List", "Отображает список включенных модулей");

        var s1 = b.bool("Маленькие буквы", false);
        var s2 = b.bool("Свечение", true);
        var s3 = b.slider("Смещение", 2.0f, 0.0f, 10.0f, 1.0f);

        b.show(s1, s2, s3);
    }

    private static void m31() {

        var b = mod(2, "Block ESP", "WallHack на блоки");
        var s1 = b.bool("Спавнера", true);
        var s2 = b.bool("Шалкеры", false);
        var s3 = b.bool("Сундуки", false);
        var s4 = b.bool("Бочки", false);
        var s5 = b.bool("Вагонетки", false);
        var s6 = b.bool("Диспенсеры", false);
        var s7 = b.bool("Подарки", false);







        b.show(s1, s2, s3, s4, s5, s6, s7);
    }

    private static void m32() {

        var b = mod(2, "China Hat", "Китайская шляпка над головой");

    }

    private static void m33() {

        var b = mod(2, "Crosshair", "Позволяет кастомизировать ваш прицел");

        var s1 = b.bool("Подсветка энтити", false);

        var s2 = b.bool("Точка по середине", false);
        var s3 = b.bool("Учитывать кулдаун", false);
        var s4 = b.slider("Радиус", 0.0f, 0.0f, 6.0f, 0.5f);
        var s5 = b.slider("Длина", 4.0f, 0.5f, 6.0f, 0.5f);
        b.show(s1, s2, s3, s4, s5);
    }

    private static void m34() {

        var b = mod(2, "HUD", "Пользовательский интерфейс");
        var s1 = b.bool("Включено", true);
        var s2 = b.bool("Фпс", true);
        var s3 = b.bool("Бпс", true);
        var s4 = b.bool("Тпс", true);
        var s5 = b.bool("Координаты", true);
        var s6 = b.bool("Включено", true);
        var s7 = b.bool("Включено", true);
        var s8 = b.bool("Включено", true);
        var s9 = b.bool("Включено", true);
        var s10 = b.bool("Включено", true);
        var s11 = b.bool("Включено", false);
        var s12 = b.bool("Оптимизация", false);
        var s13 = b.bool("Включено", true);
        var s14 = b.bool("Свечение", true);
        var s15 = b.bool("Маленькие буквы", false);
        var s16 = b.bool("Включено", true);
        var s17 = b.bool("Включено", true);
        var s18 = b.bool("Тестовый режим", false);
        b.show(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18);
    }

    private static void m35() {

        var b = mod(2, "Item Physics", "Реалистичная физика предметов");
        var s1 = b.slider("Скорость вращения", 5.0f, 2.0f, 7.0f, 0.1f);
        b.show(s1);
    }

    private static void m36() {

        var b = mod(2, "Items Overlay", "Отображает полезные данные о предметах в инвентаре");

    }

    private static void m37() {

        var b = mod(2, "Jump Circles", "Добавляет анимацию прыжка под вами");
        var s1 = b.slider("Радиус", 1.5f, 1.0f, 2.0f, 0.1f);
        var s2 = b.slider("Радиус свечения", 1.0f, 1.0f, 2.0f, 0.1f);
        var s3 = b.slider("Скорость", 0.3f, 0.3f, 1.0f, 0.1f);



        b.show(s1, s2, s3);
    }

    private static void m38() {

        var b = mod(2, "No Render", "Убирает некоторые элементы игры");
        var s1 = b.bool("Эффект тотема", true);
        var s2 = b.bool("Игроков", false);
        var s3 = b.bool("Заголовки", false);
        var s4 = b.bool("Босс бар", false);
        var s5 = b.bool("Скорборд", false);
        var s6 = b.bool("Лаву/Воду", false);

        b.show(s1, s2, s3, s4, s5, s6);
    }

    private static void m39() {

        var b = mod(2, "Notifications", "Откладка о каких либо действиях");
        var s1 = b.bool("Включено", true);
        var s2 = b.bool("Включено", false);
        var s3 = b.bool("Вас", false);
        var s4 = b.bool("Друзей", false);
        var s5 = b.bool("Включено", false);
        var s6 = b.bool("Включено", false);
        var s7 = b.bool("Включено", false);
        var s8 = b.bool("Включено", false);
        var s9 = b.bool("Уведомлять об окончании важных эффектов", true);
        var s10 = b.bool("Воспроизводить звук", true);
        var s11 = b.slider("Питч звука", 1.0f, 0.1f, 2.0f, 0.1f);
        b.show(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11);
    }

    private static void m40() {

        var b = mod(2, "Pointers", "Рисует стрелки к сущностям");


        var s1 = b.mode("Режим", 0, "Клиентский", "Индикатор");

        var s2 = b.mode("Режим", 0, "Обычный", "Пиксельный");
        var s3 = b.slider("Размер", 7.0f, 5.0f, 10.0f, 1.0f);
        var s4 = b.slider("Радиус", 100.0f, 50.0f, 150.0f, 5.0f);
        var s5 = b.bool("Свечение", true);
        var s6 = b.group("Другое", s3, s4, s5);
        var s7 = b.group("Шрифты", s2);
        var s8 = b.group("Цвета", s1);
        var s9 = b.group("Дополнения");
        var s10 = b.group("Цели");
        b.show(s10, s9, s8, s7, s6);
    }

    private static void m41() {

        var b = mod(2, "Shulker Preview", "Отображает содержимое шалкера при наведении на него");

    }

    private static void m42() {

        var b = mod(2, "Structure Timer", "Отображает время действия трапок и пластов");

    }

    private static void m43() {

        var b = mod(2, "Swing Animations", "Изменяет положение предметов в руках");
        var s1 = b.mode("Режим", 0, "Type 1", "Type 2", "Default");
        var s2 = b.slider("Сила", 70.0f, 10.0f, 100.0f, 10.0f);
        var s3 = b.slider("Скорость", 7.0f, 1.0f, 10.0f, 1.0f);
        var s4 = b.slider("X координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s5 = b.slider("Y координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s6 = b.slider("Z координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s7 = b.slider("X координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s8 = b.slider("Y координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s9 = b.slider("Z координата", 0.0f, -1.5f, 1.5f, 0.1f);
        var s10 = b.bool("Только с AttackAura", false);
        var s11 = b.group("Другое", s10);
        var s12 = b.group("Позиция левой руки", s7, s8, s9);
        var s13 = b.group("Позиция правой руки", s4, s5, s6);
        var s14 = b.group("Анимация", s1, s2, s3);

        b.show(s14, s13, s12, s11);
    }

    private static void m44() {

        var b = mod(2, "Time Changer", "Изменяет время суток");
        var s1 = b.mode("Режим", 0, "Закат", "День", "Ночь");
        b.show(s1);
    }

    private static void m45() {

        var b = mod(2, "Trails", "Оставляет след за вами");

    }

    private static void m46() {

        var b = mod(3, "Auto Exp", "Автоматически кидает пузырьки с опытом под ноги");

        var s1 = b.slider("Задержка", 0.0f, 0.0f, 100.0f, 10.0f);
        b.show(s1);
    }

    private static void m47() {

        var b = mod(3, "Auto Leave", "Избегает определенных условий");
        var s1 = b.bool("Мало ХП", true);
        var s2 = b.slider("Количество ХП", 10.0f, 5.0f, 19.0f, 1.0f);
        var s3 = b.bool("Рядом игрок", false);
        var s4 = b.slider("Дистанция до игрока", 128.0f, 10.0f, 128.0f, 5.0f);
        var s5 = b.bool("Staff в сети", false);
        var s6 = b.bool("Staff в спеке", false);
        var s7 = b.bool("Бан в чате", false);
        var s8 = b.slider("Минут для перезахода", 1.0f, 0.1f, 30.0f, 0.1f);

        b.show(s1, s2, s3, s4, s5, s6, s7, s8);
    }

    private static void m48() {

        var b = mod(3, "Auto Reconnect", "Автоматически перезаходит на последний игровой режим");

    }

    private static void m49() {

        var b = mod(3, "Auto Respawn", "Автоматически респавнит вас после смерти");

    }

    private static void m50() {

        var b = mod(3, "Auto Tool", "Автоматически берет лучший инструмент для ломания блока");

    }

    private static void m51() {

        var b = mod(3, "Auto Use", "Автоматически использует предметы в нужный момент");

    }

    private static void m52() {

        var b = mod(3, "Blink", "Замедляет пакеты отправляемые на сервер");
        var s1 = b.mode("Режим замедления", 0, "Normal", "Delay");
        var s2 = b.slider("Длительность", 0.5f, 0.1f, 5.0f, 0.1f);
        var s3 = b.bool("Отображать настоящую позицию", true);

        b.show(s1, s2, s3);
    }

    private static void m53() {

        var b = mod(3, "Chest Stealer", "Автоматически забирает ресурсы из сундука");

    }

    private static void m54() {

        var b = mod(3, "Fast Break", "Позволяет ломать блоки быстрее");
        var s1 = b.slider("Сила", 0.4f, 0.0f, 1.0f, 0.1f);
        var s2 = b.slider("Задержка", 0.0f, 0.0f, 5.0f, 1.0f);
        var s3 = b.bool("Обход Grim", false);
        b.show(s1, s2, s3);
    }

    private static void m55() {

        var b = mod(3, "Free Camera", "Позволяет летать в режиме наблюдателя");
        var s1 = b.bool("Выключать при дамаге", false);
        var s2 = b.slider("Скорость", 1.5f, 0.5f, 5.0f, 0.1f);
        b.show(s1, s2);
    }

    private static void m56() {

        var b = mod(3, "Ghost Hand", "Позволяет взаимодействовать с контейнерами сквозь стены");

    }

    private static void m57() {

        var b = mod(3, "Gui Move", "Позволяет ходить в открытых контейнерах");
        var s1 = b.mode("Режим", 0, "Advanced", "Funtime", "Vanilla");
        var s2 = b.bool("Не в текстовых полях", true);
        b.show(s1, s2);
    }

    private static void m58() {

        var b = mod(3, "Items Delay", "Ставит задержку на предмет после его использования");
        var s1 = b.bool("Включено", true);
        var s2 = b.slider("Задержка в секундах", 14.0f, 1.5f, 60.0f, 0.5f);
        var s3 = b.bool("Включено", true);
        var s4 = b.slider("Задержка в секундах", 5.0f, 0.1f, 60.0f, 0.5f);
        var s5 = b.bool("Включено", true);
        var s6 = b.slider("Задержка в секундах", 5.0f, 0.1f, 60.0f, 0.5f);
        var s7 = b.bool("Включено", true);
        var s8 = b.slider("Задержка в секундах", 5.0f, 0.1f, 60.0f, 0.5f);
        var s9 = b.bool("Включено", true);
        var s10 = b.slider("Задержка в секундах", 5.0f, 1.5f, 60.0f, 0.5f);
        var s11 = b.bool("Компенсировать задержку", false);
        var s12 = b.bool("Только в ПВП", false);
        b.show(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12);
    }

    private static void m59() {

        var b = mod(3, "No Clip", "Позволяет проходить сквозь блоки");

    }

    private static void m60() {

        var b = mod(3, "No Delay", "Убирает задержку на что-либо");
        var s1 = b.bool("Прыжок", true);
        var s2 = b.slider("Задержка для прыжка", 0.0f, 0.0f, 10.0f, 1.0f);
        var s3 = b.bool("ПКМ", false);
        var s4 = b.slider("Задержка для ПКМ", 0.0f, 0.0f, 4.0f, 1.0f);
        b.show(s1, s2, s3, s4);
    }

    private static void m61() {

        var b = mod(3, "No Interact", "Не дает нажимать ПКМ по выбранным объектам");
        var s1 = b.bool("Все объекты", false);
        var s2 = b.bool("Игроки", false);
        var s3 = b.bool("Армор стенды", true);
        var s4 = b.bool("Сундуки", false);
        var s5 = b.bool("Бочки", false);
        var s6 = b.bool("Двери", true);
        var s7 = b.bool("Воронки", true);
        var s8 = b.bool("Кнопки", true);
        var s9 = b.bool("Раздатчики", true);
        var s10 = b.bool("Нотные блоки", false);
        var s11 = b.bool("Верстаки", true);
        var s12 = b.bool("Люки", true);
        var s13 = b.bool("Печки", true);
        var s14 = b.bool("Калитки", true);
        var s15 = b.bool("Наковальни", false);
        var s16 = b.bool("Рычаги", false);
        var s17 = b.bool("Только в ПВП", false);
        b.show(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17);
    }

    private static void m62() {

        var b = mod(3, "No Push", "Игнорирует отталкивание от чего-либо");
        var s1 = b.bool("Блоки", true);
        var s2 = b.bool("Сущности", true);
        var s3 = b.bool("Удочки", true);
        var s4 = b.bool("Воду", false);
        var s5 = b.bool("Пузыри", false);
        var s6 = b.bool("Границу", false);
        b.show(s1, s2, s3, s4, s5, s6);
    }

    private static void m63() {

        var b = mod(3, "Nuker", "Автоматически ломает блоки вокруг вас");
        var s1 = b.bool("Смотреть на блоки", true);
        var s2 = b.slider("Радиус по XZ", 3.0f, 1.0f, 6.0f, 1.0f);
        var s3 = b.slider("Радиус по Y", 3.0f, 1.0f, 6.0f, 1.0f);
        var s4 = b.slider("Задержка", 150.0f, 0.0f, 2000.0f, 10.0f);
        var s5 = b.bool("Оптимизировать копание", true);

        b.show(s1, s2, s3, s4, s5);
    }

    private static void m64() {

        var b = mod(3, "Projectile Helper", "Утилитарный модуль для снарядов и их оружия");

        var s1 = b.bool("Автоматически стрелять если цель в зоне", false);
        var s2 = b.bool("Не давать отпустить если полностью не заряжен", true);
        var s3 = b.bool("Автоматически стрелять если цель в зоне", false);
        var s4 = b.bool("Автоматически отпускать при полном заряде", false);
        var s5 = b.bool("Не давать отпустить если полностью не заряжен", true);
        var s6 = b.bool("Автоматически стрелять если цель в зоне", false);
        var s7 = b.slider("Сила натяжения", 0.5f, 0.1f, 1.0f, 0.1f);
        var s8 = b.bool("Не давать отпустить если не достаточно заряжен", true);
        var s9 = b.slider("Размер хитбокса", 0.3f, -0.2f, 0.3f, 0.01f);
        var s10 = b.group("Другое", s9);
        var s11 = b.group("Лук", s6, s7, s8);
        var s12 = b.group("Арбалет", s3, s4, s5);
        var s13 = b.group("Трезубец", s1, s2);
        var s14 = b.group("Траектории");
        s7.showIf(() -> s6.on());

        b.show(s14, s13, s12, s11, s10);
    }

    private static void m65() {

        var b = mod(3, "Streamer Mode", "Скрывает некоторые элементы игры");
        var s1 = b.bool("Скрывать свой", true);
        var s2 = b.bool("Скрывать друзей", false);
        var s3 = b.bool("Скрывать информацию", false);
        var s4 = b.bool("Скрывать анархию/гриф", false);
        var s5 = b.bool("Скрывать координаты", false);
        var s6 = b.bool("Режим ютубера", false);
        var s7 = b.group("Разное", s4, s5, s6);
        var s8 = b.group("Чат", s3);
        var s9 = b.group("Никнеймы", s1, s2);
        b.show(s9, s8, s7);
    }

    private static void m66() {

        var b = mod(4, "Anti AFK", "Обходит сервера с проверкой на АФК");
        var s1 = b.mode("Режим", 0, "Funtime", "Vanilla");
        b.show(s1);
    }

    private static void m67() {

        var b = mod(4, "Anti Shuffler", "Убирает переключение слотов античитом");

    }

    private static void m68() {

        var b = mod(4, "Auto Accept", "Автоматически принимает запросы на что-либо");

    }

    private static void m69() {

        var b = mod(4, "Auto Auth", "Автоматически регистрируется и авторизируется на сервере");

    }

    private static void m70() {

        var b = mod(4, "Auto Buy", "Утилитарный модуль для работы с аукционом");
        var s1 = b.mode("Процесс", 0, "Покупающий", "Проверяющий");

        b.show(s1);
    }

    private static void m71() {

        var b = mod(4, "Auto Duel", "Автоматически находит дуэли");
        var s1 = b.mode("Сервер", 0, "Funtime", "ReallyWorld");
        b.show(s1);
    }

    private static void m72() {

        var b = mod(4, "Auto Fish", "Автоматически ловит рыбу за вас");

    }

    private static void m73() {

        var b = mod(4, "Base Finder", "Утилитарный модуль для поиска баз");
        var s1 = b.bool("Сундуки", true);
        var s2 = b.bool("Шалкера", true);
        var s3 = b.bool("Бочки", true);
        var s4 = b.bool("Наковальни", false);
        var s5 = b.bool("Столы зачарования", false);
        var s6 = b.bool("Печки", false);
        var s7 = b.bool("Избегать враждебных существ", true);
        var s8 = b.group("Другое", s7);
        var s9 = b.group("Триггеры", s1, s2, s3, s4, s5, s6);
        b.show(s9, s8);
    }

    private static void m74() {

        var b = mod(4, "Casino", "Test");

    }

    private static void m75() {

        var b = mod(4, "Chat Helper", "Утилитарный модуль для чата");

        var s1 = b.bool("Сохранение истории", false);
        b.show(s1);
    }

    private static void m76() {

        var b = mod(4, "Clan Upgrader", "Помогает в улучшении клана на Funtime");

    }

    private static void m77() {

        var b = mod(4, "Click Friend", "Добавляет или удаляет игрока из друзей на клавишу");

    }

    private static void m78() {

        var b = mod(4, "Click Pearl", "Бросает эндер-пёрл на клавишу");
        var s1 = b.mode("Режим", 0, "Vanilla", "HolyWorld");

        b.show(s1);
    }

    private static void m79() {

        var b = mod(4, "Creeper Farm", "Baritone для охоты на криперов");
        var s1 = b.bool("Автоматический прыжок", true);
        var s2 = b.bool("Атаковать одновременно несколько существ", true);
        var s3 = b.bool("Отключаться при полном инвентаре", true);
        var s4 = b.bool("Автоматический перезаход на режим", true);
        var s5 = b.slider("Секунд для перезахода", 60.0f, 10.0f, 600.0f, 5.0f);
        s5.showIf(() -> s4.on());
        b.show(s1, s2, s3, s4, s5);
    }

    private static void m80() {

        var b = mod(4, "Death Coords", "Отображает координаты вашей смерти");

    }

    private static void m81() {

        var b = mod(4, "Elytra Swap", "Автоматически переключает слот с нагрудником на элитру");



        var s1 = b.bool("Автоматический полёт", true);
        var s2 = b.bool("Запускать 1 фейерверк", false);
        var s3 = b.bool("Не работать в ПВП", false);
        b.show(s1, s2, s3);
    }

    private static void m82() {

        var b = mod(4, "Event Parser", "Отслеживает появление событий и устанавливает путь к ним");
        var s1 = b.mode("Режим", 0, "Автоматический", "Ручной");
        var s2 = b.bool("Автоматически удалять точку через время", true);
        var s3 = b.slider("Минут для удаления", 10.0f, 1.0f, 30.0f, 1.0f);


        b.show(s1, s2, s3);
    }

    private static void m83() {

        var b = mod(4, "FT Helper", "Утилитарный модуль для Funtime");
        var s1 = b.bool("Улучшать команды", true);
        var s2 = b.group("Другое");
        var s3 = b.group("Чат", s1);
        var s4 = b.group("Авто-шахта");
        var s5 = b.group("Аукцион");
        b.show(s5, s4, s3, s2);
    }

    private static void m84() {

        var b = mod(4, "Farm Builder", "Помогает в постройке фермы криперов");
        var s1 = b.mode("Режим", 0, "Люки", "Полублоки");

        b.show(s1);
    }

    private static void m85() {

        var b = mod(4, "Grief Joiner", "Помогает зайти на гриф сервера ReallyWorld");

    }

    private static void m86() {

        var b = mod(4, "HW Helper", "Утилитарный модуль для HolyWorld");

    }

    private static void m87() {

        var b = mod(4, "Item Scroller", "Позволяет быстро забирать или перекладывать предметы");
        var s1 = b.slider("Задержка", 0.0f, 0.0f, 100.0f, 10.0f);
        b.show(s1);
    }

    private static void m88() {

        var b = mod(4, "Leave Tracker", "Отслеживает позицию телепорта игроков");

    }

    private static void m89() {

        var b = mod(4, "Minecraft Optimizer", "Оптимизирует майнкрафт");
        var s1 = b.bool("Баннеры", true);
        var s2 = b.bool("Рамки", false);
        b.show(s1, s2);
    }

    private static void m90() {

        var b = mod(4, "No Server Rots", "Убирает ротацию со стороны сервера");

    }

    private static void m91() {

        var b = mod(4, "Potion Tracker", "Отслеживает накладываемые зелья на игроков");

    }

    private static void m92() {

        var b = mod(4, "RW Helper", "Утилитарный модуль для ReallyWorld");


    }

    private static void m93() {

        var b = mod(4, "SRP Spoofer", "Убирает ресурспак на серверах где это запрещено");

    }

    private static void m94() {

        var b = mod(4, "Test Func", "Test :)");

    }

    private static void m95() {

        var b = mod(4, "Totem Tracker", "Отслеживает снос тотемов");

    }

    private static void m96() {

        var b = mod(4, "Tps Sync", "Синхронизирует модули с тикрейтом сервера");

    }

    private static void m97() {

        var b = mod(4, "Use Tracker", "Отслеживает использование предметов");

    }
}
