// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.jar.Attributes.Name;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.*;
import frc.robot.swerve.*;
import frc.team1699.commands.AgitateCommand;
import frc.team1699.commands.CloseShootCommand;
import frc.team1699.commands.FarShootCommand;
import frc.team1699.commands.ShootCommand;
import frc.team1699.commands.ShuffleCommand;
import frc.team1699.subsystems.*;
import frc.team1699.subsystems.ClimbSubsystem.ClimbPosition;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.utils.vision.AllianceFlip;
import frc.utils.vision.RobotPose;

public class RobotContainer {
  public static boolean isAimingAtHub=false;

  private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  // private double MaxSpeed = 1.0 *
  // TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts
  // desired top speed
  private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top
                                                                                      // speed
  private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max
                                                                                    // angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      // .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) //
      // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
      // .withDeadband(.1);

  private final SwerveRequest.FieldCentric driveJoysticks = drive
      .withDeadband(MaxSpeed * 0.12)
      // .withRotationalDeadband(MaxAngularRate * 0.1) //
      // Add a 10% deadband
      .withVelocityX(-driverController.getLeftY()* MaxSpeed)
      .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
      .withRotationalRate(-driverController.getRightX()* MaxAngularRate); // Drive counterclockwise with negative X (left)

  private final SwerveRequest.FieldCentricFacingAngle driveFacingHub = new SwerveRequest.FieldCentricFacingAngle()
      .withDeadband(MaxSpeed * 0.12)
      // Add a 10% deadband
      .withVelocityX(
          - driverController.getLeftY() * MaxSpeed)
      .withVelocityY(
              - driverController.getLeftX() * MaxSpeed) // Drive counterclockwise with negative X (left)
      .withHeadingPID(5, 0, 0);

  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
  private final IndexerSubsystem indexer = new IndexerSubsystem();
  private final HopperSubsystem hopper = new HopperSubsystem();
  private final ShooterHoodSubsystem shootHood = new ShooterHoodSubsystem();
  private final ShooterSubsystem shoot = new ShooterSubsystem();
  private final IntakePivotSubsystem intakePivot = new IntakePivotSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final ClimbSubsystem climb = new ClimbSubsystem();
  private final VisionSubsystem vision = new VisionSubsystem(drivetrain::addVisionMeasurement);

  private Command autoShootCommand = new ShootCommand(shoot, shootHood, indexer, hopper, intake, true);
  private Command autoCloseShootCommand = new CloseShootCommand(shoot, shootHood, indexer, hopper, intake);

  private Command shootOnFlyCommand = new ShootCommand(
        shoot, 
        shootHood, 
        indexer, 
        hopper, 
        intake, 
        .25
    );

  public RobotContainer() {
    NamedCommands.registerCommand("AimToHub", Commands.runOnce(() -> isAimingAtHub=false)
        .andThen(new WaitUntilCommand(() -> RobotPose.facingHub())));
    NamedCommands.registerCommand("ShootCommand", intakePivot.setPositionCommand(IntakePositions.AGITATE)
        .alongWith(autoShootCommand)
        .andThen(() -> isAimingAtHub=false)
        .andThen(intakePivot.setPositionCommand(IntakePositions.GROUND_INTAKE))
        .andThen(shootHood.setPositionCommand(HoodPositions.MAX)));

    NamedCommands.registerCommand("LastShootCommand", intakePivot.setPositionCommand(IntakePositions.AGITATE)
        .alongWith(autoShootCommand)
        .andThen(() -> isAimingAtHub=false)
        .andThen(intakePivot.setPositionCommand(IntakePositions.GROUND_INTAKE))
        .andThen(shootHood.setPositionCommand(HoodPositions.MAX)));

    NamedCommands.registerCommand("ShootOnFlyCommand", intakePivot.setPositionCommand(IntakePositions.AGITATE)
        .alongWith(shootOnFlyCommand)
        .andThen(() -> isAimingAtHub=false)
        .andThen(intakePivot.setPositionCommand(IntakePositions.GROUND_INTAKE))
        .andThen(shootHood.setPositionCommand(HoodPositions.MAX)));

    NamedCommands.registerCommand("CloseShootCommand", autoCloseShootCommand);
    NamedCommands.registerCommand("ToggleIntake", intakePivot.togglePivotCommand());
        // .andThen(new WaitUntilCommand(() -> intakePivot.isInTolerance())));
    NamedCommands.registerCommand("StartIntake", intake.setSpeedCommand(IntakeSpeeds.INTAKE));
    NamedCommands.registerCommand("StopIntake", intake.setSpeedCommand(IntakeSpeeds.STORED));
    NamedCommands.registerCommand("Wait2s", new WaitCommand(2));

    NamedCommands.registerCommand("WarmupShooter", shoot.setSpeedCommand(ShootingSpeeds.CLOSE));

    NamedCommands.registerCommand("MaxShootHood", shootHood.setPositionCommand(HoodPositions.MAX));

    configureBindings();

    RobotPose.setPoseSupplier(drivetrain::getState);
  }

  private void configureBindings() {
    // Note that X is defined as forward according to WPILib convention,
    // and Y is defined as to the left according to WPILib convention.
    drivetrain.setDefaultCommand(
        // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> 
          isAimingAtHub ? 
            driveFacingHub
              .withTargetDirection(AllianceFlip.flip(RobotPose.getHeadingTowardsHub()))
              .withVelocityX(-driverController.getLeftY()* MaxSpeed * .15)
              .withVelocityY(-driverController.getLeftX() * MaxSpeed * .15) : // Drive left with negative X (left)
            driveJoysticks
              .withVelocityX(-driverController.getLeftY()* MaxSpeed)
              .withVelocityY(-driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
              .withRotationalRate(-driverController.getRightX()* MaxAngularRate) // Drive counterclockwise with negative X (left)
          )
        );

    // Idle while the robot is disabled. This ensures the configured
    // neutral mode is applied to the drive motors while disabled.
    final var idle = new SwerveRequest.Idle();
    RobotModeTriggers.disabled().whileTrue(
        drivetrain.applyRequest(() -> idle).ignoringDisable(true));

    // Reset the field-centric heading on left bumper press.
    driverController.y().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

    // driverController.leftTrigger()
    //     .onTrue(
    //         shootHood.setPositionCommand(HoodPositions.CLIMB)
    //             .alongWith(climb.setPosition(ClimbPosition.EXTENDED))
    // );

    // driverController.rightTrigger()
    //     .onTrue(
    //         shootHood.setPositionCommand(HoodPositions.STORED)
    //             .alongWith(climb.setPosition(ClimbPosition.STORED))
    // );

    // operatorController.leftTrigger().whileTrue(Commands.runOnce(() -> Logger.recordOutput("PIDOutput", driveFacingHub.HeadingController.getLastAppliedOutput())));

    operatorController.rightTrigger()
        .onTrue(
            intake.setSpeedCommand(IntakeSpeeds.OUTTAKE)
        )
        .onFalse(
            intake.setSpeedCommand(IntakeSpeeds.STORED)
        );

    

    drivetrain.registerTelemetry(logger::telemeterize);

    operatorController.a()
        .onTrue(
            intakePivot.togglePivotCommand());

    // operatorController.b()
    //     .onTrue(
    //         new AgitateCommand(intakePivot)
    //     );

    operatorController.b()
        .onTrue(
            intakePivot.setPositionCommand(IntakePositions.AGITATE)
        )
        .onFalse(
            intakePivot.setPositionCommand(IntakePositions.GROUND_INTAKE)
        );

    operatorController.leftTrigger()
        .onTrue(
            intake.toggleSpeedCommand());

    operatorController.x()
        .whileTrue(
            new CloseShootCommand(shoot, shootHood, indexer, hopper, intake));

    operatorController.povUp()
        .whileTrue(
            new FarShootCommand(shoot, shootHood, indexer, hopper, intake));

    // operatorController.povLeft()
    //     .onTrue(
    //         Commands.runOnce(() -> isAimingAtHub=false));

    operatorController.y()
        .whileTrue(
            new ShuffleCommand(shoot, shootHood, indexer, hopper, intake));

    operatorController.leftBumper()
        .onTrue(Commands.runOnce(() -> isAimingAtHub=true))
        .onFalse(Commands.runOnce(() -> isAimingAtHub=false));

    // TODO: DISCUSS DIFFRENCE FROM X IN SPEEDS W/ DRIVERS
    operatorController.rightBumper()
        .whileTrue(
            new ShootCommand(shoot, shootHood, indexer, hopper, intake));

    driverController.b().whileTrue(drivetrain.applyRequest(
        () -> point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))));

    // Run SysId routines when holding back/start and X/Y.
    // Note that each routine should be run exactly once in a single log.
    // driverController.back().and(driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
    // driverController.back().and(driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
    // driverController.start().and(driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
    // driverController.start().and(driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

    drivetrain.registerTelemetry(logger::telemeterize);
  }

    private Command toggleAimToHub() {
        return Commands.runOnce(() -> isAimingAtHub=!isAimingAtHub);
    }

    public void setGroundIntake() {
        intakePivot.setPosition(IntakePositions.GROUND_INTAKE);
    }

    // public Command getAutoCommand(String autoString) {
    //     // if(autoString==null) {
    //     //     return null;
    //     // }
    //     if(autoString==AutoConstants.doNothing) {
    //         return new PrintCommand(autoString);
    //     }
    //     if(autoString==AutoConstants.shootMiddle) {
    //         return new CloseShootCommand(shoot, shootHood, indexer, hopper, intake);
    //     }
    //     return AutoBuilder.buildAuto(autoString);
    // }
}