package frc.team1699.subsystems;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;
import frc.utils.vision.*;

public class VisionSubsystem extends SubsystemBase {
    public static TagWaypoint currentWaypoint;
    public static double currentAmbiguity;
    private Camera leftCam, rightCam;
    private Camera[] cams;
    private boolean hasTag;
    private double distanceToScore, yaw;
    private Pose3d estimatedPose;
    private EstimateConsumer estimateConsumer;

    @FunctionalInterface
    public static interface EstimateConsumer {
        public void accept(Pose2d pose, double timestamp, Matrix<N3, N1> estimationStdDevs);
    }

    public VisionSubsystem (EstimateConsumer estimateConsumer) {
        this.estimateConsumer=estimateConsumer;
        leftCam = new Camera(
            VisionConstants.kLeftCamName,
            VisionConstants.kLeftCamOffset
        );
        rightCam = new Camera(
            VisionConstants.kRightCamName,
            VisionConstants.kRightCamOffset
        );
        cams= new Camera[]{rightCam,leftCam};

        PortForwarder.add(5800, "photonvision.local:5800", 5800);
        currentWaypoint = TagWaypoint.NONE;
    }

    public double getYaw() {
        return this.estimatedPose.getRotation().getZ();
    }

    public double getX() {
        return this.estimatedPose.getX();
    }

    public double getY() {
        return this.estimatedPose.getY();
    }

    public double getZ() {
        return this.estimatedPose.getZ();
    }

    public boolean getHasTag() {
        return this.hasTag;
    }

    public double getDistanceToScore() {
        return this.distanceToScore;
    }

    public void setYawOnWaypoint() {
        // double tempDegrees = (Math.atan((this.x)/(this.y))*180/Math.PI);
        // this.yaw= (tempDegrees <0.0 ? tempDegrees + 90 : tempDegrees -90 );
        yaw = Math.atan2(
            Math.abs(getY()-currentWaypoint.pose.getY()),
            Math.abs(getX()-currentWaypoint.pose.getX())
        );
    }


    public void setDistanceToScore() {
        distanceToScore = Math.sqrt(
            Math.pow(Math.abs(getX()-currentWaypoint.pose.getX()),2)
            + Math.pow(Math.abs(getY()-currentWaypoint.pose.getY()),2)
        );
    }

    public void setWaypoint(TagWaypoint waypoint) {
        currentWaypoint = waypoint;
    }

    public boolean isInTolerance() {
        return Math.abs(this.getYaw()) < VisionConstants.kTolerance;
    }

    private int cooldown=3;
    private int currentItteration=0;

    @Override
    public void periodic() {
        if (currentWaypoint==TagWaypoint.NONE || cooldown< currentItteration ) {
            currentItteration++;
            return;
        }
        currentItteration=0;
        for(Camera cam : cams) {
            cam.updateVisionEstimate();
            cam.getVisionEstimate().ifPresent(
                est -> {
                    // Change our trust in the measurement based on the tags we can see
                    estimatedPose = est.estimatedPose;
                    var estStdDevs = cam.getEstimationStdDevs();

                    estimateConsumer.accept(est.estimatedPose.toPose2d(), est.timestampSeconds, estStdDevs);
                });
        }

        SmartDashboard.putNumber("Current X Position on left Cam: ", estimatedPose.toPose2d().getX());
        SmartDashboard.putNumber("Current Y Position on left Cam: ", estimatedPose.toPose2d().getY());
        SmartDashboard.putNumber("Current Yaw Position on left Cam: ", estimatedPose.getRotation().getZ());

        SmartDashboard.putNumber("Current Yaw to waypoint", yaw);
        SmartDashboard.putNumber("Current Distance to waypoint", yaw);
    }


    public enum TagWaypoint {
        NONE(),
        RED_HUB(VisionConstants.kRedHubPose);

        public Pose2d pose;
        TagWaypoint(Pose2d pose) {
            this.pose = pose;
        }
        TagWaypoint() {
            this.pose =new Pose2d();
        }
    }
}