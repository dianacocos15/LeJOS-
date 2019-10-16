//// SimpleDriver.java
//// 
//// A simple code fragment  that illustrates the use of the EV3 motors,
//// based on the original code by Simon Parsons, 2013.
////
//// Terry Payne
//// 30th September 2017
////
//// This demo uses the Button class to determine when to start and stop
//// the motors, and then utilises the general Motor class to control each
//// motor.  The LCD class is also used to clear the screen, although
//// messages are sent using the System.out.println method.
//
//import lejos.hardware.Brick;
//import lejos.hardware.BrickFinder;
//import lejos.hardware.Button;
//import lejos.hardware.lcd.GraphicsLCD;
//import lejos.hardware.lcd.LCD;
//import lejos.hardware.motor.EV3LargeRegulatedMotor;
//
//public class SimpleDriver {
//
//	public static void main(String[] args) {
//		// Get the default instance of the Brick, to get access to its resources
//		Brick myEV3 = BrickFinder.getDefault();
//		GraphicsLCD lcd = myEV3.getGraphicsLCD();
//		EV3LargeRegulatedMotor motorL = new EV3LargeRegulatedMotor(myEV3.getPort("B"));
//		EV3LargeRegulatedMotor motorR = new EV3LargeRegulatedMotor(myEV3.getPort("D"));
//		
//		lcd.drawString("Press any button", 0, 20, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
//		lcd.drawString("to start", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
//        Button.waitForAnyPress();
//        LCD.clear();
//        motorL.setSpeed(400); 	//  Set the motor to rotate 720 degrees (i.e. 2 revolutions) per sec
//        motorR.setSpeed(400); 	//  Set the motor to rotate 720 degrees (i.e. 2 revolutions) per sec
//        motorL.forward();
//        motorR.forward();
//
//		lcd.drawString("Press ENTER", 0, 30, 0);
//		lcd.drawString("to stop", 0, 50, 0);
//		while (true) {
//	        Button.waitForAnyPress();
//	        if (Button.ENTER.isDown()) {
//	        	motorL.stop(true);
//	        	motorR.stop();
//	        	// Note that motor.stop() will only return after the motor has stopped
//	        	// (which may take a few hundredths of a millisecond)
//	        	// To avoid the method blocking and have it return immediately,
//	        	// use the alternative methods:
//	        	// 		motor.stop(true);
//	        	//		*do stuff unrelated to this motor*
//	        	//		motor.waitComplete()
//	        	// This is useful if the *do stuff* is, for example, stopping
//	        	// the other motor as well!
//	    		break;
//	        }
//		}
//		lcd.drawString("Left Tacho: "+motorL.getTachoCount(), 0, 80, 0);
//		lcd.drawString("Right Tacho: "+motorR.getTachoCount(), 0, 100, 0);
//		motorL.close();
//		motorR.close();
//        Button.waitForAnyPress();
//        lcd.clear();
//	}
//
//}