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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FRCRobot {
	Robot wpilibrobot;
	public DriveSystem driveSys;
	public Navigation navigation;
	RobotConfigReader configReader;
	private Joystick joystick1;
	private Joystick joystick2 = null;
	private LogitechF310 gamepad2;
	private LogitechF310 gamepad1;
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
		SmartDashboard.putString("Robot", "2018-robot");
		System.out.println("frc6880: Config reader: " + configReader);
		
		
		// TODO Instantiate PneumaticController object before instantiating driveTrain
		//  or any other attachment.
		pcmObj = new PneumaticController(20);
		
		driveTrainName = configReader.getDriveTrainName();
        System.out.println("frc6880: driveTrainName: " + driveTrainName);
        SmartDashboard.putString("Drive train name", driveTrainName);
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
        
//        joystick1 = new Joystick(0);
        gamepad1 = new LogitechF310(0);
        if(configReader.getDriverStationConfig())
        {
        	joystick2 = new Joystick(1);
        	gamepad2 = new LogitechF310(2);
        }
        else
        	gamepad2 = new LogitechF310(1);
        
        powerMon = new PowerMonitor(this);
        
        lift = new Lift(this);
        cubeHandler = new CubeHandler(this);
        
        CameraServer.getInstance().startAutomaticCapture();
        
        stateMachine = new StateMachine(this);
        
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
		driveSys.arcadeDrive(-gamepad1.leftStickY(), gamepad1.rightStickX());
		if(gamepad1.rightBumper())
			driveSys.setHiSpd();
		else if (gamepad1.leftBumper())
			driveSys.setLoSpd();
		if(gamepad2.dpadDown())
	    	cubeHandler.grabCube();
	    else if(gamepad2.dpadUp())
	    	cubeHandler.releaseCube();
		
		lift.moveWithPower(-gamepad2.rightStickY());
	    
//	    if(gamepad2.rightBumper())
//    	{
//    		isMoveHeight = true;
//    		lift.setTargetHeight(lift.getCurPos() + lift.rangeValue);
//    	}
//    	else if(gamepad2.leftBumper())
//    	{
//    		isMoveHeight = true;
//    		lift.setTargetHeight(lift.getCurPos() - lift.rangeValue);
//    	}
//    	
//    	if(isMoveHeight&&gamepad2.rightStickY()==0)
//    	{
//    		isMoveHeight = !(lift.moveToHeight(0.5));
//    	}
//    	else
//    		lift.moveWithPower(-gamepad2.rightStickY());
//		System.out.println("frc6880: curpos: "+lift.getCurPos());
	    
//		if(joystick1.getThrottle()>0)
//		{
//		    if(joystick2 == null)
//		    {
//		    	driveSys.arcadeDrive(-joystick1.getY(), joystick1.getTwist());
//		    	
//		    	if(joystick1.getRawButton(8))
//			    	driveSys.setHiSpd();
//			    if(joystick1.getRawButton(10))
//			    	driveSys.setLoSpd();
//			    
//		    	if(joystick1.getRawButton(6))
//			    	lift.moveWithPower(0.5);
//			    else if(joystick1.getRawButton(4))
//			    	lift.moveWithPower(-0.3);
//			    else
//			    	lift.stop();
//			    
//			    if(joystick1.getTrigger())
//			    	cubeHandler.grabCube();
//			    else if(joystick1.getRawButton(2))
//			    	cubeHandler.releaseCube();
//			    
//			    if(joystick1.getRawButton(7))
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() + lift.rangeValue);
//		    	}
//		    	else if(joystick1.getRawButton(9))
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() - lift.rangeValue);
//		    	}
//		    	
//		    	if(isMoveHeight)
//		    	{
//		    		isMoveHeight = !(lift.moveToHeight(0.5));
//		    	}
//		    }
//		    else if(joystick2 != null)
//		    {
//		    	driveSys.tankDrive(-joystick1.getY(), -joystick2.getY());
//		    	
//		    	if(joystick1.getRawButton(2))
//		    		cubeHandler.grabCube();
//		    	else if(joystick2.getRawButton(2))
//		    		cubeHandler.releaseCube();
//		    	
//		    	if(joystick1.getRawButton(5))
//		    		driveSys.setHiSpd();
//		    	else if(joystick1.getRawButton(3))
//		    		driveSys.setLoSpd();
//		    	
//		    	if(joystick1.getTrigger())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() + lift.rangeValue);
//		    	}
//		    	else if(joystick2.getTrigger())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() - lift.rangeValue);
//		    	}
//		    	
//		    	if(isMoveHeight)
//		    	{
//		    		isMoveHeight = !(lift.moveToHeight(0.5));
//		    	}
//		    }
//		}
//		else
//		{
//			if(joystick2 == null)
//		    {
//		    	driveSys.arcadeDrive(-joystick1.getY(), joystick1.getTwist());
//		    	
//		    	if(joystick1.getRawButton(8))
//			    	driveSys.setHiSpd();
//			    if(joystick1.getRawButton(10))
//			    	driveSys.setLoSpd();
//			    
//		    	
//			    
//			    if(gamepad2.dpadDown())
//			    	cubeHandler.grabCube();
//			    else if(gamepad2.dpadUp())
//			    	cubeHandler.releaseCube();
//			    
//			    if(gamepad2.rightBumper())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() + lift.rangeValue);
//		    	}
//		    	else if(gamepad2.leftBumper())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() - lift.rangeValue);
//		    	}
//		    	
//		    	if(isMoveHeight&&gamepad2.rightStickY()==0)
//		    	{
//		    		isMoveHeight = !(lift.moveToHeight(0.5));
//		    	}
//		    	else
//		    		lift.moveWithPower(-gamepad2.rightStickY());
//		    }
//		    else if(joystick2 != null)
//		    {
//		    	driveSys.tankDrive(-joystick1.getY(), -joystick2.getY());
//		    	
//		    	if(gamepad2.dpadDown())
//			    	cubeHandler.grabCube();
//			    else if(gamepad2.dpadUp())
//			    	cubeHandler.releaseCube();
//		    	
//		    	if(joystick1.getTrigger())
//		    		driveSys.setHiSpd();
//		    	else if(joystick2.getTrigger())
//		    		driveSys.setLoSpd();
//		    	
//		    	lift.moveWithPower(-gamepad2.rightStickY());
//		    	
//		    	if(gamepad2.rightBumper())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() + lift.rangeValue);
//		    	}
//		    	else if(gamepad2.leftBumper())
//		    	{
//		    		isMoveHeight = true;
//		    		lift.setTargetHeight(lift.getCurPos() - lift.rangeValue);
//		    	}
//		    	
//		    	if(isMoveHeight&&gamepad2.rightStickY()==0)
//		    	{
//		    		isMoveHeight = !(lift.moveToHeight(0.5));
//		    	}
//		    	else
//		    		lift.moveWithPower(-gamepad2.rightStickY());
//		    }
//		}
	}
	
	public void initAutonomous()
	{
		//Reset encoders
		driveSys.resetEncoders();
		driveSys.setHiSpd();
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("Game Data", gameData);
		String autoPos = configReader.getAutoPosition();
        SmartDashboard.putString("Auto Position", autoPos);
		String autoOption = configReader.getAutoOption();
        SmartDashboard.putString("Auto Option", autoOption);
        String autoTask = "crossTheLine";
//		String autoPos = SmartDashboard.getString("AutoPos", "none");
//		String autoOption = SmartDashboard.getString("AutoOption", "none");
//		boolean scale = SmartDashboard.getBoolean("Scale", false);
//        System.out.println("frc6880: autopos dashboard: "+SmartDashboard.getString("AutoPos", "none"));
//		autoPos = SmartDashboard.getString("AutoPos", autoPos);
//		autoOption = SmartDashboard.getString("AutoOption", autoOption);
		if (autoPos.equals("pos1")) 
		{
		    if (autoOption.equals("scale")) {
		        // Claim the scale
    		    if(gameData.charAt(1) == 'L') {
    		        autoTask = "scaleL";
    		    } else if (gameData.charAt(1) == 'R' && gameData.charAt(0)=='L') {
    		        autoTask = "switchL";
    		    } else {
    		        // Something bad happened; just move forward for autoquest
                    autoTask = "crossTheLine";
    		    }
		    } else if (autoOption.equals("switch")) {
		        // Claim the switch or do nothing (just move forward)
                if(gameData.charAt(0) == 'L') {
                    autoTask = "switchL";
                } else if (gameData.charAt(0) == 'R') {
                    autoTask = "crossTheLine";
                } else {
                    // Something bad happened; just move forward for autoquest
                    autoTask = "crossTheLine";
                }
		    }
		} 
		else if (autoPos.equals("pos2")) 
		{
		    if (autoOption.equals("scale")) {
		        // Invalid option
		    } else if (autoOption.equals("switch")) {
                // Claim the switch or do nothing (just move forward)
                if(gameData.charAt(0) == 'L') {
                    autoTask = "switchL";
                } else if (gameData.charAt(0) == 'R') {
                    autoTask = "switchR";
                } else {
                    // Something bad happened; just move forward for autoquest
                    autoTask = "crossTheLine";
                }
		    }
		} 
		else if (autoPos.equals("pos3"))
		{
            if (autoOption.equals("scale")) {
                // Claim the scale
                if(gameData.charAt(1) == 'L'&&gameData.charAt(0)=='R') {
                    autoTask = "switchR";
                } else if (gameData.charAt(1) == 'R'&&gameData.charAt(0)=='R') {
                    autoTask = "scaleR";
                } else if (gameData.charAt(1)=='R'&&gameData.charAt(0)=='L'){
                	autoTask = "scaleR";
                } else {
                    // Something bad happened; just move forward for autoquest
                    autoTask = "crossTheLine";
                }
            } else if (autoOption.equals("switch")) {
                // Claim the switch or do nothing (just move forward)
                if(gameData.charAt(0) == 'L') {
                    autoTask = "crossTheLine";
                } else if (gameData.charAt(0) == 'R') {
                    autoTask = "switchR";
                } else {
                    // Something bad happened; just move forward for autoquest
                    autoTask = "crossTheLine";
                }
            }
		}

		autonTasks = new AutonomousTasks(this, autoPos, autoTask);
		SmartDashboard.putString("Selected Auto", autoTask);
		autonTasks.initFirstTask();
	}
	
	public void runAutonomous()
	{
	    autonTasks.runNextTask();
//	    powerMon.displayCurrentPower();
		return;
	}
		
	public boolean isEnabled()
	{
		return wpilibrobot.isEnabled();
	}
}