// package frc.team1699.commands;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
// import frc.robot.Configs.ShooterHoodConfigs;
// import frc.robot.Constants.AutoConstants;
// import frc.robot.Constants.VisionConstants;
// import frc.robot.Robot;
// import frc.team1699.subsystems.HopperSubsystem;
// import frc.team1699.subsystems.IndexerSubsystem;
// import frc.team1699.subsystems.IntakePivotSubsystem;
// import frc.team1699.subsystems.ShooterHoodSubsystem;
// import frc.team1699.subsystems.ShooterSubsystem;
// import frc.team1699.subsystems.VisionSubsystem;
// import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
// import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
// import frc.team1699.subsystems.IntakePivotSubsystem.IntakePositions;
// import frc.team1699.subsystems.IntakeSubsystem.IntakeSpeeds;
// import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
// import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;
// import frc.team1699.subsystems.IntakeSubsystem;
// import frc.utils.vision.RobotPose;

// public class FarShootCommand extends Command {
//     private final ShooterSubsystem shoot;
//     private final ShooterHoodSubsystem shootHood;
//     private final IndexerSubsystem indexer;
//     private final HopperSubsystem hopper;
//     private final IntakeSubsystem intake;
//     private final Timer time;

//     public FarShootCommand(
//         ShooterSubsystem shoot, 
//         ShooterHoodSubsystem shootHood,
//         IndexerSubsystem indexer, 
//         HopperSubsystem hopper,
//         IntakeSubsystem intake
//     ) {
//         this.shoot = shoot;
//         this.shootHood = shootHood;
//         this.indexer = indexer;
//         this.hopper = hopper;
//         this.intake=intake;
//         this.time=new Timer();

//         addRequirements(shoot, indexer, hopper, intake);
//     }

//     @Override
//     public void initialize() {
//         ShooterHoodSubsystem.pasueInterpolate=true;
//         intake.setSpeed(IntakeSpeeds.INTAKE);
//         if(Robot.isInAuto) {
//             time.start();
//         }
//     }

//     @Override
//     public void execute() {
//         shoot.setSpeed(ShootingSpeeds.FAR);
//         shootHood.setPosition(HoodPositions.MIN);
//         hopper.setSpeed(HopperSpeeds.INTAKE);
//         if(shoot.isTotalInTollerance().getAsBoolean()) {
//             indexer.setSpeed(IndexingSpeeds.INTAKE);
//         } else {
//             indexer.setSpeed(IndexingSpeeds.STORED);
//         }
//     }

//     @Override
//     public boolean isFinished() {
//         return time.hasElapsed(AutoConstants.kShootTimerLong);
//     }

//     @Override
//     public void end(boolean isFinished) {
//         shoot.setSpeed(ShootingSpeeds.STORED);

//         indexer.setSpeed(IndexingSpeeds.STORED);
//         hopper.setSpeed(HopperSpeeds.STORED);
//         intake.setSpeed(IntakeSpeeds.STORED);
//         ShooterHoodSubsystem.pasueInterpolate=false;
//     }
// }
