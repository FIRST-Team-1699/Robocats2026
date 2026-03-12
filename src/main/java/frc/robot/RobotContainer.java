// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.lang.invoke.MethodHandles.Lookup.ClassOption;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.*;
import frc.robot.swerve.*;
import frc.team1699.commands.ShootCommand;
import frc.team1699.subsystems.*;
import frc.team1699.subsystems.ClimbSubsystem.ClimbPosition;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.PivotPositions;
import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.team1699.subsystems.VisionSubsystem.TagWaypoint;
import frc.team1699.commands.*;


public class RobotContainer {
    private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    private final CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

    // private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            // .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
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

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(
                  Math.abs(driverController.getLeftY()) > .1 ?
                  -driverController.getLeftY() * MaxSpeed : 0
                  ) // Drive forward with negative Y (forward)
                  .withVelocityY(
                    Math.abs(driverController.getLeftX()) > .1 ?
                    -driverController.getLeftX() * MaxSpeed : 0
                  ) // Drive left with negative X (left)
                    
                  .withRotationalRate(
                    (
                      Math.abs(driverController.getRightX()) > .1 ?
                        -driverController.getRightX() : 0
                    ) * MaxAngularRate
                  ) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        // driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driverController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driverController.back().and(driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driverController.back().and(driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driverController.start().and(driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driverController.start().and(driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        driverController.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
      /*
              operatorController.povUp()
            .onTrue(climb.setPosition(ClimbPosition.EXTENDED));

        operatorController.povDown()
            .onTrue(climb.setPosition(ClimbPosition.STORED));
            */
  
      /*
          operatorController.povUp()
      .onTrue(intakePivot.setPosition(PivotPositions.STORED));

    operatorController.povDown()
      .onTrue(intakePivot.setPosition(PivotPositions.GROUND_INTAKE));

    operatorController.leftBumper()
      .onTrue(intake.setSpeed(IntakeSpeeds.INTAKE))
      .onFalse(intake.setSpeed(IntakeSpeeds.STORED));
    operatorController.rightBumper()
      .onTrue(intake.setSpeed(IntakeSpeeds.OUTTAKE))
      .onFalse(intake.setSpeed(IntakeSpeeds.STORED));
      */
    // operatorController.povUp()
    //   .onTrue(shootHood.setRaw(0.1))
    //   .onFalse(shootHood.setRaw(0));
    // operatorController.povDown()
    //   .onTrue(shootHood.setRaw(-0.1))
    //   .onFalse(shootHood.setRaw(0));

    operatorController.povUp()
      .onTrue(shootHood.setPositionCommand(HoodPositions.MAX));
    operatorController.povDown()
      .onTrue(shootHood.setPositionCommand(HoodPositions.MIN));

    operatorController.a()
      .onTrue(shootHood.setPositionCommand(HoodPositions.INTERPOLATED));
    operatorController.b()
      .onTrue(shootHood.setPositionCommand(HoodPositions.MIN));

    operatorController.rightTrigger()
        .onTrue(
          intakePivot.setPosition(PivotPositions.GROUND_INTAKE)
            .alongWith(intake.setSpeed(IntakeSpeeds.INTAKE))
        )
        .onFalse(
          intake.setSpeed(IntakeSpeeds.STORED)
        );
      operatorController.rightBumper()
        .onTrue(
          new ShootCommand(shoot, shootHood, indexer, hopper, vision)
        );

      driverController.a()
        .whileTrue(new AimToWaypointCommand(vision, drivetrain, TagWaypoint.RED_HUB));


    // driverController.a()
    //   .onTrue(
    //     shootHood.setPosition(HoodPositions.CLIMB)
    //       .alongWith(climb.setPosition(ClimbPosition.EXTENDED))
    // );

    // driverController.b()
    //   .onTrue(
    //     shootHood.setPosition(HoodPositions.STORED)
    //       .alongWith(climb.setPosition(ClimbPosition.STORED))
    // );


/*
    operatorController.leftBumper()
      .onTrue(shoot.setSpeed(ShootingSpeeds.INTAKE))
      .onFalse(shoot.stopAll());
    operatorController.rightBumper()
      .onTrue(shoot.setSpeed(ShootingSpeeds.OUTTAKE))
      .onFalse(shoot.stopAll());
 */     
        // operatorController.leftBumper()
        //     .onTrue(indexer.indexUntilFull());
        // operatorController.rightBumper()
        //     .onTrue(indexer.setSpeed(IndexingSpeeds.STORED));

        // operatorController.leftBumper()
        //     .onTrue(hopper.setSpeed(HopperSpeeds.INTAKE))
        //     .onFalse(hopper.setSpeed(HopperSpeeds.STORED));
        // operatorController.rightBumper()
        //     .onTrue(hopper.setSpeed(HopperSpeeds.OUTTAKE))
        //     .onFalse(hopper.setSpeed(HopperSpeeds.STORED));  
        // driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driverController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driverController.getLeftY(), -driverController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driverController.back().and(driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driverController.back().and(driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driverController.start().and(driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driverController.start().and(driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        driverController.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);


    }

    // TODO: REPLACE WITH PATHPLANNER CODE
    public Command getAutonomousCommand() {
        // return null;
        //return shoot.runOnce(() -> System.out.println("Running auto..."));
    //   return Commands.sequence(
    //   intakePivot.sysIDQuasistatic(Direction.kReverse),
    //   Commands.waitSeconds(3),
    //   intakePivot.sysIDQuasistatic(Direction.kForward),
    //   Commands.waitSeconds(3),
    //   intakePivot.sysIDDynamic(Direction.kReverse), 
    //   Commands.waitSeconds(3),
    //   intakePivot.sysIDDynamic(Direction.kForward)     
    // );

    return Commands.sequence(
      shootHood.sysIDQuasistatic(Direction.kReverse),
      Commands.waitSeconds(3),
      shootHood.sysIDQuasistatic(Direction.kForward),
      Commands.waitSeconds(3),
      shootHood.sysIDDynamic(Direction.kReverse), 
      Commands.waitSeconds(3),
      shootHood.sysIDDynamic(Direction.kForward)     
    );
      
      /*
       final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            // drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // // Then slowly drive forward (away from us) for 5 seconds.
            // drivetrain.applyRequest(() ->
            //     drive.withVelocityX(0.5)
            //         .withVelocityY(0)
            //         .withRotationalRate(0)
            // )
            // .withTimeout(5.0),
            // // Finally idle for the rest of auton
            // drivetrain.applyRequest(() -> idle)
        );
        */
    }
}
