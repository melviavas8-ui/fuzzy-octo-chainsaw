package daamky.zenith.base.rotation;

import daamky.zenith.Rotation;
import daamky.zenith.ZenithAccess;
import daamky.zenith.base.rotation.mods.Agent;
import daamky.zenith.base.rotation.mods.BcCamera;
import daamky.zenith.base.rotation.mods.BcHand;
import daamky.zenith.base.rotation.mods.Class21;
import daamky.zenith.base.rotation.mods.Class22;
import daamky.zenith.base.rotation.mods.Class23;
import daamky.zenith.base.rotation.mods.Class24;
import daamky.zenith.base.rotation.mods.Class25;
import daamky.zenith.base.rotation.mods.Class26;
import daamky.zenith.base.rotation.mods.HandRotationLogger;
import daamky.zenith.base.rotation.mods.Motor;
import daamky.zenith.base.rotation.mods.MotorClaude;
import daamky.zenith.base.rotation.mods.MotorHand2;
import daamky.zenith.base.rotation.mods.SlewRotationMod;
import daamky.zenith.base.rotation.mods.TokenMotor;
import daamky.zenith.utility.misc.Class10;
import daamky.zenith.utility.misc.Class14;
import daamky.zenith.utility.misc.Class17;
import daamky.zenith.utility.misc.Class18;
import daamky.zenith.utility.misc.Class19;
import daamky.zenith.utility.misc.Class20;
import daamky.zenith.utility.misc.Class29;
import daamky.zenith.utility.misc.Class34;
import daamky.zenith.utility.misc.Class38;
import daamky.zenith.utility.misc.Class39;
import daamky.zenith.utility.misc.Class40;
import daamky.zenith.utility.misc.Class41;
import daamky.zenith.utility.misc.Class43;
import daamky.zenith.utility.misc.Class44;
import daamky.zenith.utility.misc.Class6;
import daamky.zenith.utility.misc.Class7;
import daamky.zenith.utility.misc.Class8;
import daamky.zenith.utility.misc.RotationConfig;

public class BcCameraTestnerv implements ZenithAccess {
   private final Class23 class23 = new Class23();
   private final Class24 class24 = new Class24();
   private Class22 class22;
   private final SlewRotationMod slewRotationMod = new SlewRotationMod();
   private final Motor motor = new Motor();
   private final TokenMotor tokenMotor = new TokenMotor();
   private final HandRotationLogger handRotationLogger = new HandRotationLogger();
   private final Agent agent = new Agent();
   private final MotorClaude motorClaude = new MotorClaude("motor_imitation.json");
   private final MotorHand2 motorHand2 = new MotorHand2();
   private final BcCamera bcCamera = new BcCamera();
   private final BcCamera bcCamera2 = new BcCamera("bc_camera_2.json", 2, 1.0);
   private final BcCamera bcCamera3 = new BcCamera("bc_camera_testnerv.json", 4, 1.0);
   private final BcHand bcHand = new BcHand();
   private final Class25 class25 = new Class25();
   private final Class26 class26 = new Class26();
   private final Class21 class21 = new Class21();
   private final RotationConfig rotationConfig = new Class29();
   private final RotationConfig rotationConfig2 = new Class44();
   private final RotationConfig rotationConfig3 = new Class41();
   private final RotationConfig rotationConfig4 = Class6.method861().method869();
   private final RotationConfig rotationConfig5 = Class20.method3287().method3288();
   private final RotationConfig rotationConfig6 = Class18.method2737().method2738();
   private final RotationConfig rotationConfig7 = Class7.method1437().method1438();
   private final RotationConfig rotationConfig8 = Class43.method7548().method7549();
   private final RotationConfig rotationConfig9 = Class14.method2017().method2018();
   private final RotationConfig rotationConfig10 = Class38.method6490().method6491();
   private final RotationConfig rotationConfig11 = Class19.method2781().method2782();
   private final RotationConfig rotationConfig12 = Class8.method1450().method1451();
   private final RotationConfig rotationConfig13 = Class34.method5354().method5355();
   private final RotationConfig rotationConfig14 = Class17.method2520().method2521();
   private final RotationConfig rotationConfig15 = Class39.method6610().method6611();
   private final RotationConfig rotationConfig16 = Class40.method6647().method6648();
   private final RotationConfig rotationConfig17 = new Class10();

   public Class22 method1850() {
      if (this.class22 == null) {
         this.class22 = new Class22();
      }

      return this.class22;
   }

   public Rotation method1851(RotationConfig var1, Rotation var2) {
      Rotation var3;
      switch (var1.method853()) {
         case INSTANT:
            var3 = this.class23.method3534(var2);
            break;
         case FUNTIME:
            var3 = this.motor.method3659();
            break;
         case GOWNO:
            var3 = this.motor.method3659();
            break;
         case GOWNO_V2:
            var3 = this.motor.method3659();
            break;
         case GOWNO_V3:
            var3 = this.motor.method3659();
            break;
         case GOWNO_V4:
            var3 = this.motor.method3659();
            break;
         case HOLYWORLD:
            var3 = this.class21.method3431(var2);
            break;
         case HUMAN:
            var3 = this.class25.method3653(var2);
            break;
         case HAND:
            return this.handRotationLogger.method3727();
         case AGENT:
            return this.agent.method3462();
         case MOTOR:
            return this.motorClaude.method3425(var2);
         case MOTOR_CLAUDE:
            return this.motorHand2.method3430(var2);
         case BC_CAMERA:
            return this.bcCamera.method3554(var2);
         case BC_CAMERA_2:
            return this.bcCamera2.method3554(var2);
         case BC_TEST_NERV:
            return this.bcCamera3.method3554(var2);
         case BC_HAND:
            return this.bcHand.method3874(var2);
         case TOKEN:
            return this.tokenMotor.method3467();
         default:
            var3 = zenith.getRotationManager().method7844();
      }

      return rotationManager.method7844().equals(var3)
         ? var3
         : rotationManager.method7844().method3141(rotationManager.method7844().method3131(var3)).method3139(rotationManager.method7844());
   }
   public Class24 getClass24() {
      return this.class24;
   }
   public SlewRotationMod getSlewRotationMod() {
      return this.slewRotationMod;
   }
   public Motor getMotor() {
      return this.motor;
   }
   public TokenMotor getTokenMotor() {
      return this.tokenMotor;
   }
   public HandRotationLogger getHandRotationLogger() {
      return this.handRotationLogger;
   }
   public Agent getAgent() {
      return this.agent;
   }
   public MotorClaude getMotorClaude() {
      return this.motorClaude;
   }
   public MotorHand2 getMotorHand2() {
      return this.motorHand2;
   }
   public BcCamera getBcCamera() {
      return this.bcCamera;
   }
   public BcCamera getBcCamera2() {
      return this.bcCamera2;
   }
   public BcCamera getBcCamera3() {
      return this.bcCamera3;
   }
   public BcHand getBcHand() {
      return this.bcHand;
   }
   public Class25 getClass25() {
      return this.class25;
   }
   public RotationConfig getRotationConfig() {
      return this.rotationConfig;
   }
   public RotationConfig getRotationConfig2() {
      return this.rotationConfig2;
   }
   public RotationConfig getRotationConfig3() {
      return this.rotationConfig3;
   }
   public RotationConfig getRotationConfig4() {
      return this.rotationConfig4;
   }
   public RotationConfig getRotationConfig5() {
      return this.rotationConfig5;
   }
   public RotationConfig getRotationConfig6() {
      return this.rotationConfig6;
   }
   public RotationConfig getRotationConfig7() {
      return this.rotationConfig7;
   }
   public RotationConfig getRotationConfig8() {
      return this.rotationConfig8;
   }
   public RotationConfig getRotationConfig9() {
      return this.rotationConfig9;
   }
   public RotationConfig getRotationConfig10() {
      return this.rotationConfig10;
   }
   public RotationConfig getRotationConfig11() {
      return this.rotationConfig11;
   }
   public RotationConfig getRotationConfig12() {
      return this.rotationConfig12;
   }
   public RotationConfig getRotationConfig13() {
      return this.rotationConfig13;
   }
   public RotationConfig getRotationConfig14() {
      return this.rotationConfig14;
   }
   public RotationConfig getRotationConfig15() {
      return this.rotationConfig15;
   }
   public RotationConfig getRotationConfig16() {
      return this.rotationConfig16;
   }
   public RotationConfig getRotationConfig17() {
      return this.rotationConfig17;
   }
}
