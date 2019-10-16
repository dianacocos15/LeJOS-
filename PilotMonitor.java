import java.text.DecimalFormat;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;

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
	
    GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
	
    // Make the monitor a daemon and set
    // the robot it monitors and the delay
    public PilotMonitor(PilotRobot r, int d){
    	this.setDaemon(true);
    	delay = d;
    	robot = r;
    	msg = "";
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
    		lcd.drawString("Robot Monitor", lcd.getWidth()/2, 0, GraphicsLCD.HCENTER);
    		lcd.setFont(Font.getSmallFont());
    		 
    		lcd.drawString("LColor: "+robot.getRightColourSensor(), 0, 20, 0);
    		lcd.drawString("RColor: "+robot.getLeftColourSensor(), 0, 30, 0);
    		lcd.drawString("Dist: "+robot.getDistance(), 0, 40, 0);  
    		lcd.drawString("Angle: "+robot.getAngle(), 0, 50, 0);    		

    		// Note that the following exploit additional information available from the
    		// MovePilot.  This could be extended to include speed, angular velocity, pose etc.
    		lcd.drawString("Motion: "+robot.getPilot().isMoving(), 0, 60, 0);
    		lcd.drawString("  type: "+robot.getPilot().getMovement().getMoveType(), 0, 70, 0);
    		lcd.drawString(msg, 0, 100, 0);    		

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
	    }
    }
    

}