package daamky.zenith.utility.misc;

import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import daamky.zenith.Zenith;
import daamky.zenith.utility.api.JsonListStorage;

public class Friends extends JsonListStorage<String> {
   public Friends() {
      super("friends.json", "", (new TypeToken<Set<String>>() {}).getType(), ConcurrentHashMap::newKeySet);
      KeySetView var1 = ConcurrentHashMap.newKeySet();
      var1.addAll(this.items);
      this.items = var1;
   }

   public boolean method7626(Entity var1) {
      return var1 instanceof PlayerEntity var2 ? this.method7627(var2.getGameProfile().name()) : false;
   }

   public boolean method7627(String var1) {
      return this.getItems().contains(var1) || this.method7628(var1);
   }

   public boolean method7628(String var1) {
      return false;
   }

   public void method7629(String var1) {
      this.getItems().remove(var1);
   }
}
