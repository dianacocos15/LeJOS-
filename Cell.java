public class Cell {
	private String value = "";
	
	public Cell() {
		value = "0";
	}
	
	public String getValue() {
		return value;
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
	
	public String toString() {
		return value+"";
	}
}
