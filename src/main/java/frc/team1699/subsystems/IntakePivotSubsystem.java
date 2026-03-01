package frc.team1699.subsystems;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volt;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Configs.IntakePivotConfigs;
import frc.robot.Constants.IntakePivotConstants;

public class IntakePivotSubsystem extends SubsystemBase {

    private PivotPositions currentPosition;
    private TalonFX leadMotor;
    private CANcoder encoder;
    private SysIdRoutine routine;
    
    public IntakePivotSubsystem() {
        
        routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(0.375).per(Seconds),
                Volts.of(2.5),
                Seconds.of(10),
                (state) -> Logger.recordOutput("Intake/ sysID state",state.toString())
            ),
            new SysIdRoutine.Mechanism((volts) -> this.voltageDrive(volts.in(Volts)), null, this)
        );
        leadMotor= new TalonFX(IntakePivotConstants.kLeadMotorID);
        encoder = new CANcoder(IntakePivotConstants.kFeedbackID);

        currentPosition=PivotPositions.STORED;
        configureMotors();

        // encoder.setPosition(encoder.getAbsolutePosition().getValue());
        leadMotor.setPosition(encoder.getAbsolutePosition().getValue());
    }

    // TODO: Check current limits


    public Command sysIDQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysIDDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }


    private void configureMotors() {
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.feedback);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.limits);


        encoder.getConfigurator().apply(IntakePivotConfigs.encoderConfig);
    }


    

    public double getError() {
        return Math.abs(Math.abs(currentPosition.degrees)-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < IntakePivotConstants.kTolerance;
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble() *360; //*360;
    }

    public Command setPosition(PivotPositions position) {
        return runOnce(() -> {
            this.currentPosition=position;
            if(currentPosition.degrees < encoderPosition()) {
                leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
                leadMotor.setControl(IntakePivotConfigs.motionRequest.withPosition(position.degrees/360));
                return;
            }
            leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
            leadMotor.setControl(IntakePivotConfigs.motionRequest.withPosition(position.degrees/360)); ///360));
        });
    }

    public void voltageDrive(double volts) {
        leadMotor.setVoltage(volts);
    }

    // public Command setRaw(double speed) {
    //     return runOnce(() -> {
    //         pauseControl();
            
    //         leadMotor.set(speed);
    //     });
    // }

    private void pauseControl() {
        leadMotor.setControl(IntakePivotConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(IntakePivotConfigs.motionRequest);
    }

    @Override
    public void periodic() {
        // if(currentPosition.equals(PivotPositions.GROUND_INTAKE) 
        //     && isInTolerance() 
        //     && hasMotionControl()
        // ) {
        //     pauseControl();
        // }

        SmartDashboard.putNumber("Intake Pivot Position: ", encoderPosition());
        SmartDashboard.putBoolean("Is Intake Pivot Motion Paused: ", hasMotionControl());
        SmartDashboard.putNumber("Error: ", getError());
        SmartDashboard.putBoolean("Is Intake Pivot In Tolerance: ", isInTolerance());
        SmartDashboard.putNumber("Intake Stator Current Pivot PID Val: ", leadMotor.getStatorCurrent().getValueAsDouble());
        SmartDashboard.putNumber("Intake Supply Current Pivot PID Val: ", leadMotor.getSupplyCurrent().getValueAsDouble());

        SmartDashboard.putNumber("Intake Voltage Pivot PID Val: ", leadMotor.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putString("Is Intake past Limit: ", leadMotor.getReverseLimit().getValue().toString());

        Logger.recordOutput("IntakeMotor/Velocity", leadMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Voltage", leadMotor.getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Position", leadMotor.getPosition().getValueAsDouble());

        // Logger.recordOutput("Intake/ Velocity: ", leadMotor.getVelocity().getValueAsDouble());
        // Logger.recordOutput("Intake/ Voltage: ", leadMotor.getMotorVoltage().getValueAsDouble());
        // Logger.recordOutput("Intake/ Position: ", leadMotor.getPosition().getValueAsDouble());        
    }

    public enum PivotPositions {
        STORED(108), 
        PLATEFORM_INTAKE(130), 
        GROUND_INTAKE(185);

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
