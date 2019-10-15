// BumperCarSimple.java
// 
// A simple application that uses the Subsumption architecture to create a
// bumper car, that drives forward, and changes direction given a collision.
//
// Terry Payne
// 1st October 2018
//

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.RangeFinder;
import lejos.robotics.RegulatedMotor;

public class BumperCarSimple {
	private static EV3MediumRegulatedMotor motorC;

	public static void main(String[] args) throws InterruptedException {
		PilotRobot me = new PilotRobot();		
		PilotMonitor myMonitor = new PilotMonitor(me, 300);	

		// Set up the behaviours for the Arbitrator and construct it.
		Behavior b1 = new DriveForward(me);
		Behavior b2 = new BackUp(me);
		Behavior [] bArray = {b1, b2};
		Arbitrator arby = new Arbitrator(bArray);
		//Brick myEV3 = BrickFinder.getDefault();
		//motorC = new EV3MediumRegulatedMotor(myEV3.getPort("C"));

		// Note that in the Arbritrator constructor, a message is sent
		// to stdout.  The following prints eight black lines to clear
		// the message from the screen
        for (int i=0; i<8; i++)
        	System.out.println("");

        // Start the Pilot Monitor
		myMonitor.start();

		// Tell the user to start
		myMonitor.setMessage("Press a key to start");				
        Button.waitForAnyPress();
        
        //ultrasonic sensor
      	//motorC.rotate(90);
      		
        // Start the Arbitrator
		arby.go();
		}
	}
