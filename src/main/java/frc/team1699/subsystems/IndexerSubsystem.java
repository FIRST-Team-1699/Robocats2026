package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.IndexerConfigs;
import frc.robot.Constants.IndexerConstants;

public class IndexerSubsystem extends SubsystemBase {
    private IndexingSpeeds currentSpeed;
    private TalonFX leadMotor;

    public IndexerSubsystem() {
        leadMotor=new TalonFX(IndexerConstants.kLeadMotorID);

        currentSpeed=IndexingSpeeds.STORED;
        configureMotors();
    }

    public void configureMotors() {
        leadMotor.getConfigurator().apply(IndexerConfigs.talonConfigs.Slot0);
        leadMotor.getConfigurator().apply(IndexerConfigs.talonConfigs.MotionMagic);
        leadMotor.getConfigurator().apply(IndexerConfigs.motorConfigs);
        leadMotor.getConfigurator().apply(IndexerConfigs.feedback);
    }

    public double getVelocity() {
        return leadMotor.getVelocity().getValueAsDouble();
    }

    public double getError() {
        return Math.abs(Math.abs(currentSpeed.getSpeed())-Math.abs(getVelocity()));
    }

    public boolean topInTolerance() {
        return getError() < IndexerConstants.kTolerance;
    }

    public Command setSpeed(IndexingSpeeds speed) {
        return runOnce(() -> {
            this.currentSpeed=speed;
            leadMotor.setControl(IndexerConfigs.motionRequest.withVelocity(speed.getSpeed()));
        });
    }

    public Command setRaw(double voltage) {
        return runOnce(() -> {
            pauseControl();

            leadMotor.set(voltage);
        });
    }

    private void pauseControl() {
        leadMotor.setControl(IndexerConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return leadMotor.getAppliedControl().equals(IndexerConfigs.motionRequest);
    }

   @Override
    public void periodic() {
        SmartDashboard.putNumber("Indexer Speed: " , leadMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putBoolean("Indexer Speed Is In Tolerance: ", topInTolerance());
        SmartDashboard.putBoolean("Indexer Speed Has Motion Paused: ", !hasMotionControl());
    }

    public enum IndexingSpeeds {
        STORED(0), 
        // TODO: TUNE
        INTAKE(1),
        OUTTAKE(-1);


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