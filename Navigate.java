import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;

public class Navigate {
	static int orientation = 1;
	//1 = E (how we set off)
	//2 = S
	//3 = W
	//4 = N
	
	private static String r = ".";
	static int i = 1;
	static int j = 1;
	
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
		for(int k = 0; k < PilotRobot.grid.length; k++) {
			for(int l = 0; l < PilotRobot.grid[0].length; l++) {
				if(k == i && j == l) {
					lcd.drawString(r, k*15 + 40, l*15, GraphicsLCD.HCENTER);
				}
				else {
					lcd.drawString(PilotRobot.grid[k][l].getValue(), k*15 + 40, l*15, GraphicsLCD.HCENTER);	
				}
			}
		}
	}
	
	public static void move() {
			Sound.beep();
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
		
		if (obstacleNorth){
			PilotRobot.grid[i][j-1].setValue("100");
		}
		if (obstacleEast){
			PilotRobot.grid[i+1][j].setValue("100");
		}
		if (obstacleSouth){
			PilotRobot.grid[i][j+1].setValue("100");
		}
		if (obstacleWest){
			PilotRobot.grid[i-1][j].setValue("100");
		}
		
		
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
}
