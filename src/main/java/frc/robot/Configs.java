package frc.robot;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

import frc.robot.Constants.PivotConstants;

public final class Configs {
    public static class PivotConfigs { 
        public static final TalonFXConfiguration talonConfigs = new TalonFXConfiguration();
        public static final MotorOutputConfigs motorConfigs = new MotorOutputConfigs();
        public static final FeedbackConfigs feedback = new FeedbackConfigs();
        public static final MotionMagicVoltage m_request = new MotionMagicVoltage(PivotConstants.kOffset);

        static {
            motorConfigs.Inverted = PivotConstants.kInverted;
            motorConfigs.PeakForwardDutyCycle = PivotConstants.kForwardLimit;
            motorConfigs.PeakReverseDutyCycle = PivotConstants.kReverseLimit;
            motorConfigs.NeutralMode = PivotConstants.kNeutral;

            talonConfigs.Slot0.GravityType = PivotConstants.kGravityCounter;
            talonConfigs.Slot0.StaticFeedforwardSign = PivotConstants.kFeedForward;

            talonConfigs.Slot0.kS = PivotConstants.kS;
            talonConfigs.Slot0.kV = PivotConstants.kV;
            talonConfigs.Slot0.kA = PivotConstants.kA;
            talonConfigs.Slot0.kP = PivotConstants.kP;
            talonConfigs.Slot0.kI = PivotConstants.kI;
            talonConfigs.Slot0.kD = PivotConstants.kD;

            talonConfigs.MotionMagic.MotionMagicCruiseVelocity = PivotConstants.kMotionMagicVelocity;
            talonConfigs.MotionMagic.MotionMagicAcceleration = PivotConstants.kMotionMagicAcceleration;
            talonConfigs.MotionMagic.MotionMagicJerk =  PivotConstants.kMotionMagicJerk;


            // NOTE: THIS FEEDBACK ASSUMES EQUAL GEAR RATIOS BETWEEN MOTORS. MAY NOT BE CORRECT
            feedback.SensorToMechanismRatio = PivotConstants.kPositionConversionFactor;
            
            // TODO: FIND OUT IF WE'RE DOING BORE OR NAH
            // feedback.FeedbackRemoteSensorID= -1;
            // feedback.FeedbackSensorSource= FeedbackSensorSourceValue.RemoteCANcoder;
        }
    }
}
