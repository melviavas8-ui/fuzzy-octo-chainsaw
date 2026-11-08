package daamky.zenith;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

/** Daamky chat logger: Zenith ChatLogger with a fixed blue palette instead of Zenith StyleManager. */
public final class ChatLogger implements MinecraftAccess {
   private static final ZenithColor PRIMARY = new ZenithColor(87, 126, 255);
   private static final ZenithColor SECONDARY = new ZenithColor(120, 190, 255);
   private static final ZenithColor TEXT = new ZenithColor(190, 200, 225);
   public static final Text text = prefix();

   public static void method2019(String var0) {
      method2020(ChatLogger.Type.INFO, var0);
   }

   public static void method2020(ChatLogger.Type var0, String var1) {
      if (mc.player != null) {
         String var3 = var1 == null ? "" : var1.replaceAll("\u00A7.", "");
         ZenithColor var4 = var0.getZenithColor().method7295(PRIMARY, 0.3F);
         mc.player.sendMessage(prefix().append(Text.literal(" ")).append(gradient(var3, var4, TEXT, false)), false);
      }
   }

   public static void method2021(String var0) {
      method2020(ChatLogger.Type.WARN, var0);
   }

   public static void method2022(String var0) {
      method2020(ChatLogger.Type.ERROR, var0);
   }

   private static MutableText prefix() {
      return Text.empty()
         .append(plain("[ ", TEXT))
         .append(gradient("Daamky", PRIMARY, SECONDARY, true))
         .append(plain(" ]", TEXT));
   }

   private static MutableText plain(String var0, ZenithColor var1) {
      return Text.literal(var0).setStyle(Style.EMPTY.withColor(var1.method7287()));
   }

   private static MutableText gradient(String var0, ZenithColor var1, ZenithColor var2, boolean var3) {
      MutableText var5 = Text.empty();
      if (var0 == null || var0.isEmpty()) return var5;
      int var6 = var0.codePointCount(0, var0.length());
      int[] var7 = new int[]{0};
      var0.codePoints().forEach(cp -> {
         float t = var6 <= 1 ? 0.0F : (float)var7[0] / (var6 - 1);
         ZenithColor c = var1.method7295(var2, t);
         var5.append(Text.literal(new String(Character.toChars(cp))).setStyle(Style.EMPTY.withColor(c.method7287()).withBold(var3)));
         var7[0]++;
      });
      return var5;
   }

   private ChatLogger() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static enum Type {
      WARN("WARN", new ZenithColor(247, 206, 59)),
      ERROR("ERROR", new ZenithColor(242, 79, 68)),
      INFO("INFO", new ZenithColor(87, 126, 255));

      private final String text;
      private final ZenithColor zenithColor;
      public String getText() { return this.text; }
      public ZenithColor getZenithColor() { return this.zenithColor; }
      private Type(String var3, ZenithColor var4) { this.text = var3; this.zenithColor = var4; }
   }
}
