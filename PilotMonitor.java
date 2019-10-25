import java.text.DecimalFormat;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Arbitrator;

// PillotMonitor.java
// 
// Based on the RobotMonitor class, this displays the robot
// state on the LCD screen; however, it works with the PilotRobot
// class that exploits a MovePilot to control the Robot.
//
// Terry Payne
// 1st October 2018
//

public class PilotMonitor extends Thread {
	private int delay;
	public PilotRobot robot;
	private String msg;
	private Arbitrator arby;
	static int blacklinecount = 0;
	
    GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
    
    BumperCarSimple car = new BumperCarSimple();
	
    // Make the monitor a daemon and set
    // the robot it monitors and the delay
    public PilotMonitor(PilotRobot r, int d, Arbitrator a){
    	this.setDaemon(true);
    	delay = d;
    	robot = r;
    	arby = a;
    	msg = "";
    	
    	for(int i = 0; i < PilotRobot.grid.length; i++) {
			for(int j = 0; j < PilotRobot.grid[0].length; j++) {
				if(i == 0 || i == PilotRobot.grid.length-1 || j == 0 || j == PilotRobot.grid[0].length-1) {
					PilotRobot.grid[i][j].setValue("X");
				}
				else PilotRobot.grid[i][j].setValue("0");
			}
		}
    }
    
    // Allow extra messages to be displayed
    public void resetMessage() {
    	this.setMessage("");
    }
    
    // Clear the message that is displayed
    public void setMessage(String str) {
    	msg = str;
    }

    // The monitor writes various bits of robot state to the screen, then
    // sleeps.
    public void run(){
    	// The decimalformat here is used to round the number to three significant digits
		DecimalFormat df = new DecimalFormat("####0.000");
    	while(true){
    		lcd.clear();
    		lcd.setFont(Font.getDefaultFont());
    		Navigate.drawGrid();
    		
    		//lcd.drawString("Robot Monitor", lcd.getWidth()/2, 0, GraphicsLCD.HCENTER);
    		 
//    		lcd.drawString("LColor: "+robot.getRightColourSensor(), 0, 20, 0);
//    		lcd.drawString("RColor: "+robot.getLeftColourSensor(), 0, 30, 0);

    		
    		if(robot.getCorrectBlackLines() == false) {
    			if (robot.getLeftColourSensor() == 7 || robot.getRightColourSensor() == 7) {
    				Sound.beep();
    				blacklinecount++;
    				Navigate.move();
    				while(robot.getLeftColourSensor() == 7 || robot.getRightColourSensor() == 7) {
    					try{
    		    			sleep(10);
    		    		}
    		    		catch(Exception e){
    		    			// We have no exception handling
    		    			;
    		    		}
    				}
    			} 
    		}
    		
    		if (PilotRobot.runMove == true) {
    			Navigate.move();
    			PilotRobot.runMove = false;
    		}
    		//lcd.drawString("Black Line Count "+blacklinecount, 0, 20, 0);
//    		
//    		lcd.drawString("Dist: "+robot.getDistance(), 0, 50, 0);  
//    		lcd.drawString("Angle: "+robot.getAngle(), 0, 60, 0);    		
//    		lcd.drawString("Correct value :  "+robot.getCorrectBlackLines(), 0, 70, 0);
//    		lcd.drawString("Behaviour"+robot.getBehavior(), 0, 80, 0);
//    		lcd.drawString("Suppressed "+ DriveForward.suppressed , 0, 90, 0);
    		
    	
//    		for(int i = 0; i < PilotRobot.grid.length; i++) {
//    			for(int j = 0; j < PilotRobot.grid.length; j++) {
//    				lcd.drawString(robot.grid[i][j], i*15 + 40, j*15, GraphicsLCD.HCENTER);
//    			}
//    		}
//    		
    			
    			
    		try{
    			sleep(delay);
    		}
    		catch(Exception e){
    			// We have no exception handling
    			;
    		}
    		
    		if(Button.ESCAPE.isDown()) {
    			System.exit(0);
    		}
    		
    		if(Button.LEFT.isDown()) {
    			arby.stop();
    		}
	    }
    	
    }
    

}