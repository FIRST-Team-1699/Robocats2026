// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.IntakePivotSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final CommandXboxController driverController =
    new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController operatorController =
      new CommandXboxController(OIConstants.kOperatorControllerPort);

  private final IntakePivotSubsystem intakePivot = new IntakePivotSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  public RobotContainer() {
    configureBindings();
  }
  private void configureBindings() {
    operatorController.povUp()
      .onTrue(intakePivot.setRaw(0.2))
      .onFalse(intakePivot.setRaw(0));
    operatorController.povUp()
      .onTrue(intakePivot.setRaw(-0.2))
      .onFalse(intakePivot.setRaw(0));

    operatorController.leftBumper()
      .onTrue(intake.setSpeed(IntakeSpeeds.INTAKE))
      .onFalse(intake.setSpeed(IntakeSpeeds.STORED));
    operatorController.rightBumper()
      .onTrue(intake.setSpeed(IntakeSpeeds.OUTTAKE))
      .onFalse(intake.setSpeed(IntakeSpeeds.STORED));
  }

  public Command getAutonomousCommand() {
    // placeholder
    return intake.runOnce(() -> System.out.println("Running auto..."));
  }
}
