package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward implements RobotTask {
	FRCRobot robot;
	double startingLocation;
	double travelDist;
	double angleToMaintain;
	private static final double SPEED_KF = 0.5;
	
	public TaskMoveForward(FRCRobot robot, double travelDist) {
		this.robot = robot;
		this.travelDist = travelDist;
	}
	
	public void initTask()
	{
		//Set starting location of the encoders
	    startingLocation = robot.driveSys.getEncoderDist();
		//Get the direction we want to travel
		angleToMaintain = robot.navigation.gyro.getYaw();
		System.out.format("angleToMaintain = %f, startingLocation = %f\n", angleToMaintain, startingLocation);
	}
	
	public boolean runTask()
	{
		double distTravelled = robot.driveSys.getEncoderDist() - startingLocation;
		System.out.format("distance travelled = %f\n", distTravelled);
		//If robot still has remaining distance
		if (Math.abs(distTravelled) > Math.abs(travelDist) )
		{
			//Go straight and slow down before we reach out target distance
		    double speed = SPEED_KF * (1 - Math.abs(distTravelled / travelDist));
		    speed = Math.max(Math.min(speed, 0.3), speed);
			robot.navigation.driveDirection(speed, angleToMaintain);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0, 0);
		return true;
	}
}
