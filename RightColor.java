import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class RightColor implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;
	public boolean right = false;
	public boolean left = false;
	
	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public RightColor(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		if (me.getCorrectBlackLines()) {
			if(7 != me.getLeftColourSensor() && 7 == me.getRightColourSensor()) {
				right = true;
				return right;
			}
		}
		
		right = false;
		return false;
	}
	
	public void action() {
		me.setBehavior("Right Color");
		
		if( 7 == me.getLeftColourSensor()  ^ 7 == me.getRightColourSensor()) {
			pilot.rotate(3);
			
			// Only move if neither is black.
			if ( 7 != me.getLeftColourSensor()  && 7 != me.getRightColourSensor()){
				pilot.travel(0.5);
			}
			
		}
		
		/* Travel if both black */
		if ( 7 == me.getLeftColourSensor()  && 7 == me.getRightColourSensor()) {
			pilot.setLinearAcceleration(PilotRobot.ACCELERATION);
			pilot.travel(3);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			me.setCorrectBlackLines(false);
		}
		
	}
}
