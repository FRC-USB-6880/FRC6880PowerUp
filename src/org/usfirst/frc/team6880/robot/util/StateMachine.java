package org.usfirst.frc.team6880.robot.util;
import org.usfirst.frc.team6880.robot.FRCRobot;

public class StateMachine
{
	enum DriveSysStates {IDLE, DRIVING, LOWGEAR, HIGEAR}
	enum LiftStates {LOWRANGE, MEDRANGE, HIRANGE, MOVING}
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
	
	public void switchLiftStates(LiftStates state)
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
			break;
		case LOWGEAR:
			break;
		case HIGEAR:
			break;
		}
		switch(currentLiftState)
		{
		case LOWRANGE:
			break;
		case MEDRANGE:
			break;
		case HIRANGE:
			break;
		case MOVING:
			break;
		}
	}
}
