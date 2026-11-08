package daamky.zenith.client.settings;

import com.google.gson.JsonObject;
import java.util.function.Supplier;
import daamky.zenith.Zenith;

public abstract class Setting {
   public final String key;
   public final String description;
   public Supplier<Boolean> visible;
   public Supplier<String> titleKey;
   public Supplier<String> tipKey;

   public Setting(String var1) {
      this(var1, "");
   }

   public Setting(String var1, String var2) {
      this.key = var1;
      this.description = var2 != null ? var2 : "";
      this.setVisible(() -> true);
   }

   public String getName() {
      return Zenith.getInstance().getLocalization().translate(this.titleKey == null ? this.key : this.titleKey.get());
   }

   public String getDescription() {
      return Zenith.getInstance().getLocalization().translate(this.tipKey == null ? this.description : this.tipKey.get());
   }

   public abstract void safe(JsonObject var1);

   public abstract void load(JsonObject var1);

   public boolean isVisible() {
      return this.visible.get();
   }
   public String getKey() {
      return this.key;
   }
   public Supplier<Boolean> getVisible() {
      return this.visible;
   }
   public Supplier<String> getTitleKey() {
      return this.titleKey;
   }
   public Supplier<String> getTipKey() {
      return this.tipKey;
   }
   public void setVisible(Supplier<Boolean> var1) {
      this.visible = var1;
   }
   public void setTitleKey(Supplier<String> var1) {
      this.titleKey = var1;
   }
   public void setTipKey(Supplier<String> var1) {
      this.tipKey = var1;
   }
}
