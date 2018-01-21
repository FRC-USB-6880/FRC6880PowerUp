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
	
	RobotTask curTask;
	RobotTask tasks [] = {new TaskMoveForward(this, 20),
						  new TaskTurnLeft(this, 90),
						  new TaskMoveForward(this, 20),
						  new TaskTurnLeft(this, 90),
						  new TaskMoveForward(this, 20),
						  new TaskTurnLeft(this, 90),
						  new TaskMoveForward(this, 20)};
	int taskNum;
	
	public FRCRobot(Robot wpilibrobot)
	{
		this.wpilibrobot = wpilibrobot;
		configReader = new RobotConfigReader("/robots.json", "2018_Robot"); 
		driveSys = new DriveSystem(this, configReader.getDriveTrainName());
		
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
		navigation = new Navigation(this, configReader.getNavigationOption("Autonomous"));
		//Start with first task
		curTask = tasks[0];
		taskNum = 0;
	}
	
	public void runAutonomous()
	{
		//Run the current task. If current task ended
		if(curTask.runTask())
		{
			//Go to next state
			curTask = tasks[++taskNum];
			curTask.initTask();
		}
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}
