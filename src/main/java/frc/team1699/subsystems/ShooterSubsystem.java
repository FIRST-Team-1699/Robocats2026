package frc.team1699.subsystems;

import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private ShootingSpeeds currentSpeed;
    private TalonFX topMotor, bottomMotor;
    private SysIdRoutine routine;

    public ShooterSubsystem() {
        topMotor=new TalonFX(ShooterConstants.kTopMotorID);
        bottomMotor= new TalonFX(ShooterConstants.kBottomMotorID);

        routine = new SysIdRoutine(
            new SysIdRoutine.Config(
                Volts.of(2).per(Seconds),
                Volts.of(10),
                Seconds.of(10),
                (state) -> Logger.recordOutput("Shooter/ sysID state",state.toString())
            ),
            new SysIdRoutine.Mechanism((volts) -> this.voltageDrive(volts.in(Volts)), null, this)
        );

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

    public BooleanSupplier isTotalInTollerance() {
        return () -> bottomInTolerance() && topInTolerance();
    }

    public Command setSpeedCommand(ShootingSpeeds speed) {
        return runOnce(() -> {
            setSpeed(speed);
        });
    }

    public void setSpeed(ShootingSpeeds speed) {
        this.currentSpeed=speed;

        topMotor.setControl(ShooterConfigs.motionRequest.withVelocity(currentSpeed.getTopSpeed()));
        bottomMotor.setControl(ShooterConfigs.motionRequest.withVelocity(currentSpeed.getBottomSpeed()));
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

    public Command sysIDQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysIDDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }

    public void voltageDrive(double volts) {
        topMotor.setVoltage(volts);
        bottomMotor.setVoltage(volts);
    }

   @Override
    public void periodic() {
        SmartDashboard.putNumber("Top Intake Speed: " , topMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Bottom Intake Speed: " , bottomMotor.getVelocity().getValueAsDouble());
        SmartDashboard.putBoolean("Top Intake Is In Tolerance: ", topInTolerance());
        SmartDashboard.putBoolean("Bottom Intake Is In Tolerance: ", bottomInTolerance());
        SmartDashboard.putBoolean("Intake Has Motion Paused: ", !hasMotionControl());

        // SmartDashboard.putNumber("Position value: ", currentSpeed.topSpeed);

        // if(getTopError() < 5 && getBottomError() < 5) {
        //     pauseControl();
        //     topMotor.set(ShooterConfigs.flyMotion.calculate(topMotor.getVelocity().getValueAsDouble(), currentSpeed.topSpeed));
        //     bottomMotor.set(ShooterConfigs.flyMotion.calculate(bottomMotor.getVelocity().getValueAsDouble(), currentSpeed.bottomSpeed));
        // }

        Logger.recordOutput("Flywheel/Velocity", topMotor.getVelocity().getValueAsDouble());
        Logger.recordOutput("Flywheel/Voltage", topMotor.getMotorVoltage().getValueAsDouble());
        Logger.recordOutput("Flywheel/Position", topMotor.getPosition().getValueAsDouble());
    }

    public enum ShootingSpeeds {
        STORED(0,0), 
        INTAKE(50,50),
        OUTTAKE(-50,-50),
        SHUFFLE(-5,-5),
        // TODO: SET
        CLOSE(-1,-1), 
        FAR(-1,-1), 
        INTERPOLATED(-35,-35);


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
