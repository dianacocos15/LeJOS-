import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.robotics.navigation.MovePilot;

public class Travel{
	private int j = Navigate.j;
	private int i = Navigate.i;
	
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
		//System.out.println("Computing Direction ... ");
		//Button.waitForAnyPress();
		if(i == target_x && j == target_y) {
			//System.out.print("\n("+Navigate.i +"," +Navigate.j +") == (" + target_x + "," + target_y+")");
			return true;
		}
		else {
			//System.out.print("\n("+Navigate.i +"," +Navigate.j +") ! (" + target_x + "," + target_y+")");
			switch(Navigate.orientation) {
				//default: Navigate.orientation = 1;
				case 1: // We are East
					{ 
						if (target_y == j - 1) { // Go north
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 4;
						//System.out.println("case1, target_y == j - 1");
					}
					else if (target_x == i + 1) {
						System.out.println("case1, target_x == i + 1");
						//travel 1
					} 
					else if (target_y == j + 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 2;
						//System.out.println("case1, target_y == j + 1");
					}
					else if (target_x == i - 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 3; 
						//System.out.println("case1, target_x == i - 1");
					}
						break;
					}
				case 2: // if south
					{
						if (target_y == j - 1) {
						//travel 1
						me.rotate(180);
						Navigate.orientation = 4;
						//System.out.println("case2, target_y == j - 1");
					}
					else if (target_x == i + 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 1;
						//System.out.println("case2, target_x == i + 1");
					} 
					else if (target_y == j + 1) {
						//travel 1
						//System.out.println("case2, target_y == j + 1");
					}
					else if (target_x == i - 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 3;
						//System.out.println("case2, target_x == i - 1");
					}
						break;
					}
				case 3: // if west
					{
						if (target_y == j - 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 4;
						//System.out.println("case3, target_y == j - 1");
					}
					else if (target_x == i + 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 1;
						//System.out.println("case3, target_x == i + 1");
					} 
					else if (target_y == j + 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 2;
						//System.out.println("case3, target_y == j + 1");
					}
					else if (target_x == i - 1) {
						//travel 1
						//System.out.println("case3, target_x == i - 1");
					}
						
						break;
					}
				case 4: // If north
					{
						if (target_y == j - 1) {
						//travel 1
						//System.out.println("case4, target_y == j - 1");
					}
					else if (target_x == i + 1) {
						//rotate 90, travel 1
						me.rotate(90);
						Navigate.orientation = 1;
						//System.out.println("case4, target_x == i + 1");
					} 
					else if (target_y == j + 1) {
						//rotate 180, travel 1
						me.rotate(180);
						Navigate.orientation = 2;
						//System.out.println("case4, target_y == j + 1");
					}
					else if (target_x == i - 1) {
						//rotate -90, travel 1
						me.rotate(-90);
						Navigate.orientation = 3;
						//System.out.println("case4, target_x == i - 1");
					}
						break;
					}
				}	
		}
		return false;
		
	}

}
