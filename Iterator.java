import java.util.List;
import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class Iterator implements Behavior{
	private PilotRobot me;
	boolean suppressed;
	boolean doneVisit;
	
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
		
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("");
		me.setBehavior("Iterator");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PilotRobot.cellIndex++;
		if (PilotRobot.cellIndex == 6 && doneVisit == false) {
			System.out.println("NEW ARRAY");
			for (int i = PilotRobot.grid.length -1; i >= 0; i--) {
				for (int j = PilotRobot.grid[0].length -1; j >= 0; j--) {
					if (PilotRobot.grid[i][j].getC() == 0.0 && PilotRobot.grid[i][j].getValue()!="X") {
							PilotRobot.cellList.add(PilotRobot.grid[i][j]);
						
					}
				}
			}
			Button.waitForAnyPress();
			doneVisit = true;
		}
		
		if(PilotRobot.cellIndex == PilotRobot.cellList.size()) {
			PilotRobot.finished = true;
		}

		
		
		//System.out.println("Cell index : " + PilotRobot.cellIndex);
		
		
		PilotRobot.finalGoalx = PilotRobot.cellList.get(PilotRobot.cellIndex).getX();
		PilotRobot.finalGoaly = PilotRobot.cellList.get(PilotRobot.cellIndex).getY();
		PilotRobot.nextCoordinate = true;
		
		PilotRobot.changedValues = true;
		PilotRobot.reached = false;
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}

}
