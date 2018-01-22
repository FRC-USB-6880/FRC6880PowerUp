package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class Navigation {
	FRCRobot robot;
	public Gyro gyro;
	private static final double GYRO_KP = 0.2;
	
	public Navigation(FRCRobot robot) {
		this.robot = robot;
		this.gyro = new NavxMXP(robot);
	}
	
	public void driveDirection(double speed, double direction)
	{
		robot.driveSys.arcadeDrive(speed, direction);
	}
	
	//TODO: Coordinate System?
	//TODO: Computer Vision?
}
