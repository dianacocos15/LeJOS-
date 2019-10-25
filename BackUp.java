// BackUp.java
// 
// An behaviour to back up the robot if the touch sensors detect
// an obstacle.  It exploits the PilotRobot class to make sure that
// calls to the motors are non-blocking (i.e. they return immediately)
// so that if the Arbitrator asks a behaviour to be suppressed, it can
// stop immediately.
//
// Terry Payne
// 1st October 2018
//

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import java.util.Collections;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;

public class BackUp implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;

	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	// Constructor - the robot, and gets access to the pilot class
	// that is managed by the robot (this saves us calling
	// me.getPilot.somemethod() all of the while)
    public BackUp(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }

	// When called, this should stop action()
	public void suppress(){
		suppressed = true;
	}
	
	// When called, determine if this behaviour should start
	public boolean takeControl(){
		if (me.getDistance() < 0.07) {
			//lcd.drawString("ROTATING", 0, 70, 0);
			return true;
		}
		return false;
	}
	
	public void action() {
		// Allow this method to run
		PilotRobot.listIndex = 0;
		me.setBehavior("Back Up");
		suppressed = false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AStar astar = new AStar(PilotRobot.grid, Navigate.j, Navigate.i);
		List<AStar.Node> n = astar.runAlgorithm(Navigate.j, Navigate.i, PilotRobot.finalGoaly,PilotRobot.finalGoalx);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PilotRobot.list = n;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LCD.clear();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(AStar.Node cell : PilotRobot.list) {
			System.out.println(cell.y + " " + cell.x);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("" + PilotRobot.grid[1][4].toInteger());
		Button.waitForAnyPress();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("" + Navigate.i +  " " + Navigate.j);
		PilotRobot.listIndex = 0;
		
		
		PilotRobot.nextCoordinate = true;
	    
		
	    while(pilot.isMoving() && !suppressed) {
	        Thread.yield();  // wait till turn is complete or suppressed is called
	    }
	    
	    // Ensure that the motors have stopped.
	    //pilot.stop();		
	}

}