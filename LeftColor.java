import java.awt.Color;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class LeftColor implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;
	public boolean right = false;
	public boolean left = false;
	
	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public LeftColor(PilotRobot robot){
    	 me = robot;
    	 pilot = me.getPilot();
    }
	
	public void suppress(){
		suppressed = true;
	}
	
	public boolean takeControl(){
		if(me.getCorrectBlackLines()) {
			if(7 == me.getLeftColourSensor() && 7 != me.getRightColourSensor()) {
				left = true;
				return left;
			}
		}
		
		left = false;
		return false;
	}

	public void action() {
		
		me.setBehavior("Left Color");
		
		if( !(7 == me.getLeftColourSensor() && 7 == me.getRightColourSensor())) {
			pilot.rotate(-3);
//			if(7 != me.getLeftColourSensor() && 7 != me.getRightColourSensor()) {
				pilot.travel(0.5, true);
			//}
		}
		else {
			pilot.travel(0.17);
			me.setCorrectBlackLines(false);
		}
	}
}