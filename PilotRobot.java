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
	//sensors
	private EV3UltrasonicSensor usSensor;
	private EV3GyroSensor gSensor;
	private EV3ColorSensor leftColor, rightColor;
	
	private SampleProvider distSP, gyroSP, leftSP, rightSP; //leftSP, rightSP	
	private float[] distSample, angleSample, leftColorSample, rightColorSample; //removed leftSample, rightSample
	private boolean correct_at_black_line = false;
	private String whichBehavior;
	boolean correct_head_turn;
	boolean rotateAction;
	
	static int correctionIncrementCount = 0;
	static char[] direction = {'N', 'E', 'S', 'W'};
	static String[][] grid = new String[8][9];
	
	
	static final int ACCELERATION = 20;
	static final int DECELERATION = 100; 
	static final int DISTANCE = 100;
	static final int TOP_SPEED = 30;
	static final int ANGULAR_ACCELERATION = 40;
	static final double DISTANCE_FROM_THE_WALL = 0.07;
	static final int ROTATE_HEAD_RIGHT = 90;
	static final int ROTATE_HEAD_LEFT = -90;
	static final int ROTATE_HEAD_CENTER = 0;
	static final int ROTATE_ROBOT_RIGHT = 30;
	static final int ROTATE_ROBOT_LEFT = -30;
	

	
	private MovePilot pilot;	


	public PilotRobot() {
		Brick myEV3 = BrickFinder.getDefault();
		//Motor.C.rotate(90);
		
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

		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 4.05).offset(-4.9);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.D, 4.05).offset(4.9);
		
		
		Chassis myChassis = new WheeledChassis( new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);

	    pilot = new MovePilot(myChassis);
	    pilot.setAngularAcceleration(ANGULAR_ACCELERATION);
	    pilot.setLinearSpeed(TOP_SPEED);
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
	
	public float getDistance() {
    	distSP.fetchSample(distSample, 0);
    	return distSample[0];
	}

	public float getAngle() {
    	gyroSP.fetchSample(angleSample, 0);
    	return angleSample[0];
	}
	
	public MovePilot getPilot() {
		return pilot;
	}
	
	public void rotate(double value) {
		

		double initialAngle = getAngle(); //gyroscope
		
		double finalAngle = getAngle();
		double difference = finalAngle - initialAngle;
		
		while (value != difference) {
			finalAngle = getAngle();
			difference = finalAngle - initialAngle;
			pilot.rotate(value - difference);
			
		}
		setCorrectBlackLines(false);
		
	}
	
	//Rotate head of the ultrasound sensor
	public void rotateHead(int position) {
		Motor.C.rotateTo(position);
	}
	
	public void setCorrectBlackLines(boolean value) {
		pilot.setAngularAcceleration(100);
		correct_at_black_line = value;
		pilot.setAngularAcceleration(ANGULAR_ACCELERATION);
	}
	
	public boolean getCorrectBlackLines() {
		return correct_at_black_line;
	}
	
	public void setBehavior(String behavior) {
		whichBehavior = behavior;
	}
	
	public String getBehavior() {
		return whichBehavior;
	}
	
}

//while left color is 7 and right color is not 7
//rotate towaREDS TH LEFT BY 5 DEGREES
//THEN WHILE left is not 7 and right is 7, rotate positive 5 degrees