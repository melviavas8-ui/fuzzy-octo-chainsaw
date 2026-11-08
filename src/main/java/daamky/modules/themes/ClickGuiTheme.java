package daamky.modules.themes;

import daamky.core.Module;
import daamky.gui.Modules;
import daamky.render.Theme;







public class ClickGuiTheme extends Module {
    private final Modules.Mode color;
    private final Modules.Slider hue, sat, bri, speed;
    private final Modules.Bool blur, glow;

    public ClickGuiTheme() {
        super("ClickGUI", "Тема меню и HUD: цвет, скорость,\nразмытие и свечение", THEMES);
        section("Цвет");
        color = mode("Цвет", 0, "Клиентский", "Кастомный");
        hue = slider("Первый цвет", 212, 0, 360, 1, "°");
        sat = slider("Насыщенность", 51, 0, 100, 1, "%");
        bri = slider("Яркость", 95, 30, 100, 1, "%");
        speed = slider("Скорость цвета", 1f, 0.2f, 3f, 0.1f, "x");
        section("Эффекты");
        blur = bool("Размытие", true);
        glow = bool("Свечение", true);
        enabled = true;
    }

    @Override protected void onEnable() { push(); }
    @Override protected void onDisable() { Theme.reset(); }
    @Override protected void onTick() { push(); }

    private void push() {
        Theme.custom = color.index == 1;
        Theme.hue = hue.value;
        Theme.sat = sat.value / 100f;
        Theme.bri = bri.value / 100f;
        Theme.speed = speed.value;
        Theme.blur = blur.value;
        Theme.glow = glow.value;
        Theme.apply();
    }
}
