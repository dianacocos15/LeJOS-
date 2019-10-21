import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;

public class Navigate {
	static int orientation = 1;
	//1 = E (how we set off)
	//2 = S
	//3 = W
	//4 = N
	
	private static String r = ".";
	private static int i = 1;
	private static int j = 1;
	
	public PilotRobot robot;
	static GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
	public Navigate(int firstCoordinate, int secondCoordinate) {
		i = firstCoordinate;
		j = secondCoordinate;
	}
	
	public static void drawGrid() {
		for(int k = 0; k < PilotRobot.grid.length; k++) {
			for(int l = 0; l < PilotRobot.grid.length; l++) {
				if(k == i && j == l) {
					lcd.drawString(r, k*15 + 40, l*15, GraphicsLCD.HCENTER);
				}
				else {
					lcd.drawString(PilotRobot.grid[k][l], k*15 + 40, l*15, GraphicsLCD.HCENTER);	
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
	
	}
