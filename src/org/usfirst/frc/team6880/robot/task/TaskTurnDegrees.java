package org.usfirst.frc.team6880.robot.task;
import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.navigation.Gyro;

public class TaskTurnDegrees implements RobotTask
{
	private double startingYaw;
	private double endYaw;
	private double targetAngle;
	private double power;
	FRCRobot robot;
	
	public TaskTurnDegrees (FRCRobot robot, double power)
	{
		this.robot = robot;
		this.power = power;
		
	}
	@Override
	public void initTask()
	{
		// TODO Auto-generated method stub
		startingYaw = robot.navigation.gyro.getYaw();
	}

	@Override
	public boolean runTask()
	{
		// TODO Auto-generated method stub
		if
		TaskTurnDegrees(robot, power);
	}

}
