package org.usfirst.frc.team6880.robot.driveTrain;

import org.usfirst.frc.team6880.robot.FRCRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TalonSRXDriveSystem implements DriveSystem {
	FRCRobot robot;
	TalonSRX motorL1;
	TalonSRX motorL2;
	SpeedControllerGroup motorLeft;
	TalonSRX motorR1;
	TalonSRX motorR2;
	SpeedControllerGroup motorRight;
	DifferentialDrive drive;
	Encoder leftEnc;
	Encoder rightEnc;
	
	/**Wheel diameter in meters*/
	private static final double WHEEL_DIAMETER = 6.0;
	/**Wheel circumference in meters*/
	private static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
	private static final double COUNTS_PER_ROTATION = 360.0;
	private static final double DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE / COUNTS_PER_ROTATION;
	
	public TalonSRXDriveSystem(FRCRobot robot)
	{
		this.robot = robot;
		motorL1 = new TalonSRX(0);
		motorL2 = new TalonSRX(1);
		motorLeft = new SpeedControllerGroup(motorL1, motorL2);
		motorR1 = new TalonSRX(2);
		motorR2 = new TalonSRX(3);
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
	
	public void arcadeDrive(double speed, double rotation)
	{
		drive.arcadeDrive(speed, rotation);
	}
	
	public void resetEncoders()
	{
		leftEnc.reset();
		rightEnc.reset();
	}
	
	public double getDist()
	{
		double curDist = (Math.abs(leftEnc.getDistance()) + Math.abs(rightEnc.getDistance())) / 2.0;
		System.out.println("frc6880: Cur Dist: " + curDist);
		return curDist;
	}
	//TODO: Basically everything to do with moving
}
