package frc.team1699.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Robot;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.RobotContainer.ShootingPosition;
import frc.team1699.subsystems.HopperSubsystem;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakePivotSubsystem;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.utils.vision.RobotPose;

public class ShootCommand extends Command {
    private double shootOffset;

    private final ShooterSubsystem shoot;
    private final ShooterHoodSubsystem shootHood;
    private final IndexerSubsystem indexer;
    private final HopperSubsystem hopper;
    private final IntakeSubsystem intake;

    private final ShootingPosition shootingPosition;

    private final Timer time;

    public ShootCommand(
        ShooterSubsystem shoot, 
        ShooterHoodSubsystem shootHood,
        IndexerSubsystem indexer, 
        HopperSubsystem hopper,
        IntakeSubsystem intake,
        ShootingPosition shootingPosition,
        double shootOffset
    ) {
        this.shoot = shoot;
        this.shootHood = shootHood;
        this.indexer = indexer;
        this.hopper = hopper;
        this.intake=intake;
        this.time=new Timer();
        this.shootingPosition=shootingPosition;
        this.shootOffset=shootOffset;

        addRequirements(shoot, shootHood, indexer, hopper,intake);
    }

    public ShootCommand(
        ShooterSubsystem shoot, 
        ShooterHoodSubsystem shootHood,
        IndexerSubsystem indexer, 
        HopperSubsystem hopper,
        IntakeSubsystem intake,
        ShootingPosition shootingPosition
    ) {
        this.shoot = shoot;
        this.shootHood = shootHood;
        this.indexer = indexer;
        this.hopper = hopper;
        this.intake=intake;
        this.time=new Timer();
        this.shootingPosition=shootingPosition;
        this.shootOffset=0;

        addRequirements(shoot, shootHood, indexer, hopper,intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(IntakeSpeeds.INTAKE);
        if(Robot.isInAuto) {
            time.start();
        }
    }

    @Override
    public void execute() {
        RobotPose.setShootOffset(shootOffset);
        shoot.setSpeed(ShootingSpeeds.INTERPOLATED);
        shootHood.setPosition(HoodPositions.INTERPOLATED);
        hopper.setSpeed(HopperSpeeds.INTAKE);
        if(shoot.isTotalInTollerance().getAsBoolean() && shootHood.isInTolerance()) {
            indexer.setSpeed(IndexingSpeeds.INTAKE);
        } else {
            indexer.setSpeed(IndexingSpeeds.STORED);
        }
    }

    @Override
    public boolean isFinished() {
        return time.hasElapsed(AutoConstants.kShootTimerLong);
    }

    @Override
    public void end(boolean isFinished) {
        RobotPose.setShootOffset(0);
        shoot.setSpeed(ShootingSpeeds.STORED);
        shootHood.setPosition(HoodPositions.INTERPOLATED);

        indexer.setSpeed(IndexingSpeeds.STORED);
        hopper.setSpeed(HopperSpeeds.STORED);

        intake.setSpeed(IntakeSpeeds.STORED);
        time.stop();
        time.reset();
    }
}
