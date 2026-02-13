package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.EmptyControl;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

import frc.robot.Constants.ClimbConstants;

public final class Configs {
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
