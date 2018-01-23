package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskSpinAngle implements RobotTask {
	FRCRobot robot;
	double endDirection;
	double angle;
	double currentDirection;
	boolean wrapNegYaw;
	
	public TaskSpinAngle(FRCRobot robot, double angle)
	{
		this.robot = robot;
		this.angle = angle;
	}
	
	public void initTask()
	{
		//Calculate the end direction
		endDirection = Math.IEEEremainder(robot.navigation.gyro.getYaw() + angle, 360);
	}
	
	public boolean runTask()
	{
		//If robot isn't facing the end direction
		if (Math.abs(Math.IEEEremainder(endDirection - robot.navigation.gyro.getYaw(), 360)) > 0.001)
		{
			//Keep on spinning towards the end direction
			robot.navigation.spinToDirection(endDirection);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
