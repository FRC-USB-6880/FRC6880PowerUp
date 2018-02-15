/**
 * 
 */
package org.usfirst.frc.team6880.robot.driveTrain;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.jsonReaders.DriveTrainReader;
import org.usfirst.frc.team6880.robot.jsonReaders.JsonReader;
import org.usfirst.frc.team6880.robot.jsonReaders.WheelSpecsReader;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * TODO
 *
 */
public class TalonSRX2spdDriveSystem implements DriveSystem {
    FRCRobot robot;
    WPI_TalonSRX motorL1;
    WPI_TalonSRX motorL2;
    SpeedControllerGroup motorLeft;
    WPI_TalonSRX motorR1;
    WPI_TalonSRX motorR2;
    SpeedControllerGroup motorRight;
    DifferentialDrive drive;
    Encoder leftEnc;
    Encoder rightEnc;
    DriveTrainReader configReader;
    WheelSpecsReader wheelSpecsReader;
    DoubleSolenoid gearShifter;
    
    private double wheelDiameter;
    private double wheelCircumference;
    private double distancePerCountLoSpd, distancePerCountHiSpd;

    /**
     * 
     */
    public TalonSRX2spdDriveSystem(FRCRobot robot, String driveSysName) {
        // TODO Auto-generated constructor stub
        this.robot = robot;
        configReader = new DriveTrainReader(JsonReader.driveTrainsFile, driveSysName);
        String wheelType = configReader.getWheelType();
        wheelSpecsReader = new WheelSpecsReader(JsonReader.wheelSpecsFile, wheelType);
        
        wheelDiameter = wheelSpecsReader.getDiameter();
        wheelCircumference = Math.PI * wheelDiameter;
        
        // TODO  Use configReader.getChannelNum() method to identify the
        //   channel numbers where each motor controller is plugged in
        motorL1 = new WPI_TalonSRX(configReader.getDeviceID("Motor_L1"));
        motorL2 = new WPI_TalonSRX(configReader.getDeviceID("Motor_L2"));
        motorL1.setInverted(true);
        motorL2.setInverted(true);
        if (configReader.isFollower("Motor_L1"))
        {
            motorL1.follow(motorL2);
            motorLeft = new SpeedControllerGroup(motorL2);
            System.out.println("Leader: Motor_L2, Follower: Motor_L1");
        }
        else if (configReader.isFollower("Motor_L2")) 
        {
            motorL2.follow(motorL1);
            motorLeft = new SpeedControllerGroup(motorL1);
            System.out.println("Leader: Motor_L1, Follower: Motor_L2");
        }
        else
            motorLeft = new SpeedControllerGroup(motorL1, motorL2);

        motorR1 = new WPI_TalonSRX(configReader.getDeviceID("Motor_R1"));
        motorR2 = new WPI_TalonSRX(configReader.getDeviceID("Motor_R2"));
        motorR1.setInverted(true);
        motorR2.setInverted(true);

        if (configReader.isFollower("Motor_R1"))
        {
            motorR1.follow(motorR2);
            motorRight = new SpeedControllerGroup(motorR2);
            System.out.println("Leader: Motor_R2, Follower: Motor_R1");
        }
        else if (configReader.isFollower("Motor_R2")) 
        {
            motorR2.follow(motorR1);
            motorRight = new SpeedControllerGroup(motorR1);
            System.out.println("Leader: Motor_R1, Follower: Motor_R2");
        }
        else
            motorRight = new SpeedControllerGroup(motorR1, motorR2);

        drive = new DifferentialDrive(motorLeft, motorRight);

        int[] encoderChannelsLeft = configReader.getEncoderChannels("LeftEncoder");
        System.out.format("frc6880: Left encoder channels = [%d, %d]\n", encoderChannelsLeft[0], encoderChannelsLeft[1]);
        leftEnc = new Encoder(encoderChannelsLeft[0], encoderChannelsLeft[1], false, Encoder.EncodingType.k4X);
        String encoderType;
        double gearRatioLoSpd, gearRatioHiSpd;
        encoderType = configReader.getEncoderType("LeftEncoder");
        if (encoderType.equals("GreyHill-63R128")) {
            gearRatioLoSpd = configReader.getGearRatio("Left_LoSpeed");
            gearRatioHiSpd = configReader.getGearRatio("Left_HiSpeed");
            System.out.println("frc6880: Left gear ratio for Low speed = " + gearRatioLoSpd + 
                    ", high speed = " + gearRatioHiSpd);
            // We will assume that the same encoder is used on both left and right sides of the drive train. 
            distancePerCountLoSpd = (wheelCircumference / configReader.getEncoderValue("LeftEncoder", "CPR")) / gearRatioLoSpd;
            distancePerCountHiSpd = (wheelCircumference / configReader.getEncoderValue("LeftEncoder", "CPR")) / gearRatioHiSpd;
            System.out.format("frc6880: distancePerCountLoSpd = %f, distancePerCountHiSpd = %f\n", 
                    distancePerCountLoSpd, distancePerCountHiSpd);

//            leftEnc.setDistancePerPulse(distancePerCount / gearRatio);
        } else {
            double distancePerCount = wheelCircumference / configReader.getEncoderValue("LeftEncoder", "CPR");
            leftEnc.setDistancePerPulse(distancePerCount);            
        }
        int[] encoderChannelsRight = configReader.getEncoderChannels("RightEncoder");
        System.out.format("frc6880: Right encoder channels = [%d, %d]\n", encoderChannelsRight[0], encoderChannelsRight[1]);
        rightEnc = new Encoder(encoderChannelsRight[0], encoderChannelsRight[1], true, Encoder.EncodingType.k4X);
//        rightEnc = new Encoder(2, 3, true, Encoder.EncodingType.k4X);
        encoderType = configReader.getEncoderType("RightEncoder");
        if (encoderType.equals("GreyHill-63R128")) {
            gearRatioLoSpd = configReader.getGearRatio("Right_LoSpeed");
            gearRatioHiSpd = configReader.getGearRatio("Right_HiSpeed");
            System.out.println("frc6880: Right gear ratio for Low speed = " + gearRatioLoSpd + 
                    ", high speed = " + gearRatioHiSpd);
            // We will assume that the same encoder is used on both left and right sides of the drive train. 
            distancePerCountLoSpd = (wheelCircumference / configReader.getEncoderValue("RightEncoder", "CPR")) / gearRatioLoSpd;
            distancePerCountHiSpd = (wheelCircumference / configReader.getEncoderValue("RightEncoder", "CPR")) / gearRatioHiSpd;
            System.out.format("frc6880: distancePerCountLoSpd = %f, distancePerCountHiSpd = %f\n", 
                    distancePerCountLoSpd, distancePerCountHiSpd);
//            rightEnc.setDistancePerPulse(distancePerCount / gearRatio);
        } else {
            double distancePerCount = wheelCircumference / configReader.getEncoderValue("RightEncoder", "CPR");
            rightEnc.setDistancePerPulse(distancePerCount);
        }
        
        // Initialize the Double Solenoid 
        gearShifter = robot.pcmObj.initializeDoubleSolenoid(4, 5);
        gearShifter.set(Value.kOff);

    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed, rightSpeed);        
    }

    @Override
    public void arcadeDrive(double speed, double rotationRate) {
        drive.arcadeDrive(speed, rotationRate);        
    }

    @Override
    public void resetEncoders() {
        leftEnc.reset();
        rightEnc.reset();        
    }

    @Override
    public double getEncoderDist() {
        return (leftEnc.getDistance() + rightEnc.getDistance()) / 2.0;
    }
    
    public void setLoSpd() {
        gearShifter.set(Value.kForward);
        leftEnc.setDistancePerPulse(distancePerCountLoSpd);
    }

    public void setHiSpd() {
        gearShifter.set(Value.kReverse);
        leftEnc.setDistancePerPulse(distancePerCountHiSpd);
    }
}
