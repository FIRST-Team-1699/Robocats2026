package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.HopperConfigs;
import frc.robot.Constants.HopperConstants;

public class HopperSubsystem extends SubsystemBase {
    private HopperSpeeds currentSpeed;
    private TalonFX leadMotor;

    public HopperSubsystem() {
        leadMotor=new TalonFX(HopperConstants.kLeadMotorID);

        currentSpeed=HopperSpeeds.STORED;
        configureMotors();
    }

    public void configureMotors() {
        leadMotor.getConfigurator().apply(HopperConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(HopperConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(HopperConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(HopperConfigs.feedback);
    }

    public double getVelocity() {
        return leadMotor.getVelocity().getValueAsDouble();
    }

    public double getError() {
        return Math.abs(Math.abs(currentSpeed.getSpeed())-Math.abs(getVelocity()));
    }

    public boolean topInTolerance() {
        return getError() < HopperConstants.kTolerance;
    }

    public Command setSpeedCommand(HopperSpeeds speed) {
        return runOnce(() -> {
            setSpeed(speed);
        });
    }

    public void setSpeed(HopperSpeeds speed) {
        this.currentSpeed=speed;
        leadMotor.set(currentSpeed.getSpeed());
    }

    public Command setRaw(double voltage) {
        return runOnce(() -> {
            pauseControl();

            leadMotor.set(voltage);
        });
    }

    private void pauseControl() {
        leadMotor.setControl(HopperConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(HopperConfigs.motionRequest);
    }

   @Override
    public void periodic() {
        SmartDashboard.putNumber("Hopper Speed: " , leadMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putBoolean("Hopper Speed Is In Tolerance: ", topInTolerance());
        SmartDashboard.putBoolean("Hopper Speed Has Motion Paused: ", !hasMotionControl());

        if(currentSpeed.getSpeed() != 0) {
            leadMotor.set(currentSpeed.speed);
        }
    }

    public enum HopperSpeeds {
        STORED(0), 
        // TODO: TUNE
        INTAKE(.07),
        OUTTAKE(-.05);


        private double speed;
        HopperSpeeds(double speed) {
            this.speed=speed;
        }

        public void setSpeed(double speed) {
            this.speed=speed;
        }

        public double getSpeed() {
            return this.speed;
        }
    }
}