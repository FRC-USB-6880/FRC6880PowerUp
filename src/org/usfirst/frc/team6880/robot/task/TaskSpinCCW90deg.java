package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskSpinCCW90deg implements RobotTask {
	FRCRobot robot;
	double endDirection;
	double currentDirection;
	boolean wrapNegYaw;
	
	public TaskSpinCCW90deg(FRCRobot robot) 
	{
		this.robot = robot;
	}
	
	public void initTask()
	{
		//Calculate the end direction
		endDirection = Math.IEEEremainder(robot.navigation.gyro.getYaw() - 90.0, 360);

		//Start turning at half speed
		robot.driveSys.arcadeDrive(0.5, -1.0);
	}
	
	public boolean runTask()
	{
		//If robot hasn't turned 90 deg
		if (Math.IEEEremainder(endDirection - robot.navigation.gyro.getYaw(), 360) < 0)		{
			//Keep on turning by leaving motors at their current values
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0.0, 0.0);
		return true;
	}
}
