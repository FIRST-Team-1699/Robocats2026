package frc.team1699.subsystems;

import org.littletonrobotics.junction.Logger;
import org.photonvision.PhotonUtils;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;
import frc.utils.vision.*;



public class VisionSubsystem extends SubsystemBase {
    public static double currentAmbiguity;
    private Camera leftCam, rightCam;
    private Camera[] cams;
    private boolean hasTag;
    private Pose3d estimatedPose = new Pose3d();
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
    }

    public Pose3d getPoseOfBot() {
        return this.estimatedPose;
    }

    public boolean getHasTag() {
        return this.hasTag;
    }

    @Override
    public void periodic() {
        for(Camera cam : cams) {
            cam.updateVisionEstimate();
            cam.getVisionEstimate().ifPresentOrElse(
                est -> {
                    // Change our trust in the measurement based on the tags we can see
                    hasTag=true;
                    estimatedPose = est.estimatedPose;
                    var estStdDevs = cam.getEstimationStdDevs();
                    
                    estimateConsumer.accept(est.estimatedPose.toPose2d(), est.timestampSeconds, estStdDevs);
                },
                () -> {
                    hasTag=false;
                }
            );
        }
    }
}