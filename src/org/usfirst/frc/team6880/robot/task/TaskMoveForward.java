package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward implements RobotTask {
	FRCRobot robot;
	double endDist;
	double direction;
	double targetDist;
	boolean moveForward;
	
	public TaskMoveForward(FRCRobot robot, double targetDist) {
		this.robot = robot;
		this.targetDist = targetDist;
		moveForward = (targetDist<0) ? false : true;
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
			double speed = (moveForward) ? 0.5 : -0.5;
			robot.driveSys.tankDrive(speed, speed);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
