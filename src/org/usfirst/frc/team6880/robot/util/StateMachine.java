package org.usfirst.frc.team6880.robot.util;

import org.usfirst.frc.team6880.robot.FRCRobot;

public class StateMachine {
	enum DriveSysStates {LOWGEAR, HIGEAR}
	enum LiftStates {LOWRANGE, MIDRANGE, HIRANGE, MOVING}
	FRCRobot robot;
	DriveSysStates currentDriveState;
	LiftStates currentLiftState;
	
	public StateMachine(FRCRobot robot)
	{
		this.robot = robot;
		currentDriveState = DriveSysStates.LOWGEAR;
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
			case LOWGEAR:
				if(robot.driveSys.getCurGear().equals("high")) switchDriveState(DriveSysStates.LOWGEAR);
				break;
			case HIGEAR:
				if(robot.driveSys.getCurGear().equals("low")) switchDriveState(DriveSysStates.HIGEAR);
				break;
		}
		switch(currentLiftState)
		{
			case LOWRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(1.0);
				switchDriveState(DriveSysStates.HIGEAR);
				break;
			case MIDRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(0.7);
				switchDriveState(DriveSysStates.LOWGEAR);
				break;
			case HIRANGE:
				if(robot.driveSys.isMoving()) switchLiftState(LiftStates.MOVING);
				robot.driveSys.changeMultiplier(0.3);
				switchDriveState(DriveSysStates.LOWGEAR);
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
				break;
		}
	}
}
