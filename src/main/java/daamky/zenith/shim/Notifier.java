package daamky.zenith.shim;

import daamky.zenith.ChatLogger;
import net.minecraft.text.Text;

/** Replacement for Zenith's HudHolder notifications: short messages go to the Daamky chat log. */
public final class Notifier {
   public void method6972(String icon, Text message) {
      ChatLogger.method2019(message == null ? "" : message.getString());
   }
}
