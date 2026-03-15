// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degree;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;

public final class Constants {
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  }

  public static class ShooterHoodConstants {
    // MOTOR CONFIGS
    public static final int kLeadMotorID=5;
    public static final int kFollowMotorID=49;

    public static final int kFeedbackID=48;
    public static final FeedbackSensorSourceValue kFeedbackSensorSource= FeedbackSensorSourceValue.RemoteCANcoder;
    public static final SensorDirectionValue kEncoderDirection = SensorDirectionValue.Clockwise_Positive;

    public static final double kForwardLimit=0.9;
    public static final double kReverseLimit=-0.9;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Coast;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 1.8;
    public static final double kV = 1;
    public static final double kA = 0.2;
    public static final double kP = 3.5;
    public static final double kI = 0.2;
    public static final double kD = 1.0;

    public static final double kMotionMagicVelocity = 40;
    public static final double kMotionMagicAcceleration = 8;
    public static final double kMotionMagicJerk = 0;

    public static final double kPositionConversionFactor = 0.25;
    public static final double kMagnetOffset=0.0833-.078;

    // EVERYTHING ELSE
    public static final double kTolerance=0.05;
  }

  public static class ShooterConstants {
    public static final int kTopMotorID=8;
    public static final int kBottomMotorID=9;

    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Coast;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 0.037131;
    public static final double kV = 0.23239;
    public static final double kA = 0.041348;
    public static final double kP = 0.35148;
    public static final double kI = 0;
    public static final double kD = 0.0;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    public static final double kPositionConversionFactor = 2.0;
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }
    public static class IndexerConstants {
    public static final int kLeadMotorID=57;

    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 0.2421875;
    public static final double kV = 3;
    public static final double kA = 2;
    public static final double kP = 80;
    public static final double kI = 0;
    public static final double kD = 4;
    public static final double kG0 = 0.5;

    public static final double kS1 = 0;
    public static final double kV1 = 0.0;
    public static final double kA1 = 0.0;
    public static final double kP1 = 0.0;
    public static final double kI1 = 0;
    public static final double kD1 = 0.0;

    public static final double kMotionMagicVelocity = 2560;
    public static final double kMotionMagicAcceleration = 20;
    public static final double kMotionMagicJerk = 4;

    public static final double kPositionConversionFactor = 1;
    public static final double rotorToSensor=45;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class HopperConstants {
    public static final int kLeadMotorID=45;

    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 4.8;
    public static final double kI = 0;
    public static final double kD = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    public static final double kPositionConversionFactor = 1;
    public static final double kOffset=0.0;

    public static final double kTolerance=2.0;
  }

  public static class BeamBreakConstants {
    public static final int kSensorID = 47;
    public static final double kHasBallInRange = 500;
    public static final double kTimeDelay= 24;
  }
  public static class IntakePivotConstants {
    // MOTOR CONFIGS
    public static final int kLeadMotorID=46;
    public static final int kFollowerMotorID=53;

    public static final int kFeedbackID=7;
    public static final FeedbackSensorSourceValue kFeedbackSensorSource= FeedbackSensorSourceValue.RemoteCANcoder;

    public static final double kForwardLimit=0.7;
    public static final double kReverseLimit=-0.7;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    // public static final SensorDirectionValue kFeedbackDirection = SensorDirectionValue.Clockwise_Positive;

    // public static final double kMagnetOffset = 2.472; //-.535

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS0 = 0;
    public static final double kV0 = 25;
    public static final double kA0 = 0;
    public static final double kP0 = 0;
    public static final double kI0 = 0;
    public static final double kD0 = 0;
    public static final double kG0 = 0.25;

    public static final double kS1 = 0;
    public static final double kV1 = 10;
    public static final double kA1 = 0;
    public static final double kP1 = 0;
    public static final double kI1 = 0;
    public static final double kD1 = 0;
    public static final double kG1 = 0.25;

    public static final double kMotionMagicVelocity = 2560;
    public static final double kMotionMagicAcceleration = 20;
    public static final double kMotionMagicJerk = 4;

    public static final double kPositionConversionFactor = 50;
    public static final double kGravityOffset = -.21
    ;

    public static final double rotorToSensor=1;

    // EVERYTHING ELSE
    public static final double kTolerance=0.02;
    public static final double kCooldownTimer=1;
  }

  public static class IntakeConstants {
    public static final int kTopMotorID=55;
    public static final int kBottomMotorID=56;

    public static final double kForwardLimit=.8;
    public static final double kReverseLimit=-.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.CounterClockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 4.8;
    public static final double kI = 0;
    public static final double kD = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    public static final double kPositionConversionFactor = 1;
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=3.0;
  }
    public static class ClimbConstants {
    // MOTOR CONFIGS
    public static final int kLeadMotorID=6;

    public static final double kForwardLimit=0.6;
    public static final double kReverseLimit=-0.6;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Coast;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 9.6;
    public static final double kI = 0;
    public static final double kD = 0.1;
    public static final double kG = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    public static final double kPositionConversionFactor = 57;
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;

  }
  public static class VisionConstants {
    // Maps Distance as key, velocity (rotations/sec) of top to value
    public static final InterpolatingDoubleTreeMap speedTopMap =
        new InterpolatingDoubleTreeMap();

    // Maps Distance as key, velocity (rotations/sec) of bottom to value
    public static final InterpolatingDoubleTreeMap speedBottomMap =
        new InterpolatingDoubleTreeMap(); 

    // Maps Distance as key, position (rotations) of top to value
    public static final InterpolatingDoubleTreeMap shootPivotMap = 
        new InterpolatingDoubleTreeMap();

    // Photon Vision Constants
    public static final String kLeftCamName = "Left_Cam";
    public static final String kRightCamName = "Right_Cam";

    // Position of camera relative to the center of the bot. Used for PhotonPoseEstimator
    // in Camera.java
    public static final Transform3d kRightCamOffset = 
      new Transform3d(
        new Translation3d(
            0,
            -Units.inchesToMeters(11.5),
            Units.inchesToMeters(20.5)
        ),
        new Rotation3d(
            Angle.ofRelativeUnits(0, Degree),
            Angle.ofRelativeUnits(20, Degree),
            Angle.ofRelativeUnits(0, Degree)
        )
    );

    public static final Transform3d kLeftCamOffset = 
      new Transform3d(
        new Translation3d(
            0,
            Units.inchesToMeters(11.5),
            Units.inchesToMeters(20.625)
        ),
        new Rotation3d(
            Angle.ofRelativeUnits(0, Degree),
            Angle.ofRelativeUnits(20, Degree),
            Angle.ofRelativeUnits(0, Degree)
        )
    );

    public static final double ambiguityTolerance=0.40;
    public static final Rotation2d kHeadingTolerance=Rotation2d.fromDegrees(5);
    public static final AprilTagFieldLayout kTagLayout =
      AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltAndymark);

    // TODO: DEBUG FOR NOISE
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);

    // TODO: Update values to score to hub
    static {
        // speedTopMap.put(3.0, -37.0);
        // speedTopMap.put(2.15, -35.0);
        // speedTopMap.put(1.37, -28.0);

        // speedBottomMap.put(3.0, -30.0);
        // speedBottomMap.put(2.15, -30.0);
        // speedBottomMap.put(1.37, -23.0);

        // shootPivotMap.put(3.0, .15);
        // shootPivotMap.put(2.15, .2);
        // shootPivotMap.put(1.37, .39);

        speedTopMap.put(.8, -30.0);
        speedTopMap.put(1.6, -30.0);
        speedTopMap.put(2.4, -31.0);
        speedTopMap.put(3.2, -33.0);
        speedTopMap.put(4.0, -36.0);
        speedTopMap.put(4.8, -44.0);

        speedBottomMap.put(.8, -25.0);
        speedBottomMap.put(1.6, -25.0);
        speedBottomMap.put(2.4, -26.0);
        speedBottomMap.put(3.2, -28.0);
        speedBottomMap.put(4.0, -31.0);
        speedBottomMap.put(4.8, -37.0);

        shootPivotMap.put(.8, .45);
        shootPivotMap.put(1.6, .25);
        shootPivotMap.put(2.4, .05);
        shootPivotMap.put(3.2, 0.01);
        shootPivotMap.put(4.0, 0.01);
        shootPivotMap.put(4.8, 0.01);

    }
  }
}
