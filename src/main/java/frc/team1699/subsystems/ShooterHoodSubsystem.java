package frc.team1699.subsystems;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Configs.ShooterHoodConfigs;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterHoodConstants;
import frc.utils.vision.RobotPose;

public class ShooterHoodSubsystem extends SubsystemBase {
    private TalonFX leadMotor, followMotor;
    private CANcoder encoder;
    private HoodPositions currentPosition;
    private SysIdRoutine routine;

    public ShooterHoodSubsystem() {
        leadMotor= new TalonFX(ShooterHoodConstants.kLeadMotorID);
        followMotor= new TalonFX(ShooterHoodConstants.kFollowMotorID);
        encoder = new CANcoder(ShooterHoodConstants.kFeedbackID);
        
        routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(0.1).per(Seconds),
                Volts.of(1.5),
                Seconds.of(10),
                (state) -> Logger.recordOutput("ShooterHood/ sysID state",state.toString())
            ),
            new SysIdRoutine.Mechanism((volts) -> this.voltageDrive(volts.in(Volts)), null, this)
        );

        currentPosition=HoodPositions.INTERPOLATED;
        configureMotors();
    }

    private void configureMotors() {
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.feedback);
        leadMotor.getConfigurator().apply(ShooterHoodConfigs.limits);

        followMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.Slot0);
        followMotor.getConfigurator().apply(ShooterHoodConfigs.talonConfigs.MotionMagic);
        followMotor.getConfigurator().apply(ShooterHoodConfigs.motorConfigs);
        followMotor.getConfigurator().apply(ShooterHoodConfigs.feedback);
        followMotor.getConfigurator().apply(ShooterHoodConfigs.limits);

        encoder.getConfigurator().apply(ShooterHoodConfigs.encoderConfigs);

        followMotor.setControl(new Follower(leadMotor.getDeviceID(), ShooterHoodConstants.kFollowInverted));
    }
    

    public double getError() {
        return Math.abs(Math.abs(currentPosition.getDegrees())-Math.abs(encoderPosition()));
    }

    public boolean isInTolerance() {
        return getError() < ShooterHoodConstants.kTolerance;
    }

    public double encoderPosition() {
        return leadMotor.getPosition().getValueAsDouble();
    }

    public Command setPositionCommand(HoodPositions position) {
        return runOnce(() -> {
            setPosition(position);
        });
    }

    public void setPosition(HoodPositions position) {
        this.currentPosition=position;
    }

    public Command setRaw(double speed) {
        return runOnce(() -> {
            leadMotor.set(speed);
        });
    }

    private void pauseControl() {
        leadMotor.setControl(ShooterHoodConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(ShooterHoodConfigs.motionRequest);
    }

    public Command sysIDQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysIDDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }

    public void voltageDrive(double volts) {
        leadMotor.setVoltage(volts);
    }

    @Override
    public void periodic() {
        HoodPositions.INTERPOLATED.setDegrees(
            RobotPose.getHoodAngle()
        );
        
        leadMotor.setControl(ShooterHoodConfigs.motionRequest.withPosition(currentPosition.degrees));

        SmartDashboard.putNumber("Shooter Pivot position: ", encoderPosition());
        SmartDashboard.putNumber("Shooter hypothetical: ", this.currentPosition.degrees);
        SmartDashboard.putBoolean("Shooter Pivot Is In Tolerance: ", isInTolerance());
        SmartDashboard.putBoolean("Shooter Pivot Has Motion Paused: ", !hasMotionControl());

        Logger.recordOutput("ShooterHood/Velocity", leadMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("ShooterHood/Voltage", leadMotor.getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("ShooterHood/Position", leadMotor.getPosition().getValueAsDouble());
    }

    public enum HoodPositions {
        STORED(0.02), 
        CLIMB(0.6),
        INTERPOLATED(0.6),

        MIN(0.02),
        MAX(0.6),
        CLOSE(0.45),
        SHUFFLE(0.02);

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
