package daamky.zenith;

import daamky.zenith.base.rotation.BcCameraTestnerv;
import daamky.zenith.utility.api.JointCodebook;
import daamky.zenith.utility.misc.Handler2;

public interface ZenithAccess extends Handler2 {
   Zenith zenith = Zenith.getInstance();
   JointCodebook jointCodebook = Zenith.getInstance().getJointCodebook();
   RotationManager rotationManager = Zenith.getInstance().getRotationManager();
   BcCameraTestnerv bcCameraTestnerv = rotationManager.getBcCameraTestnerv();
}
