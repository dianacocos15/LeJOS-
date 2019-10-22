import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

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
	
	public PilotRobot robot;
	static GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public Navigate(int firstCoordinate, int secondCoordinate) {
		i = firstCoordinate;
		j = secondCoordinate;
	}
	
	public static void drawGrid() {
		for(int k = 0; k < PilotRobot.grid.length; k++) {
			for(int l = 0; l < PilotRobot.grid[0].length; l++) {
				if(k == i && j == l) {
					lcd.drawString(r, k*15 + 40, l*15, GraphicsLCD.HCENTER);
				}
				else {
					lcd.drawString(String.valueOf(PilotRobot.grid[k][l]), k*15 + 40, l*15, GraphicsLCD.HCENTER);	
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
		}
	
	public static void markObstacles() {
		/*
		 *	if there's an obstacle in front
		 *		if our orientation is E/S/W/N, increment given cells
		 *			EAST: (i+1, j)
		 *			SOUTH: (i, j-1)
		 *			WEST: (i-1, j)
		 *			NORTH: (i, j-1)  
		 */
		if(front == true) {
			switch(Navigate.orientation) {
			case 1: PilotRobot.grid[i+1][j]++;
				break;
			case 2: PilotRobot.grid[i][j+1]++;
				break;
			case 3: PilotRobot.grid[i-1][j]++;
				break;
			case 4: PilotRobot.grid[i][j-1]++;
				break;
			}
		}
		
		else if(left == true) {
			switch(Navigate.orientation) {
			case 1: PilotRobot.grid[i][j-1]++;
				break;
			case 2: PilotRobot.grid[i+1][j]++;
				break;
			case 3: PilotRobot.grid[i][j+1]++;
				break;
			case 4: PilotRobot.grid[i-1][j]++;
				break;
			}
		}
		
		else if(right == true){
			switch(Navigate.orientation) {
			case 1: PilotRobot.grid[i][j+1]++;
				break;
			case 2: PilotRobot.grid[i-1][j]++;
				break;
			case 3: PilotRobot.grid[i][j-1]++;
				break;
			case 4: PilotRobot.grid[i+1][j]++;
				break;
			}
		}
		
		front = false;
		left = false;
		right = false;
	}
	
	public static String currentPosition() {
		String position = "(" + i + ", " + j + ")";
		
		return position;
	}
}
