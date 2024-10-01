package rr.rr1223;

import java.io.*;
import java.net.*;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import gamecode.code.GameCode;
import gamecode.code.Panel;
import gameteris.Tetris;
import plane.GamePlane;
import rr.rr1223.GameFoodie;
import rr.rr1223.GameImage;

public class PlayerSocket {
	public static String[] name_scores = {"NO DATA","NO DATA","NO DATA"};
	public static String[] playerProgress = new String[3];
	public static String playerName = "No Name";
	
    //player state
    public static int NOT_JOINED = 0;
    public static int JOINED = 1;
    public static int HOME_SCREEN = 2;
	public static int GAMIMG = 3;
	public static int SCORE_SCREEN = 4;
	public static int FINISH = 5;

    static int playerState = NOT_JOINED;
    static int currentGame = 1;
	static private String tempScore;
	static private boolean gameOver = false;
    
    //create init window
    static WaitingScreen waitingScreen = new WaitingScreen();
    static String msg = "";   
    
    //signal
    public static boolean sentToServer = false;
    public static boolean receiveFromServer = false;
    public static boolean changePage = true;

	public static int playing = 0;
	public static int next_playing = 0;

	public static void main(String[] args) {
	final Tetris tetris = new Tetris();
	final GamePlane gamePlane = new GamePlane();
	final GameFoodie gameFoodie = new GameFoodie();
	final GameCode gamecode = new GameCode();
	
	

	
	 
	
	int BaudRate = 1200;
	int DataBits = 8;
	int StopBits = SerialPort.ONE_STOP_BIT;
	int Parity = SerialPort.NO_PARITY;
	
	//find available ports 
	SerialPort[] AvailablePorts = SerialPort.getCommPorts();
	for(int i = 0; i < AvailablePorts.length; i++) {
		System.out.print(AvailablePorts[i].getSystemPortName()+"\n");
	}
	//Select a port
	final SerialPort MySerialPort = AvailablePorts[0];//COM3
	System.out.println("Current Pot: "+MySerialPort.getSystemPortName());
	//Set Serial port parameters
	MySerialPort.setComPortParameters(BaudRate, DataBits, StopBits, Parity);
	MySerialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
	MySerialPort.openPort();
	if (MySerialPort.isOpen())//Check whether port open/not
		System.out.println("is Open "+"\n");
	else
		System.out.println(" Port not open "+"\n");
	

	
	MySerialPort.addDataListener(new SerialPortDataListener() {
	   @Override
	   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
	   @Override
	   public void serialEvent(SerialPortEvent event)
	   {
	      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
	         return;

	      
	      byte[] readBuffer = new byte[1];
	      
	      int numRead = MySerialPort.readBytes(readBuffer, readBuffer.length);
	      //System.out.println("Read " + numRead + " bytes.");
	    //Convert bytes to String
	      if(readBuffer.length > 0) {
	    	  String S = null;
			try {
				S = new String(readBuffer, "UTF-8");
				
				
				if("w".equals(S)) {
	    			GameImage.UP = true;
	    			Panel.direction = "U";
	    			tetris.up();
	    			gamePlane.planeObj.up();
	    		}
	    		if("s".equals(S)) {
	    			GameImage.DOWN = true;
	    			Panel.direction = "D";
	    			tetris.down();
	    			gamePlane.planeObj.down();
	    		}
	    		if("a".equals(S)) {
	    			GameImage.LEFT = true;
	    			Panel.direction = "L";
	    			tetris.left();
 	    			gamePlane.planeObj.left();
	    		}
	    		if("d".equals(S)) {
	    			GameImage.RIGHT = true;
	    			Panel.direction = "R";
	    			tetris.right();
	    			gamePlane.planeObj.right();
	    		}
	    		if("t".equals(S)) {
	    			GameImage.UP = false;
	    			GameImage.DOWN = false;
	    			GameImage.LEFT = false;
	    			GameImage.RIGHT = false;
	    		}
	    		if("x".equals(S)) {
	    			try {
						Thread.sleep(100);
					} catch (InterruptedException e){
						e.printStackTrace();
					}
	    			switch (playing) {
					case 1:
						//GameFoodie gameFoodie = new GameFoodie();	
						gameFoodie.stop();
						break;
					case 2:	
						//tetris.launch();

						break;
					case 3:
						//gamePlane.launch();
						gamePlane.stop();
						break;
					case 4:							
						//gamecode.launch();
						gamecode.stop();
						break;			
					default:
						break;
				}
	    			//S = null;
	    		}
	    		
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				if(!"t".equals(S)) {
					System.out.println("Received -> "+ S);
				}
	      }
	   }
	});
		
		
		
		
	waitingScreen.setVisible(true);
 	waitingScreen.setLocationRelativeTo(null);
	while(true){
		System.out.println("playing: "+playing+",next_playing: "+next_playing);
		if(playing != next_playing){
			playing = next_playing;
			waitingScreen.setVisible(false);
			if(playing != 0){
				switch (playing) {
					case 1:
						//GameFoodie gameFoodie = new GameFoodie();	
						gameFoodie.launch();
						//gameFoodie.closeWindow();
						while(!gameFoodie.isComplete);
						//gameFoodie.closeWindow();
						//gameFoodie.closeBGM();
						//gameFoodie = null;
													
						next_playing = 0;
						break;
					case 2:
						//GamePlane gamePlane = new GamePlane();	
						tetris.launch();
						next_playing = 0;
						break;
					case 3:
						//GamePlane gamePlane1 = new GamePlane();	
						gamePlane.launch();
						gamePlane.setVisible(false);
						
						next_playing = 0;
						break;
					case 4:
						//GamePlane gamePlane2 = new GamePlane();	
						gamecode.launch();
						gamecode.setVisible(false);
						while(!Panel.isComplete);
						next_playing = 0;
						break;			
					default:
						break;
				}
			}else{
				waitingScreen.setVisible(true);
			}	
		}
		
	}
	
	}//end of main
}//end of class
