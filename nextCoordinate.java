import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

import java.util.ArrayList;
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
		if(me.getBehavior() != "Right Color" && me.getBehavior() != "Left Color") {
			return PilotRobot.nextCoordinate;
		}
		return false;
	}
	
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		// Travels to where specified
		//newList = PilotRobot.list;
		
		//System.out.print(newList);
		//System.out.print("size is :" + newList.size());
		me.setBehavior("Next Behaviour");
		if(PilotRobot.finalGoalx == Navigate.i && PilotRobot.finalGoaly == Navigate.j) {
			PilotRobot.reached = true;
			return;
		}
		else if (PilotRobot.changedValues) {
			getList();
			PilotRobot.changedValues = false;
		}
		
		
		AStar.Node n = newList.get(PilotRobot.listIndex);
		Travel t = new Travel(n.y,n.x, me);
		if(PilotRobot.grid[n.y][n.x].getValue() == "X") {
			getList();
			//System.out.println(Navigate.i + " " + Navigate.j);
			return;
		}
		
		else {
			//System.out.println("Entered the else");
			//Button.waitForAnyPress();
			
			boolean correctCoord = t.travelCorrectDirection();
			
//			System.out.println("After travelCorrectDirection");
//			//Button.waitForAnyPress();
//			
//			System.out.println(Navigate.i + " " + Navigate.j);
//			System.out.println(n.y + "" + n.x);
//			System.out.println("Orientation :"+Navigate.orientation);
//			System.out.println(correctCoord);
//			//Button.waitForAnyPress();
			if (correctCoord) {
				PilotRobot.nextCoordinate = true;
				PilotRobot.listIndex++;
			}else {
				PilotRobot.nextCoordinate = false;
			}
		}
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		suppressed = true;
	}
	
	public void getList() {
		Cell[][] newGrid;
		try {
			newGrid = PilotRobot.getGridCopy();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			newGrid = PilotRobot.grid;
		}
		AStar astar = new AStar(newGrid, Navigate.j, Navigate.i);
		List<AStar.Node> newNodes = astar.runAlgorithm(Navigate.j, Navigate.i, PilotRobot.finalGoaly, PilotRobot.finalGoalx);
		newList = new ArrayList<AStar.Node>(newNodes);
		for(AStar.Node node : newList) {
			//System.out.println(node.y + "" + node.x);
			
		}
		//Button.waitForAnyPress();
		PilotRobot.listIndex = 1;
	}
}
