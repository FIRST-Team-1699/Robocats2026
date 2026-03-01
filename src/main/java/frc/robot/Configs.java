package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.signals.GravityTypeValue;

import frc.robot.Constants.IntakePivotConstants;
import frc.robot.Constants.IntakeConstants;

public final class Configs {
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
            talonConfigs.Slot0.kG = IntakePivotConstants.kG;
            
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
            
            // feedback.FeedbackRemoteSensorID= IntakePivotConstants.kFeedbackID;
            // feedback.FeedbackSensorSource= IntakePivotConstants.kFeedbackSensorSource;

            encoderConfig.AbsoluteSensorDiscontinuityPoint = 0.5;
            encoderConfig.MagnetOffset = -.53;
    
            limits.ForwardSoftLimitThreshold = .0;
            limits.ReverseSoftLimitThreshold = -.23;

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
}
