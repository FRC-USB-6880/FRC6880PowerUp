/**
 * 
 */
package org.usfirst.frc.team6880.robot.task;

import org.usfirst.frc.team6880.robot.FRCRobot;
import org.usfirst.frc.team6880.robot.task.*;

/**
 * This class maintains the sequence of autonomous tasks to be run.
 *
 */
public class AutonomousTasks {
    RobotTask curTask;
    int taskNum;
    boolean tasksDone;
    RobotTask[] tasks;
    FRCRobot robot;
    
    /**
     * 
     */
    public AutonomousTasks(FRCRobot robot, String taskSet) {
        this.robot = robot;
        // TODO Initialize a tasks array depending on the taskSet specified.
        if (taskSet.equalsIgnoreCase("TaskSet1")) {
            tasks = new RobotTask[1];
            tasks[0] = new TaskMoveForward20m(robot);
        } else if (taskSet.equalsIgnoreCase("TaskSet2")) {
            tasks = new RobotTask[7];
            int i = 0;
            tasks[i++] = new TaskMoveForward(this.robot, 20);
            tasks[i++] = new TaskTurnLeft(this.robot, 90);
            tasks[i++] = new TaskMoveForward(this.robot, 20);
            tasks[i++] = new TaskTurnLeft(this.robot, 90);
            tasks[i++] = new TaskMoveForward(this.robot, 20);
            tasks[i++] = new TaskTurnLeft(this.robot, 90);
            tasks[i++] = new TaskMoveForward(this.robot, 20);
        }
        //Start with first task
        curTask = tasks[0];
        taskNum = 0;
        tasksDone = false;
    }
    
    public void runNextTask() {
        //Run the current task. If current task ended
        if (!tasksDone && curTask.runTask())
        {
            //If there are still tasks to run
            if (taskNum + 1 < tasks.length)
            {
                System.out.println("Finished running task number " + taskNum);
                //Go to next task
                curTask = tasks[++taskNum];
                //Begin the next task
                curTask.initTask();
            }
            else
            {
                tasksDone = true;
                System.out.println("Robot has finished running the autonomous tasks");
            }
        }
    }

}
