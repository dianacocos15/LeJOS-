// DriveForward.java
// 
// An behaviour to drive the robot forward - this is the simplest 
// behaviour that we consider (and hence the takeControl() action 
// is true.  It exploits the PilotRobot class drive the robot 
// forward using a non-blocking call (i.e. it returns immediately) 
// so that if the Arbitrator asks a behaviour to be suppressed, it 
// can stop immediately.
//
// Terry Payne
// 1st October 2018

import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.MovePilot;

public class DriveForward implements Behavior {
	public static boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;

	// Constructor - store a reference to the robot
	public DriveForward(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }

	// When called, this should stop action()
	public void suppress(){
		suppressed = true;
	}

	// This returns true, so will always take control (if
	// no higher priority behaviour also takes control).
	// We could modify this to look for a "finish" variable
	// so that if the robot should stop, then we could simply
	// not take control.  If no behaviour takes control, the
	// Arbritrator will end.
	public boolean takeControl(){
		if (!pilot.isMoving()) {
			return true;	
		}
		return false;	
	}

	// This is our action function.  This starts the motor running
	// (which returns immediately).  We then simply run until told
	// to suppress our action, in which case we stop.
	public void action() {
		// Allow this method to run
		me.setBehavior("Drive Forward");
		suppressed = false;
		
		
		// Go forward
		pilot = me.getPilot();
		
		me.correct_head_turn = false;
		
		boolean rotateAction = true;
		/**
		 * Check if necessary to correct.
		 * */
		pilot.setLinearAcceleration(PilotRobot.ACCELERATION);
		if(me.getCorrectBlackLines() == true) {
			suppressed = false;
			pilot.travel(25, true);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
		}
		else {
			suppressed = false;
			pilot.travel(25);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			
			if (me.getDistance() < 0.08 && me.correct_head_turn  == false) {
				rotateAction = false;
				me.correct_head_turn = true;
			}
			
			me.rotateHead(PilotRobot.ROTATE_HEAD_RIGHT);
			
			if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && rotateAction && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_RIGHT);
				me.correct_head_turn = true;
			}
			
			else if (me.getDistance() < 0.23 && me.getDistance() > 0.13 && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_LEFT);
				me.correct_head_turn = true;
			}
	
			me.rotateHead(PilotRobot.ROTATE_HEAD_CENTER);
			
			me.rotateHead(PilotRobot.ROTATE_HEAD_LEFT);
			if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && rotateAction && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_LEFT);
				me.correct_head_turn = true;
			}
			
			else if (me.getDistance() < 0.23 && me.getDistance() > 0.13 && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_RIGHT);
				me.correct_head_turn = true;
			}
			me.rotateHead(PilotRobot.ROTATE_HEAD_CENTER);
			
			if(me.correct_head_turn) {
				me.setCorrectBlackLines(true);
			}
		}
		
		// While we can run, yield the thread to let other threads run.
		// It is important that no action function blocks any otherf action.
	//	while (!suppressed) {
	//		Thread.yield();
//		}
		
	    // Ensure that the motors have stopped.
		//me.getPilot().stop();
	}
}