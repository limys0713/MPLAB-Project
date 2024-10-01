package rr.rr1223;

import gamecode.code.*;

import gameteris.Tetris;
//import plane.GamePlane;
import plane.GamePlane;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class main {

//	public static void main(String[] args) {
//		
//		
////		final Tetris tetris = new Tetris();
////		final GamePlane gamePlane = new GamePlane();
////		GameFoodie gameFoodie = new GameFoodie();
////		GameCode gamecode = new GameCode();
////		
////		
////	
////		
////		 
////		
////		int BaudRate = 1200;
////		int DataBits = 8;
////		int StopBits = SerialPort.ONE_STOP_BIT;
////		int Parity = SerialPort.NO_PARITY;
////		
////		//find available ports 
////		SerialPort[] AvailablePorts = SerialPort.getCommPorts();
////		for(int i = 0; i < AvailablePorts.length; i++) {
////			System.out.print(AvailablePorts[i].getSystemPortName()+"\n");
////		}
////		//Select a port
////		final SerialPort MySerialPort = AvailablePorts[0];//COM3
////		System.out.println("Current Pot: "+MySerialPort.getSystemPortName());
////		//Set Serial port parameters
////		MySerialPort.setComPortParameters(BaudRate, DataBits, StopBits, Parity);
////		MySerialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
////		MySerialPort.openPort();
////		if (MySerialPort.isOpen())//Check whether port open/not
////			System.out.println("is Open "+"\n");
////		else
////			System.out.println(" Port not open "+"\n");
////		
////	
////		
////		MySerialPort.addDataListener(new SerialPortDataListener() {
////		   @Override
////		   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
////		   @Override
////		   public void serialEvent(SerialPortEvent event)
////		   {
////		      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
////		         return;
////
////		      
////		      byte[] readBuffer = new byte[1];
////		      
////		      int numRead = MySerialPort.readBytes(readBuffer, readBuffer.length);
////		      //System.out.println("Read " + numRead + " bytes.");
////		    //Convert bytes to String
////		      if(readBuffer.length > 0) {
////		    	  String S = null;
////				try {
////					S = new String(readBuffer, "UTF-8");
////					if("w".equals(S)) {
////		    			GameImage.UP = true;
////		    			Panel.direction = "U";
////		    			tetris.up();
////		    			gamePlane.planeObj.up();
////		    		}
////		    		if("s".equals(S)) {
////		    			GameImage.DOWN = true;
////		    			Panel.direction = "D";
////		    			tetris.down();
////		    			gamePlane.planeObj.down();
////		    		}
////		    		if("a".equals(S)) {
////		    			GameImage.LEFT = true;
////		    			Panel.direction = "L";
////		    			tetris.left();
////     	    			gamePlane.planeObj.left();
////		    		}
////		    		if("d".equals(S)) {
////		    			GameImage.RIGHT = true;
////		    			Panel.direction = "R";
////		    			tetris.right();
////		    			gamePlane.planeObj.right();
////		    		}
////		    		if("t".equals(S)) {
////		    			GameImage.UP = false;
////		    			GameImage.DOWN = false;
////		    			GameImage.LEFT = false;
////		    			GameImage.RIGHT = false;
////		    		}
////					
////				} catch (UnsupportedEncodingException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////					if(!"t".equals(S)) {
////						System.out.println("Received -> "+ S);
////					}
////		      }
////		   }
////		});
////		
////		
////		
////		
////		waitingScreen.setVisible(true);
////	 	waitingScreen.setLocationRelativeTo(null);
////		while(true){
////		//	System.out.println("playing: "+playing+",next_playing: "+next_playing);
////			if(playing != next_playing){
////				playing = next_playing;
////				waitingScreen.setVisible(false);
////				if(playing != 0){
////					switch (playing) {
////						case 1:
////							//GameFoodie gameFoodie = new GameFoodie();	
////							gameFoodie.launch();
////							
////							gameFoodie.closeWindow();
////							gameFoodie.closeBGM();
////							gameFoodie = null;
////														
////							next_playing = 0;
////							break;
////						case 2:
////							//GamePlane gamePlane = new GamePlane();	
////							tetris.launch();
////							
////							next_playing = 0;
////							break;
////						case 3:
////							//GamePlane gamePlane1 = new GamePlane();	
////							gamePlane.launch();
////							next_playing = 0;
////							break;
////						case 4:
////							//GamePlane gamePlane2 = new GamePlane();	
////							gamecode.launch();
////							gamecode.setVisible(false);
////							next_playing = 0;
////							break;			
////						default:
////							break;
////					}
////				}else{
////					waitingScreen.setVisible(true);
////				}	
////			}
////			
////		}
////		
////		
////		//gameFoodie.launch();
////
////		
////		//gamePlane.launch();
////		
////	
////		
////		
//////		gameFoodie.launch();		
//////		if(GameFoodie.isComplete) {
//////			try {
//////				Thread.sleep(5000);
//////				gameFoodie.closeBGM();
//////			} catch (InterruptedException e1) {
//////				e1.printStackTrace();
//////			}
//////
//////			
////			gameFoodie.closeWindow();
////			gameFoodie.closeBGM();
////			gameFoodie = null;
//////
//////		}
//////		gamePlane.launch();
////		
////		gamecode.launch();
////		if(Panel.isComplete) {
////			gamecode.setVisible(false);
//////		}
////		
////
////		
////		
////		tetris.launch();
////		tetris.setVisible(false);
////		
//		//tetris.launch();
//		System.out.print("FFFFFFFFFIIIIIIIIINNNNNNNNNN");
//	
//		
//		
//		
//	
//	}
//	
}