import java.io.*;
import java.net.*;


public class PCClient {
	public static void main(String[] args) throws IOException {
		String ip = "192.168.70.62"; 
		
		if(args.length > 0)
			ip = args[0];
		DataInputStream dIn;
		InputStream in;
		Socket sock;
		
		String str[][] = new String[8][9];
		
		
		while(true) {
			try {
				sock = new Socket(ip, 1234);
				System.out.println("Connected");
				in = sock.getInputStream();
				dIn = new DataInputStream(in);
				
				while (sock.isConnected()) {
					System.out.flush(); 
					for (int x = 0; x < 8; x++) {
						System.out.print("\n");
						for(int y = 0; y < 9; y++) {
							str[x][y] = dIn.readUTF();
							//System.out.print(str[x][y]);
						}
					}
					System.out.println("\n");
					System.out.println("\n");
					System.out.println("\n");
					System.out.println("\n");

					
					
					String position = dIn.readUTF();
					
					String yCoord = dIn.readUTF();
					String xCoord = dIn.readUTF();
					
					
					for (int x = 0; x < 9; x++) {
						System.out.print("\n");
						for(int y = 0; y < 8; y++) {
							if(x == Integer.parseInt(xCoord) && y == Integer.parseInt(yCoord)) {
								System.out.print(".");
							}
							else {
								System.out.print(str[y][x]);
							}
							System.out.print(" ");
						}
					}
					
					System.out.println(position);
					
					try{
		    			Thread.sleep(400);
		    		}
		    		catch(Exception e){
		    			// We have no exception handling
		    			;
		    		}
				}
			}catch(Exception e) {}
			
		    
		}
		
		//sock.close();
	}
}
