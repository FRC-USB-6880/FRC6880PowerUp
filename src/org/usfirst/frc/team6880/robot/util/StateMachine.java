package org.usfirst.frc.team6880.robot.util;
import org.usfirst.frc.team6880.robot.FRCRobot;

public class StateMachine
{
	enum DriveSysStates {IDLE, DRIVING, LOWGEAR, HIGEAR}
	enum LiftStates {LOWRANGE, MIDRANGE, HIRANGE, MOVING}
	FRCRobot robot;
	DriveSysStates currentDriveState;
	LiftStates currentLiftState;
	
	public StateMachine(FRCRobot robot)
	{
		this.robot = robot;
		currentDriveState = DriveSysStates.IDLE;
		currentLiftState = LiftStates.LOWRANGE;
	}
	
	public void switchDriveState(DriveSysStates state)
	{
		currentDriveState = state;
	}
	
	public void switchLiftState(LiftStates state)
	{
		currentLiftState = state;
	}
	
	public void loop()
	{
		switch(currentDriveState)
		{
			case IDLE:
				if(robot.driveSys.isMoving()) switchDriveState(DriveSysStates.DRIVING);
				break;
			case DRIVING:
				if(robot.driveSys.getCurrentGear().equals("low")) switchDriveState(DriveSysStates.LOWGEAR);
				else if(robot.driveSys.getCurrentGear().equals("high")) switchDriveState(DriveSysStates.HIGEAR);
				break;
			case LOWGEAR:
				if(robot.driveSys.getCurrentGear().equals("high"))
					switchDriveState(DriveSysStates.HIGEAR);
				robot.driveSys.setLoSpd();
				System.out.println("frc6880: Switched to low gear");
				break;
			case HIGEAR:
				if(robot.driveSys.getCurrentGear().equals("low")) switchDriveState(DriveSysStates.LOWGEAR);
				robot.driveSys.setHiSpd();
				System.out.println("frc6880: Switched to high gear");
				break;
		}
		switch(currentLiftState)
		{
			case LOWRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(1.0);
				switchDriveState(DriveSysStates.HIGEAR);
				System.out.println("frc6880: In low range");
				break;
			case MIDRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(0.7);
				switchDriveState(DriveSysStates.LOWGEAR);
				System.out.println("frc6880: In mid range");
				break;
			case HIRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(0.3);
				switchDriveState(DriveSysStates.LOWGEAR);
				System.out.println("frc6880: In high range");
				break;
			case MOVING:
				if(!robot.lift.isMoving())
				{
					if(robot.lift.getHeight() >= 4)
					{
						switchLiftState(LiftStates.HIRANGE);
					}
					else if(robot.lift.getHeight() >= 2)
					{
						switchLiftState(LiftStates.MIDRANGE);
					}
					else
					{
						switchLiftState(LiftStates.LOWRANGE);
					}
				}
				robot.driveSys.changeMultiplier(0.5);
				switchDriveState(DriveSysStates.LOWGEAR);
				System.out.println("frc6880: Moving lift");
				break;
		}
	}
}