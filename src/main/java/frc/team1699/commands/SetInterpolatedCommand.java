package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.VisionConfigs;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;

public class SetInterpolatedCommand extends Command {
    private final VisionSubsystem vision;
    
    public SetInterpolatedCommand(
        VisionSubsystem vision
    ) {
        this.vision = vision;
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        ShootingSpeeds.INTERPOLATED.setSpeeds(
            VisionConfigs.speedTopMap.get(vision.getDistanceToTag()),
            VisionConfigs.speedBottomMap.get(vision.getDistanceToTag())
        );

        HoodPositions.INTERPOLATED.setDegrees(
            VisionConfigs.shootPivotMap.get(vision.getDistanceToTag())
        );
    }

    @Override
    public void execute() {}

    public boolean isFinished() { return true; }

    public void end (boolean isInterrupted) {}
}
