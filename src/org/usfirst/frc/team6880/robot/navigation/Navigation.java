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
		gyro_GoStraight_KP = configReader.getIMUVariableDouble("KP");
	}
	
	public void driveDirection(double speed, double direction)
	{
		robot.driveSys.arcadeDrive(speed, gyro_GoStraight_KP * (gyro.getYaw() - direction));
	}
	//TODO Create driveStraightToDistance()
	
	
	//TODO Create turnForDegrees()
	
	
	//TODO Create turnToHeading()	
	//TODO: Coordinate System?
	//TODO: Computer Vision?
}
