package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfigurator;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.DeviceIdentifier;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import frc.robot.Constants.*;


public final class Configs {
    public static class ShooterHoodConfigs { 
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();
        public static final MagnetSensorConfigs encoderConfigs = new MagnetSensorConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();
        public static final SoftwareLimitSwitchConfigs limits = new SoftwareLimitSwitchConfigs();

        public static final  MotionMagicVoltage motionRequest = new MotionMagicVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();

        static {
            motorConfigs.Inverted = ShooterHoodConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = ShooterHoodConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = ShooterHoodConstants.kReverseLimit;
            motorConfigs.NeutralMode = ShooterHoodConstants.kNeutral;

            talonConfigs.Slot0.GravityType = ShooterHoodConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = ShooterHoodConstants.kFeedForward;

            talonConfigs.Slot0.kS = ShooterHoodConstants.kS;
            talonConfigs.Slot0.kV = ShooterHoodConstants.kV;
            talonConfigs.Slot0.kA = ShooterHoodConstants.kA;
            talonConfigs.Slot0.kP = ShooterHoodConstants.kP;
            talonConfigs.Slot0.kI = ShooterHoodConstants.kI;
            talonConfigs.Slot0.kD = ShooterHoodConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = ShooterHoodConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = ShooterHoodConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  ShooterHoodConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = ShooterHoodConstants.kPositionConversionFactor;

            feedback.FeedbackRemoteSensorID= ShooterHoodConstants.kFeedbackID;
            feedback.FeedbackSensorSource= ShooterHoodConstants.kFeedbackSensorSource;
            feedback.RotorToSensorRatio = 81;

            encoderConfigs.AbsoluteSensorDiscontinuityPoint = 0.5;
            encoderConfigs.MagnetOffset = ShooterHoodConstants.kMagnetOffset;
            encoderConfigs.SensorDirection = ShooterHoodConstants.kEncoderDirection;
            // ROTATIONS:
            limits.ForwardSoftLimitThreshold = 0.616;
            limits.ReverseSoftLimitThreshold = 0.01;

            limits.ForwardSoftLimitEnable = true;
            limits.ReverseSoftLimitEnable = true;
        }
    }

    public static class ShooterConfigs {
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs topMotorConfigs = new MotorOutputConfigs();
        public static MotorOutputConfigs bottomMotorConfigs;

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

        public static final  MotionMagicVelocityVoltage motionRequest = new MotionMagicVelocityVoltage(0);
        public static final  EmptyControl pauseMotion = new EmptyControl();
        public static final BangBangController flyMotion = new BangBangController();

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

            talonConfigs.Slot0.kS = IndexerConstants.kS0;
            talonConfigs.Slot0.kV = IndexerConstants.kV0;
            talonConfigs.Slot0.kA = IndexerConstants.kA0;
            talonConfigs.Slot0.kP = IndexerConstants.kP0;
            talonConfigs.Slot0.kI = IndexerConstants.kI0;
            talonConfigs.Slot0.kD = IndexerConstants.kD0;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IndexerConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IndexerConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IndexerConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IndexerConstants.kPositionConversionFactor;
        }
    }

    public static class HopperConfigs {
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

            talonConfigs.Slot0.kS = IndexerConstants.kS0;
            talonConfigs.Slot0.kV = IndexerConstants.kV0;
            talonConfigs.Slot0.kA = IndexerConstants.kA0;
            talonConfigs.Slot0.kP = IndexerConstants.kP0;
            talonConfigs.Slot0.kI = IndexerConstants.kI0;
            talonConfigs.Slot0.kD = IndexerConstants.kD0;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IndexerConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IndexerConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IndexerConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IndexerConstants.kPositionConversionFactor;
        }
    }  
  public static class IntakePivotConfigs { 
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();
        public static final FeedbackConfigs feedback = new FeedbackConfigs();
        public static final SoftwareLimitSwitchConfigs limits = new SoftwareLimitSwitchConfigs();

        public static final MotionMagicVoltage motionRequest = new MotionMagicVoltage(0);
        public static final EmptyControl pauseMotion = new EmptyControl();
        public static final MagnetSensorConfigs encoderConfig = new MagnetSensorConfigs();

        static {
            motorConfigs.Inverted = IntakePivotConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = IntakePivotConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = IntakePivotConstants.kReverseLimit;
            motorConfigs.NeutralMode = IntakePivotConstants.kNeutral;

            talonConfigs.Slot0.GravityType = IntakePivotConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = IntakePivotConstants.kFeedForward;

            talonConfigs.Slot0.kS = IntakePivotConstants.kS0;
            talonConfigs.Slot0.kV = IntakePivotConstants.kV0;
            talonConfigs.Slot0.kA = IntakePivotConstants.kA0;
            talonConfigs.Slot0.kP = IntakePivotConstants.kP0;
            talonConfigs.Slot0.kI = IntakePivotConstants.kI0;
            talonConfigs.Slot0.kG = IntakePivotConstants.kG0;
            
            talonConfigs.Slot0.GravityType = IntakePivotConstants.kGravityCounter;
            talonConfigs.Slot1.StaticFeedforwardSign = IntakeConstants.kFeedForward;

            // talonConfigs.Slot1.kS = IntakePivotConstants.kS1;
            // talonConfigs.Slot1.kV = IntakePivotConstants.kV1;
            // talonConfigs.Slot1.kA = IntakePivotConstants.kA1;
            // talonConfigs.Slot1.kP = IntakePivotConstants.kP1;
            // talonConfigs.Slot1.kI = IntakePivotConstants.kI1;
            // talonConfigs.Slot1.kD = IntakePivotConstants.kD1;
            // talonConfigs.Slot0.kG = IntakePivotConstants.kG;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = IntakePivotConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = IntakePivotConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  IntakePivotConstants.kMotionMagicJerk;

            feedback.SensorToMechanismRatio = IntakePivotConstants.kPositionConversionFactor;
            feedback.RotorToSensorRatio = IntakePivotConstants.rotorToSensor;
            
            feedback.FeedbackRemoteSensorID= IntakePivotConstants.kFeedbackID;
            feedback.FeedbackSensorSource= IntakePivotConstants.kFeedbackSensorSource;

            encoderConfig.AbsoluteSensorDiscontinuityPoint = 0.5;
            encoderConfig.MagnetOffset = IntakePivotConstants.kMagnetOffset;
            encoderConfig.SensorDirection = IntakePivotConstants.kFeedbackDirection;
    
            limits.ForwardSoftLimitThreshold = 0.24;
            limits.ReverseSoftLimitThreshold = -0.0005;

            // limits.ForwardSoftLimitThreshold = .4;
            // limits.ReverseSoftLimitThreshold = .4;

            limits.ForwardSoftLimitEnable = true;
            limits.ReverseSoftLimitEnable = true;
        }
    }
    public static class IntakeConfigs {
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
            motorConfigs.DutyCycleNeutralDeadband = 0.05;

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

    public static class VisionConfigs {
        public static final InterpolatingDoubleTreeMap speedTopMap =
            new InterpolatingDoubleTreeMap();

        public static final InterpolatingDoubleTreeMap speedBottomMap =
            new InterpolatingDoubleTreeMap(); 

        public static final InterpolatingDoubleTreeMap shootPivotMap = 
            new InterpolatingDoubleTreeMap();

        static {
            speedTopMap.put(3.0, -37.0);
            speedTopMap.put(2.15, -35.0);
            speedTopMap.put(1.37, -28.0);

            speedBottomMap.put(3.0, -30.0);
            speedBottomMap.put(2.15, -30.0);
            speedBottomMap.put(1.37, -23.0);

            shootPivotMap.put(3.0, .15);
            shootPivotMap.put(2.15, .2);
            shootPivotMap.put(1.37, .39);
        }
    }
}
