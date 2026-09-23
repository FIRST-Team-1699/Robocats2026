package frc.team1699.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    // -------------------------------------------------------------------------
    // Hardware Constants (Adjust these matching your physical setup)
    // -------------------------------------------------------------------------
    private static final int PWM_PORT = 0;       // Must be a physical PWM port on the roboRIO
    private static final int STRIP_LENGTH = 60;  // Exact number of total physical LEDs on your strip

    // Hardware variables 
    private final AddressableLED m_led;
    private final AddressableLEDBuffer m_buffer;

    // Animation tracking state
    private int m_rainbowFirstPixelHue = 0;

    /**
     * Constructor initialized when power is applied to the robot.
     * The LEDs instantly boot, configure, and output colors.
     */
    public LED() {
        m_led = new AddressableLED(PWM_PORT);
        m_buffer = new AddressableLEDBuffer(STRIP_LENGTH);

        m_led.setLength(m_buffer.getLength());
        m_led.setData(m_buffer);
        
        // As long as the roboRIO has electrical power, this command commands 
        // the hardware to output signals continuously, even if the robot is disabled.
        m_led.start();
    }

    @Override
    public void periodic() {
        // Enclosing in a try-catch ensures a math or indexing error 
        // will never trip the main thread or cause a robot code crash.
        try {
            // Loop through every single LED pixel in the buffer
            for (int i = 0; i < m_buffer.getLength(); i++) {
                // Math Core: Shift the hue based on the pixel index 'i' relative to the full length.
                // This forces the color cycle to stretch uniformly from one end of the strip to the other.
                final int hue = (m_rainbowFirstPixelHue + (i * 180 / m_buffer.getLength())) % 180;
                
                // Assign HSV colors: Hue (0-180), Saturation (0-255), Value/Brightness (0-255)
                m_buffer.setHSV(i, hue, 255, 255);
            }

            // Increment the base starting hue each frame to move the rainbow down the strip.
            // Adjust the value "+ 2" to make the flow speed faster or slower.
            m_rainbowFirstPixelHue = (m_rainbowFirstPixelHue + 2) % 180;

            // Commit the processed frame data to the DMA controller over the PWM line.
            m_led.setData(m_buffer);

        } catch (Exception e) {
            DriverStation.reportError("LED Subsystem logic failed safely: " + e.getMessage(), e.getStackTrace());
        }
    }

  /**
   * Creates a command that runs a pattern on the entire LED strip.
   *
   * @param pattern the LED pattern to run
   */
  public Command runPattern(LEDPattern pattern) {
    return run(() -> pattern.applyTo(m_buffer));
  }
}
