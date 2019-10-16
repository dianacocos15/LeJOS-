import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

// PilotRobot.java
// 
// Based on the SimpleRobot class, this provides access to the
// sensors, and constructs a MovePilot to control the robot.
//
// Terry Payne
// 1st October 2018
//

public class PilotRobot {
	//private EV3TouchSensor leftBump, rightBump;	
	private EV3UltrasonicSensor usSensor;
	private EV3GyroSensor gSensor;
	//private EV3MediumRegulatedMotor rMotor;
	private SampleProvider distSP, gyroSP, leftSP, rightSP; //leftSP, rightSP	
	private float[] distSample, angleSample, leftColorSample, rightColorSample; //removed leftSample, rightSample
	private MovePilot pilot;
	//private EV3LargeRegulatedMotor motorL, motorR;
	private EV3ColorSensor leftColor, rightColor;

	public PilotRobot() {
		Brick myEV3 = BrickFinder.getDefault();
		//Motor.C.rotate(90);
		
//		leftBump = new EV3TouchSensor(myEV3.getPort("S1"));
//		rightBump = new EV3TouchSensor(myEV3.getPort("S4"));
		usSensor = new EV3UltrasonicSensor(myEV3.getPort("S3"));
		gSensor = new EV3GyroSensor(myEV3.getPort("S2"));
		leftColor = new EV3ColorSensor(myEV3.getPort("S1"));
		rightColor = new EV3ColorSensor(myEV3.getPort("S4"));

		leftSP = leftColor.getRGBMode();
		rightSP = rightColor.getRGBMode();
		distSP = usSensor.getDistanceMode();
		gyroSP = gSensor.getAngleMode();
		
//		leftSample = new float[leftSP.sampleSize()];		// Size is 1
//		rightSample = new float[rightSP.sampleSize()];		// Size is 1
		distSample = new float[distSP.sampleSize()];		// Size is 1
		angleSample = new float[gyroSP.sampleSize()];	// Size is 1

		// Set up the wheels by specifying the diameter of the
		// left (and right) wheels in centimeters, i.e. 4.05 cm.
		// The offset number is the distance between the centre
		// of wheel to the centre of robot (4.9 cm)
		// NOTE: this may require some trial and error to get right!!!
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 4.05).offset(-4.9);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.D, 4.05).offset(4.9);
		
//		rMotor = new EV3MediumRegulatedMotor(myEV3.getPort("S3"));
//		rMotor.rotateTo(90, true);
		
		Chassis myChassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);

	    pilot = new MovePilot(myChassis);
	    
		// Reset the value of the gyroscope to zero
		gSensor.reset();
	}
	
	public void closeRobot() {
		leftColor.close();
		rightColor.close();
		usSensor.close();
		gSensor.close();
	}
	
	// Get the colour from the colour sensor
		public int getLeftColourSensor() {
	    	int left = leftColor.getColorID();
	    	return left;
		}
		
		// Get the colour from the colour sensor
		public int getRightColourSensor() {
	    	int right = rightColor.getColorID();
	    	return right;
		}

//	public boolean isLeftBumpPressed() {
//    	leftSP.fetchSample(leftSample, 0);
//    	return (leftSample[0] == 1.0);
//	}
//	
//	public boolean isRightBumpPressed() {
//    	rightSP.fetchSample(rightSample, 0);
//    	return (rightSample[0] == 1.0);
//	}
	
	public float getDistance() {
    	distSP.fetchSample(distSample, 0);
    	return distSample[0];
	}
	
//	// Get the colour from the colour sensor
//		public float[] getRightColourSensor() {
//	    	rightSP.fetchSample(rightColorSample, 0);
//	    	return rightColorSample;
//		}

	public float getAngle() {
    	gyroSP.fetchSample(angleSample, 0);
    	return angleSample[0];
	}
	
//	public void rotate(int angle) {
//		float beginAngle = getAngle();
//		pilot.rotate(angle);
//		float finalAngle = getAngle();
//		float diff = finalAngle - beginAngle;
//		pilot.rotate(diff);
//	}
	
	public MovePilot getPilot() {
		return pilot;
	}
}

//while left color is 7 and right color is not 7
//rotate towaREDS TH LEFT BY 5 DEGREES
//THEN WHILE left is not 7 and right is 7, rotate positive 5 degrees