package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class DriveTrainReader extends JsonReader {
	JSONObject driveTrainObj;
    String driveTrainName;

    public DriveTrainReader(String filePath, String driveTrainName)
    {
        super(filePath);
        try {
            driveTrainName = JsonReader.getKeyIgnoreCase(rootObj, driveTrainName);
            driveTrainObj = (JSONObject) rootObj.get(driveTrainName);
            this.driveTrainName = driveTrainName;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMotorType(String motorName) {
        String motorType = null;
        JSONObject obj;

        try {
            String key = JsonReader.getKeyIgnoreCase(driveTrainObj, "Motors");
            obj = (JSONObject) driveTrainObj.get(key);
            motorType = getString(obj, motorName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (motorType);
    }
    
    public int getChannelNum(String motorLocation) {
        int channelNum=-1;
        try {
            String key = JsonReader.getKeyIgnoreCase(driveTrainObj, motorLocation);
            JSONObject obj = (JSONObject) driveTrainObj.get(key);
            key = JsonReader.getKeyIgnoreCase(obj, "Channel");
            channelNum = this.getInt(obj, key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (channelNum);
    }

    public String getWheelType(){
        String wheelType = null;
        try {
            wheelType = getString(driveTrainObj, "WheelType");
        } catch (Exception e){
            e.printStackTrace();
        }

        return wheelType;
    }

    public String getDriveSysName() {
        return driveTrainName;
    }

    public int getMaxMotorSpeed(String autoOrTeleop) {
        int maxMotorSpeed = 0;
        try {
            maxMotorSpeed = getInt(driveTrainObj, autoOrTeleop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxMotorSpeed);
    }
    
    // encoderKey can be "PPR" (Pulses Per Rotation) or "CPR" (Counts Per Rotation) or MaxRPM
    public int getEncoderValue(String encoderLoc, String encoderKey)
    {
        JSONObject encoderLocObj, encoderTypeObj;
        JsonReader encoderSpecsReader;
    	int counts = 0;
    	try{
    	    String key = JsonReader.getKeyIgnoreCase(driveTrainObj, encoderLoc);
    	    encoderLocObj = (JSONObject) driveTrainObj.get(key);
    	    key = JsonReader.getKeyIgnoreCase(encoderLocObj, "encoderType");
    	    String encoderType = this.getString(encoderLocObj, key);
//    	    System.out.println("frc6880: encoderTYpe = " + encoderType);
    	    encoderSpecsReader = new JsonReader(JsonReader.encoderSpecsFile);
            encoderTypeObj = (JSONObject) encoderSpecsReader.rootObj.get(encoderType);
    		counts = getInt(encoderTypeObj, encoderKey);
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	return counts;
    }
    
    public void printForDebug() {
        System.out.println("frc6880: L1 channel number = " + getChannelNum("L1"));
        System.out.println("frc6880: L2 channel number = " + getChannelNum("L2"));
        System.out.println("frc6880: R1 channel number = " + getChannelNum("R1"));
        System.out.println("frc6880: R2 channel number = " + getChannelNum("R2"));
        System.out.println("frc6880: encoder PPR = " + getEncoderValue("LeftEncoder", "PPR"));
    }
}
