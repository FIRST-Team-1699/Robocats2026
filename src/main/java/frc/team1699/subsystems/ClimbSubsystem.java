package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ClimbConfigs;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
    private TalonFX leadMotor;
    private ClimbPosition currentPosition;
    public ClimbSubsystem() {
        leadMotor= new TalonFX(ClimbConstants.kLeadMotorID);

        configureMotors();
    }

    private void configureMotors() {
        leadMotor.getConfigurator().apply(ClimbConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(ClimbConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(ClimbConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(ClimbConfigs.feedback);
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble();
    }

    public double getError() {
        return Math.abs(Math.abs(currentPosition.getDegrees())-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < ClimbConstants.kTolerance;
    }

    public Command setPosition(ClimbPosition position) {
        return runOnce(() -> {
            this.currentPosition=position;
            leadMotor.setControl(ClimbConfigs.motionRequest.withPosition(position.degrees));
        });
    }

    public Command setRaw(double speed) {
        return runOnce(() -> {
            pauseControl();

            leadMotor.set(speed);
        });
    }

    private void pauseControl() {
        leadMotor.setControl(ClimbConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(ClimbConfigs.motionRequest);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Climb position: ", encoderPosition());
        SmartDashboard.putBoolean("Climb Is In Tolerance: ", isInTolerance());
        SmartDashboard.putBoolean("Climb Has Motion Paused: ", !hasMotionControl());
    }

    public enum ClimbPosition {
        STORED(0),
        EXTENDED(90);
        private double degrees;
        private ClimbPosition(double degrees) {
            this.degrees=degrees;
        }
        public double getDegrees() {
            return this.degrees;
        }
    }
}
