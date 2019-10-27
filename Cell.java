public class Cell implements Cloneable {
	private String value = "";
	private static double M = 0.0; // Total times observed
	private static double C = 0.0; // Total times detected

	public Cell() {
		value = "0";
	}
	
	public Cell clone()throws CloneNotSupportedException{  
		return (Cell)super.clone();  
	 }
	
	public String getValue() {
		return value;
	}
	
	public static void incrementEmptyCell() {
		// IF VALUE IS NOT X THEREBY NOT A WALL
		C = C+1;
	}
	
	public static void incrementOccupiedCell() {
		// IF VALUE IS NOT X
		M = M + (1 - Travel.numberOfMoves*0.05);
		C = C+1;
	}
	
	public float returnProbability() {
		// IF VALUE VARIABLE NOT EQUAL TO STRING X
		if(value.equalsIgnoreCase("X")) {
			return (float)100;
		}else{
			if(M == 0) {
				return (float)0;
			}else {
				return (float) ((C/M)*100);
			}
		}
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
