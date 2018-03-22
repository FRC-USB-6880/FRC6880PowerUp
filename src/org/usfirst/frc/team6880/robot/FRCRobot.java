package org.usfirst.frc.team6880.robot;

import org.usfirst.frc.team6880.robot.attachments.CubeHandler;
import org.usfirst.frc.team6880.robot.attachments.Lift;
import org.usfirst.frc.team6880.robot.driveTrain.DriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.TalonSRX2spdDriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.TalonSRXDriveSystem;
import org.usfirst.frc.team6880.robot.driveTrain.VictorSPDriveSystem;
import org.usfirst.frc.team6880.robot.jsonReaders.*;
import org.usfirst.frc.team6880.robot.navigation.Navigation;
import org.usfirst.frc.team6880.robot.task.*;
import org.usfirst.frc.team6880.robot.util.LogitechF310;
import org.usfirst.frc.team6880.robot.util.PneumaticController;
import org.usfirst.frc.team6880.robot.util.PowerMonitor;
import org.usfirst.frc.team6880.robot.util.StateMachine;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	RobotConfigReader configReader;
	private Joystick joystick1;
	private Joystick joystick2 = null;
	private LogitechF310 gamepad;
	public PowerDistributionPanel pdp;
	PowerMonitor powerMon;
	public PneumaticController pcmObj;
	public Lift lift;
	public CubeHandler cubeHandler;
	private double invertMult;
	private StateMachine stateMachine;
	private boolean isMoveHeight;
	
	
	AutonomousTasks autonTasks;
	
	public FRCRobot(Robot wpilibrobot)
	{
	    String driveTrainName;

	    this.wpilibrobot = wpilibrobot;
	    this.pdp = new PowerDistributionPanel();
		
		// Important:  Base directory has to be set before trying to read any JSON file
		JsonReader.setBaseDir(JsonReader.baseDir);
		
		configReader = new RobotConfigReader(JsonReader.robotsFile, "2018-robot");
		System.out.println("frc6880: Config reader: " + configReader);
		
		// TODO Instantiate PneumaticController object before instantiating driveTrain
		//  or any other attachment.
		pcmObj = new PneumaticController(20);

		driveTrainName = configReader.getDriveTrainName();
        System.out.println("frc6880: driveTrainName: " + driveTrainName);
		if (driveTrainName.equals("4VictorSP-1spd-WestCoastDrive"))
		    driveSys = new VictorSPDriveSystem(this, driveTrainName);
		else if (driveTrainName.equals("CAN-4TalonSRX-1spd-WestCoastDrive"))
		    driveSys = new TalonSRXDriveSystem(this, driveTrainName);
		else if(driveTrainName.equals("CAN-4TalonSRX-2spd-WestCoastDrive"))
		{
			driveSys = new TalonSRX2spdDriveSystem(this, driveTrainName);
			driveSys.setHiSpd();
		}

        navigation = new Navigation(this, configReader.getNavigationOption());

        joystick1 = new Joystick(0);
        if(configReader.getDriverStationConfig())
        {
        	joystick2 = new Joystick(1);
        	gamepad = new LogitechF310(2);
        }
        else
        	gamepad = new LogitechF310(1);
        
        powerMon = new PowerMonitor(this);
        
        lift = new Lift(this);
//        cubeHandler = new CubeHandler(this);
        
//        CameraServer.getInstance().startAutomaticCapture();
        
//        stateMachine = new StateMachine(this);
        
        isMoveHeight = false;
	}
	
	public void initTeleOp()
	{
		driveSys.setLoSpd();
	}
	
	public void runTeleOp()
	{
		//TODO: Map controller sticks to drive system
		//Possible: map misc. controller buttons to tasks?
		stateMachine.loop();
		
		
		
	    
		if(joystick1.getThrottle()>0)
		{
		    if(joystick2 == null)
		    {
		    	driveSys.arcadeDrive(-joystick1.getY(), joystick1.getTwist());
		    	
		    	if(joystick1.getRawButton(8))
			    	driveSys.setHiSpd();
			    if(joystick1.getRawButton(10))
			    	driveSys.setLoSpd();
			    
		    	if(joystick1.getRawButton(6))
			    	lift.moveWithPower(0.5);
			    else if(joystick1.getRawButton(4))
			    	lift.moveWithPower(-0.3);
			    else
			    	lift.stop();
			    
			    if(joystick1.getTrigger())
			    	cubeHandler.grabCube();
			    else if(joystick1.getRawButton(2))
			    	cubeHandler.releaseCube();
			    
			    if(joystick1.getRawButton(7))
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() + Lift.RANGE_VALUE);
		    	}
		    	else if(joystick1.getRawButton(9))
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() - Lift.RANGE_VALUE);
		    	}
		    	
		    	if(isMoveHeight)
		    	{
		    		isMoveHeight = !(lift.moveToHeight(0.5));
		    	}
		    }
		    else if(joystick2 != null)
		    {
		    	driveSys.tankDrive(-joystick1.getY(), -joystick2.getY());
		    	
		    	if(joystick1.getRawButton(2))
		    		cubeHandler.grabCube();
		    	else if(joystick2.getRawButton(2))
		    		cubeHandler.releaseCube();
		    	
		    	if(joystick1.getRawButton(5))
		    		driveSys.setHiSpd();
		    	else if(joystick1.getRawButton(3))
		    		driveSys.setLoSpd();
		    	
		    	if(joystick1.getTrigger())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() + Lift.RANGE_VALUE);
		    	}
		    	else if(joystick2.getTrigger())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() - Lift.RANGE_VALUE);
		    	}
		    	
		    	if(isMoveHeight)
		    	{
		    		isMoveHeight = !(lift.moveToHeight(0.5));
		    	}
		    }
		}
		else
		{
			if(joystick2 == null)
		    {
		    	driveSys.arcadeDrive(-joystick1.getY(), joystick1.getTwist());
		    	
		    	if(joystick1.getRawButton(8))
			    	driveSys.setHiSpd();
			    if(joystick1.getRawButton(10))
			    	driveSys.setLoSpd();
			    
		    	lift.moveWithPower(-gamepad.rightStickY());
			    
			    if(gamepad.dpadDown())
			    	cubeHandler.grabCube();
			    else if(gamepad.dpadUp())
			    	cubeHandler.releaseCube();
			    
			    if(gamepad.rightBumper())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() + Lift.RANGE_VALUE);
		    	}
		    	else if(gamepad.leftBumper())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() - Lift.RANGE_VALUE);
		    	}
		    	
		    	if(isMoveHeight)
		    	{
		    		isMoveHeight = !(lift.moveToHeight(0.5));
		    	}
		    }
		    else if(joystick2 != null)
		    {
		    	driveSys.tankDrive(-joystick1.getY(), -joystick2.getY());
		    	
		    	if(gamepad.dpadDown())
			    	cubeHandler.grabCube();
			    else if(gamepad.dpadUp())
			    	cubeHandler.releaseCube();
		    	
		    	if(joystick1.getTrigger())
		    		driveSys.setHiSpd();
		    	else if(joystick2.getTrigger())
		    		driveSys.setLoSpd();
		    	
		    	lift.moveWithPower(-gamepad.rightStickY());
		    	
		    	if(gamepad.rightBumper())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() + Lift.RANGE_VALUE);
		    	}
		    	else if(gamepad.leftBumper())
		    	{
		    		isMoveHeight = true;
		    		lift.setTargetHeight(lift.getCurPos() - Lift.RANGE_VALUE);
		    	}
		    	
		    	if(isMoveHeight)
		    	{
		    		isMoveHeight = !(lift.moveToHeight(0.5));
		    	}
		    }
		}
	}
	
	public void initAutonomous()
	{
		//Reset encoders
		driveSys.resetEncoders();
		driveSys.setHiSpd();
		autonTasks = new AutonomousTasks(this, configReader.getAutoOption());	
		autonTasks.initFirstTask();
	}
	
	public void runAutonomous()
	{
	    autonTasks.runNextTask();
//	    powerMon.displayCurrentPower();
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}