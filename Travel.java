import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;

public class Travel{
	private static int j = Navigate.j;
	private static int i = Navigate.i;
	
	private static int target_x;
	private static int target_y;
	public boolean suppressed;
	public static final double CELL_DISTANCE = 24.5;
	
	PilotRobot me;
	MovePilot robot;
	
	public Travel(int x, int y, PilotRobot pilot) {
		target_x = x;
		target_y = y;
		
		this.me = pilot;
		robot = me.getPilot();
	}
	
	
	public void suppress() {
		suppressed = true;
	}
	
	public boolean travelCorrectDirection() {
		System.out.println("Travel Reached.");
		if(Navigate.i == target_x && Navigate.j == target_y) {
			return true;
		}
		else {
			switch(Navigate.orientation) {
				default: Navigate.orientation = 1;
				case 1: // We are East
					if (target_y == j - 1) { // Go north
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 4;
					}
					else if (target_x == i + 1) {
					} 
					else if (target_y == j + 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 2;
					}
					else if (target_x == i - 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 3; 
					}
					break;
				case 2: // if south
					if (target_y == j - 1) {
						//travel 1
						me.rotate(180);
						Navigate.orientation = 4;
					}
					else if (target_x == i + 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 1;
					} 
					else if (target_y == j + 1) {
						//rotate 180, travel 1
					}
					else if (target_x == i - 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 3;
					}
					break;
				case 3: // if west
					if (target_y == j - 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 4;
					}
					else if (target_x == i + 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 1;
					} 
					else if (target_y == j + 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 2;
					}
					else if (target_x == i - 1) {
						//travel 1
					}
					break;
				case 4: // If north
					if (target_y == j - 1) {
						//travel 1
					}
					else if (target_x == i + 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 1;
					} 
					else if (target_y == j + 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 2;
					}
					else if (target_x == i - 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 3;
					}
					break;
				}	
		}
		return false;
		
	}

}
