import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;

public class Navigate {
	static int orientation = 1; //CHANGEBACK
	//1 = E (how we set off)
	//2 = S
	//3 = W
	//4 = N
	
	private static String r = ".";
	static int i = 1;
	static int j = 1;
	static Cell[][] oldGrid;
	static Cell[] list = new Cell[6];

	
	static boolean front  = DriveForward.obstacle_front;
	static boolean right  = DriveForward.obstacle_right;
	static boolean left = DriveForward.obstacle_left;
	
	public static PilotRobot robot;
	
	static GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public Navigate(int firstCoordinate, int secondCoordinate, PilotRobot pilot) {
		i = firstCoordinate;
		j = secondCoordinate;
		robot = pilot;
	}
	
	public static void drawGrid() {
		if(PilotRobot.grid != oldGrid) {
			
			lcd.setFont(Font.getSmallFont());
			lcd.clear();
			
			if(robot.currentMode == "Letters") {
				for(int k = 0; k < PilotRobot.grid.length; k++) {
					for(int l = 0; l < PilotRobot.grid[0].length; l++) {
						if(k == i && j == l) {
							lcd.drawString(r, k*15 + 40, l*15, GraphicsLCD.HCENTER);
						}
						else {
							if(PilotRobot.grid[k][l].getValue() == "100") {
								lcd.drawString("X", k*15 + 40, l*15, GraphicsLCD.HCENTER);	
							}
							else {
								lcd.drawString(PilotRobot.grid[k][l].getValue(), k*15 + 40, l*15, GraphicsLCD.HCENTER);	
							}
						}
					}
				}
			}else {
				for(int k = 0; k < PilotRobot.grid.length; k++) {
					for(int l = 0; l < PilotRobot.grid[0].length; l++) {
						if(k == i && j == l) {
							lcd.drawString(r, k*15 + 40, l*15, GraphicsLCD.HCENTER);
						}
						else {
							if(PilotRobot.grid[k][l].getValue() == "100") {
								lcd.drawString("X", k*15 + 40, l*15, GraphicsLCD.HCENTER);	
							}
							else {
								lcd.drawString(String.valueOf((int)PilotRobot.grid[k][l].returnProbability()), k*15 + 40, l*15, GraphicsLCD.HCENTER);
							}
						}
					}
				}
			}
		}
	}
	
	public static void move() {
			switch(orientation) {
			default: orientation = 1;
			case 1: i++;
				break;
			case 2: j++;
				break;
			case 3: i--;
				break;
			case 4: j--;
				break;
			
			}
			
			PilotRobot.nextCoordinate = true;
		}
	
	public static void markObstacles() {
		/*
		 *	if there's an obstacle in front
		 *		if our orientation is E/S/W/N, increment given cells
		 *			EAST: (i+1, j)
		 *			SOUTH: (i, j+1)
		 *			WEST: (i-1, j)
		 *			NORTH: (i, j-1)  
		 */
		boolean obstacleNorth = false; //
		boolean obstacleEast = false; // 
		boolean obstacleSouth = false; // 
		boolean obstacleWest = false; // 		
		
		boolean checkedNorth = false; //
		boolean checkedEast = false; // 
		boolean checkedSouth = false; // 
		boolean checkedWest = false; // 		
		
		if(front == true) {
			switch(Navigate.orientation) {
			case 1: obstacleEast = true;
				break;
			case 2: obstacleSouth = true;
				break;
			case 3: obstacleWest = true;
				break;
			case 4: obstacleNorth = true;
				break;
			}
		}
		
		else if(left == true) {
			switch(Navigate.orientation) {
			case 1: obstacleNorth = true;
				break;
			case 2: obstacleEast = true;
				break;
			case 3: obstacleSouth = true;
				break;
			case 4: obstacleWest = true;
				break;
			}
		}
		
		else if(right == true){
			switch(Navigate.orientation) {
			case 1:  obstacleSouth = true;
				break;
			case 2:  obstacleWest = true;
				break;
			case 3: obstacleNorth = true;
				break;
			case 4:  obstacleEast = true;
				break;
			}
		}
		
		
		switch(Navigate.orientation) {
			case 1: 
				checkedEast = true;
				checkedNorth = true;
				checkedSouth=true;
			case 2:
				checkedEast = true;
				checkedSouth=true;
				checkedWest =true;
			case 3:
				checkedNorth = true;
				checkedSouth=true;
				checkedWest=true;
			case 4:
				checkedNorth=true;
				checkedEast =true;
				checkedWest=true;
		}
		
		if (obstacleNorth){
			PilotRobot.grid[i][j-1].setValue("X");
			Cell.incrementOccupiedCell();
		} else Cell.incrementEmptyCell();

		if (obstacleEast){
			PilotRobot.grid[i+1][j].setValue("X");
			Cell.incrementOccupiedCell();
		} else Cell.incrementEmptyCell();
		
		if (obstacleSouth){
			PilotRobot.grid[i][j+1].setValue("X");
			Cell.incrementOccupiedCell();
		} else Cell.incrementEmptyCell();
		
		if (obstacleWest){
			PilotRobot.grid[i-1][j].setValue("X");
			Cell.incrementOccupiedCell();
		} else Cell.incrementEmptyCell();
		
		
		//Increment visited for each block checked for obstacles.
		if (checkedNorth){
			PilotRobot.grid[i][j-1].incrementC();
		}
		if (checkedEast){
			PilotRobot.grid[i+1][j].incrementC();
		}
		if (checkedSouth){
			PilotRobot.grid[i][j+1].incrementC();
		}
		if (checkedWest){
			PilotRobot.grid[i-1][j].incrementC();
		}
		
		//increment checked for block standing on.
		PilotRobot.grid[i][j].incrementC();
		
		front = false;
		left = false;
		right = false;
	}
	
	public static String currentPosition() {
		String position = "(" + i + ", " + j + ")";
		
		return position;
	}
	
	public static String getX() {
		return "" + i;
	}
	
	public static String getY() {
		return "" + j;
	}
	
	public static String getOrientationAsString() {
		String orientationAsString = "";
		
		switch(orientation) {
		case 1: orientationAsString = ">";
			break;
		case 2: orientationAsString = "V";
			break;
		case 3: orientationAsString = "<";
			break;
		case 4: orientationAsString = "^";
			break;
		}
		
		return orientationAsString;
	}
}
