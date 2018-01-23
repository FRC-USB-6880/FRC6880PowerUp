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
	
	/**
	 * This method makes the robot drive facing a target direction
	 * @param speed The speed for the robot to drive at
	 * @param targetDirection The desired direction for the robot to drive
	 */
	public void driveDirection(double speed, double targetDirection)
	{
		robot.driveSys.arcadeDrive(speed, GYRO_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360) / 180);
	}
	
	/**
	 * This method makes the robot drive spin to a target orientation
	 * @param targetDirection The desired direction for the robot to point
	 */
	public void spinToDirection(double targetDirection)
	{
		robot.driveSys.arcadeDrive(GYRO_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360) / 180, 1);
	}
	//TODO: Coordinate System?
	//TODO: Computer Vision?
}
