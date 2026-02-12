package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterHoodConstants;

public class ShooterSubsystem extends SubsystemBase {
    private ShootingSpeeds currentPosition;
    private TalonFX topMotor, bottomMotor;

    public ShooterSubsystem() {
        topMotor=new TalonFX(ShooterConstants.kTopMotorID);
        bottomMotor= new TalonFX(ShooterConstants.kBottomMotorID);

        currentPosition=ShootingSpeeds.STORED;
        configureMotors();
    }

    public void configureMotors() {
        topMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.Slot0);
        topMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.MotionMagic);
        topMotor.getConfigurator().apply(ShooterConfigs.topMotorConfigs);
        topMotor.getConfigurator().apply(ShooterConfigs.topFeedback);

        bottomMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.Slot0);
        bottomMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.MotionMagic);
        bottomMotor.getConfigurator().apply(ShooterConfigs.bottomMotorConfigs);
        bottomMotor.getConfigurator().apply(ShooterConfigs.bottomFeedback);
    }

    public double getTopError() {
        return Math.abs(Math.abs(currentPosition.getTopSpeed())-Math.abs(topEncoderPosition()));
    }

    public double getBottomError() {
        return Math.abs(Math.abs(currentPosition.getBottomSpeed())-Math.abs(bottomEncoderPosition()));
    }

    public boolean topInTolerance() {
        return getTopError() < ShooterHoodConstants.kTolerance;
    }

    public boolean bottomInTolerance() {
        return getBottomError() < ShooterHoodConstants.kTolerance;
    }

    public double topEncoderPosition() {
        return topMotor.getPosition().getValueAsDouble();
    }

    public double bottomEncoderPosition() {
        return bottomMotor.getPosition().getValueAsDouble();
    }

    public Command setPosition(ShootingSpeeds position) {
        return runOnce(() -> {
            this.currentPosition=position;
            topMotor.setControl(ShooterConfigs.motionRequest.withVelocity(position.getTopSpeed()));
            bottomMotor.setControl(ShooterConfigs.motionRequest.withVelocity(position.getBottomSpeed()));
        });
    }

    public Command setTopRaw(double speed) {
        return runOnce(() -> {
            topMotor.set(speed);
        });
    }

    public Command setBottomRaw(double speed) {
        return runOnce(() -> {
            bottomMotor.set(speed);
        });
    }

    public Command stopAll() {
        return runOnce(() -> {
            bottomMotor.set(0);
            topMotor.set(0);
        });
    }

    public enum ShootingSpeeds {
        STORED(0,0), 
        // TODO: TUNE
        CLOSE(-1,-1), FAR(-1,-1), INTERPOLATED(-1,-1);


        private double topSpeed, bottomSpeed;
        ShootingSpeeds(double topSpeed,double bottomSpeed) {
            this.topSpeed=topSpeed;
            this.bottomSpeed=bottomSpeed;
        }

        public void setSpeeds(double topSpeed, double bottomSpeed) {
            this.topSpeed=topSpeed;
            this.bottomSpeed=bottomSpeed;
        }

        public double getTopSpeed() {
            return this.topSpeed;
        }

        public double getBottomSpeed() {
            return this.bottomSpeed;
        }
    }
}
