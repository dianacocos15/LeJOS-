import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 
public class AStar {
    private final List<Node> open; //list of unexpanded neighbours
    private final List<Node> closed; //list of expanded neighbours
    private final List<Node> path;
    private final Cell[][] maze;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    //private final boolean diag; //false
 
    // Node class for convienience
    static class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;
        Node(Node parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
       }
       // Compare by f value (g + h)
       @Override
       public int compareTo(Object o) {
           Node that = (Node) o;
           return (int)((this.g + this.h) - (that.g + that.h));
       }
   }
 
    AStar(Cell[][] maze, int xstart, int ystart) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
    }
    /*
    ** Finds path to xend/yend or returns null
    **
    ** @param (int) xend coordinates of the target position
    ** @param (int) yend
    *
    ** @return (List<Node> | null) the path
    */
    public List<Node> findPathTo(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now); //add current node to closed list because we expanded it
        addNeigborsToOpenList(); //add neighbours of current node to open list for further potential expansion
        
        /*
         *	while the current position of the node is not the destination 
         * 	if the open list if empty
         * 	return a null list because there are no neighbours to visit
         */
        while (this.now.x != this.xend || this.now.y != this.yend) { 
            if (this.open.isEmpty()) { 
                return null;
            }
            
            //get the first node of the open list - the lowest F value (lowest H + G)
            this.now = this.open.get(0); // get first node (lowest f score)
            
            //remove the node with the lowest F value (first node)
            this.open.remove(0); 
            
            //add this node to the closed list because we expanded it
            this.closed.add(this.now); // and add to the closed
            
            //
            addNeigborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }
    /*
    ** Looks in a given List<> for a node
    **
    ** @return (bool) NeightborInListFound
    */
    private static boolean findNeighborInList(List<Node> array, Node node) {
        //return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
        
    	for(Node e: array) {
    		if(e.x == node.x && e.y == node.y) {
    			return true;
    		}
    	}
    	
    	return false;     		
    }
    /*
    ** Calulate distance between this.now and xend/yend
    **
    ** @return (int) distance
    */
    private double distance(int dx, int dy) {
//        if (this.diag) { // if diagonal movement is alloweed
//            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend); // return hypothenuse
//        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); // else return "Manhattan distance"
   //     }
    }
    private void addNeigborsToOpenList() {
        Node node;
        
        //check each neighbour of the current node
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
            	
            	//check diagonal
            	//if diagonal is true and we're not checking the current node this particular iteration
//                if (!this.diag && x != 0 && y != 0) {
//                	//
//                    continue; // skip if diagonal movement is not allowed
//                }
                
                if (x != 0 && y != 0) {
                	//
                    continue; // skip if diagonal movement is not allowed
                }
                
                
                //the following node is an instance of a neighbour
                //G = from start to current node
                //distance from current node(neighbour) to destination
                //this calculates the distance from a neighbour to destination - MANHATTAN DISTANCE
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                
                //if not current node
                //and neighbour on x coordinate is not a boundary
                //and neighbour on y coordinate is not boundary
                //and if cell was not visited (is part of path)
                //neighbour was not found in closed list
                if ((x != 0 || y != 0) // not this.now
                    && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length // check maze boundaries
                    && this.now.y + y >= 0 && this.now.y + y < this.maze.length
                    && this.maze[this.now.y + y][this.now.x + x].toInteger() != -1 
                    && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // if not already done
                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                        node.g += maze[this.now.y + y][this.now.x + x].toInteger(); // add movement cost for this square
 
                        // diagonal cost = sqrt(hor_cost² + vert_cost²)
                        // in this example the cost would be 12.2 instead of 11
                        /*
                        if (diag && x != 0 && y != 0) {
                            node.g += .4;	// Diagonal movement cost = 1.4
                        }
                        */
                        
                        //add node to open list
                        this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }
 
    //public static void main(String[] args) {
        // -1 = blocked
        // 0+ = additional movement cost
//        int[][] maze = {
//            {  0,  0,  0,  0,100,  0,  0,  0},
//            {  0,  0,  0,  0,  0,  0,  0,  0},
//            {  0,  0,  0,100,100,100,  0,  0},
//            {  0,  0,  0,  0,  0,100,  0,  0},
//            {  0,  0,100,  0,  0,100,  0,  0},
//            {  0,  0,100,  0,  0,100,  0,  0},
//            {  0,  0,100,100,100,100,  0,  0},
//            {  0,  0,100,  0,  0,  0,  0,  0},
//        };
    
    public List<Node> runAlgorithm(int xstart, int ystart, int xend, int yend) {
    
        AStar as = new AStar(maze, xstart, ystart);
        List<Node> path = as.findPathTo(xend, yend);
        if (path != null) {
        	for(Node n: path) {
        		System.out.print("[" + n.x + ", " + n.y + "] ");
        		maze[n.y][n.x].setValue("-1");
        	}
            System.out.printf("\nTotal cost: %.02f\n", path.get(path.size() - 1).g);
 
            for (Cell[] maze_row : maze) {
                for (Cell maze_entry : maze_row) {
                    switch (maze_entry.toString()) {
                        case "0":
                            System.out.print("_");
                            break;
                        case "-1":
                            System.out.print("*");
                            break;
                        default:
                            System.out.print("#");
                    }
                }
                System.out.println();
            }
            return path;
        }
        	return null;
    }
}