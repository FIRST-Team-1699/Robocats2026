package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterHoodConstants;

public final class Configs {
    public static class ShooterHoodConfigs { 
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();

        public static final FeedbackConfigs feedback = new FeedbackConfigs();

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
        }
    }

    public static class ShooterConfigs {
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
}
