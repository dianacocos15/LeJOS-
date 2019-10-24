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

import java.util.List;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

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
		
		me.setBehavior("Back Up");
		suppressed = false;
		AStar astar = new AStar(PilotRobot.grid, Navigate.i, Navigate.j);
		List<AStar.Node> n = astar.runAlgorithm(Navigate.i, Navigate.j, PilotRobot.finalGoalx,PilotRobot.finalGoaly);
		PilotRobot.list = n;
		PilotRobot.listIndex = 0;
		
		//Recalculate and add back list
		
		
		//me.rotate(90);
		//Navigate.orientation++;
		me.setCorrectBlackLines(true);
	    
	    
	    while(pilot.isMoving() && !suppressed) {
	        Thread.yield();  // wait till turn is complete or suppressed is called
	    }
	    
	    // Ensure that the motors have stopped.
	    //pilot.stop();		
	}

}