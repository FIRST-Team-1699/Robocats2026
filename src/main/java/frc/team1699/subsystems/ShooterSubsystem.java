package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterHoodConstants;

public class ShooterSubsystem extends SubsystemBase {
    private ShootingSpeeds currentSpeed;
    private TalonFX topMotor, bottomMotor;

    public ShooterSubsystem() {
        topMotor=new TalonFX(ShooterConstants.kTopMotorID);
        bottomMotor= new TalonFX(ShooterConstants.kBottomMotorID);

        currentSpeed=ShootingSpeeds.STORED;
        configureMotors();
    }

    public void configureMotors() {
        topMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.Slot0);
        topMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.MotionMagic);
        topMotor.getConfigurator().apply(ShooterConfigs.topMotorConfigs);
        topMotor.getConfigurator().apply(ShooterConfigs.feedback);

        bottomMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.Slot0);
        bottomMotor.getConfigurator().apply(ShooterConfigs.talonConfigs.MotionMagic);
        bottomMotor.getConfigurator().apply(ShooterConfigs.bottomMotorConfigs);
        bottomMotor.getConfigurator().apply(ShooterConfigs.feedback);
    }

    public double getTopError() {
        return Math.abs(Math.abs(currentSpeed.getTopSpeed())-Math.abs(topEncoderPosition()));
    }

    public double getBottomError() {
        return Math.abs(Math.abs(currentSpeed.getBottomSpeed())-Math.abs(bottomEncoderPosition()));
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

    public Command setSpeed(ShootingSpeeds speed) {
        return runOnce(() -> {
            this.currentSpeed=speed;
            topMotor.setControl(ShooterConfigs.motionRequest.withVelocity(speed.getTopSpeed()));
            bottomMotor.setControl(ShooterConfigs.motionRequest.withVelocity(speed.getBottomSpeed()));
        });
    }

    public Command setRaw(double topVoltage, double bottomVoltage) {
        return runOnce(() -> {
            topMotor.set(topVoltage);
            bottomMotor.set(bottomVoltage);
        });
    }

   @Override
    public void periodic() {
        SmartDashboard.putNumber("Top Intake Speed: " , topMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Bottom Intake Speed: " , bottomMotor.getVelocity().getValueAsDouble());
    }

    public enum ShootingSpeeds {
        STORED(0,0), 
        INTAKE(5,5),
        OUTTAKE(-5,-5),
        // TODO: SET
        CLOSE(-1,-1), 
        FAR(-1,-1), 
        INTERPOLATED(-1,-1);


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
