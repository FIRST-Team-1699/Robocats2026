// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

public final class Constants {
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
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

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
    public static final StaticFeedforwardSignValue kFeedForward = StaticFeedforwardSignValue.UseVelocitySign;

    // TODO: TUNE
    // DOWN

    // NEED
    public static final double kS0 = 5.481;
    public static final double kV0 = -15.146;
    public static final double kA0 = 2.1944;
    public static final double kP0 = 0;
    public static final double kI0 = 0;
    public static final double kD0 = 0;
    public static final double kG = 11.274;



    
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

    public static final double kMotionMagicVelocity = .5;
    public static final double kMotionMagicAcceleration = .1;
    public static final double kMotionMagicJerk = 0;

    // TODO: SET
    public static final double kPositionConversionFactor = 20;
    // TODO: SET
    // public static final double kOffset=-1;

    public static final double rotorToSensor=1;

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
}
