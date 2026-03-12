package frc.utils.vision;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.units.measure.Angle;
import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.VisionSubsystem;

public class Camera {
    public boolean enabled;

    private PhotonCamera cam;
    private PhotonPoseEstimator poseEstimator;
    private PhotonTrackedTarget currentTag;
    private Transform3d offsets;

    private Optional<EstimatedRobotPose> visionEst;
    private List<PhotonPipelineResult> results;
    private Matrix<N3, N1> currentStandardDev;
    /**Camera class to hold positional and camera data. 
     * automatically sets pipeline to 1 upon construction.
     * @param name Name of Camera
     * @param offsets Transform3d, holds positional data of camera
     */
    public Camera(String name, Transform3d offsets) {
        this.cam = new PhotonCamera(name);
        this.offsets = offsets;
        this.enabled = false;
        this.poseEstimator = new PhotonPoseEstimator(VisionConstants.kTagLayout, offsets);

        this.setPipelineIndex(0);
    }

    public void setPipelineIndex(int index) {
        this.cam.setPipelineIndex(index);
    }

    public PhotonCamera getCam() {
        return this.cam;
    }

    private void setResults() {
        this.results= this.cam.getAllUnreadResults();
    }

    public PhotonPipelineResult getLatestResult() {
        return this.results.get(results.size()-1);
    }

    public boolean hasValidTags() {
        return !results.isEmpty() && getLatestResult().hasTargets();
    }

    public void updateVisionEstimate() {
        visionEst = Optional.empty();
        setResults();
        for (var result : results) {
            visionEst = poseEstimator.estimateCoprocMultiTagPose(result);
            if (visionEst.isEmpty()) {
                visionEst = poseEstimator.estimateLowestAmbiguityPose(result);
            }
            updateEstimationStdDevs(visionEst, result.getTargets());
        }
    }

    private void updateEstimationStdDevs(
        Optional<EstimatedRobotPose> estimatedPose, List<PhotonTrackedTarget> targets) {
        if (estimatedPose.isEmpty()) {
            // No pose input. Default to single-tag std devs
            currentStandardDev = VisionConstants.kSingleTagStdDevs;

        } else {
            // Pose present. Start running Heuristic
            var estStdDevs = VisionConstants.kSingleTagStdDevs;
            int numTags = 0;
            double avgDist = 0;

            // Precalculation - see how many tags we found, and calculate an average-distance metric
            for (var tgt : targets) {
                var tagPose = poseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
                if (tagPose.isEmpty()) continue;
                numTags++;
                avgDist +=
                        tagPose
                                .get()
                                .toPose2d()
                                .getTranslation()
                                .getDistance(estimatedPose.get().estimatedPose.toPose2d().getTranslation());
            }

            if (numTags == 0) {
                // No tags visible. Default to single-tag std devs
                currentStandardDev = VisionConstants.kSingleTagStdDevs;
            } else {
                // One or more tags visible, run the full heuristic.
                avgDist /= numTags;
                // Decrease std devs if multiple targets are visible
                if (numTags > 1) estStdDevs = VisionConstants.kMultiTagStdDevs;
                // Increase std devs based on (average) distance
                if (numTags == 1 && avgDist > 4)
                    estStdDevs = VecBuilder.fill(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                else estStdDevs = estStdDevs.times(1 + (avgDist * avgDist / 30));
                currentStandardDev = estStdDevs;
            }
        }
    }

    public Matrix<N3, N1> getEstimationStdDevs() {
        return currentStandardDev;
    }

    public PhotonTrackedTarget getCurrentTag() {
        return this.currentTag;
    }

    public Optional<EstimatedRobotPose> getVisionEstimate() {
        return this.visionEst;
    }

    public Transform3d getOffsets() {
        return this.offsets;
    }
}