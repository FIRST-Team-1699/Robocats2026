package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.HopperSubsystem;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.utils.vision.RobotPose;

public class ShootCommand extends Command {
    private final ShooterSubsystem shoot;
    private final ShooterHoodSubsystem shootHood;
    private final IndexerSubsystem indexer;
    private final HopperSubsystem hopper;

    public ShootCommand(
        ShooterSubsystem shoot, 
        ShooterHoodSubsystem shootHood,
        IndexerSubsystem indexer, 
        HopperSubsystem hopper
    ) {
        this.shoot = shoot;
        this.shootHood = shootHood;
        this.indexer = indexer;
        this.hopper = hopper;

        addRequirements(shoot, shootHood, indexer, hopper);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        ShooterHoodSubsystem.HoodPositions.INTERPOLATED.setDegrees(
            RobotPose.getHoodAngle()
        );
        ShooterSubsystem.ShootingSpeeds.INTERPOLATED.setSpeeds(
            RobotPose.getFlywheelTopSpeed(),
            RobotPose.getFlywheelBottomSpeed()
        );

        shootHood.setPosition(HoodPositions.INTERPOLATED);
        shoot.setSpeed(ShootingSpeeds.INTERPOLATED);
        // hopper.setSpeed(HopperSpeeds.INTAKE);
        if(shoot.isTotalInTollerance().getAsBoolean()) {
            indexer.setSpeed(IndexingSpeeds.INTAKE);
        } else {
            indexer.setSpeed(IndexingSpeeds.STORED);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        shoot.setSpeed(ShootingSpeeds.STORED);
        indexer.setSpeed(IndexingSpeeds.STORED);
        // hopper.setSpeed(HopperSpeeds.STORED);
    }
}
