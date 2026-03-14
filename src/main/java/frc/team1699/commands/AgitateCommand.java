package frc.team1699.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.IntakePivotConstants;
import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.HopperSubsystem;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakePivotSubsystem;
import frc.team1699.subsystems.ShooterHoodSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
import frc.utils.vision.RobotPose;

public class AgitateCommand extends Command {
    private final IntakePivotSubsystem intakePivot;

    public AgitateCommand(
        IntakePivotSubsystem intakePivot
    ) {
        this.intakePivot=intakePivot;

        addRequirements(intakePivot);
    }

    @Override
    public void initialize() {
        intakePivot.setPosition(IntakePositions.GROUND_INTAKE);
    }

    @Override
    public void execute() {
        intakePivot.setPosition(
            intakePivot.getCurrentPosition()==IntakePositions.AGITATE ?
                IntakePositions.GROUND_INTAKE : IntakePositions.AGITATE
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        intakePivot.setPosition(IntakePositions.GROUND_INTAKE);
    }
}
