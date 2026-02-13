// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final CommandXboxController driverController =
    new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController operatorController =
      new CommandXboxController(OIConstants.kOperatorControllerPort);

  private final ShooterHoodSubsystem shootHood = new ShooterHoodSubsystem();
  private final ShooterSubsystem shoot = new ShooterSubsystem();
  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    operatorController.povUp()
      .onTrue(shootHood.setRaw(0.2))
      .onFalse(shootHood.setRaw(0));
    operatorController.povUp()
      .onTrue(shootHood.setRaw(-0.2))
      .onFalse(shootHood.setRaw(0));

    operatorController.leftBumper()
      .onTrue(shoot.setSpeed(ShootingSpeeds.INTAKE))
      .onFalse(shoot.setSpeed(ShootingSpeeds.STORED));
    operatorController.rightBumper()
      .onTrue(shoot.setSpeed(ShootingSpeeds.OUTTAKE))
      .onFalse(shoot.setSpeed(ShootingSpeeds.STORED));
  }

  // public Command getAutonomousCommand() {
  //
  // }
}
