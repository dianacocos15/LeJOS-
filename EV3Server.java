import java.io.*;
import java.net.*;

public class EV3Server extends Thread {
	
public static final int port = 1234;
	static DataOutputStream dOut;
	static ServerSocket server;
	
	
	public void run(){
		try {
			server = new ServerSocket(port);
			//System.out.println("Awaiting client..");
			Socket client = server.accept();
			//System.out.println("CONNECTED");
			OutputStream out = client.getOutputStream();
			dOut = new DataOutputStream(out);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			for (int x = 0; x < PilotRobot.grid.length; x++) {
				for(int y = 0; y < PilotRobot.grid[x].length; y++) {
					try {
						double holder = PilotRobot.grid[x][y].returnProbability();
						String probability = String.valueOf(holder);
						dOut.writeUTF(probability);
						//dOut.writeUTF(PilotRobot.grid[x][y].getValue());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			try {
				dOut.writeUTF(Navigate.getOrientationAsString());
				//dOut.writeUTF(Navigate.getOrientation());
				dOut.writeUTF(Navigate.currentPosition());
				dOut.writeUTF(Navigate.getX());
				dOut.writeUTF(Navigate.getY());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				dOut.flush();
				server.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			try{
				sleep(400);
			}
			catch(Exception e){
				e.printStackTrace();
				;
			}
		}
	}
}
