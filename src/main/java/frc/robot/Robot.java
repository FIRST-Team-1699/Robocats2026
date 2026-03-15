// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;

import com.ctre.phoenix6.HootAutoReplay;
import com.pathplanner.lib.auto.AutoBuilder;

import java.util.Optional;

import org.littletonrobotics.junction.LogDataReceiver;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.utils.vision.RobotPose;

public class Robot extends LoggedRobot {
  private Command autoCommand;

  private final SendableChooser<String> autoChooser;

  private final String doNothing = "doNothing";
  private final String shootTop = "Shoot-Top";
  private final String shootMiddle = "Shoot-Middle";
  private final String shootBottom = "Shoot-Bottom";
  private final String depo = "DEPO";
  private final String hp = "HP";
  private final String topNeutral = "Top-Neutral";

  private Optional<Alliance> lastAlliance;
  private String selectedAutoString;

  private final RobotContainer robotContainer;
  private final HootAutoReplay timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

  public Robot() {
    robotContainer = new RobotContainer();
    autoChooser = new SendableChooser<>();
    autoChooser.addOption("Do Nothing:", doNothing);
    autoChooser.addOption(shootTop, shootTop);
    autoChooser.addOption(shootBottom, shootBottom);
    autoChooser.addOption(shootMiddle, shootMiddle);
    autoChooser.addOption(depo, depo);
    autoChooser.addOption(hp, hp);
    autoChooser.addOption(topNeutral, topNeutral);

    SmartDashboard.putData(autoChooser);

    lastAlliance = DriverStation.getAlliance();
    selectedAutoString = autoChooser.getSelected();
    
    // Not the best practice, but will not throw error if doNothing is selected
    autoCommand = AutoBuilder.buildAuto(autoChooser.getSelected());
    
    Logger.addDataReceiver(new NT4Publisher());

    Logger.start();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    RobotPose.periodic();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    // schedule the autonomous command (example)
    // if (autoCommand!= null) {
      // CommandScheduler.getInstance().schedule(autoCommand);
      CommandScheduler.getInstance().schedule(autoCommand);
    // }
  }
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (autoCommand != null) {
            CommandScheduler.getInstance().cancel(autoCommand);
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}
}
