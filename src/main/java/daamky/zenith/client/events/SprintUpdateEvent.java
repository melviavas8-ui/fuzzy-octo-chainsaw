package daamky.zenith.client.events;

import daamky.zenith.CancellableEvent;

public class SprintUpdateEvent extends CancellableEvent {
   private boolean flag;
   public SprintUpdateEvent(boolean var1) {
      this.flag = var1;
   }
   public boolean isFlag2() {
      return this.flag;
   }
   public void setFlag2(boolean var1) {
      this.flag = var1;
   }
}
