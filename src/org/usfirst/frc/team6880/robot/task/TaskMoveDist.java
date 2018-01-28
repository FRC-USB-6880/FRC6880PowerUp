package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveDist implements RobotTask {
	FRCRobot robot;
	double speed;
	double endDist;
	double direction;
	double targetDist;
	
	public TaskMoveDist(FRCRobot robot, double speed, double targetDist) {
		this.robot = robot;
		this.targetDist = targetDist;
		this.speed = speed;
	}
	
	public void initTask()
	{
		//Set target distance to robot's current distance + targetDist
		endDist = robot.driveSys.getEncoderDist() + targetDist;
	}
	
	public boolean runTask()
	{
		//If robot hasn't traveled far enough
		if (Math.abs(robot.driveSys.getEncoderDist()) < Math.abs(endDist))
		{
			//Go straight at half speed
			robot.driveSys.tankDrive(speed, speed);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
