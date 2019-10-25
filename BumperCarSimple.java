import java.util.ArrayList;
import java.util.List;

public class BumperCarSimple {

    public static void main(String[] args) {
        PilotRobot me = new PilotRobot();

        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            // We have no exception handling
            ;
        }


        // Set up the behaviours for the Arbitrator and construct it.
        Behavior b1 = new DriveForward(me);
        //Behavior b2 = new BackUp(me);
        Behavior b3 = new LeftColor(me);
        Behavior b4 = new RightColor(me);

        //Behavior b5 = new BothColors(me);
        //Behavior b6 = new Navigate(me);
        Cell[][] newGrid;
        try {
            newGrid = PilotRobot.getGridCopy();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            newGrid = PilotRobot.grid;
        }

        AStar astar = new AStar(newGrid, Navigate.j, Navigate.i);
        List<AStar.Node> n = astar.runAlgorithm(Navigate.j, Navigate.i, PilotRobot.finalGoaly, PilotRobot.finalGoalx);
        PilotRobot.list = n;

        ArrayList<Cell> stopsAlongTheWay = new ArrayList<>(6);
        stopsAlongTheWay.add(PilotRobot.grid[1][1]);
        stopsAlongTheWay.add(PilotRobot.grid[1][6]); //ask about x and y
        stopsAlongTheWay.add(PilotRobot.grid[4][6]);
        stopsAlongTheWay.add(PilotRobot.grid[4][1]);
        stopsAlongTheWay.add(PilotRobot.grid[7][1]);
        stopsAlongTheWay.add(PilotRobot.grid[7][6]);

        //LCD.drawString("" + n.size(), 20, 20);
        //LCD.drawString("" + PilotRobot.listIndex, 20, 20);



        Behavior b5 = new nextCoordinate(me, n);

        Behavior [] bArray = {b1, b4, b3, b5};
        Arbitrator arby = new Arbitrator(bArray);
        PilotMonitor myMonitor = new PilotMonitor(me, 50, arby);
        EV3Server ev3server = new EV3Server();


        // Note that in the Arbritrator constructor, a message is sent
        // to stdout.  The following prints eight black lines to clear
        // the message from the screen
        for (int i=0; i<8; i++)
            System.out.println("");

        // Start the Pilot Monitor
        myMonitor.start();
        ev3server.start();

        // Tell the user to start
        myMonitor.setMessage("Press a key to start");
        Button.waitForAnyPress();

        //Prevent erroneous situation where sensor detects objects on initialisation




        // Start the Arbitrator
        arby.go();
    }
}