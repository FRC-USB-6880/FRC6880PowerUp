package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.VictorSPDriveSystem;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	
	RobotTask curTask;
	RobotTask tasks [] = {new TaskMoveForward(this, 12),
						  new TaskSpinAngle(this, -90)};
	int taskNum;
	boolean tasksDone;
	
	public FRCRobot(Robot wpilibrobot)
	{
		this.wpilibrobot = wpilibrobot;
		this.driveSys = new VictorSPDriveSystem(this);
		navigation = new Navigation(this);
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
		//Start with first task
		curTask = tasks[0];
		curTask.initTask();
		taskNum = 0;
		tasksDone = false;
	}
	
	public void runAutonomous()
	{
		//If we still need to run tasks
		if (!tasksDone) {
			//Run the current task. If current task ended
			if (curTask.runTask())
			{
				//Check if there are still tasks to run
				if (taskNum + 1 < tasks.length)
				{
					System.out.println("Finished running task number " + taskNum);
					//Go to next task
					curTask = tasks[++taskNum];
					//Begin the next task
					curTask.initTask();
				}
				//Else we're done running tasks
				else
				{
					//Keep track that we're done running tasks
					tasksDone = true;
					System.out.println("Robot has finished running the autonomous tasks");
				}
			}
		}
		//We're done running tasks, stop the robot
		else
		{
			driveSys.tankDrive(0, 0);
		}
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}