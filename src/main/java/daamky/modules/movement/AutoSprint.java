package daamky.modules.movement;

import daamky.core.Module;
import daamky.core.MoveUtil;
import daamky.gui.Modules;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import net.minecraft.entity.effect.StatusEffects;


public class AutoSprint extends Module {
    private final Modules.Bool hunger, blind, sneak, crawl, use, collide;

    public AutoSprint() {
        super("Auto Sprint", "Автоматически включает спринт", MOVEMENT);
        hunger = bool("Голод", false);
        blind = bool("Слепоту", false);
        sneak = bool("Приседание", false);
        crawl = bool("Ползание", false);
        use = bool("Использование предметов", false);
        collide = bool("Горизонтальную коллизию", false);

    }

    @Override protected void onTick() {
        ClientPlayerEntity p = mc.player;
        if (!MoveUtil.isMoving() || !mc.options.forwardKey.isPressed()) return;
        if (!hunger.value && p.getHungerManager().getFoodLevel() <= 6 && !p.getAbilities().allowFlying) return;
        if (!blind.value && p.hasStatusEffect(StatusEffects.BLINDNESS)) return;
        if (!sneak.value && p.isSneaking()) return;
        if (!crawl.value && p.isCrawling()) return;
        if (!use.value && p.isUsingItem()) return;
        if (!collide.value && p.horizontalCollision) return;
        p.setSprinting(true);
    }
}
