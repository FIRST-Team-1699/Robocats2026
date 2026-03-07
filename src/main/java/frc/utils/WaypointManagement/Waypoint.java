package frc.utils.WaypointManagement;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Pose2d;

/** Describes a target for bot angular or positional management
 *  for vision. For example, the Hub is a waypoint.
 */
public class Waypoint {
    private HashMap<Integer,Pose2d> targetOffsetMap = new HashMap<>();
    // private double yawOffset;
    /** Uses targetOffsetMap to relate any ID to an offset (i.e.: waypoint)
     * @param offset additional offset of yaw of a position
     * @param tags april tags of waypoint
     */
    public Waypoint(AprilTagPoint ...tags) {
        for(AprilTagPoint tag : tags) {
            this.targetOffsetMap.put(tag.getID(), tag.getOffset());
        }
    }
    /** returns the {x,y} offset from a tag to a waypoint.
     *  NOT INTENDED FOR A REPLACEMENT OF hasID.
     *  @param id Id of the april tag
     */
    public Pose2d getOffset(int id) {
        return this.targetOffsetMap.get(id);
    }

    /** returns if array has specified id.
     * @param id Id of a tag
     **/
    public boolean hasId(int id) {
        return this.targetOffsetMap.containsKey(id);
    }
}