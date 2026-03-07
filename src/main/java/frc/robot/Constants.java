// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

public final class Constants {
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    // public static final int kOperatorControllerPort = 1;
  }

  public static class ShooterHoodConstants {
    // MOTOR CONFIGS
    // TODO: SET
    public static final int kLeadMotorID=5;
    public static final int kFollowMotorID=49;

    public static final int kFeedbackID=48;
    public static final FeedbackSensorSourceValue kFeedbackSensorSource= FeedbackSensorSourceValue.RemoteCANcoder;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Coast;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    public static final double kS = 0.3;
    public static final double kV = 2.4;
    public static final double kA = 0.0;
    public static final double kP = 200;
    public static final double kI = 0;
    public static final double kD = 0.0;

    public static final double kMotionMagicVelocity = 6400;
    public static final double kMotionMagicAcceleration = 3000;
    public static final double kMotionMagicJerk = 1600;

    // TODO: VERIFY
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=4;
  }

  public static class ShooterConstants {
    public static final int kTopMotorID=8;
    public static final int kBottomMotorID=9;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Coast;

    // TODO: DETERMINE IF SETTING THIS VALUE WILL FIX FUTURE PROBLEM OR CREATE ONE
    // public static final GravityTypeValue kGravityCounter = GravityTypeValue.Elevator_Static;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    public static final double kS = .22;
    public static final double kV = 0.185;
    public static final double kA = 0.0;
    public static final double kP = .5;
    public static final double kI = 0;
    public static final double kD = 0.0;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    // TODO: SET
    public static final double kPositionConversionFactor = 2.0;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }
    public static class IndexerConstants {
    public static final int kLeadMotorID=57;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    // DOWN

    // NEED
    // public static final double kS0 = 5.481;
    // public static final double kV0 = -15.146;
    // public static final double kA0 = 2.1944;
    // public static final double kP0 = 0;
    // public static final double kI0 = 0;
    // public static final double kD0 = 0;
    // public static final double kG = 11.274;

    // public static final double kS0 = 0.172;
    // public static final double kV0 = 1.77;
    // public static final double kA0 = 0.467;
    // public static final double kP0 = 0;
    // public static final double kI0 = 0;
    // public static final double kD0 = 0;
    // public static final double kG = 0.0232;


    public static final double kS0 = 0.2421875;
    public static final double kV0 = 3;
    public static final double kA0 = 2;
    public static final double kP0 = 80;
    public static final double kI0 = 0;
    public static final double kD0 = 4;
    public static final double kG0 = 0.5;


    // UP
    // public static final double kS1 = -5;
    // public static final double kV1 = 0.12;
    // public static final double kA1 = 0.01;
    // public static final double kP1 = 0.8;
    // public static final double kI1 = 0;
    // public static final double kD1 = 0.1;

    public static final double kS1 = 0;
    public static final double kV1 = 0.0;
    public static final double kA1 = 0.0;
    public static final double kP1 = 0.0;
    public static final double kI1 = 0;
    public static final double kD1 = 0.0;

    public static final double kMotionMagicVelocity = 2560;
    public static final double kMotionMagicAcceleration = 20;
    public static final double kMotionMagicJerk = 4;

    // TODO: SET
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    // public static final double kOffset=-1;

    public static final double rotorToSensor=45;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class IntakeConstants {
    public static final int kTopMotorID=55;
    public static final int kBottomMotorID=56;

    // TODO: TUNE
    public static final double kForwardLimit=.8;
    public static final double kReverseLimit=-.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.CounterClockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 4.8;
    public static final double kI = 0;
    public static final double kD = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    // TODO: SET
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=3.0;
  }

  public static class HopperConstants {
    public static final int kLeadMotorID=45;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 4.8;
    public static final double kI = 0;
    public static final double kD = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    // TODO: SET
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class BeamBreakConstants {
    public static final int kSensorID = 47;
    public static final double kHasBallInRange = 500;
    public static final double kTimeDelay= 24;
  }
  public static class IntakePivotConstants {
    // MOTOR CONFIGS
    // TODO: SET
    public static final int kLeadMotorID=46;

    public static final int kFeedbackID=7;
    public static final FeedbackSensorSourceValue kFeedbackSensorSource= FeedbackSensorSourceValue.RemoteCANcoder;

    // TODO: TUNE
    public static final double kForwardLimit=0.7;
    public static final double kReverseLimit=-0.7;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    public static final SensorDirectionValue kFeedbackDirection = SensorDirectionValue.Clockwise_Positive;

    public static final double kMagnetOffset = 2.472; //-.535

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    // DOWN

    // NEED
    // public static final double kS0 = 5.481;
    // public static final double kV0 = -15.146;
    // public static final double kA0 = 2.1944;
    // public static final double kP0 = 0;
    // public static final double kI0 = 0;
    // public static final double kD0 = 0;
    // public static final double kG = 11.274;

    // public static final double kS0 = 0.172;
    // public static final double kV0 = 1.77;
    // public static final double kA0 = 0.467;
    // public static final double kP0 = 0;
    // public static final double kI0 = 0;
    // public static final double kD0 = 0;
    // public static final double kG = 0.0232;

    public static final double kS0 = 0.2421875;
    public static final double kV0 = 3;
    public static final double kA0 = 2;
    public static final double kP0 = 80;
    public static final double kI0 = 0;
    public static final double kD0 = 4;
    public static final double kG0 = 0.5;

    // UP
    // public static final double kS1 = -5;
    // public static final double kV1 = 0.12;
    // public static final double kA1 = 0.01;
    // public static final double kP1 = 0.8;
    // public static final double kI1 = 0;
    // public static final double kD1 = 0.1;

    public static final double kS1 = 0;
    public static final double kV1 = 0.0;
    public static final double kA1 = 0.0;
    public static final double kP1 = 0.0;
    public static final double kI1 = 0;
    public static final double kD1 = 0.0;

    public static final double kMotionMagicVelocity = 2560;
    public static final double kMotionMagicAcceleration = 20;
    public static final double kMotionMagicJerk = 4;

    // TODO: SET
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    // public static final double kOffset=-1;

    public static final double rotorToSensor=45;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class IntakeConstants {
    public static final int kTopMotorID=55;
    public static final int kBottomMotorID=56;

    // TODO: TUNE
    public static final double kForwardLimit=.8;
    public static final double kReverseLimit=-.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.CounterClockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    public static final double kS = 0.25;
    public static final double kV = 0.12;
    public static final double kA = 0.01;
    public static final double kP = 4.8;
    public static final double kI = 0;
    public static final double kD = 0.1;

    public static final double kMotionMagicVelocity = 80;
    public static final double kMotionMagicAcceleration = 160;
    public static final double kMotionMagicJerk = 1600;

    // TODO: SET
    public static final double kPositionConversionFactor = 1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=3.0;
  }
    public static class ClimbConstants {
    // MOTOR CONFIGS
    // TODO: SET
    public static final int kLeadMotorID=6;

    public static final double kForwardLimit=0.6;
    public static final double kReverseLimit=-0.6;

    public static final InvertedValue kInverted= InvertedValue.CounterClockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
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

    // TODO: SET
    public static final double kPositionConversionFactor = 11.4;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;

  }
  public static class VisionConstants {
    public static final String kCamOneName = "Cool_Cam1";
    public static final String kCamTwoName = "Cool_Cam2";

    public static final double cam1XOffset = 0.0;
    public static final double cam1YOffset = 0.14;
    public static final double cam1YawOffset = 0.0;

    public static final double cam2XOffset = 0;
    public static final double cam2YOffset = -0.38;
    // public static final double cam2YawOffset = 15.0;
    public static final double cam2YawOffset = -25.0;

    public static final double ambiguityTolerance=0.40;
    public static final double kTolerance=0.4;
    // public static InterpolatingDoubleTreeMap score =new InterpolatingDoubleTreeMap();
    public static final AprilTagFieldLayout kTagLayout =
      AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

  }
}
