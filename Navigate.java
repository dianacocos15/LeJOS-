import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Navigate implements Behavior {
	public boolean suppressed;
	private PilotRobot me;
	private MovePilot pilot;
	
	GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public Navigate(PilotRobot robot) {
		me = robot;
		pilot = me.getPilot();
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
