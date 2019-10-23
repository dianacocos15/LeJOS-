// BumperCarSimple.java
// 
// A simple application that uses the Subsumption architecture to create a
// bumper car, that drives forward, and changes direction given a collision.
//
// Terry Payne
// 1st October 2018
//


//new behavior
//when color sensors are both black drive forward for 3cm

import lejos.hardware.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BumperCarSimple {

	public static void main(String[] args) {
		PilotRobot me = new PilotRobot();			
		
		 try{
				Thread.sleep(1000);
			}
			catch(Exception e){
				// We have no exception handling
				;
			}
		
		
		// Set up the behaviours for the Arbitrator and construct it.
		Behavior b1 = new DriveForward(me);
		Behavior b2 = new BackUp(me);
		Behavior b3 = new LeftColor(me);
		Behavior b4 = new RightColor(me);
		//Behavior b5 = new BothColors(me);
		//Behavior b6 = new Navigate(me);
	
		Behavior [] bArray = {b1, b4, b3, b2};
		Arbitrator arby = new Arbitrator(bArray);
		PilotMonitor myMonitor = new PilotMonitor(me, 50, arby);
		EV3Server ev3server = new EV3Server();
		
		
		// Note that in the Arbritrator constructor, a message is sent
		// to stdout.  The following prints eight black lines to clear
		// the message from the screen
        for (int i=0; i<8; i++)
        	System.out.println("");

        // Start the Pilot Monitor
		myMonitor.start();
		ev3server.start();

		// Tell the user to start
		myMonitor.setMessage("Press a key to start");				
        Button.waitForAnyPress();
        
        //Prevent eroneous situation where sensor detects objects on initialisation
       
        
        
        
        // Start the Arbitrator
		arby.go();
	}
}