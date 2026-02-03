package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.PivotConfigs;
import frc.robot.Constants.PivotConstants;

public class PivotSubsystem extends SubsystemBase {
    private TalonFX leadMotor, followMotor;
    private PivotPositions currentPosition;
    public PivotSubsystem() {
        leadMotor= new TalonFX(PivotConstants.kLeadMotorID);
        followMotor= new TalonFX(PivotConstants.kFollowMotorID);

        configureMotors();
    }

    private void configureMotors() {
        leadMotor.getConfigurator().apply(PivotConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(PivotConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(PivotConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(PivotConfigs.feedback);

        followMotor.setControl(new Follower(leadMotor.getDeviceID(), PivotConstants.kFollowInverted));
    }
    

    public double getError() {
        return Math.abs(Math.abs(currentPosition.degrees)-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < PivotConstants.kTolerance;
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble();
    }

    public Command setPosition(PivotPositions position) {
        return runOnce(() -> {
            this.currentPosition=position;
            leadMotor.setControl(PivotConfigs.m_request.withPosition(position.degrees));
        });
    }

    public Command setRaw(double speed) {
        return runOnce(() -> {
            leadMotor.set(speed);
        });
    }

    @Override
    public void periodic() {
        System.out.println("Encoder position: " + encoderPosition());
    }

    public enum PivotPositions {
        STORED(0), AIMING(0);

        private double degrees;
        private PivotPositions(double degrees) {
            this.degrees=degrees;
        }

        void setPosition(double degrees) {
            this.degrees=degrees;
        }
        double encoderPosition() {
            return this.degrees;
        }
    }
}
