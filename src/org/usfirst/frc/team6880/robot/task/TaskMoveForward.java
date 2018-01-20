package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward implements RobotTask {
	FRCRobot robot;
	double endDist;
	double direction;
	double targetDist;
	
	public TaskMoveForward(FRCRobot robot, double targetDist) {
		this.robot = robot;
		this.targetDist = targetDist;
	}
	
	public void initTask()
	{
		//Set target distance to robot's current distance + targetDist
		endDist = robot.driveSys.getEncoderDist() + targetDist;
	}
	
	public boolean runTask()
	{
		//If robot hasn't traveled far enough
		if (robot.driveSys.getEncoderDist() < endDist)
		{
			//Go straight at half speed
			robot.driveSys.tankDrive(0.5, 0.5);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
