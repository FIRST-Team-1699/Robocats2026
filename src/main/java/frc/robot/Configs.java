package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import frc.robot.Constants.*;

/**Public static class designed to store all Subsystems' configurations,
 * where configurations are defined as Motor, 
 */
public final class Configs {
    public static class ShooterHoodConfigs { 
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();
        public static final MagnetSensorConfigs encoderConfigs = new MagnetSensorConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();
        public static final SoftwareLimitSwitchConfigs limits = new SoftwareLimitSwitchConfigs();

        // Motion profiles are declared here to allow for static modification and clarity
        public static final  MotionMagicVoltage motionRequest = new MotionMagicVoltage(0);
        public static final VoltageOut voltage = new VoltageOut(1);
        public static final  NeutralOut pauseMotion = new NeutralOut();

        static {
            // MotorOutputConfigs: configurations that are hard coded for the Shooter Hood Motors
            // This is applied to the motor.
            motorConfigs.Inverted = ShooterHoodConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = ShooterHoodConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = ShooterHoodConstants.kReverseLimit;
            motorConfigs.NeutralMode = ShooterHoodConstants.kNeutral;

            // TalonFXConfiguration: configurations that relate to PID, Feedforwards, gravity counter, etc.
            // This is applied to the motor.
            talonConfigs.Slot0.GravityType = ShooterHoodConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = ShooterHoodConstants.kFeedForward;

            talonConfigs.Slot0.kS = ShooterHoodConstants.kS;
            talonConfigs.Slot0.kV = ShooterHoodConstants.kV;
            talonConfigs.Slot0.kA = ShooterHoodConstants.kA;
            talonConfigs.Slot0.kP = ShooterHoodConstants.kP;
            talonConfigs.Slot0.kI = ShooterHoodConstants.kI;
            talonConfigs.Slot0.kD = ShooterHoodConstants.kD;

            // TalonFXConfiguration.MotionMagic: configurations that relate specifically to a MotionMagic profile 
            // (i.e.: MagicMotionVoltage). This is applied to the motor.
            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = ShooterHoodConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = ShooterHoodConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  ShooterHoodConstants.kMotionMagicJerk;

            // FeedbackConfigs: configurations that relate to the motor's feedback. If an external encoder is used,
            // its advisable to construct a CANcoder inside the given subystem and use MagnetSensorConfigs. This is
            // applied to the motor
            feedback.SensorToMechanismRatio = ShooterHoodConstants.kPositionConversionFactor;

            feedback.FeedbackRemoteSensorID= ShooterHoodConstants.kFeedbackID;
            feedback.FeedbackSensorSource= ShooterHoodConstants.kFeedbackSensorSource;
            feedback.RotorToSensorRatio = 81;

            // MagnetSensorConfigs: configurations that are applied to an external encoder. 
            encoderConfigs.AbsoluteSensorDiscontinuityPoint = 0.5;
            encoderConfigs.MagnetOffset = ShooterHoodConstants.kMagnetOffset;
            encoderConfigs.SensorDirection = ShooterHoodConstants.kEncoderDirection;
            
            // SoftwareLimitSwitchConfigs: configurations that limit a motor's movement in rotations. 
            // This is applied to the motor.
            limits.ForwardSoftLimitThreshold = 0.616;
            limits.ReverseSoftLimitThreshold = 0.01;

            limits.ForwardSoftLimitEnable = true;
            limits.ReverseSoftLimitEnable = true;
        }
    }

    public static class ShooterConfigs {
        // See ShooterHoodConfigs for documentation

        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs topMotorConfigs = new MotorOutputConfigs();
        public static MotorOutputConfigs bottomMotorConfigs;

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVelocityVoltage motionRequest = new MotionMagicVelocityVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();

        static {
            topMotorConfigs.Inverted = ShooterConstants.kTopInverted;
            topMotorConfigs.PeakForwardDutyCycle = ShooterConstants.kForwardLimit;
            topMotorConfigs.PeakReverseDutyCycle = ShooterConstants.kReverseLimit;
            topMotorConfigs.NeutralMode = ShooterConstants.kNeutral;

            bottomMotorConfigs=topMotorConfigs.clone();
            bottomMotorConfigs.Inverted=ShooterConstants.kBottomInverted;

            talonConfigs.Slot0.StaticFeedforwardSign = ShooterConstants.kFeedForward;

            talonConfigs.Slot0.kS = ShooterConstants.kS;
            talonConfigs.Slot0.kV = ShooterConstants.kV;
            talonConfigs.Slot0.kA = ShooterConstants.kA;
            talonConfigs.Slot0.kP = ShooterConstants.kP;
            talonConfigs.Slot0.kI = ShooterConstants.kI;
            talonConfigs.Slot0.kD = ShooterConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = ShooterConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = ShooterConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  ShooterConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = ShooterConstants.kPositionConversionFactor;
        }
    }
    public static class IndexerConfigs {
        // See ShooterHoodConfigs for documentation

        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVelocityVoltage motionRequest = new MotionMagicVelocityVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();

        static {
            motorConfigs.Inverted = IndexerConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = IndexerConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = IndexerConstants.kReverseLimit;
            motorConfigs.NeutralMode = IndexerConstants.kNeutral;

            talonConfigs.Slot0.StaticFeedforwardSign = IndexerConstants.kFeedForward;

            talonConfigs.Slot0.kS = IndexerConstants.kS;
            talonConfigs.Slot0.kV = IndexerConstants.kV;
            talonConfigs.Slot0.kA = IndexerConstants.kA;
            talonConfigs.Slot0.kP = IndexerConstants.kP;
            talonConfigs.Slot0.kI = IndexerConstants.kI;
            talonConfigs.Slot0.kD = IndexerConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IndexerConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IndexerConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IndexerConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IndexerConstants.kPositionConversionFactor;
        }
    }

    public static class HopperConfigs {
        // See ShooterHoodConfigs for documentation

        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVelocityVoltage motionRequest = new MotionMagicVelocityVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();

        static {
            motorConfigs.Inverted = IndexerConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = IndexerConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = IndexerConstants.kReverseLimit;
            motorConfigs.NeutralMode = IndexerConstants.kNeutral;

            talonConfigs.Slot0.StaticFeedforwardSign = IndexerConstants.kFeedForward;

            talonConfigs.Slot0.kS = IndexerConstants.kS;
            talonConfigs.Slot0.kV = IndexerConstants.kV;
            talonConfigs.Slot0.kA = IndexerConstants.kA;
            talonConfigs.Slot0.kP = IndexerConstants.kP;
            talonConfigs.Slot0.kI = IndexerConstants.kI;
            talonConfigs.Slot0.kD = IndexerConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IndexerConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IndexerConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IndexerConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IndexerConstants.kPositionConversionFactor;
        }
    }  
  public static class IntakePivotConfigs { 
        // See ShooterHoodConfigs for documentation

        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs breakMotorOutput = new MotorOutputConfigs();
        public static MotorOutputConfigs coastMotorOutput = new MotorOutputConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();
        // public static final SoftwareLimitSwitchConfigs limits = new SoftwareLimitSwitchConfigs();

        public static final MotionMagicVoltage motionRequest = new MotionMagicVoltage(0);
        public static final MotionMagicVoltage slowMotionRequest = new MotionMagicVoltage(0);
        public static final NeutralOut pausePassiveMotion = new NeutralOut();
        public static final VoltageOut pauseActiveMotion = new VoltageOut(0.2);
        // public static final MagnetSensorConfigs encoderConfig = new MagnetSensorConfigs();

        static {
            breakMotorOutput.Inverted = IntakePivotConstants.kInverted;
            breakMotorOutput.PeakForwardDutyCycle = IntakePivotConstants.kForwardLimit;
            breakMotorOutput.PeakReverseDutyCycle = IntakePivotConstants.kReverseLimit;
            breakMotorOutput.NeutralMode = NeutralModeValue.Brake;

            // coastMotorOutput = breakMotorOutput.clone();
            // coastMotorOutput.NeutralMode = NeutralModeValue.Coast;

            talonConfigs.Slot0.GravityType = IntakePivotConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = IntakePivotConstants.kFeedForward;

            talonConfigs.Slot0.kS = IntakePivotConstants.kS0;
            talonConfigs.Slot0.kV = IntakePivotConstants.kV0;
            talonConfigs.Slot0.kA = IntakePivotConstants.kA0;
            talonConfigs.Slot0.kP = IntakePivotConstants.kP0;
            talonConfigs.Slot0.kI = IntakePivotConstants.kI0;
            talonConfigs.Slot0.kG = IntakePivotConstants.kG0;

            talonConfigs.Slot0.GravityArmPositionOffset = IntakePivotConstants.kGravityOffset;

            talonConfigs.Slot1.GravityType = IntakePivotConstants.kGravityCounter;
            talonConfigs.Slot1.StaticFeedforwardSign = IntakePivotConstants.kFeedForward;

            talonConfigs.Slot1.kS = IntakePivotConstants.kS1;
            talonConfigs.Slot1.kV = IntakePivotConstants.kV1;
            talonConfigs.Slot1.kA = IntakePivotConstants.kA1;
            talonConfigs.Slot1.kP = IntakePivotConstants.kP1;
            talonConfigs.Slot1.kI = IntakePivotConstants.kI1;
            talonConfigs.Slot1.kG = IntakePivotConstants.kG1;

            talonConfigs.Slot1.GravityArmPositionOffset = IntakePivotConstants.kGravityOffset;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IntakePivotConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IntakePivotConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IntakePivotConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IntakePivotConstants.kPositionConversionFactor;

            motionRequest.Slot = 0;
            slowMotionRequest.Slot = 1;
        }
    }
    public static class IntakeConfigs {
        // See ShooterHoodConfigs for documentation

        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs topMotorConfigs = new MotorOutputConfigs();
        public static MotorOutputConfigs bottomMotorConfigs;

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVelocityVoltage motionRequest = new MotionMagicVelocityVoltage(0);
        public static final EmptyControl pauseMotion = new EmptyControl();

        static {
            topMotorConfigs.Inverted = IntakeConstants.kTopInverted;
            topMotorConfigs.PeakForwardDutyCycle = IntakeConstants.kForwardLimit;
            topMotorConfigs.PeakReverseDutyCycle = IntakeConstants.kReverseLimit;
            topMotorConfigs.NeutralMode = IntakeConstants.kNeutral;

            bottomMotorConfigs=topMotorConfigs.clone();
            bottomMotorConfigs.Inverted=IntakeConstants.kBottomInverted;

            talonConfigs.Slot0.StaticFeedforwardSign = IntakeConstants.kFeedForward;

            talonConfigs.Slot0.kS = IntakeConstants.kS;
            talonConfigs.Slot0.kV = IntakeConstants.kV;
            talonConfigs.Slot0.kA = IntakeConstants.kA;
            talonConfigs.Slot0.kP = IntakeConstants.kP;
            talonConfigs.Slot0.kI = IntakeConstants.kI;
            talonConfigs.Slot0.kD = IntakeConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IntakeConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IntakeConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IntakeConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IntakeConstants.kPositionConversionFactor;
        }
    }
      public static class ClimbConfigs {
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVoltage motionRequest = new MotionMagicVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();

        static {
            motorConfigs.Inverted = ClimbConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = ClimbConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = ClimbConstants.kReverseLimit;
            motorConfigs.NeutralMode = ClimbConstants.kNeutral;
            // TODO: Mark for removal
            // motorConfigs.DutyCycleNeutralDeadband = 0.05;

            talonConfigs.Slot0.GravityType = ClimbConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = ClimbConstants.kFeedForward;

            talonConfigs.Slot0.kS = ClimbConstants.kS;
            talonConfigs.Slot0.kV = ClimbConstants.kV;
            talonConfigs.Slot0.kA = ClimbConstants.kA;
            talonConfigs.Slot0.kP = ClimbConstants.kP;
            talonConfigs.Slot0.kI = ClimbConstants.kI;
            talonConfigs.Slot0.kD = ClimbConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = ClimbConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = ClimbConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  ClimbConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = ClimbConstants.kPositionConversionFactor;
        }
    }
}
