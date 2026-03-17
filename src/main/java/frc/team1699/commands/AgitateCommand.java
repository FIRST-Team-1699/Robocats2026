package frc.team1699.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IntakePivotSubsystem;
import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;

public class AgitateCommand extends Command {
    private final IntakePivotSubsystem intakePivot;
    private final Timer time;

    public AgitateCommand(
        IntakePivotSubsystem intakePivot
    ) {
        this.intakePivot=intakePivot;
        this.time= new Timer();

        addRequirements(intakePivot);
    }

    @Override
    public void initialize() {
        time.start();
    }

    @Override
    public void execute() {
        if(time.get() % 2 == 0) {
            intakePivot.setPosition(
                IntakePositions.GROUND_INTAKE 
            );
        } else {
            intakePivot.setPosition(
                IntakePositions.AGITATE 
            );
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        intakePivot.setPosition(IntakePositions.GROUND_INTAKE);
        time.stop();
        time.reset();
    }
}
