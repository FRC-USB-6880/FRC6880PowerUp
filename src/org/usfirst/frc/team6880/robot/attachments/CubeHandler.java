/**
 * 
 */
package org.usfirst.frc.team6880.robot.attachments;
import org.usfirst.frc.team6880.robot.FRCRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * TODO
 * add methods grabCube(), releaseCube(), ....
 *
 */
public class CubeHandler
{
	DoubleSolenoid cubeHandler;
	FRCRobot robot;
    /**
     * 
     */
    public CubeHandler(FRCRobot robot)
    {
        // TODO Auto-generated constructor stub
    	this.robot = robot;
    	cubeHandler = robot.pcmObj.initializeDoubleSolenoid(0, 1);
        cubeHandler.set(Value.kOff);
    }
    
    public void grabCube()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kForward);
    }
    public void releaseCube()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void idle()
    {
    	cubeHandler.set(DoubleSolenoid.Value.kOff);
    }
}
