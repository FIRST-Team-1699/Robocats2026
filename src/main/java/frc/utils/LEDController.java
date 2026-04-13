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
public class LEDController extends SubsystemBase {
    // DECLARATIONS
    private AddressableLED leds; 
    private AddressableLEDBuffer ledBuffer;
        

    private int cycleTicks;
    private int blinkTicks;

    private  TargetRGB currentRGB;
    private  boolean blink;
/*
    static {
        leds.setLength(LEDConstants.kLEDLength);
        currentRGB = TargetRGB.BLUE;
        start();
        changeColor(currentRGB);
    }
*/
    public LEDController() {
        leds = new AddressableLED(LEDConstants.kPort);
        ledBuffer = new AddressableLEDBuffer(LEDConstants.kLEDLength);

        leds.setLength(ledBuffer.getLength());

        cycleTicks = 0;
        blinkTicks = 0;

        currentRGB = TargetRGB.BLUE;
        blink = false;

        start();
        changeColor(currentRGB);

    }

    /**Changes the colot of LEDs */
    public void changeColor(TargetRGB targetRGB) {
        // STOPS FROM WASTING RESOURCES VIA RETURN
        // ALLOWS LEDS TO TURN ON/OFF, BLINK FOR INTAKE
        if(blink && blinkTicks < 10) {
            setColorDirectly(TargetRGB.NONE);
        } else {
            setColorDirectly(targetRGB);
        }
        leds.setData(ledBuffer);
    }

    public void setColorDirectly(TargetRGB targetRGB) {
        for(int i = 0; i < LEDConstants.kLEDLength; i++) {
            ledBuffer.setRGB(i, targetRGB.red, targetRGB.green, targetRGB.blue);
        }
        leds.setData(ledBuffer);
        currentRGB = targetRGB;
    }

    // METHODS TO MANIPULATE LEDs in-match
    /**Enables LEDs and assigns color to "START_UP" Enum */
    public void start() {
        leds.start();
    }

    /**Disables LEDs*/
    public  void stop() {
        leds.stop();
    }

    public void periodic() {
        changeColor(TargetRGB.BLUE);
        cycleTicks++;
        blinkTicks++;
        if (blinkTicks > 20) blinkTicks = 0;
        /*
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
            */
    }

    private boolean endOfAuto() {
        return Robot.time <= 5.0;
    }

    private boolean wonTransitionPeriod() {
        // TODO: Fix
        // return Robot.time > 130.0;
        return false;
    }

    private boolean endOfActive() {
        if(Robot.hasWonAuto) {
            // return ()
        } else {

        }
        return false;
    }

    private boolean endOfEndgame() {
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

    private void updateColor() {
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
