/**
 * 
 */
package org.usfirst.frc.team6880.robot.attachments;
import org.usfirst.frc.team6880.robot.FRCRobot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * TODO
 *
 */
//motor, FRC robot, methods for raising and lowering lift, 
public class Lift
{
	FRCRobot robot;
	WPI_TalonSRX liftMotor;
	private double height;
	public Encoder liftEncoder;
	public static final long MAX_LOW = 7233;
	public static final long MAX_MID = 14466;
	public static final long MAX_HIGH = 21700;
	public static final long RANGE_VALUE = 7233;
	
	private double spoolDiameter;
	private double spoolCircumference;
	private double distancePerCount;
	private double curPower;
	private long targetPos;
    /**
     * 
     */
    public Lift(FRCRobot robot)
    {
        // TODO Auto-generated constructor stub
    	this.robot = robot;
    	liftMotor = new WPI_TalonSRX(15);
    	height = 0;
//    	liftEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
    	spoolDiameter = 2;
    	spoolCircumference = Math.PI * spoolDiameter;
    	distancePerCount = spoolCircumference / 360;
    	curPower = 0.0;
//    	liftEncoder.setDistancePerPulse(distancePerCount);
    	/*
    	 * The status frames with their default period include:
    	 * General Status 1 (10ms)
    	 * Feedback0 Status 2 (20ms)
    	 * Quadrature Encoder Status 3 (160ms)
    	 * Analog Input / Temperature / Battery Voltage Status 4 (160ms)
    	 * Pulse Width Status 8 (160ms)
    	 * Targets Status 10 (160ms)
    	 * PIDF0 Status 14 (160ms)
    	 */
//    	liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
    	liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
    	
    	targetPos = 0;
    }
    
    public void stop()
    {
    	liftMotor.set(0);
    }
    
    public boolean checkUpperLimit()
    {
    	if(getCurPos()>=MAX_HIGH)
    		return true;
    	return false;
    }
    
    public boolean checkLowerLimit()
    {
    	if(getCurPos()<=0)
    		return true;
    	return false;
    }
    
    public long getCurPos()
    {
    	return -liftMotor.getSelectedSensorPosition(0);
    }
    
    public void moveWithPower(double power)
    {
    	if(power<0)
    		liftMotor.set(checkLowerLimit() ? 0.0 : power);
    	else if(power>0)
    		liftMotor.set(checkUpperLimit() ? 0.0 : power);
    	curPower = power;
    }
    public void displayCurrentPosition()
    {
        double value = liftMotor.getSelectedSensorPosition(0);
        System.out.format("frc6880: Current lift encoder value = %f\n", value);
//        SmartDashboard.putNumber("LiftPos", value);
    }
    
    public boolean moveToHeight(double power)
    {
		if(targetPos < getCurPos()) 
		{
			moveWithPower(-power);
			return false;
		}
		else if(targetPos > getCurPos())
		{
			moveWithPower(power);
			return false;
		}
		
		stop();
		return true;
    }
    
    public void setTargetHeight(long target)
    {
    	if(targetPos>MAX_HIGH)
    		targetPos = MAX_HIGH;
    	else if (targetPos<0)
    		targetPos = 0;
    	else
    		targetPos = target;
    }

	public boolean isMoving() {
		// TODO Auto-generated method stub
		if(curPower != 0.0)
			return true;
		return false;
	}

}
