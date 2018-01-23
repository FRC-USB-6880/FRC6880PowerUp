package org.usfirst.frc.team6880.robot.driveTrain;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.*;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveSystem {
	FRCRobot robot;
	VictorSP motorL1;
	VictorSP motorL2;
	SpeedControllerGroup motorLeft;
	VictorSP motorR1;
	VictorSP motorR2;
	SpeedControllerGroup motorRight;
	DifferentialDrive drive;
	Encoder leftEnc;
	Encoder rightEnc;
	DriveTrainReader configReader;
	WheelSpecsReader wheelSpecsReader;
	
	private static double WHEEL_DIAMETER;
	private static double WHEEL_CIRCUMFERENCE;
	private static double DISTANCE_PER_PULSE;
	
	public DriveSystem(FRCRobot robot, String driveSysName)
	{
		this.robot = robot;
        configReader = new DriveTrainReader(JsonReader.driveTrainsFile, driveSysName);
        String wheelType = configReader.getWheelType();
        wheelSpecsReader = new WheelSpecsReader(JsonReader.wheelSpecsFile, wheelType);
		
		WHEEL_DIAMETER = wheelSpecsReader.getDiameter();
		WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
		DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE / configReader.getEncoderValue("LeftEncoder", "PPR");
		
		motorL1 = new VictorSP(0);
		motorL2 = new VictorSP(1);
		motorLeft = new SpeedControllerGroup(motorL1, motorL2);
		motorR1 = new VictorSP(2);
		motorR2 = new VictorSP(3);
		motorRight = new SpeedControllerGroup(motorR1, motorR2);
		drive = new DifferentialDrive(motorLeft, motorRight);
		leftEnc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		leftEnc.setDistancePerPulse(DISTANCE_PER_PULSE);
		rightEnc = new Encoder(2, 3, true, Encoder.EncodingType.k4X);
		rightEnc.setDistancePerPulse(DISTANCE_PER_PULSE);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public void arcadeDrive(double speed, double rotationRate)
	{
		drive.arcadeDrive(speed, rotationRate);
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getEncoderDist()
	{
		return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
	}
	//TODO: Basically everything to do with moving
}
