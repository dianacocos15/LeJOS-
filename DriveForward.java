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
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.MovePilot;

public class DriveForward implements Behavior {
	public static boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;
	private boolean dontDrive = false;
//	private static int i = Navigate.i;
//	private static int j = Navigate.j;
	
	static boolean obstacle_front;
	static boolean obstacle_right;
	static boolean obstacle_left;

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
		
		suppressed = false;
		
		
		// Go forward
		pilot = me.getPilot();
		
		/**
		 * Check if necessary to correct.
		 * */
		pilot.setLinearAcceleration(PilotRobot.ACCELERATION);
		if(me.getBehavior() == "Left Color" || me.getBehavior() == "Right Color") {
			Sound.beep();
			pilot.travel(13);
			dontDrive = true;
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			checkObstacles();
		}
		else if(me.getCorrectBlackLines() == true && dontDrive == false) {
			suppressed = false;
			pilot.setLinearAcceleration(5);
			pilot.travel(24.5, true);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
		}
		else if (dontDrive == false){
			suppressed = false;
			pilot.travel(24.5);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			checkObstacles();
			
		}
		
		dontDrive = false;
		me.setBehavior("Drive Forward");
		
//		while(pilot.isMoving() && !suppressed) {
//	        Thread.yield();  // wait till turn is complete or suppressed is called
//	    }
//		
//		pilot.stop();
	}
	
	
	public void checkObstacles() {
		me.correct_head_turn = false;
		boolean rotateAction = true;
		int wallCorrectionDirection = 0; //no correction
		
		if (me.getDistance() < 0.08 && me.correct_head_turn  == false) {
			rotateAction = false;
			me.correct_head_turn = true;
		}
		
		if (me.distanceSample() < 0.10) {
			Navigate.front = true;
			Navigate.markObstacles();
		}
		
		me.rotateHead(PilotRobot.ROTATE_HEAD_LEFT);
		if(me.distanceSample() < 0.15) {
			Navigate.left = true;
			Navigate.markObstacles();
		}
		
		if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && rotateAction && me.correct_head_turn == false) {
			wallCorrectionDirection = PilotRobot.ROTATE_ROBOT_RIGHT;
			me.correct_head_turn = true;
		}
		
		else if (me.getDistance() < 0.23 && me.getDistance() > 0.13 && me.correct_head_turn == false) {
			wallCorrectionDirection = PilotRobot.ROTATE_ROBOT_LEFT;
			me.correct_head_turn = true;
		}

		//me.rotateHead(PilotRobot.ROTATE_HEAD_CENTER);
		
		me.rotateHead(PilotRobot.ROTATE_HEAD_RIGHT);
		if(me.distanceSample() < 0.15) {
			Navigate.right = true;
			Navigate.markObstacles();
		}
		
		if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && rotateAction && me.correct_head_turn == false) {
			wallCorrectionDirection = PilotRobot.ROTATE_ROBOT_LEFT;
			me.correct_head_turn = true;
		}
		
		else if (me.getDistance() < 0.23 && me.getDistance() > 0.13 && me.correct_head_turn == false) {
			wallCorrectionDirection = PilotRobot.ROTATE_ROBOT_RIGHT;
			me.correct_head_turn = true;
		}
		me.rotateHead(PilotRobot.ROTATE_HEAD_CENTER);
		
		if(me.correct_head_turn) {
			me.setCorrectBlackLines(true);
		}
		
		pilot.rotate(wallCorrectionDirection);
		wallCorrectionDirection = 0;	
	}
	
//	public static void displayObstacles() {
//		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
//		lcd.drawString("Front Obstacle"+PilotRobot.obstacle_front, 0, 20, 0);
//		lcd.drawString("Right Obstacle"+PilotRobot.obstacle_right, 0, 30, 0);
//		lcd.drawString("Left Obstacle"+PilotRobot.obstacle_left, 0, 40, 0);
//	}
		
		
		//head forward
		//case 1: orientation = 1
		//increment (i+1, j)
		//case 2: orientation = 2
		//increment (i, j+1)
		//case 3
		//decrement (i,j)
		//case 4
		//increment (i, j-1)
	
		//head left
		//case 1
		//(i, -j)
		//case 2
		//(i+1, j)
		//case 3
		//(i, j+1)
		//case 4
		//(i-1, j)
		
		//head right
		//case 1 (i, j+1)
		//case 2 (i-1, j)
		//case 3 (i, j-1)
		//case 4 (i+1, j)
	}	