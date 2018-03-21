package org.usfirst.frc.team6880.robot.util;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;

public class StateMachine {
	enum DriveSysStates {LOWGEAR, HIGEAR}
	enum LiftStates {LOWRANGE, MIDRANGE, HIRANGE}
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
				if(robot.driveSys.getCurGear()==DriveSystem.Gears.HIGH) switchDriveState(DriveSysStates.LOWGEAR);
				robot.driveSys.setLoSpd();
				break;
			case HIGEAR:
				if(robot.driveSys.getCurGear()==DriveSystem.Gears.LOW) switchDriveState(DriveSysStates.HIGEAR);
				robot.driveSys.setHiSpd();
				break;
		}
		switch(currentLiftState)
		{
			case LOWRANGE:
				if(robot.lift.getCurPos() >= 14466)
					switchLiftState(LiftStates.HIRANGE);
				else if(robot.lift.getCurPos() >= 7233)
					switchLiftState(LiftStates.MIDRANGE);
				else
				{
					robot.driveSys.changeMultiplier(1.0);
					switchDriveState(DriveSysStates.HIGEAR);
				}
				break;
			case MIDRANGE:
				if(robot.lift.getCurPos() >= 14466)
					switchLiftState(LiftStates.HIRANGE);
				else if(robot.lift.getCurPos() < 7233)
					switchLiftState(LiftStates.LOWRANGE);
				else
				{
					robot.driveSys.changeMultiplier(1.0);
					switchDriveState(DriveSysStates.LOWGEAR);
				}
				break;
			case HIRANGE:
				if(robot.lift.getCurPos() < 7233)
					switchLiftState(LiftStates.LOWRANGE);
				else if(robot.lift.getCurPos() < 14466)
					switchLiftState(LiftStates.MIDRANGE);
				else
				{
					robot.driveSys.changeMultiplier(0.3);
					switchDriveState(DriveSysStates.LOWGEAR);
				}
				break;
		}
	}
}
