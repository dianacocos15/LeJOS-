import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import lejos.hardware.lcd.Font;

import java.net.*;


public class PCClient {
	public static void main(String[] args) throws IOException {
		String ip = "192.168.70.66"; 
		String dot = ".";
		
		
		if(args.length > 0)
			ip = args[0];
		DataInputStream dIn;
		InputStream in;
		Socket sock;
		
		 JFrame frame = new JFrame("Robot Navigation Grid");
	     JTable table = new JTable(8,9);
		
	     String str[][] = new String[8][9];
				
		 table.setRowHeight(100);
		 table.setFont(new java.awt.Font("", 1, 42));
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(1000,1000);
	     frame.getContentPane().add(table); 
	     frame.setVisible(true);
	     
	     
	     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
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
							//str[x][y] = dIn.readUTF();
							table.getColumnModel().getColumn(y).setCellRenderer(centerRenderer);
							table.setValueAt(str[x][y], x, y);
							str[x][y] = dIn.readUTF();
						}
					}
					System.out.println("\n");
					System.out.println("\n");
					System.out.println("\n");
					System.out.println("\n");	
					
					String orientation = dIn.readUTF();
					
					String position = dIn.readUTF();
					
					String yCoord = dIn.readUTF();
					String xCoord = dIn.readUTF();
					
					
					for (int x = 0; x < 9; x++) {
						System.out.print("\n");
						for(int y = 0; y < 8; y++) {
							if(x == Integer.parseInt(xCoord) && y == Integer.parseInt(yCoord)) {
								table.setValueAt(orientation, x, y);
							}
							else {
								System.out.print("\nCurrent Pos : " + str[y][x]);
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