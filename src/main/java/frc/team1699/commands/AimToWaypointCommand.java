package frc.team1699.commands;




import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;


import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.team1699.subsystems.CommandSwerveDrivetrain;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.VisionSubsystem.TagWaypoint;
import frc.utils.vision.Waypoint;




public class AimToWaypointCommand extends Command {
    private TagWaypoint point;
    private final PIDController rotationalController = new PIDController(1, 0, 0.01);

    private double rotationalOutput;
    private VisionSubsystem vision;
    private CommandSwerveDrivetrain drivetrain;
    public AimToWaypointCommand(
        VisionSubsystem vision,
        CommandSwerveDrivetrain drivetrain,
        TagWaypoint point
    ) {
        this.vision = vision;
        this.drivetrain = drivetrain;
        this.point=point;

        addRequirements(
            this.vision,
            this.drivetrain
        );
    }

    @Override
    public void initialize() {
        vision.setWaypoint(point);
        vision.disableStickyCam();
    }


    @Override
    public void execute() {
        // System.out.println(Math.toRadians(vision.getYaw()));
        // rotationalOutput = MathUtil.clamp(
        //    rotationalController.calculate(
        //     Math.toRadians(vision.getYaw()), 0
        //     ), -1.5, 1.5
        // );
        System.out.println(-Math.toRadians(vision.getYaw()));
        drivetrain.setControl(
            new SwerveRequest.RobotCentric()
                .withRotationalRate(-Math.toRadians(vision.getYaw()))
                .withDriveRequestType(DriveRequestType.OpenLoopVoltage)
        );
    }




   @Override
   public boolean isFinished() {
       return vision.isInTolerance();
        // return false;
   }




   @Override
   public void end(boolean isInterupted) {}
}




