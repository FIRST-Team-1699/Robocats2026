package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;

import frc.robot.Constants.IndexerConstants;

public final class Configs {
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
}
