package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IntakeConfigs;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private TalonFX topMotor, bottomMotor;
    private IntakeSpeeds currentSpeed;
    public IntakeSubsystem() {
        topMotor= new TalonFX(IntakeConstants.kTopMotorID);
        bottomMotor= new TalonFX(IntakeConstants.kBottomMotorID);
        currentSpeed=IntakeSpeeds.STORED;

        configureMotors();
    }

    private void configureMotors() {
        topMotor.getConfigurator().apply(IntakeConfigs.talonConfigs.Slot0);
        topMotor.getConfigurator().apply(IntakeConfigs.talonConfigs.MotionMagic);
        topMotor.getConfigurator().apply(IntakeConfigs.topMotorConfigs);
        topMotor.getConfigurator().apply(IntakeConfigs.feedback);

        bottomMotor.getConfigurator().apply(IntakeConfigs.talonConfigs.Slot0);
        bottomMotor.getConfigurator().apply(IntakeConfigs.talonConfigs.MotionMagic);
        bottomMotor.getConfigurator().apply(IntakeConfigs.bottomMotorConfigs);
        bottomMotor.getConfigurator().apply(IntakeConfigs.feedback);
    }

    public Command setSpeed(IntakeSpeeds speed) {
        return runOnce(() -> {
            currentSpeed=speed;
            topMotor.setControl(IntakeConfigs.motionRequest.withVelocity(speed.topSpeed));
            bottomMotor.setControl(IntakeConfigs.motionRequest.withVelocity(speed.bottomSpeed));
        });
    }

    public Command setRaw(double topVoltage,double bottomVoltage) {
        return runOnce(() -> {
            // pauseControl();

            topMotor.set(topVoltage);
            bottomMotor.set(bottomVoltage);
        });
    }

    public Command stopAll() {
        return runOnce(() -> {
            // pauseControl();

            topMotor.set(0);
            bottomMotor.set(0);
        });
    }

    // private void pauseControl() {
    //     topMotor.setControl(IntakeConfigs.pauseMotion);
    //     bottomMotor.setControl(IntakeConfigs.pauseMotion);
    // }

    private double getTopVelocity() {
        return topMotor.getVelocity().getValueAsDouble();
    }

    private double getBottomVelocity() {
        return bottomMotor.getVelocity().getValueAsDouble();
    }

    private double getTopError() {
        return Math.abs(Math.abs(currentSpeed.topSpeed)-Math.abs(getTopVelocity()));
    }

    private double getBottomError() {
        return Math.abs(Math.abs(currentSpeed.topSpeed)-Math.abs(getBottomVelocity()));
    }

    public boolean isTopInTolerance() {
        return getTopError() < IntakeConstants.kTolerance;
    }

    public boolean isBottomInTolerance() {
        return getBottomError() < IntakeConstants.kTolerance;
    }

    private boolean hasMotionControl() {
        return topMotor.getAppliedControl().equals(IntakeConfigs.motionRequest);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake Top Motor velocity: ", getTopVelocity());
        SmartDashboard.putNumber("IntakeBottom Motor velocity: ", getBottomVelocity());
        SmartDashboard.putBoolean("Is Intake Motion Paused: ", !hasMotionControl());
        SmartDashboard.putBoolean("Is Intake Top In Tolerance: ", isTopInTolerance());
        SmartDashboard.putBoolean("Is Intake Bottom In Tolerance: ", isBottomInTolerance());
    }

    public enum IntakeSpeeds {
        STORED(0,0), 
        // TODO: TUNE
        INTAKE(5,5), 
        OUTTAKE(-5,-5);

        private double topSpeed, bottomSpeed;
        private IntakeSpeeds(double topSpeed, double bottomSpeed) {
            this.topSpeed=topSpeed;
            this.bottomSpeed=bottomSpeed;
        }
    }
}
