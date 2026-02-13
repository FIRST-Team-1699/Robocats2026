// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

  public static class ShooterHoodConstants {
    // MOTOR CONFIGS
    // TODO: SET
    public static final int kLeadMotorID=-1;
    public static final int kFollowMotorID=-1;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kInverted= InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;
    public static final MotorAlignmentValue kFollowInverted= MotorAlignmentValue.Opposed;

    public static final GravityTypeValue kGravityCounter = GravityTypeValue.Arm_Cosine;
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

    // TODO: VERIFY
    public static final double kPositionConversionFactor = 360/81;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class ShooterConstants {
    public static final int kTopMotorID=-1;
    public static final int kBottomMotorID=-1;

    // TODO: TUNE
    public static final double kForwardLimit=0.8;
    public static final double kReverseLimit=-0.8;

    public static final InvertedValue kTopInverted= InvertedValue.Clockwise_Positive;
    public static final InvertedValue kBottomInverted= InvertedValue.Clockwise_Positive;

    public static final NeutralModeValue kNeutral= NeutralModeValue.Brake;

    // TODO: DETERMINE IF SETTING THIS VALUE WILL FIX FUTURE PROBLEM OR CREATE ONE
    // public static final GravityTypeValue kGravityCounter = GravityTypeValue.Elevator_Static;
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
    public static final double kPositionConversionFactor = -1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }
}
