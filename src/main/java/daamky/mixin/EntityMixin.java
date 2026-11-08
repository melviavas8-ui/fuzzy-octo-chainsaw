package daamky.mixin;

import daamky.core.Rot;
import daamky.modules.player.NoPush;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void daamky$pushAway(Entity other, CallbackInfo ci) {
        if ((Object) this == MinecraftClient.getInstance().player && NoPush.entities()) ci.cancel();
    }

    @Inject(method = "isPushedByFluids", at = @At("HEAD"), cancellable = true)
    private void daamky$fluids(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == MinecraftClient.getInstance().player && NoPush.water()) cir.setReturnValue(false);
    }


    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    private void daamky$moveFix(float speed, Vec3d input, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (self != MinecraftClient.getInstance().player || !Rot.active || !Rot.moveFix) return;
        double d = input.lengthSquared();
        if (d < 1.0E-7) { ci.cancel(); return; }
        Vec3d v = (d > 1.0 ? input.normalize() : input).multiply(speed);
        double delta = Math.toRadians(self.getYaw() - Rot.yaw);
        double x = v.x * Math.cos(delta) - v.z * Math.sin(delta), z = v.z * Math.cos(delta) + v.x * Math.sin(delta);
        double r = Math.toRadians(Rot.yaw), sin = Math.sin(r), cos = Math.cos(r);
        self.setVelocity(self.getVelocity().add(x * cos - z * sin, v.y, z * cos + x * sin));
        ci.cancel();
    }
}
