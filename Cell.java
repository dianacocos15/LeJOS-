public class Cell {
    private String value = "";
    private double M = 0.0; // Total times observed
    private double C = 0.0; // Total times detected

    private int x;
    private int y;

    public Cell() {
        value = "0";
    }

    public String getValue() {
        return value;
    }

    public void incrementEmptyCell() {
        // IF VALUE IS NOT X THEREBY NOT A WALL
        C = C+1;
    }

    //if m only gets incremented - use josh
    //if m both incr and decr - use slides

    public void incrementOccupiedCell() {
        // IF VALUE IS NOT X
        M = M + (1 - Travel.numberOfMoves*0.05);
        C = C+1;
    }

    public double returnProbability() {
        //probability before  + 1 - count*0.05

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
}