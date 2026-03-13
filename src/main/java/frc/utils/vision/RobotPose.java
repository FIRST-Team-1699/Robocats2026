package frc.utils.vision;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Configs.VisionConfigs;
import frc.robot.Constants.VisionConstants;
import frc.utils.vision.AllianceFlip;
import frc.utils.vision.FieldConstants;

public class RobotPose {
    private static Supplier<SwerveDriveState> stateSupplier;
    private static Pose2d pose;
    private static Rotation2d headingToHub;

    private static Translation2d hubTranslation;

    private static Alliance lastAlliance;

    // Key is distance in meters, value is speed in rotations / second
    // private static final InterpolatingDoubleTreeMap flywheelMap;
    // Key is distance in meters, value is angle in degrees
    // private static final InterpolatingDoubleTreeMap hoodMap;

    static {
        pose = Pose2d.kZero;
        headingToHub = Rotation2d.kZero;

        hubTranslation = AllianceFlip.flip(FieldConstants.Hub.innerCenterPoint.toTranslation2d());

        DriverStation.getAlliance().ifPresentOrElse(alliance -> lastAlliance = alliance, () -> lastAlliance = null);

    //     flywheelMap = new InterpolatingDoubleTreeMap();
    //     flywheelMap.put(-1.0, 0.0);

    //     hoodMap = new InterpolatingDoubleTreeMap();
    //     hoodMap.put(-1.0, 0.0);
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

    public static double getFlywheelTopSpeed() {
        return VisionConfigs.speedTopMap.get(hubTranslation.getDistance(pose.getTranslation()));
    }

    public static double getFlywheelBottomSpeed() {
        return VisionConfigs.speedBottomMap.get(hubTranslation.getDistance(pose.getTranslation()));
    }

    public static double getHoodAngle() {
        return VisionConfigs.shootPivotMap.get(hubTranslation.getDistance(pose.getTranslation()));
    }

    public static boolean facingHub() {
        return Math.abs(pose.getRotation().minus(headingToHub).getDegrees()) < VisionConstants.kHeadingTolerance.getDegrees();
    }

    public static double distanceToHub() {
        return hubTranslation.getDistance(pose.getTranslation());
    }
}
