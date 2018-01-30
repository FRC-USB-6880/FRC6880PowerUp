package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class CoordinateSys {
    
    double starting_x, starting_y, starting_theta;
    double current_x, current_y, current_theta;

    /**
     * 
     */
    public CoordinateSys(FRCRobot robot, double[] startingPos) {
        // This will be invoked during robotInit()
    }
    
    public void setOrigin() {
        // TODO
        // Set the current pose as (x = 0, y = 0, theta = 0)
        // Get the gyro reading and encoder counts
        // This will be called during autonomousInit()
    }
    
    public void displayCurrentPose() {
        // TODO
        
    }

}
