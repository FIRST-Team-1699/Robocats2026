package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.Robot;
import frc.team1699.subsystems.HopperSubsystem;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.utils.vision.RobotPose;

public class ShuffleCommand extends Command {
    private final ShooterSubsystem shoot;
    private final ShooterHoodSubsystem shootHood;
    private final IndexerSubsystem indexer;
    private final HopperSubsystem hopper;
    private final IntakeSubsystem intake;

    public ShuffleCommand(
        ShooterSubsystem shoot, 
        ShooterHoodSubsystem shootHood,
        IndexerSubsystem indexer, 
        HopperSubsystem hopper,
        IntakeSubsystem intake
    ) {
        this.shoot = shoot;
        this.shootHood = shootHood;
        this.indexer = indexer;
        this.hopper = hopper;
        this.intake=intake;

        addRequirements(shoot, shootHood, indexer, hopper, intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(IntakeSpeeds.INTAKE);

        // start with the indexer speed set to stored, will be changed to shoot when shooter is in tollerance
        indexer.setSpeed(IndexingSpeeds.STORED);
    }

    @Override
    public void execute() {
        shoot.setSpeed(ShootingSpeeds.SHUFFLE);
        shootHood.setPosition(HoodPositions.MIN);
        // if(shoot.isTotalInTollerance().getAsBoolean() && shootHood.isInTolerance()) {
         if(shoot.isTotalInTollerance().getAsBoolean() && shootHood.isInTolerance()) {
            // && !indexer.inShooting()) {
            indexer.setSpeed(IndexingSpeeds.SHOOTING);
            hopper.setSpeed(HopperSpeeds.INTAKE);
        } 
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        RobotPose.setShootOffset(0);
        shoot.setSpeed(ShootingSpeeds.STORED);
        shootHood.setPosition(HoodPositions.INTERPOLATED);

        indexer.setSpeed(IndexingSpeeds.STORED);
        hopper.setSpeed(HopperSpeeds.STORED);

        intake.setSpeed(IntakeSpeeds.STORED);
    }
}
