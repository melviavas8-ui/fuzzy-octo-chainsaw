package daamky.zenith.client.events;

import net.minecraft.network.packet.Packet;
import daamky.zenith.CancellableEvent;

public class SendPacketEvent extends CancellableEvent {
   private Packet<?> packet;
   public Packet<?> getPacket2() {
      return this.packet;
   }
   public void setPacket(Packet<?> var1) {
      this.packet = var1;
   }
   public SendPacketEvent(Packet<?> var1) {
      this.packet = var1;
   }
}
