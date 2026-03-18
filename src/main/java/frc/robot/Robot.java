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
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.Constants.AutoConstants;
import frc.utils.vision.RobotPose;

public class Robot extends LoggedRobot {
  private Command autoCommand;

  private SendableChooser<String> autoChooser;

  private Optional<Alliance> lastAlliance;
  private String selectedAutoString;

  private final RobotContainer robotContainer;
  private final HootAutoReplay timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

  public Robot() {
    robotContainer = new RobotContainer();
    autoChooser = new SendableChooser<String>();
    autoChooser.addOption(AutoConstants.doNothing, AutoConstants.doNothing);
    autoChooser.addOption(AutoConstants.shootTop, AutoConstants.shootTop);
    autoChooser.addOption(AutoConstants.shootBottom, AutoConstants.shootBottom);
    autoChooser.addOption(AutoConstants.shootMiddle, AutoConstants.shootMiddle);
    autoChooser.addOption(AutoConstants.shootMiddleMotion, AutoConstants.shootMiddleMotion);
    autoChooser.addOption(AutoConstants.depo, AutoConstants.depo);
    autoChooser.addOption(AutoConstants.hp, AutoConstants.hp);
    autoChooser.addOption(AutoConstants.topNeutral, AutoConstants.topNeutral);
    
    autoChooser.setDefaultOption(AutoConstants.shootMiddle, AutoConstants.shootMiddle);
    SmartDashboard.putData(autoChooser);

    lastAlliance = DriverStation.getAlliance();
    selectedAutoString = autoChooser.getSelected();
    autoCommand = AutoBuilder.buildAuto(autoChooser.getSelected());
    // autoCommand = robotContainer.getAutoCommand(autoChooser.getSelected());
    
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
  public void disabledPeriodic() {
    if(!DriverStation.getAlliance().equals(lastAlliance) || !autoChooser.getSelected().equalsIgnoreCase(selectedAutoString)) {
      lastAlliance = DriverStation.getAlliance();
      selectedAutoString = autoChooser.getSelected();
      // autoCommand = robotContainer.getAutoCommand(selectedAutoString);
      autoCommand = AutoBuilder.buildAuto(autoChooser.getSelected());
    }
  }

  @Override
  public void autonomousInit() {
    if (autoCommand!= null) {
      CommandScheduler.getInstance().schedule(autoCommand);
    }
  }
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
      if (autoCommand != null) {
        CommandScheduler.getInstance().cancel(autoCommand);
        RobotContainer.isAimingAtHub=false;
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
