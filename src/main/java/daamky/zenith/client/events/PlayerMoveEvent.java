package daamky.zenith.client.events;

import net.minecraft.util.math.Vec3d;
import daamky.zenith.CancellableEvent;

public class PlayerMoveEvent extends CancellableEvent {
   private Vec3d vec3d;
   public PlayerMoveEvent(Vec3d var1) {
      this.vec3d = var1;
   }
   public Vec3d getVec3d() {
      return this.vec3d;
   }
   public void setVec3d(Vec3d var1) {
      this.vec3d = var1;
   }
}
