package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward20m implements RobotTask {
	FRCRobot robot;
	double endDist;
	double direction;
	
	public TaskMoveForward20m(FRCRobot robot) {
		this.robot = robot;
	}
	
	public void initTask()
	{
		//Set target distance to robot's current distance + 20m
		endDist = robot.driveSys.getDist() + 2;
		//Get the direction we want to travel
		direction = robot.navigation.gyro.getYaw();
	}
	
	public boolean runTask()
	{
		//If robot hasn't traveled 20m
		if (robot.driveSys.getDist() < endDist)
		{
			//Go straight at half speed
			robot.navigation.driveDirection(0.5, direction);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
