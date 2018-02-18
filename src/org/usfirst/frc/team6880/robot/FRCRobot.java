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
	public Joystick joystick;
	public PowerDistributionPanel pdp;
	PowerMonitor powerMon;
	public PneumaticController pcmObj;
	public Lift lift;
	public CubeHandler cubeHandler;
	private double invertMult;
	StateMachine machine;
	
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

        joystick = new Joystick(0);
        
        powerMon = new PowerMonitor(this);
        
        lift = new Lift(this);
        cubeHandler = new CubeHandler(this);
        machine = new StateMachine(this);
        CameraServer.getInstance().startAutomaticCapture();
        
	}
	
	public void initTeleOp()
	{
		driveSys.setLoSpd();
	}
	
	public void runTeleOp()
	{
		//TODO: Map controller sticks to drive system
		//Possible: map misc. controller buttons to tasks?
		machine.loop();
		invertMult = -joystick.getThrottle();
	    driveSys.arcadeDrive(-invertMult*joystick.getY(), joystick.getTwist());
	    
	    if(joystick.getRawButton(6))
	    {
	    	lift.moveWithPower(0.5);
	    }
	    else if(joystick.getRawButton(4))
	    	lift.moveWithPower(-0.3);
	    else
	    	lift.stop();
	    
	    if(joystick.getTrigger())
	    	cubeHandler.grabCube();
	    else if(joystick.getRawButton(2))
	    {
	    	cubeHandler.releaseCube();
	    }
	    
	    if(joystick.getRawButton(5))
	    	driveSys.setHiSpd();
	    if(joystick.getRawButton(3))
	    	driveSys.setLoSpd();
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