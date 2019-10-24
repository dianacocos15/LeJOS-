public class Cell {
	private String value = "";
	private double M = 0.0; // Total times observed
	private double C = 0.0; // Total times detected

	
	//counter for number of moves so far
	
	
	
	public Cell() {
		value = "0";
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
