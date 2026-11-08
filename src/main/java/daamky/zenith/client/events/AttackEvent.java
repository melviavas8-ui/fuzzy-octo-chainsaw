package daamky.zenith.client.events;

import net.minecraft.entity.Entity;
import daamky.zenith.CancellableEvent;

public final class AttackEvent extends CancellableEvent {
   private final Entity entity;
   private final AttackEvent.Type type;
   public Entity getEntity() {
      return this.entity;
   }
   public AttackEvent.Type getType4() {
      return this.type;
   }
   public AttackEvent(Entity var1, AttackEvent.Type var2) {
      this.entity = var1;
      this.type = var2;
   }

   public static enum Type {
      POST,
      PRE;
   }
}
