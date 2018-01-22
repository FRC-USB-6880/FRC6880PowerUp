package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	
	AutonomousTasks autonTasks;
	
	public FRCRobot(Robot wpilibrobot)
	{
		this.wpilibrobot = wpilibrobot;
		this.driveSys = new DriveSystem(this);
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
