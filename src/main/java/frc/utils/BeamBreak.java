package frc.utils;

import java.util.function.BooleanSupplier;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import frc.robot.Constants.BeamBreakConstants;

public class BeamBreak {
    private static TimeOfFlight sensor = new TimeOfFlight(BeamBreakConstants.kSensorID);
    static {
        sensor.setRangingMode(RangingMode.Short, BeamBreakConstants.kTimeDelay);
    }
    
    public static BooleanSupplier hasBall() {
        return () -> sensor.getRange()<BeamBreakConstants.kHasBallInRange;
    }

    public static double getDistance() {
        return sensor.getRange();
    }
}

