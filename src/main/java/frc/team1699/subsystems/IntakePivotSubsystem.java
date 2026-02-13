package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IntakePivotConfigs;
import frc.robot.Constants.IntakePivotConstants;

public class IntakePivotSubsystem extends SubsystemBase {
    private PivotPositions currentPosition;
    private TalonFX leadMotor;
    
    public IntakePivotSubsystem() {
        leadMotor= new TalonFX(IntakePivotConstants.kLeadMotorID);

        currentPosition=PivotPositions.STORED;
        configureMotors();
    }

    private void configureMotors() {
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.feedback);
    }
    

    public double getError() {
        return Math.abs(Math.abs(currentPosition.degrees)-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < IntakePivotConstants.kTolerance;
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble();
    }

    public Command setPosition(PivotPositions position) {
        return runOnce(() -> {
            this.currentPosition=position;
            leadMotor.setControl(IntakePivotConfigs.motionRequest.withPosition(position.degrees));
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
        if(currentPosition.equals(PivotPositions.GROUND_INTAKE) 
            && isInTolerance() 
            && leadMotor.getAppliedControl().equals(IntakePivotConfigs.motionRequest)) {
            leadMotor.setControl(IntakePivotConfigs.pauseMotion);
        }
    }

    public enum PivotPositions {
        STORED(0), 
        PLATEFORM_INTAKE(-1), 
        GROUND_INTAKE(-1);

        private double degrees;
        private PivotPositions(double degrees) {
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
