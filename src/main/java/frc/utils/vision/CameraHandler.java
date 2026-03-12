package frc.utils.vision;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.VisionSubsystem;

public class CameraHandler {
    private Camera[] cams;
    private Camera targetCam;

    private Transform3d currentOffsets;
    public CameraHandler(Camera ...cams) {
        this.cams=cams;
    }

    public void disableStickyCam() {
        for(var cam: cams) {
            cam.enabled=true;
        }
    }

    public PhotonTrackedTarget getBestTag() {
        PhotonTrackedTarget bestTag=null;
        for(var cam: cams) {
            cam.setLowestAmbiguity();
            var currentTag=cam.getCurrentTag();
            if(cam.getCurrentTag()==null || !cam.enabled) {
                continue;
            }
            if(
                currentTag.getPoseAmbiguity()<VisionSubsystem.currentAmbiguity*VisionConstants.ambiguityTolerance
                    && currentTag.getPoseAmbiguity() != -1
                    && hasTargetTag(currentTag.fiducialId)
            ) {
                VisionSubsystem.currentAmbiguity = currentTag.getPoseAmbiguity();
                bestTag=currentTag;
                targetCam=cam;
                currentOffsets=cam.getOffsets();
                enableStickyCam();
            }
        }
        return bestTag;
    }  

    private boolean hasTargetTag(int id) {
        return VisionSubsystem.currentWaypoint.waypoint.hasId(id);
    }

    private void enableStickyCam() {
        for(var cam: cams) {
            if(cam!=targetCam) {
                cam.enabled=false;
            }
        }
    }

    public double getXOffset() {
        return this.currentOffsets.getTranslation().getX();
    }

    public double getYOffset() {
        return this.currentOffsets.getTranslation().getY();
    }

    public double getPitch() {
        return this.currentOffsets.getRotation().getY();
    }

    public String getCurrentCamName() {
        return this.targetCam.getCam().getName();
    }
}