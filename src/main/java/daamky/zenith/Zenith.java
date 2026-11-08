package daamky.zenith;

import daamky.zenith.base.rotation.BcCameraTestnerv;
import daamky.zenith.shim.Notifier;
import daamky.zenith.shim.StyleStub;
import daamky.zenith.utility.api.JointCodebook;
import daamky.zenith.utility.misc.Friends;
import daamky.zenith.utility.misc.Localization;
import daamky.zenith.utility.misc.TaskScheduler;
import daamky.zenith.utility.render.ServerDetector;
import java.io.File;
import net.minecraft.client.MinecraftClient;

/**
 * Daamky replacement for the original Zenith hub class. Only the services the ported game core needs
 * (rotations, scheduler, localization, friends, server detection) are created here; no cloud, store,
 * cosmetics, licensing or Zenith UI.
 */
public final class Zenith {
   public static Zenith INSTANCE = null;
   public static final File file = new File(MinecraftClient.getInstance().runDirectory, "Daamky");

   private Localization localization;
   private StyleStub styleManager;
   private Friends friends;
   private ServerDetector serverDetector;
   private TaskScheduler taskScheduler;
   private RotationManager rotationManager;
   private JointCodebook jointCodebook;
   private Notifier hudHolder;

   public Zenith() {
      INSTANCE = this;
   }

   /** Creates the core services. Must run before any class implementing ZenithAccess is loaded. */
   public void init() {
      file.mkdirs();
      this.localization = new Localization();
      this.styleManager = new StyleStub();
      this.hudHolder = new Notifier();
      this.friends = new Friends();
      this.serverDetector = new ServerDetector();
      this.taskScheduler = new TaskScheduler();
      this.jointCodebook = new JointCodebook();
      this.rotationManager = new RotationManager();
   }

   public static Zenith getInstance() {
      if (INSTANCE == null) {
         synchronized (Zenith.class) {
            if (INSTANCE == null) {
               new Zenith().init();
            }
         }
      }
      return INSTANCE;
   }

   public Localization getLocalization() { return this.localization; }
   public StyleStub getStyleManager() { return this.styleManager; }
   public Friends getFriends() { return this.friends; }
   public ServerDetector getServerDetector() { return this.serverDetector; }
   public TaskScheduler getTaskScheduler() { return this.taskScheduler; }
   public RotationManager getRotationManager() { return this.rotationManager; }
   public JointCodebook getJointCodebook() { return this.jointCodebook; }
   public Notifier getHudHolder() { return this.hudHolder; }
}
