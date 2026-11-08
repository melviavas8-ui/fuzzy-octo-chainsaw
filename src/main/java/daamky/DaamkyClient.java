package daamky;

import daamky.core.InterfaceManager;
import daamky.gui.Modules;
import daamky.hud.CelestialLegacyHud;
import daamky.hud.DaamkyHud;
import daamky.render.Fonts;
import daamky.render.Gfx;
import daamky.render.MsdfText;
import daamky.render.Txt;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class DaamkyClient implements ClientModInitializer {
    public static final String MOD_ID = "daamky";
    private static KeyBinding openKey;
    private static final java.util.Set<Modules.Mod> HELD = new java.util.HashSet<>();

    @Override
    public void onInitializeClient() {
        InterfaceManager.init();
        daamky.core.ModuleManager.init();

        openKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.daamky.open", GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.create(Identifier.of(MOD_ID, "main"))));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openKey.wasPressed()) mc.setScreen(InterfaceManager.createClickGui());
        });

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            var window = mc.getWindow();
            for (Modules.Mod mod : Modules.ALL) {
                if (mod.key < 0) continue;
                boolean down = mc.currentScreen == null && InputUtil.isKeyPressed(window, mod.key);
                if (down && !HELD.contains(mod)) {
                    mod.enabled = !mod.enabled;
                    HELD.add(mod);
                } else if (!down) {
                    HELD.remove(mod);
                }
            }
        });

        HudRenderCallback.EVENT.register((ctx, tick) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (Fonts.regular() == null || mc.options.hudHidden) return;
            Gfx.ctx = ctx;
            if (InterfaceManager.isLegacy()) CelestialLegacyHud.render(ctx);
            else DaamkyHud.render(ctx);

            daamky.modules.render.JumpCircles.render(new org.joml.Matrix3x2f(ctx.getMatrices()), tick.getTickProgress(false));
            daamky.hud.Souls.render(new org.joml.Matrix3x2f(ctx.getMatrices()), tick.getTickProgress(false));

            if (Modules.demoOn()) {
                MsdfText.msdfMode = Modules.useMsdf();
                MsdfText.baseWeight = Modules.weight();
                var matrix = new org.joml.Matrix3x2f(ctx.getMatrices());
                float size = Modules.demoSize();
                Txt.reg(matrix, "Съешь ещё этих мягких французских булок", 6, 40, size, 0xFFFFFFFF);
                Txt.bold(matrix, "The quick brown fox jumps over the lazy dog 0123456789", 6, 40 + size * 1.3f, size, 0xFFFFFFFF);
            }
        });
    }
}
