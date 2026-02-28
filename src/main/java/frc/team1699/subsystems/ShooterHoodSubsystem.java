package frc.team1699.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.ShooterHoodConstants;

public class ShooterHoodSubsystem extends SubsystemBase {
    private TalonFX leadMotor, followMotor;
    private CANcoder encoder;
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
        return Math.abs(Math.abs(currentPosition.getDegrees())-Math.abs(encoderPosition()*360));
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
            leadMotor.setControl(ShooterHoodConfigs.motionRequest.withPosition(position.degrees/360));
        });
    }

    public Command setRaw(double speed) {
        return runOnce(() -> {
            // pauseControl();
            leadMotor.set(speed);
        });
    }

    private void pauseControl() {
        leadMotor.setControl(ShooterHoodConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(ShooterHoodConfigs.motionRequest);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Pivot position: ", encoderPosition()*360);
        SmartDashboard.putNumber("Shooter hypothetical: ", this.currentPosition.degrees);
        SmartDashboard.putBoolean("Shooter Pivot Is In Tolerance: ", isInTolerance());
        SmartDashboard.putBoolean("Shooter Pivot Has Motion Paused: ", !hasMotionControl());
    }

    public enum HoodPositions {
        STORED(0), 
        AIMING(-1), 
        CLIMB(-1),

        MIN(0),
        MAX(56.16);

        private double degrees;
        private HoodPositions(double degrees) {
            this.degrees=degrees;
        }

        public void setDegrees(double degrees) {
            this.degrees=degrees;
        }
        public double getDegrees() {
            return this.degrees;
        }
    }
}
