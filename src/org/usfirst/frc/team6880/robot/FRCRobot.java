package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	RobotConfigReader configReader;
	
	AutonomousTasks autonTasks;
	
	public FRCRobot(Robot wpilibrobot)
	{
		this.wpilibrobot = wpilibrobot;
		configReader = new RobotConfigReader("/robots.json", "2018_Robot"); 
		driveSys = new DriveSystem(this, configReader.getDriveTrainName());
		navigation = new Navigation(this, configReader.getNavigationOption("NavxMXP"));
		
		
	}
	
	public void initTeleOp()
	{
		navigation = new Navigation(this, configReader.getNavigationOption("Teleop"));
	}
	
	public void runTeleOp()
	{
		//TODO: Map controller sticks to drive system
		//Possible: map misc. controller buttons to tasks?
	}
	
	public void initAutonomous()
	{
		//Resent encoders
		driveSys.resetEncoders();
		autonTasks = new AutonomousTasks(this, "TaskSet1");		
	}
	
	public void runAutonomous()
	{
	    autonTasks.runNextTask();
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}
