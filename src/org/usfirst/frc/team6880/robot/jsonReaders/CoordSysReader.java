package org.usfirst.frc.team6880.robot.jsonReaders;

import org.json.simple.JSONObject;

public class CoordSysReader extends JsonReader {
	String coordSysName;
	JSONObject coordSysObj;
	
	public CoordSysReader(String filePath, String coordSysName) {
		super(filePath);
        try {
            coordSysName = JsonReader.getKeyIgnoreCase(rootObj, coordSysName);
            coordSysObj = (JSONObject) rootObj.get(coordSysName);
            this.coordSysName = coordSysName;
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	public getPos() {
		
	}
}
