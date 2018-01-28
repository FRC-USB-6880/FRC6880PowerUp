package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class TaskMoveForward implements RobotTask {
	FRCRobot robot;
	double endDist;
	double travelDist;
	double angleToMaintain;
	private static final double SPEED_KF = 0.5;
	
	public TaskMoveForward(FRCRobot robot, double travelDist) {
		this.robot = robot;
		this.travelDist = travelDist;
	}
	
	public void initTask()
	{
		//Set target distance to robot's current distance + the distance we will travel
		endDist = robot.driveSys.getEncoderDist() + travelDist;
		//Get the direction we want to travel
		angleToMaintain = robot.navigation.gyro.getYaw();
		System.out.format("angleToMaintain = %f, endDist = %f\n", angleToMaintain, endDist);
	}
	
	public boolean runTask()
	{
		double remainingDist = Math.abs(endDist) - Math.abs(robot.driveSys.getEncoderDist());
		System.out.format("remaining distance = %f\n", remainingDist);
		//If robot still has remaining distance
		if (remainingDist > 0)
		{
			//Go straight and slow down before we reach out target distance
		    double speed = SPEED_KF * remainingDist / travelDist;
		    speed = Math.max(Math.min(speed, 0.3), 0.7);
			robot.navigation.driveDirection(speed, angleToMaintain);
			return false;
		}
		//Else stop the robot and tell robot to go to next task
		robot.driveSys.tankDrive(0, 0);
		return true;
	}
}
