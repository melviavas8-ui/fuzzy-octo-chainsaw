package daamky.mixin;

import daamky.modules.player.NoPush;
import daamky.core.Rot;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    private void daamky$pushOut(double x, double z, CallbackInfo ci) {
        if (NoPush.blocks()) ci.cancel();
    }

    private float daamky$ry, daamky$rp;
    private boolean daamky$swapped;


    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void daamky$rotHead(CallbackInfo ci) {
        if (!Rot.active) return;
        ClientPlayerEntity p = (ClientPlayerEntity) (Object) this;
        daamky$ry = p.getYaw(); daamky$rp = p.getPitch(); daamky$swapped = true;
        p.setYaw(Rot.yaw); p.setPitch(Rot.pitch);
    }

    @Inject(method = "sendMovementPackets", at = @At("RETURN"))
    private void daamky$rotTail(CallbackInfo ci) {
        if (!daamky$swapped) return;
        daamky$swapped = false;
        ClientPlayerEntity p = (ClientPlayerEntity) (Object) this;
        p.setYaw(daamky$ry); p.setPitch(daamky$rp);
    }
}
