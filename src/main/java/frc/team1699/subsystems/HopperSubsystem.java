package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.HopperConfigs;
import frc.robot.Constants.HopperConstants;

public class HopperSubsystem extends SubsystemBase {
    private IndexingSpeeds currentSpeed;
    private TalonFX leadMotor;

    public HopperSubsystem() {
        leadMotor=new TalonFX(HopperConstants.kLeadMotorID);

        currentSpeed=IndexingSpeeds.STORED;
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

    public Command setSpeed(IndexingSpeeds speed) {
        return runOnce(() -> {
            this.currentSpeed=speed;
            leadMotor.setControl(HopperConfigs.motionRequest.withVelocity(speed.getSpeed()));
        });
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
    }

    public enum IndexingSpeeds {
        STORED(0), 
        // TODO: TUNE
        INTAKE(.1),
        OUTTAKE(-.1);


        private double speed;
        IndexingSpeeds(double speed) {
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