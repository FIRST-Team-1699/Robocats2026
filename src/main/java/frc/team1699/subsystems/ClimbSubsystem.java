package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ClimbConfigs;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
    private TalonFX leadMotor;
    private CANcoder encoder;
    private ClimbPosition currentPosition;
    public ClimbSubsystem() {
        leadMotor= new TalonFX(ClimbConstants.kLeadMotorID);

        currentPosition=ClimbPosition.STORED;
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
        return Math.abs(Math.abs(currentPosition.getRotations())-Math.abs(this.encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < ClimbConstants.kTolerance;
    }

    public Command setPosition(ClimbPosition position) {
        return runOnce(() -> {
            this.currentPosition=position;
            leadMotor.setControl(ClimbConfigs.motionRequest.withPosition(position.rotations));
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
        STORED(0.0),
        EXTENDED(4.778320);
        private double rotations;
        private ClimbPosition(double degrees) {
            this.rotations=degrees;
        }
        public double getRotations() {
            return this.rotations;
        }
    }
}
