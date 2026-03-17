package frc.team1699.subsystems;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.ControlRequest;
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

        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot0);
        followMotor.getConfigurator().apply(IntakePivotConfigs.talonConfigs.Slot1);
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
        return leadMotor.getPosition().getValueAsDouble(); 
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

    public void voltageDrive(double volts) {
        leadMotor.setVoltage(volts);
    }

    private void pauseActiveControl() {
        leadMotor.setControl(
            isInGroundIntake() ?
                IntakePivotConfigs.pauseActiveMotion:
                IntakePivotConfigs.motionRequest.withPosition(currentPosition.rotations)
        );
    }

    private void pausePassiveControl() {
        leadMotor.setControl(
            isInStored() ?
                IntakePivotConfigs.pausePassiveMotion:
                IntakePivotConfigs.motionRequest.withPosition(currentPosition.rotations)
        );
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

    public boolean isInMotion() {
        return this.leadMotor.getAppliedControl() == IntakePivotConfigs.motionRequest;
    }

    @Override
    public void periodic() {
        if(isInTolerance() 
            && isInMotion()) {
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
        SmartDashboard.putNumber("Inatke Error: ", getError());
        SmartDashboard.putBoolean("Is Intake Pivot In Tolerance: ", isInTolerance());

        SmartDashboard.putNumber("Intake Voltage Pivot PID Val: ", leadMotor.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("IntakeMotor/Velocity", leadMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Voltage", leadMotor.getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("IntakeMotor/Position", leadMotor.getPosition().getValueAsDouble());    
    }

    public enum IntakePositions {
        STORED(0.00), 
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
