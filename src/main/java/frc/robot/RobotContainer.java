// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
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
      .onTrue(shoot.setRaw(0.2, 0.2))
      .onFalse(shoot.stopAll());
    operatorController.rightBumper()
      .onTrue(shoot.setRaw(-0.2, -0.2))
      .onFalse(shoot.stopAll());
  }

  public Command getAutonomousCommand() {
    // placeholder
    return shoot.runOnce(() -> System.out.println("Running auto..."));
  }
}
