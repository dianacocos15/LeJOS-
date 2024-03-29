import lejos.hardware.Sound;
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
			if(Color.BLACK != me.getLeftColourSensor() && Color.BLACK == me.getRightColourSensor()) {
				right = true;
				return right;
			}
		}
		
		right = false;
		return false;
	}
	
	public void action() {
		me.setBehavior("Right Color");
		pilot.setAngularAcceleration(100);
		if( Color.BLACK == me.getLeftColourSensor()  ^ Color.BLACK == me.getRightColourSensor()) {
			pilot.rotate(3);
			PilotRobot.correctionIncrementCount++;
			
			// Only move if neither is black.
			if ( Color.BLACK != me.getLeftColourSensor()  && Color.BLACK != me.getRightColourSensor()){
				pilot.travel(0.5);
			}
			
		}
		
		/* Travel if both black */
		if ( Color.BLACK == me.getLeftColourSensor()  && Color.BLACK == me.getRightColourSensor()) {
			pilot.setLinearAcceleration(PilotRobot.ACCELERATION);
			PilotRobot.correctionIncrementCount = 0;
			pilot.travel(3);
			pilot.setLinearAcceleration(PilotRobot.DECELERATION);
			me.setCorrectBlackLines(false);
			PilotRobot.runMove = true;
		}
		
		if(PilotRobot.correctionIncrementCount > 10) {
			pilot.travel(0.05);
			//PilotRobot.runMove = true;
		}
		
		pilot.setAngularAcceleration(PilotRobot.ANGULAR_ACCELERATION);
		
	}
}
