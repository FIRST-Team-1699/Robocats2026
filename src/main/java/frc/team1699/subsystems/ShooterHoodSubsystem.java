package frc.team1699.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.ShooterHoodConstants;

public class ShooterHoodSubsystem extends SubsystemBase {
    private TalonFX leadMotor, followMotor;
    private HoodPositions currentPosition;

    public ShooterHoodSubsystem() {
        leadMotor= new TalonFX(ShooterHoodConstants.kLeadMotorID);
        followMotor= new TalonFX(ShooterHoodConstants.kFollowMotorID);
        
        currentPosition=HoodPositions.STORED;
        configureMotors();
    }

    private void configureMotors() {
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.feedback);

        followMotor.setControl(new Follower(ShooterHoodConstants.kLeadMotorID, ShooterHoodConstants.kFollowInverted));
    }
    

    public double getError() {
        return Math.abs(Math.abs(currentPosition.degrees)-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < ShooterHoodConstants.kTolerance;
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble();
    }

    public Command setPosition(HoodPositions position) {
        return runOnce(() -> {
            this.currentPosition=position;
            leadMotor.setControl(ShooterHoodConfigs.motionRequest.withPosition(position.degrees));
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

    public enum HoodPositions {
        STORED(0), AIMING(0), CLIMB(0);

        private double degrees;
        private HoodPositions(double degrees) {
            this.degrees=degrees;
        }

        public void setPosition(double degrees) {
            this.degrees=degrees;
        }
        public double getPosition() {
            return this.degrees;
        }
    }
}
