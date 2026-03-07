package frc.utils.WaypointManagement;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**Handles the relationship between:
 * 1. Targeted ID Tags
 * 2. Their offset to a given waypoint
*/
public class AprilTagPoint {
    /**stores offset from tag to waypoint*/
    private Pose2d offsetToWaypoint;
    /**id of targeted tag*/
    private int id;

    /**@param id April Tag ID
     * @param offsetToWaypoint Pose2d offset to waypoint
     */
    public AprilTagPoint(int id, Pose2d offsetToWaypoint) {
        this.id=id;
        new Pose2d(1,2,new Rotation2d());
        this.offsetToWaypoint=new Pose2d(offsetToWaypoint.getX(),offsetToWaypoint.getY(), new Rotation2d());
    }

    public int getID() {
        return this.id;
    }
    /** returns Pose2d of offset */
    public Pose2d getOffset() {
        return this.offsetToWaypoint;
    }
}
