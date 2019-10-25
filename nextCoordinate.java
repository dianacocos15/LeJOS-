import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

import java.util.Collections;
import java.util.List;

public class nextCoordinate implements Behavior {
	private PilotRobot me;
	private MovePilot pilot;
	public static List<AStar.Node> newList;
	
	public static boolean suppressed;
	
	
	public nextCoordinate(PilotRobot robot, List<AStar.Node> list){
   	 	me = robot;
   	 	pilot = me.getPilot();
   	 	this.newList = list;
	}
	
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return PilotRobot.nextCoordinate;
	}
	
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		// Travels to where specified
		//newList = PilotRobot.list;
		
		//System.out.print(newList);
		//System.out.print("size is :" + newList.size());
		System.out.println("NEXT COORD BEHAVIOUR");
		
		AStar.Node n = PilotRobot.list.get(PilotRobot.listIndex);
		Travel t = new Travel(n.x,n.y, me);
		boolean correctCoord = t.travelCorrectDirection();
		if (correctCoord) {
			PilotRobot.nextCoordinate = true;
			PilotRobot.listIndex++;
		}else {
			PilotRobot.nextCoordinate = false;
		}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}
}
