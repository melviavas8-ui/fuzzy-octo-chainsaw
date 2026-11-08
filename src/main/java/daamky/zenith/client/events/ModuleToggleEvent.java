package daamky.zenith.client.events;

import com.darkmagician6.eventapi.events.Event;
import daamky.zenith.base.Module;

public class ModuleToggleEvent implements Event {
   private final Module module;
   private final boolean flag;
   public ModuleToggleEvent(Module var1, boolean var2) {
      this.module = var1;
      this.flag = var2;
   }
   public Module getModule() {
      return this.module;
   }
   public boolean isEnabled() {
      return this.flag;
   }
}
