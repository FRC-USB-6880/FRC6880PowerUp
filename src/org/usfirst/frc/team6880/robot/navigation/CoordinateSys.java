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
    
    public void setCurrentPos(double x, double y) {
    	current_x = x;
    	current_y = y;
    }
    
    public void displayCurrentPose() {
        // TODO
        
    }

}
