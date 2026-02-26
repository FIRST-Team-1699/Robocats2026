package frc.utils.WaypointManagement;

import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.VisionSubsystem;

public class CameraHandler {
    private Camera[] cams;
    private Camera targetCam;

    private double xOffset,yOffset;
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
                enableStickyCam();
            }
        }
        setOffsets();
        return bestTag;
    }  

    private boolean hasTargetTag(int id) {
        return VisionSubsystem.currentWaypoint.waypoint.hasId(id);
    }
    
    private void setOffsets() {
        if(targetCam!= null) {
            this.xOffset=targetCam.getXOffset();
            this.yOffset=targetCam.getYOffset();
        }
    }

    private void enableStickyCam() {
        for(var cam: cams) {
            if(cam!=targetCam) {
                cam.enabled=false;
            }
        }
    }

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }
}