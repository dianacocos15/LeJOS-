
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
		
		if((7 == me.getLeftColourSensor() && 7 == me.getRightColourSensor()) || 
				13 == me.getLeftColourSensor() && 13 == me.getRightColourSensor()) {
			both = true;
			return both;
		}
		
		both = false;
		return both;
	}

	public void action() {
		if(both == true) {
			boolean rotateAction = true;
			pilot.setLinearAcceleration(15);
			pilot.travel(18);
			pilot.setLinearAcceleration(100);
			
			if (me.getDistance() < 0.08) {
				rotateAction = false;
			}
			
			me.rotateHead(90);
			if (me.getDistance() < 0.08 && rotateAction) {
				pilot.rotate(20);
			}
			me.rotateHead(0);
			
			me.rotateHead(-90);
			if (me.getDistance() < 0.08 && rotateAction) {
				pilot.rotate(-20);
			}
			me.rotateHead(0);
			
		}
	}
}
