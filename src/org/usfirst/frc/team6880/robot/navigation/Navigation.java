package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;



public class Navigation {
	FRCRobot robot;
	public Gyro gyro;
	private double gyro_GoStraight_KP = 0.2;
	
	public Navigation(FRCRobot robot, String navOptStr) {
		this.robot = robot;
		NavOptionsReader configReader = new NavOptionsReader(JsonReader.navigationFile, navOptStr);
		if(configReader.imuExists())
			this.gyro = new NavxMXP(robot);
		gyro_GoStraight_KP = configReader.getIMUVariableDouble("Kp");
	}
	
	/**
	 * This method makes the robot drive facing a target direction
	 * @param speed The speed for the robot to drive at
	 * @param targetDirection The desired direction for the robot to drive
	 */
	public void driveDirection(double speed, double targetDirection)
	{
		robot.driveSys.arcadeDrive(Math.max(speed, 0.1), gyro_GoStraight_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360));
	}

	//TODO Create driveStraightToDistance()
	
	/**
	 * This method makes the robot drive spin to a target orientation
	 * @param targetDirection The desired direction for the robot to point
	 */
	public void spinToDirection(double targetDirection)
	{
		robot.driveSys.arcadeDrive(0, gyro_GoStraight_KP * Math.IEEEremainder(targetDirection - gyro.getYaw(), 360) / 180);
	}
	
	//TODO Create turnForDegrees()
	
	
	//TODO Create turnToHeading()	

	//TODO: Coordinate System?
	//TODO: Computer Vision?
}
