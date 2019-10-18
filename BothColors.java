
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;



public class BothColors implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;
	public boolean both = false;
	
	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public BothColors(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
	
		if(me.getCorrectBlackLines() == true) {
			if(7 == me.getLeftColourSensor() && 7 == me.getRightColourSensor()){
				both = true;
				me.correct_head_turn = false;
				return both;
			}
		}
		
		both = false;
		return both;
	}

	public void action() {
		me.setBehavior("Both Colors");
		me.correct_head_turn = false;
		
		if(both == true) {
			me.rotateAction = true;
			pilot.setLinearAcceleration(PilotRobot.ACCELERATION);
			pilot.travel(17);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			
			if (me.getDistance() < 0.08 && me.correct_head_turn  == false) {
				me.rotateAction = false;
				me.correct_head_turn = true;
			}
			
			me.rotateHead(PilotRobot.ROTATE_HEAD_RIGHT);
			if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && me.rotateAction && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_RIGHT);
				me.correct_head_turn = true;
			}
			
			else if (me.getDistance() < 0.23 && me.getDistance() > 0.13 && me.correct_head_turn == false) {
				pilot.rotate(PilotRobot.ROTATE_ROBOT_LEFT);
				me.correct_head_turn = true;
			}
	
			me.rotateHead(PilotRobot.ROTATE_HEAD_CENTER);
			
			me.rotateHead(PilotRobot.ROTATE_HEAD_LEFT);
			if (me.getDistance() < PilotRobot.DISTANCE_FROM_THE_WALL && me.rotateAction && me.correct_head_turn == false) {
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
	}
}
