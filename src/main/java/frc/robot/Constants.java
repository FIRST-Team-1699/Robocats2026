// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

public final class Constants {
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
  }
  public static class IndexerConstants {
    public static final int kLeadMotorID=-1;

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
    public static final double kPositionConversionFactor = -1;
    // TODO: SET
    public static final double kOffset=0.0;

    // EVERYTHING ELSE
    public static final double kTolerance=2.0;
  }

  public static class BeamBreakConstants {
        public static final int kSensorID = 47;
        public static final double kHasBallInRange = 100;
        public static final double kTimeDelay= 24;
  }
}
