package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class NavOptionsReader extends JsonReader {
	String navOptStr;
    public JSONObject navOptObj;
    public JSONObject lfObj=null;
    public JSONObject imuObj=null;
    public JSONObject rangeObj=null;
    public JSONObject encoderVarsObj=null;

    public NavOptionsReader(String filePath, String navOptStr) {
        super(filePath);
        try {
            String key = JsonReader.getKeyIgnoreCase(rootObj, navOptStr);
            this.navOptObj =(JSONObject) rootObj.get(key);
            key = JsonReader.getKeyIgnoreCase(navOptObj, "LineFollow");
            if (key != null) {
                lfObj = (JSONObject) navOptObj.get(key);
            }
            key = JsonReader.getKeyIgnoreCase(navOptObj, "IMU");
            if (key != null) {
                imuObj = (JSONObject) navOptObj.get(key);
            }
            key = JsonReader.getKeyIgnoreCase(navOptObj, "RangeSensor");
            if (key != null) {
                rangeObj = (JSONObject) navOptObj.get(key);
            }
            key = JsonReader.getKeyIgnoreCase(navOptObj, "DriveSysEncoderVariables");
            if (key != null) {
                encoderVarsObj = (JSONObject) navOptObj.get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean lineFollowerExists() {
        return (this.lfObj != null);
    }

    public boolean imuExists() {
        return (this.imuObj != null);
    }

    public boolean rangeSensorExists() { return (this.rangeObj != null); }

    public boolean encoderVarsExist() { return (this.encoderVarsObj != null); }

    public String getLightSensorName() {
        String lightSensorName = null;
        String key=null;
        JSONObject lightSensorObj;
        try {
            key = JsonReader.getKeyIgnoreCase(lfObj, "LightSensor");
            lightSensorObj = (JSONObject) lfObj.get(key);
            lightSensorName = getString(lightSensorObj, "name");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Light sensor not found");
        }
        return (lightSensorName);
    }

    public String getLightSensorType() {
        String sensorType = null;
        JSONObject lightSensorObj;
        try {
            String key = JsonReader.getKeyIgnoreCase(lfObj, "LightSensor");
            lightSensorObj = (JSONObject) lfObj.get(key);
            sensorType = getString(lightSensorObj, "type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (sensorType);
    }

    public double getIMUVariableDouble(String variableName) {
        double value=0.0;
        try {
            value = getDouble(imuObj, variableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (value);
    }

    public double getDoubleDriveSysEncVar(String varName) {
        double maxSpeed = 1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, varName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }

    public double getTurningMaxSpeed() {
        double maxSpeed=1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, "TurningMaxSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }

    public double getStraightLineMaxSpeed() {
        double maxSpeed=1.0;
        try {
            maxSpeed = getDouble(encoderVarsObj, "StraightLineMaxSpeed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (maxSpeed);
    }
}
