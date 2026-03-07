package frc.team1699.subsystems;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;
import frc.utils.WaypointManagement.*;

public class VisionSubsystem extends SubsystemBase {
    public static TagWaypoint currentWaypoint;
    public static double currentAmbiguity;
    private boolean isLeft;
    private int targetTagId;

    private Camera cam2, cam1;
    private CameraHandler camHandler;
    private boolean hasTag;
    private double yaw, x, y, z, xWaypointOffset, yWaypointOffset, distanceToTag; // yawCameraOffset;

    public VisionSubsystem () {
        cam1 = new Camera(
            VisionConstants.kCamOneName,
            new Transform3d(
                new Translation3d(
                    VisionConstants.cam1XOffset,
                    VisionConstants.cam1YOffset,
                    0
                ),
                new Rotation3d()
            )
        );
        cam2 = new Camera(
            VisionConstants.kCamTwoName,
            new Transform3d(
                new Translation3d(
                    VisionConstants.cam2XOffset,
                    VisionConstants.cam2YOffset,
                    0
                ),
                new Rotation3d()
            )
        );
        camHandler= new CameraHandler(cam1,cam2);

        PortForwarder.add(5800, "photonvision.local:5800", 5800);
        currentWaypoint=TagWaypoint.CAMERA_TUNE;
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean getHasTag() {
        return this.hasTag;
    }

    public double getDistanceToTag() {
        return this.distanceToTag;
    }

    public void setYawOnWaypoint() {
        double tempDegrees = (Math.atan((this.x)/(this.y))*180/Math.PI);
        this.yaw= (tempDegrees <0.0 ? tempDegrees + 90 : tempDegrees -90 );
    }


    public void setDistanceToTag() {
        double tempDistance = Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0);
        this.distanceToTag = Math.sqrt(tempDistance);
    }

    public void setWaypoint(TagWaypoint waypoint) {
        currentWaypoint = waypoint;
    }

    public boolean isInTolerance() {
        return Math.abs(this.getYaw()) < VisionConstants.kTolerance;
    }

    PhotonTrackedTarget bestTag;
    @Override
    public void periodic() {
        if (currentWaypoint==TagWaypoint.NONE) {
            return;
        }
        bestTag=camHandler.getBestTag();
        if (bestTag!=null) {
            this.targetTagId=bestTag.getFiducialId();

            this.hasTag=true;
            // TODO: DECIDE IF SWITCHING TO TAG -> CAM
            this.x=bestTag.getBestCameraToTarget().getX() + this.camHandler.getXOffset();
            this.y=bestTag.getBestCameraToTarget().getY() + this.camHandler.getYOffset();
            this.z=bestTag.getBestCameraToTarget().getZ();

            // TODO: Verify with old code
            this.isLeft=
                this.bestTag.getBestCameraToTarget().inverse().getY() 
                    - Math.cos(bestTag.getYaw())*this.camHandler.getYOffset() > 0;

            this.xWaypointOffset=currentWaypoint.waypoint.getOffset(this.targetTagId).getX();
            // TODO: FIX LOGIC SOMEWHERE HERE
            this.yWaypointOffset= isLeft ?
                currentWaypoint.waypoint.getOffset(this.targetTagId).getY() :
                -currentWaypoint.waypoint.getOffset(this.targetTagId).getY();

            this.y+=yWaypointOffset;
            this.x+=xWaypointOffset;


            setYawOnWaypoint();
            setDistanceToTag();
            SmartDashboard.putNumber("X: ", this.x);
            SmartDashboard.putNumber("Y: ", this.y);
            SmartDashboard.putNumber("Recorded Yaw: ", bestTag.getYaw());
            SmartDashboard.putNumber("Actual Yaw: ", this.yaw);
            SmartDashboard.putBoolean("is Left", isLeft);
            SmartDashboard.putBoolean("is In Tolerance", isInTolerance());
            return;
        } 
        currentAmbiguity=1;
        this.hasTag=false;
    }

    public void disableStickyCam() {
        camHandler.disableStickyCam();
    }


    public enum TagWaypoint {
        NONE(),
        CAMERA_TUNE(new Waypoint(    
            new AprilTagPoint(3,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_HUB(new Waypoint(
            new AprilTagPoint(18,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(27,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(26,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(25,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(24,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(21,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_SHUFFLE_TOP(new Waypoint(
            new AprilTagPoint(17,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(19,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_SHUFFLE_BOTTOM(new Waypoint(
            new AprilTagPoint(20,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(22,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_HP(new Waypoint(
            new AprilTagPoint(29,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_GROUND_INTAKE(new Waypoint(
            new AprilTagPoint(23,  new Pose2d(0,0, new Rotation2d()))
        )),
        BLUE_CLIMB(new Waypoint(
            new AprilTagPoint(31,  new Pose2d(0,0, new Rotation2d()))
        )),

        RED_HUB(new Waypoint(
            new AprilTagPoint(5,  new Pose2d(0.46,0, new Rotation2d())),
            new AprilTagPoint(8,  new Pose2d(0.46,-.323, new Rotation2d())),
            new AprilTagPoint(9,  new Pose2d(0.46,0.352, new Rotation2d())),
            new AprilTagPoint(10,  new Pose2d(0.46,0, new Rotation2d())),
            new AprilTagPoint(11,  new Pose2d(0.46,0.352, new Rotation2d())),
            new AprilTagPoint(2,  new Pose2d(0.46,0, new Rotation2d())),
            new AprilTagPoint(11,  new Pose2d(0.46,0.352, new Rotation2d())),
            new AprilTagPoint(2,  new Pose2d(0.46,0, new Rotation2d())),
            new AprilTagPoint(3,  new Pose2d(0.46,0.352, new Rotation2d())),
            new AprilTagPoint(4,  new Pose2d(0.46,0, new Rotation2d()))
        )),
        RED_SHUFFLE_TOP(new Waypoint(
            new AprilTagPoint(6,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(4,  new Pose2d(0,0, new Rotation2d()))
        )),
        RED_SHUFFLE_BOTTOM(new Waypoint(
            new AprilTagPoint(3,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(1,  new Pose2d(0,0, new Rotation2d()))
        )),
        RED_HP(new Waypoint(
            new AprilTagPoint(13,  new Pose2d(0,0, new Rotation2d()))
        )),
        RED_GROUND_INTAKE(new Waypoint(
            new AprilTagPoint(7,  new Pose2d(0,0, new Rotation2d()))
        )),
        RED_CLIMB(new Waypoint(
            new AprilTagPoint(15,  new Pose2d(0,0, new Rotation2d()))
        )),
        
        NEUTRAL_TOP(new Waypoint(
            new AprilTagPoint(6,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(17,  new Pose2d(0,0, new Rotation2d()))
        )),
        NEUTRAL_BOTTOM(new Waypoint(
            new AprilTagPoint(1,  new Pose2d(0,0, new Rotation2d())),
            new AprilTagPoint(22,  new Pose2d(0,0, new Rotation2d()))
        ));
        public Waypoint waypoint;
        TagWaypoint(Waypoint waypoint) {
            this.waypoint = waypoint;
        }
        TagWaypoint() {
            this.waypoint = new Waypoint(new AprilTagPoint(0,  new Pose2d(0,0, new Rotation2d())));
        }
    }
}