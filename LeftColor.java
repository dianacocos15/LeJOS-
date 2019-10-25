import java.awt.Color;

import lejos.hardware.Sound;
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
		pilot.setAngularAcceleration(100);
		/*
		 * Issue ? Does this mean if neither is black rotate?
		 * Fix - Exclusive or (only rotate if one is black)
		 * **/
		if( 7 == me.getLeftColourSensor()  ^ 7 == me.getRightColourSensor()) {
			pilot.rotate(-3);
			PilotRobot.correctionIncrementCount++;
			
			// Only move if neither is black. Consider commenting this out as behaves odly on colours.
			if ( 7 != me.getLeftColourSensor()  && 7 != me.getRightColourSensor()){
				pilot.travel(0.5, true);
			}
			
		}
		
		/*Travel if both black*/
		if ( 7 == me.getLeftColourSensor()  && 7 == me.getRightColourSensor()) {
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