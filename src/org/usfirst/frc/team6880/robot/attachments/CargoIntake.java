package frc.robot.attachments;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.Robot;

public class CargoIntake 
 
{
    SpeedController controller;
    public CargoIntake (Robot robot) 
    {
        controller = new Spark(6);
    }

    public void in() 
    {
        controller.set(1);
        
    }

    public void out()
    {
        controller.set(-1);
    
    }

    public void idle()
    {
        controller.set(0);
    }

}
