package frc.team1699.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.ShooterConstants;

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

    public double getTopVelocity() {
        return topMotor.getVelocity().getValueAsDouble();
    }

    public double getBottomVelocity() {
        return bottomMotor.getVelocity().getValueAsDouble();
    }

    public double getTopError() {
        return Math.abs(Math.abs(currentSpeed.getTopSpeed())-Math.abs(getTopVelocity()));
    }

    public double getBottomError() {
        return Math.abs(Math.abs(currentSpeed.getBottomSpeed())-Math.abs(getBottomVelocity()));
    }

    public boolean topInTolerance() {
        return getTopError() < ShooterConstants.kTolerance;
    }

    public boolean bottomInTolerance() {
        return getBottomError() < ShooterConstants.kTolerance;
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
            pauseControl();

            topMotor.set(topVoltage);
            bottomMotor.set(bottomVoltage);
        });
    }

    public Command stopAll() {
        return runOnce(() -> {
            pauseControl();

            topMotor.set(0);
            bottomMotor.set(0);
        });
    }

    private void pauseControl() {
        topMotor.setControl(ShooterConfigs.pauseMotion);
        bottomMotor.setControl(ShooterConfigs.pauseMotion);
    }

    private boolean hasMotionControl() {
        return topMotor.getAppliedControl().equals(ShooterConfigs.motionRequest);
    }

   @Override
    public void periodic() {
        SmartDashboard.putNumber("Top Intake Speed: " , topMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Bottom Intake Speed: " , bottomMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putBoolean("Top Intake Is In Tolerance: ", topInTolerance());
        SmartDashboard.putBoolean("Bottom Intake Is In Tolerance: ", bottomInTolerance());
        SmartDashboard.putBoolean("Intake Has Motion Paused: ", !hasMotionControl());
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
