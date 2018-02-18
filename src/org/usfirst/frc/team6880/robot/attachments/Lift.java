/**
 * 
 */
package org.usfirst.frc.team6880.robot.attachments;
import org.usfirst.frc.team6880.robot.FRCRobot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
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
	
	private double spoolDiameter;
	private double spoolCircumference;
	private double distancePerCount;
	private boolean moving;
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
    	moving = false;
//    	liftEncoder.setDistancePerPulse(distancePerCount);
    	liftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1, 10);
    	liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    }
    
    public void stop()
    {
    	liftMotor.set(0);
    }
    
    public void moveWithPower(double power)
    {
    	liftMotor.set(power);
    }
    
    public void moveToHeight(double targetHeight, double power)
    {
    	double amountRaise = targetHeight - height;
    	if(liftMotor.getSelectedSensorPosition(0) != amountRaise)
    	{
    		if(amountRaise < 0) moveWithPower(-power);
    		else moveWithPower(power);
    	}
    	else stop();
    	height += liftMotor.getSelectedSensorPosition(0);
    }
    
    public double getHeight()
    {
    	return height;
    }
    
    public boolean isMoving()
    {
    	return moving;
    }

}
