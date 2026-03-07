// package frc.team1699.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
// import frc.team1699.subsystems.IndexerSubsystem;
// import frc.team1699.subsystems.ShooterSubsystem;
// import frc.team1699.subsystems.ShooterSubsystem.ShootingSpeeds;

// public class ShootCommand extends Command {
//     private final IndexerSubsystem indexer;
//     private final ShooterSubsystem shooter;
//     public ShootCommand(ShooterSubsystem shooter, IndexerSubsystem indexer) {
//         this.shooter = shooter;
//         this.indexer = indexer;
//         addRequirements(shooter, indexer);
//     }

//     @Override
//     public void initialize() {
//         shooter.setSpeed(ShootingSpeeds.INTERPOLATED)
//             .andThen(new WaitUntilCommand(shooter.isTotalInTollerance()))
//             .andThen(ind);
//     }
// }
