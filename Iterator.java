import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class Iterator implements Behavior{
	private PilotRobot me;
	boolean suppressed;
	
	Cell[] list = new Cell[6];
	
	public Iterator(PilotRobot robot) {
		me = robot;
	}
	
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		if (PilotRobot.reached == true) {
			return true;
		}
		if (PilotRobot.cellIndex == 6){
			System.exit(0);
		}
		
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		me.setBehavior("Iterator");
		PilotRobot.cellIndex++;
		
		//System.out.println("Cell index : " + PilotRobot.cellIndex);
		
		
		PilotRobot.finalGoalx = PilotRobot.cellList[PilotRobot.cellIndex].getX();
		PilotRobot.finalGoaly = PilotRobot.cellList[PilotRobot.cellIndex].getY();

		PilotRobot.changedValues = true;
		PilotRobot.reached = false;
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}

}
