package daamky.zenith.client.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EntityAddEvent implements Event {
   private Entity entity;
   public Entity getEntity() {
      return this.entity;
   }
   public EntityAddEvent(Entity var1) {
      this.entity = var1;
   }
}
