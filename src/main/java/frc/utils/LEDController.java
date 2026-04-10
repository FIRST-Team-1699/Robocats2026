package frc.utils;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.LEDConstants;

// NOTE: THIS CLASS MAY NOT WORK INDEPENDENTLY BECAUSE THE REQUIRED SUBSYSTEMS ARE INVISIBLE TO IT 
public class LEDController {
    // DECLARATIONS
    private static final AddressableLED leds = 
        new AddressableLED(LEDConstants.kPort);
    private static AddressableLEDBuffer ledBuffer
        = new AddressableLEDBuffer(LEDConstants.kLEDLength);

    private static int cycleTicks = 0;
    private static int blinkTicks = 0;

    private static TargetRGB currentRGB = TargetRGB.BLUE;
    private static boolean blink = false;

    static {
        leds.setLength(LEDConstants.kLEDLength);
        currentRGB = TargetRGB.BLUE;
        start();
        changeColor(currentRGB);
    }

    /**Changes the colot of LEDs */
    public static void changeColor(TargetRGB targetRGB) {
        // STOPS FROM WASTING RESOURCES VIA RETURN
        // ALLOWS LEDS TO TURN ON/OFF, BLINK FOR INTAKE
        if(blink && blinkTicks < 10) {
            setColorDirectly(TargetRGB.NONE);
        } else {
            setColorDirectly(targetRGB);
        }
        leds.setData(ledBuffer);
    }

    public static void setColorDirectly(TargetRGB targetRGB) {
        for(int i = 0; i < LEDConstants.kLEDLength; i++) {
            ledBuffer.setRGB(i, targetRGB.red, targetRGB.green, targetRGB.blue);
        }
        leds.setData(ledBuffer);
        currentRGB = targetRGB;
    }

    // METHODS TO MANIPULATE LEDs in-match
    /**Enables LEDs and assigns color to "START_UP" Enum */
    public static void start() {
        leds.start();
    }

    /**Disables LEDs*/
    public static void stop() {
        leds.stop();
    }

    public static void periodic() {
        double time = DriverStation.getMatchTime();
        cycleTicks++;
        blinkTicks++;
        if(blinkTicks > 20) {
            blinkTicks = 0;
        }

        blink =
            (endOfAuto())
                || (wonTransitionPeriod())
                || (endOfActive())
                || (endOfEndgame());

        if(cycleTicks >= 10) {
            updateColor();
        }
    }

    private static boolean endOfAuto() {
        return Robot.time <= 5.0;
    }

    private static boolean wonTransitionPeriod() {
        // TODO: Fix
        // return Robot.time > 130.0;
        return false;
    }

    private static boolean endOfActive() {
        if(Robot.hasWonAuto) {
            // return ()
        } else {

        }
        return false;
    }

    private static boolean endOfEndgame() {
        return Robot.time <= 5.0;
    }

    double[] timeEndsWin = new double[]{
        // TRANSITION
        140,
        // ENDS
        130,
        // ACTIVE PERIOD
        105,
        // INACTIVE PERIOD
        80,
        // ACTIVE
        55,
        // ENDGAME
        30
    };

    double[] timeEndsLose = new double[]{
        // TRANSITION
        140,
        // ENDS
        130,
        // INACTIVE PERIOD
        105,
        // ACTIVE PERIOD
        80,
        // INACTIVE
        55,
        // ENDGAME
        30
    };

    private static void updateColor() {
        if(Robot.isInAuto) {
            
            return;
        }


    }

    /**enums for determining RGBs of LEDs*/
    public enum TargetRGB {
        NONE(0, 0, 0), // NONE

        BLUE(18, 255, 148), // Blue

        RED(255, 13, 13),// Red

        GOLD(255, 180, 0),// Gold

        GREEN(5, 255, 10);//Green

        // VARIABLES TO SET INTS for LEDs' enums
        int red;
        int green;
        int blue;

        /**Constructor for LEDs color
         * @param hue
         * Determines hue of LEDs
         * @param saturation
         * determines saturation of LEDs
         * @param value
         * determines value of LEDs
         */
        private TargetRGB(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}
