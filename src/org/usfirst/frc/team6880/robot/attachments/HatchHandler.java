package frc.robot.attachments;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

  
public class HatchHandler<FRCRobot> 
{       
        DoubleSolenoid hatchHandler;
        FRCRobot robot;
        private boolean open;
    
        public HatchHandler (FRCRobot robot) 
    {
        hatchHandler = robot.pcmObj.initializeDoubleSolenoid(2, 3);
        hatchHandler.set(Value.kOff);
    }

    public void grabHatch()
    {
        hatchHandler.set(DoubleSolenoid.Value.kForward);
        open = false;
    }

    public void releaseHatch()
    {
        hatchHandler.set(DoubleSolenoid.Value.kReverse);
        open = true;
    }
    
    public void idle()
    {
        hatchHandler.set(DoubleSolenoid.Value.kOff);
    }  

    public boolean isOpen()
    {
        return open;
    }
}
