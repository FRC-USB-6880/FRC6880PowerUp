package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.VictorSPDriveSystem;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;
import org.usfirst.frc.team6880.robot.util.LogitechF310;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	RobotConfigReader configReader;
	public LogitechF310 gamepad;
	
	AutonomousTasks autonTasks;
	
	public FRCRobot(Robot wpilibrobot)
	{
		this.wpilibrobot = wpilibrobot;
		
		// Important:  Base directory has to be set before trying to read any JSON file
		JsonReader.setBaseDir(JsonReader.baseDir);
		
		configReader = new RobotConfigReader(JsonReader.robotsFile, "2017-offseason-robot");
		System.out.println("frc6880: Config reader: " + configReader);
		driveSys = new VictorSPDriveSystem(this, configReader.getDriveTrainName());
		gamepad = new LogitechF310(0);
	}
	
	public void initTeleOp()
	{
		navigation = new Navigation(this, configReader.getNavigationOption("teleop_navigation"));
	}
	
	public void runTeleOp()
	{
		//TODO: Map controller sticks to drive system
		//Possible: map misc. controller buttons to tasks?
	    driveSys.arcadeDrive(gamepad.leftStickY(), gamepad.rightStickX());
	}
	
	public void initAutonomous()
	{
		navigation = new Navigation(this, configReader.getNavigationOption("autonomous_navigation"));
		//Reset encoders
		driveSys.resetEncoders();
		autonTasks = new AutonomousTasks(this, "TaskList1");		
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