public class Cell implements Cloneable {
	private String value = "";
	private double M = 0.0; // Total times observed
	private double C = 0.0; // Total times detected

	private int x;
	private int y;
	
	//counter for number of moves so far
	
	
	
	public Cell(int x, int y) {
		this.x =x;
		this.y =y;
		value = "0";
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public Cell clone()throws CloneNotSupportedException{  
		return (Cell)super.clone();  
	 }
	
	public String getValue() {
		return value;
	}
	
	public void incrementEmptyCell() {
		// IF VALUE IS NOT X THEREBY NOT A WALL
		M = M-1;
		C = C+1;
	}
	
	public void incrementOccupiedCell() {
		// IF VALUE IS NOT X
		M = M+1;
		C = C+1;
	}
	
	public double returnProbability() {
		// IF VALUE VARIABLE NOT EQUAL TO STRING X
		return (C/M)*100;
	}
	
	public void setValue(String newValue) {
		value = newValue;
	}
	
	public void incrementCell() {
		if (!("X".equals(value))) { 
			int intValue = Integer.parseInt(value);
			intValue++;
			value = "" + intValue;
		}
		
	}
	
	public int toInteger() {
		if (!("X".equals(value))) { 
			return Integer.parseInt(value);
		}
		else return 100;
	}
}
