package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class CoordinateSys {
    
    double current_x, current_y, current_theta;

    /**
     * 
     */
    public CoordinateSys(FRCRobot robot) {
        // This will be invoked during robotInit()
    }
    
    public void setCurrentPos(double x, double y, double theta) {
    	current_x = x;
    	current_y = y;
    	current_theta = theta;
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
