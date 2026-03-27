package frc.utils.vision;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.VisionConstants;

public class RobotPose {
    private static double shootOffset=0;
    private static Supplier<SwerveDriveState> stateSupplier;
    private static Pose2d pose;
    private static Rotation2d headingToHub;

    private static Translation2d hubTranslation;

    private static Alliance lastAlliance;

    static {
        pose = Pose2d.kZero;
        headingToHub = Rotation2d.kZero;

        hubTranslation = AllianceFlip.flip(FieldConstants.Hub.innerCenterPoint.toTranslation2d());

        DriverStation.getAlliance().ifPresentOrElse(alliance -> lastAlliance = alliance, () -> lastAlliance = null);
    }

    public static void setPoseSupplier(Supplier<SwerveDriveState> supplier) {
        stateSupplier = supplier;
    }

    public static void periodic() {
        refreshPosesIfAllianceChanged();

        if (stateSupplier == null) {
            return;
        }

        pose = stateSupplier.get().Pose;

        headingToHub = new Rotation2d(hubTranslation.getX() - pose.getX(), hubTranslation.getY() - pose.getY());

        Logger.recordOutput("Localization/RobotPose/Pose", pose);
        Logger.recordOutput("Localization/RobotPose/HeadingToHub", headingToHub);

        SmartDashboard.putNumber("Distance to Hub: ", getDistanceToHub());
    }

    private static void refreshPosesIfAllianceChanged() {
        DriverStation.getAlliance().ifPresent(alliance -> {
            if (lastAlliance != alliance) {
                lastAlliance = alliance;

                hubTranslation = AllianceFlip.flip(FieldConstants.Hub.innerCenterPoint.toTranslation2d());
            }
        });
    }

    public static Pose2d getPose() {
        return pose;
    }

    public static Rotation2d getHeadingTowardsHub() {
        return headingToHub;
    }

    public static double getDistanceToHub() {
        return hubTranslation.getDistance(pose.getTranslation());
    }

    public static double getFlywheelTopSpeed() {
        return VisionConstants.speedTopMap.get(hubTranslation.getDistance(pose.getTranslation())+shootOffset);
    }

    public static double getFlywheelBottomSpeed() {
        return VisionConstants.speedBottomMap.get(hubTranslation.getDistance(pose.getTranslation())+shootOffset);
    }

    public static double getHoodAngle() {
        return VisionConstants.shootPivotMap.get(hubTranslation.getDistance(pose.getTranslation())+shootOffset);
    }

    public static void setShootOffset(double offset) {
        shootOffset=offset;
    }

    public static boolean facingHub() {
        return Math.abs(pose.getRotation().minus(headingToHub).getDegrees()) < VisionConstants.kHeadingTolerance.getDegrees();
    }

    public static double distanceToHub() {
        return hubTranslation.getDistance(pose.getTranslation());
    }

    public enum Waypoints {
        NONE(),
        HUB(AllianceFlip.flip(FieldConstants.Hub.innerCenterPoint.toTranslation2d())),
        SHUFFLE_LEFT(),
        SHUFFLE_RIGHT();

        public final Translation2d translation;
        Waypoints() {
            this.translation=new Translation2d();
        }
        Waypoints(Translation2d translation) {
            this.translation = translation;
        }
    }
}
