// TODO: TEST NATIVE PACKAGE WAITCOMMAND, SWITCH IF FAILED

// package frc.team1699.commands;

// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.team1699.subsystems.HopperSubsystem.HopperSpeeds;
// import frc.team1699.subsystems.IndexerSubsystem.IndexingSpeeds;
// import frc.team1699.subsystems.ShooterHoodSubsystem.HoodPositions;
// import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;

// public class WaitTimedCommand extends Command{
//     private  final double elaspeTime;
//     private final Timer time;

//     public WaitTimedCommand(double elaspeTime) {
//         this.time=new Timer();
//         this.elaspeTime=elaspeTime;
//     }

//     @Override
//     public void initialize() {}

//     @Override
//     public void execute() {}

//     @Override
//     public boolean isFinished() {
//         return time.hasElapsed(elaspeTime);
//     }

//     @Override
//     public void end(boolean isFinished) {
//         time.stop();
//         time.reset();
//     }
// }
