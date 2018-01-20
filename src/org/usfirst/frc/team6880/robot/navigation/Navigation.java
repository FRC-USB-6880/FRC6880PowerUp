package org.usfirst.frc.team6880.robot.navigation;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;



public class Navigation {
	FRCRobot robot;
	public Gyro gyro=null;
	NavOptionsReader configReader;
	private static double GYRO_KP;
	
	public Navigation(FRCRobot robot, String navOptStr) {
		this.robot = robot;
		configReader = new NavOptionsReader("/team6880/navigation_options.json", navOptStr);
		if(configReader.imuExists())
			this.gyro = new NavxMXP(robot);
		GYRO_KP = configReader.getIMUVariableDouble("KP");
	}
	
	//TODO Create driveStraightToDistance()
	
	
	//TODO Create turnForDegrees()
	
	
	//TODO Create turnToHeading()
}
