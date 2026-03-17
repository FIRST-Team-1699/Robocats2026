package frc.utils.vision;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.utils.vision.FieldConstants;

public class AllianceFlip {
    public static boolean shouldFlip() {
        return DriverStation.getAlliance().isPresent()
            && DriverStation.getAlliance().get() == Alliance.Red;
    }

    public static Translation2d flip(Translation2d position) {
        if(shouldFlip()) {
            return new Translation2d(
                FieldConstants.fieldLength,
                FieldConstants.fieldWidth
            ).minus(position);
        } else {
            return position;
        }
    }

    public static Rotation2d flip(Rotation2d rotation) {
        if (shouldFlip()) {
            return Rotation2d.k180deg.plus(rotation);
        } else {
            return rotation;
        }
    }

}
