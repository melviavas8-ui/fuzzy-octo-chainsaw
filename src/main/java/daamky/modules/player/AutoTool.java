package daamky.modules.player;

import daamky.core.Module;
import daamky.gui.Modules;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;


public class AutoTool extends Module {
    private final Modules.Bool back;
    private int saved = -1;

    public AutoTool() {
        super("Auto Tool", "Автоматически берет лучший\nинструмент для ломания блока", PLAYER);
        back = bool("Возвращать слот", true);
    }

    @Override protected void onDisable() { restore(); }

    private void restore() {
        if (saved >= 0 && mc.player != null) mc.player.getInventory().setSelectedSlot(saved);
        saved = -1;
    }

    @Override protected void onTick() {
        boolean breaking = mc.options.attackKey.isPressed() && mc.crosshairTarget instanceof BlockHitResult r
                && r.getType() == HitResult.Type.BLOCK;
        if (!breaking) { if (back.value) restore(); return; }
        BlockState st = mc.world.getBlockState(((BlockHitResult) mc.crosshairTarget).getBlockPos());
        if (st.isAir()) return;
        var inv = mc.player.getInventory();
        int best = -1;
        float bs = 1f;
        for (int i = 0; i < 9; i++) {
            ItemStack s = inv.getStack(i);
            float sp = s.getMiningSpeedMultiplier(st);
            if (sp > bs) { bs = sp; best = i; }
        }
        if (best >= 0 && best != inv.getSelectedSlot()) {
            if (saved < 0) saved = inv.getSelectedSlot();
            inv.setSelectedSlot(best);
        }
    }
}
