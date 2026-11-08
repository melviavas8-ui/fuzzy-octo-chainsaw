package daamky.zenith.shim;

import daamky.zenith.ZenithColor;

/** Fixed Daamky palette exposing the small part of Zenith's StyleManager API the game core uses. */
public final class StyleStub {
   private static final Palette STYLE = new Palette();

   public Palette getCurrentStyle() { return STYLE; }

   public ZenithColor getClientColor(int index) { return STYLE.getPrimaryColor().getZenithColor(); }

   public static final class Palette {
      private final Swatch primary = new Swatch(new ZenithColor(87, 126, 255));
      private final Swatch text = new Swatch(new ZenithColor(190, 200, 225));
      public Swatch getPrimaryColor() { return primary; }
      public Swatch getTextEnable() { return text; }
   }

   public static final class Swatch {
      private final ZenithColor color;
      Swatch(ZenithColor c) { this.color = c; }
      public ZenithColor getZenithColor() { return color; }
   }
}
