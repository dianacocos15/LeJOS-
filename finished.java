import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class finished implements Behavior {

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return PilotRobot.finished;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		Button.waitForAnyPress();
		System.exit(0);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
