package org.usfirst.frc.team6880.robot.jsonReaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AutonomousOptionsReader extends JsonReader {
	public JSONObject conditions;
	public JSONArray startingPos;
	public JSONArray tasks;
    public AutonomousOptionsReader(String filePath, String autonomousOption) {

        super(filePath);
        try {
            String key = getKeyIgnoreCase(rootObj, autonomousOption);
            conditions = (JSONObject) rootObj.get(key);

            key = getKeyIgnoreCase(conditions, "starting-pos");
            startingPos = (JSONArray) getArray(conditions, key);

            key = getKeyIgnoreCase(conditions, "tasks");
            tasks = (JSONArray) getArray(conditions, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("autonomousOption = " + autonomousOption + ", tasks = " + tasks);
    }
    public AutonomousOptionsReader(String filePath){
        super(filePath);
    }

    public JSONObject getTask(int taskNum){
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject)tasks.get(taskNum);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray getStartingPos() {
    	return startingPos;
    }
    
    public JSONArray getAllTasks()
    {
    	return this.tasks;
    }
    
    public List<String> getAll(){
        Iterator<?> keysIterator = rootObj.keySet().iterator();
        ArrayList<String> rootObjNames = new ArrayList<String>();
        while (keysIterator.hasNext()){
            rootObjNames.add((String) keysIterator.next());
        }
        return rootObjNames;
    }
}
