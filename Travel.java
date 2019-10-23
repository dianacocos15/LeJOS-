import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Travel implements Behavior{
	public boolean suppressed;
	public Travel() {
		
	}
	
	public void suppress() {
		suppressed = true;
	}
	
	public void action() {
		
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
