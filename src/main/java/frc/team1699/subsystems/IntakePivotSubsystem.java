package frc.team1699.subsystems;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.RobotContainer;
import frc.robot.Configs.IntakePivotConfigs;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.IntakePivotConstants;
import frc.robot.Constants.ShooterHoodConstants;

public class IntakePivotSubsystem extends SubsystemBase {

    private IntakePositions currentPosition;
    private TalonFX leadMotor, followMotor;
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
        followMotor= new TalonFX(IntakePivotConstants.kFollowerMotorID);

        currentPosition=IntakePositions.STORED;
        configureMotors();
    }

    public Command sysIDQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysIDDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }


    private void configureMotors() {
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.breakMotorOutput);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.feedback);
        // leadMotor.getConfigurator().apply(IntakePivotConfigs.limits);

        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.MotionMagic);
        followMotor.getConfigurator().apply(IntakePivotConfigs.breakMotorOutput);
        followMotor.getConfigurator().apply(IntakePivotConfigs.feedback);

        followMotor.setControl(new Follower(leadMotor.getDeviceID(), IntakePivotConstants.kFollowInverted));
    }


    

    public double getError() {
        return Math.abs(currentPosition.rotations-getEncoderPosition());
    }

    public boolean isInTolerance() {
        return getError() < IntakePivotConstants.kTolerance;
    }

    public double getEncoderPosition() {
        return leadMotor.getPosition().getValueAsDouble(); //*360;
    }

    public IntakePositions getCurrentPosition() {
        return currentPosition;
    }

    public Command setPositionCommand(IntakePositions position) {
        return runOnce(() -> {
            setPosition(position);
        });
    }

    public void setPosition(IntakePositions position) {
        this.currentPosition=position;
        leadMotor.setControl(IntakePivotConfigs.motionRequest.withPosition(position.rotations)); 
    }

    public void setPositionSlow(IntakePositions position) {
        this.currentPosition=position;
        leadMotor.setControl(IntakePivotConfigs.slowMotionRequest.withPosition(position.rotations)); 
    }


    public void voltageDrive(double volts) {
        leadMotor.setVoltage(volts);
    }

    private void pauseActiveControl() {
        leadMotor.setControl(IntakePivotConfigs.pauseActiveMotion);
    }

    private void pausePassiveControl() {
        leadMotor.setControl(IntakePivotConfigs.pausePassiveMotion);
    }

    public Command voltage(double volts) {
        return runOnce(() -> {
            leadMotor.setControl(new VoltageOut(volts));
        });
    }

    public Command togglePivotCommand() {
        return new ConditionalCommand(
            setPositionCommand(IntakePositions.STORED), 
            setPositionCommand(IntakePositions.GROUND_INTAKE), 
            this::isInGroundIntake
        );
    }

    public boolean isInGroundIntake() {
        return getCurrentPosition()==IntakePositions.GROUND_INTAKE;
    }

    public boolean isInStored() {
        return getCurrentPosition()==IntakePositions.STORED;
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(IntakePivotConfigs.motionRequest);
    }

    public void setSlot0() {
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
    }

    public void setSlot1() {
        leadMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
    }

    @Override
    public void periodic() {
        // TODO: TO TEST
        if(isInTolerance() 
            && currentPosition != IntakePositions.AGITATE
        ) {
            if(isInGroundIntake()) {
                pauseActiveControl();
                leadMotor.setNeutralMode(NeutralModeValue.Coast);
                followMotor.setNeutralMode(NeutralModeValue.Coast);
            }
            if(isInStored()) {
                pausePassiveControl();
                leadMotor.setNeutralMode(NeutralModeValue.Brake);
                followMotor.setNeutralMode(NeutralModeValue.Brake);
            }
        }

        SmartDashboard.putNumber("Intake Pivot Position: ", getEncoderPosition());
        // SmartDashboard.putBoolean("Is Intake Pivot Motion Paused: ", hasMotionControl());
        SmartDashboard.putNumber("Inatke Error: ", getError());
        SmartDashboard.putBoolean("Is Intake Pivot In Tolerance: ", isInTolerance());
        // SmartDashboard.putNumber("Intake Stator Current Pivot PID Val: ", leadMotor.getStatorCurrent().getValueAsDouble());
        // SmartDashboard.putNumber("Intake Supply Current Pivot PID Val: ", leadMotor.getSupplyCurrent().getValueAsDouble());
        SmartDashboard.putNumber("Wnated Intake ", currentPosition.rotations);

        SmartDashboard.putNumber("Intake Voltage Pivot PID Val: ", leadMotor.getMotorVoltage().getValueAsDouble());

        SmartDashboard.putString("Is Intake past Limit: ", leadMotor.getReverseLimit().getValue().toString());

        Logger.recordOutput("IntakeMotor/Velocity", leadMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Voltage", leadMotor.getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Position", leadMotor.getPosition().getValueAsDouble());

        // Logger.recordOutput("Intake/ Velocity: ", leadMotor.getVelocity().getValueAsDouble());
        // Logger.recordOutput("Intake/ Voltage: ", leadMotor.getMotorVoltage().getValueAsDouble());
        // Logger.recordOutput("Intake/ Position: ", leadMotor.getPosition().getValueAsDouble());        
    }

    public enum IntakePositions {
        STORED(0.00), 
        // PLATEFORM_INTAKE(130), 
        AGITATE(.15),
        GROUND_INTAKE(0.22);

        private double rotations;
        private IntakePositions(double degrees) {
            this.rotations=degrees;
        }

        public void setPosition(double degrees) {
            this.rotations=degrees;
        }
        public double getPosition() {
            return this.rotations;
        }
    }
}
