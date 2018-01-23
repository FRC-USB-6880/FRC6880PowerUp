package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward implements RobotTask {
	FRCRobot robot;
	double endDist;
	double travelDist;
	double direction;
	private static final double SPEED_KP = 0.5;
	
	public TaskMoveForward(FRCRobot robot, double travelDist) {
		this.robot = robot;
		this.travelDist = travelDist;
	}
	
	public void initTask()
	{
		//Set target distance to robot's current distance + the distance we will travel
		endDist = robot.driveSys.getDist() + travelDist;
		//Get the direction we want to travel
		direction = robot.navigation.gyro.getYaw();
	}
	
	public boolean runTask()
	{
		double remainingDist = endDist - robot.driveSys.getDist();
		//If robot still has remaining distance
		if (remainingDist > 0)
		{
			//Go straight and slow down before we reach out target distance
			robot.navigation.driveDirection(SPEED_KP * remainingDist / travelDist, direction);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0, 0);
		return true;
	}
}
