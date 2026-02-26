// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

public final class Constants {
  public static class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
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
