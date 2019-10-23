import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
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
	static Cell[][] grid = new Cell[8][9];
	
	
	static final int ACCELERATION = 20;
	static final int DECELERATION = 100; 
	static final int DISTANCE = 100;
	static final int TOP_SPEED = 30;
	static final int ANGULAR_ACCELERATION = 40;
	static final double DISTANCE_FROM_THE_WALL = 0.07;
	static final int ROTATE_HEAD_LEFT = 90;
	static final int ROTATE_HEAD_RIGHT = -90;
	static final int ROTATE_HEAD_CENTER = 0;
	static final int ROTATE_ROBOT_RIGHT = 30;
	static final int ROTATE_ROBOT_LEFT = -30;
	static final int SAMPLE_SIZE = 300;
	static float average;
	static boolean runMove = false;
	static GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();


	
	private MovePilot pilot;	


	public PilotRobot() {
		Brick myEV3 = BrickFinder.getDefault();
		Navigate navigate = new Navigate(3,3, this);
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
		
		//Work ya bastud
		for(int i =0; i< grid.length; i ++ ) {
			for(int j=0;j<grid[0].length;j++) {
				grid[i][j] = new Cell();
			}
		}
		
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
	
	public void rotate(int value) {
		

		int initialAngle = (int)getAngle(); //gyroscope
		
		int finalAngle = (int)getAngle();
		int difference = (int)(finalAngle - initialAngle);
		
		while (value != difference) {
			finalAngle = (int)getAngle();
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
	
	public static float getTravelDistance() {
		float numberOfRevolutionsB = Motor.B.getTachoCount();
		float numberOfRevolutionsD = Motor.D.getTachoCount();
		
		float avgRevolutions = (numberOfRevolutionsB + numberOfRevolutionsD)/2;
		
		float distance = (float)(Math.PI * 4.05 * avgRevolutions);
		
		return distance;
	}
	
	public void resetTachoCount() {
		Motor.B.resetTachoCount();
		Motor.D.resetTachoCount();	
	}
	
	public float distanceSample() {
		float currentDistance = getDistance();
		average = 0;
		float[] distances = new float[SAMPLE_SIZE];
		int m = 0;
		float sum = 0;
		while (m < distances.length) {
			if(currentDistance > 0 && currentDistance != Float.POSITIVE_INFINITY) {
				distances[m] = getDistance();
				m++;
			}
		}
		
		for (float d : distances) {
			sum += d;
		}
		
		average = sum/distances.length;
		

		return average;
	}	
}

//while left color is 7 and right color is not 7
//rotate towaREDS TH LEFT BY 5 DEGREES
//THEN WHILE left is not 7 and right is 7, rotate positive 5 degrees