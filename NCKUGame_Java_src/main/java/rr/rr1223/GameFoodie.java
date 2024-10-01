package rr.rr1223;
import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;


public class GameFoodie extends JFrame{
	public static boolean isComplete = false;
	public static boolean isStop = true;
	
	public static int nextLevel = 10;
	//state 
	public static int state = 0;
	Clip bgm = null;
	Image offScreenImage;
	
	private int width = 1100;
	private int height = 800;

	private double random;
	
	private int repeatCount = 0;
	
	
	
	Background bg = new Background();
	
	Restaurant rest;
	
	Eater eater = new Eater();
	
	
	public void launch() {
		loadBGM("GoodSong/veryGoodSong.wav");
		playBGM();
		//this.setFocusable(true);
	    this.setVisible(true);
		this.setSize(width, height);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		showRules();
		this.addWindowListener(new WindowAdapter() {
			
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                	//PlayerSocket.next_playing = 0;
                	isComplete = true;
                	closeBGM();
                    dispose();
                    
                }
            }
        });
	    
	    
	    //滑鼠點擊
	    this.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e) {
	    		super.mouseClicked(e);
	    		if(e.getButton() == 1 && state == 0) {
	    			state = 1;
	    			repaint();
	    		}
	    	}	    	
	    });
	    //MOVE listener
	    this.addKeyListener(new KeyAdapter() {
	    	
	    	public void keyPressed(KeyEvent e) {
	    		super.keyPressed(e);
//	    		if(e.getKeyCode() == 38) {
//	    			GameImage.UP = true;
//	    		}
//	    		if(e.getKeyCode() == 40) {
//	    			GameImage.DOWN = true;
//	    		}
//	    		if(e.getKeyCode() == 37) {
//	    			GameImage.LEFT = true;
//	    		}
//	    		if(e.getKeyCode() == 39) {
//	    			GameImage.RIGHT = true;
//	    		}
	    		if(e.getKeyCode() == 32) {
	    			switch(state) {
	    				case 1:
	    				case 3:
	    					stopBGM();
	    					isStop = true;
	    					GameImage.drawWord(getGraphics(), "Press Space to Continue", Color.blue, 50, 250, 440);
	    					state = 4;
	    					break;
	    				case 4:
	    					playBGM();
	    					isStop = false;
	    					if(eater.level >= 3) {
	    						state = 3;
	    					}else {
	    						state = 1;
	    					}
	    					break;
	    				
	    			}
	    		}
	    	}
	    	
//	    	public void keyReleased(KeyEvent e) {
//	    		super.keyReleased(e);
//	    		if(e.getKeyCode() == 38) {
//	    			GameImage.UP = false;
//	    		}
//	    		if(e.getKeyCode() == 40) {
//	    			GameImage.DOWN = false;
//	    		}
//	    		if(e.getKeyCode() == 37) {
//	    			GameImage.LEFT = false;
//	    		}
//	    		if(e.getKeyCode() == 39) {
//	    			GameImage.RIGHT = false;
//	    		}
//	    	}
//	    	
	    	
	    	
	    });
	    //create rest period
	    while(!isComplete) {
		    	if(repeatCount%20 == 0 && !isStop) {
		    		GameImage.timeleft = GameImage.timeleft - 1;
		    	}
		    	if(GameImage.timeleft == 0) {
		    		closeBGM();
		    		state = 2;
		    		isComplete = true;
		    		
		    	}
		    	repaint();
		    	repeatCount++;
		    	try {
					Thread.sleep(40);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    }
	}//launch end
	
	public void closeWindow() {
        dispose();
    }
	
	public void stop() {
		switch(state) {
		case 1:
		case 3:
			stopBGM();
			isStop = true;
			GameImage.drawWord(getGraphics(), "Press Space to Continue", Color.blue, 50, 250, 440);
			state = 4;
			break;
		case 4:
			playBGM();
			isStop = false;
			if(eater.level >= 3) {
				state = 3;
			}else {
				state = 1;
			}
			break;
		
		}
	}
	
	
	
	//image show
	public void paint(Graphics g) {
		
		offScreenImage = createImage(width, height);
		Graphics gImage  = offScreenImage.getGraphics();
		bg.paintSelf(gImage, eater.level);
	
		switch(state) {
		case 0://wait for start
			isStop = true;
			break;
		case 1://in game
			isStop = false;
			eater.paintSelf(gImage);
			logic();
			for(Restaurant rest:GameImage.RestaurantList) {
				rest.paintSelf(gImage);
			}
			break;
		case 2://end
//			for(Restaurant rest:GameImage.RestaurantList) {
//				rest.paintSelf(gImage);
//			}
			break;
		case 3://super state
			eater.paintSelf(gImage);
			logic();
			for(Restaurant rest:GameImage.RestaurantList) {
				rest.paintSelf(gImage);
			}
			break;
		case 4:
			
			return;
		default:
		
		}
		
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	void logic() {
		//me level
		if(GameImage.score < 10) {
			GameImage.level = 0;
			eater.level = 1;
			nextLevel = 16 - GameImage.score + 1;
			
		}else if(GameImage.score <= 16) {
			GameImage.level = 1;
			nextLevel = 16 - GameImage.score + 1;
			
		}else if(GameImage.score <= 28) {
			GameImage.level = 2;
			eater.level = 2;
			nextLevel = 28 - GameImage.score + 1;
			
		}else if(GameImage.score <= 80) {
			GameImage.level = 3;
			eater.level = 3;
			nextLevel = 80 - GameImage.score;
			
		}else if(GameImage.score <= 1000) {
			state = 3;
			GameImage.level = 4;
			eater.level = 5;
			
		}
		random = Math.random();
		
		
		
		//lv不同 餐廳不同
		switch(GameImage.level) {
			case 4://super mode
				if(repeatCount %8 == 0) {
					if(random > 0.75) {
						Restaurant restaurant = new Rest_15();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.5 && random <= 0.75){
						Restaurant restaurant = new Rest_14();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.25 && random <= 0.5) {
						Restaurant restaurant = new Rest_13();
						GameImage.RestaurantList.add(restaurant);
					}else{
						Restaurant restaurant = new Rest_16();
						GameImage.RestaurantList.add(restaurant);
					}
				}
				break;	
			//under 300
			case 3:
			case 2:
				if(repeatCount %40 == 0) {
					if(random > 0.75) {
						Restaurant restaurant = new Rest_9();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.5 && random <= 0.75){
						Restaurant restaurant = new Rest_10();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.25 && random <= 0.5) {
						Restaurant restaurant = new Rest_11();
						GameImage.RestaurantList.add(restaurant);
					}else{
						Restaurant restaurant = new Rest_12();
						GameImage.RestaurantList.add(restaurant);
					}
				}
			//under 200
			case 1:
				if(repeatCount %30 == 0) {
					if(random > 0.75) {
						Restaurant restaurant = new Rest_5();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.5 && random <= 0.75){
						Restaurant restaurant = new Rest_6();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.25 && random <= 0.5) {
						Restaurant restaurant = new Rest_7();
						GameImage.RestaurantList.add(restaurant);
					}else{
						Restaurant restaurant = new Rest_8();
						GameImage.RestaurantList.add(restaurant);
					}
				}
			//under 100
			case 0:
				if(repeatCount %15 == 0) {
					if(random > 0.75) {
						Restaurant restaurant = new Rest_l();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.5 && random <= 0.75){
						Restaurant restaurant = new Rest_r();
						GameImage.RestaurantList.add(restaurant);
					}else if(random > 0.25 && random <= 0.5) {
						Restaurant restaurant = new Rest_3();
						GameImage.RestaurantList.add(restaurant);
					}else{
						Restaurant restaurant = new Rest_4();
						GameImage.RestaurantList.add(restaurant);
					}
				}
			break;
			
			
		}
		//move way
		for(Restaurant rest:GameImage.RestaurantList) {
			rest.x = rest.x + rest.direction*rest.speed;
			//撞到
			if(eater.getRec().intersects(rest.getRec()) ) {
				if(eater.level >= rest.type) {
					rest.x = -200;
					rest.y = -200;
					GameImage.score = GameImage.score + rest.count;
				}else {
					rest.x = -200;
					rest.y = -200;
					GameImage.score = GameImage.score + rest.count;
					GameImage.money = GameImage.money - rest.count*100;
					//lose 
					if(GameImage.money == 0) {
						state = 2; ///lose
						isComplete = true;
					}
				}
			}
		}
		
	}
	private void showRules() {
	    JDialog rulesDialog = new JDialog(this, "Rules", Dialog.ModalityType.APPLICATION_MODAL);
	    rulesDialog.setSize(660, 230);
	    rulesDialog.setLocationRelativeTo(this);
	    JTextArea rulesTextArea = new JTextArea();
	    rulesTextArea.setText("Game Rules:\n\n" +
	            " 1. Goal: Eat the \"right\" restaurant according to your level. AS MORE AS YOU CAN \n" +
	            " 2. Divided into three level   \n" +
	            " 3. Arrow keys control the direction. Press Space to pause   \n" +
	            " 4. If you eat at a restaurant that exceeds your own level, you will be deducted(Bank Saving)   \n" +
	            " 5. Go to \"Super Mode\" after 80 points   \n" +
	            " 6. This Round is Important and Easy to Get Point and Catch Up Other Players  \n\n" 
	            );

	    rulesTextArea.setEditable(false);
	    rulesTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
	    

	    rulesTextArea.setForeground(new Color(101-70, 67-50, 33-20)); // 设置文字颜色为深咖啡色
	    rulesTextArea.setBackground(new Color(210, 180, 140)); // 设置背景颜色为浅棕色

	    
	    
	    rulesDialog.add(rulesTextArea);
	    rulesDialog.setVisible(true);
	    
	}
	
	private void loadBGM(String soundPath) {
		try {
			bgm = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath));
			bgm.open(ais);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
	        e.printStackTrace();
	    }
	}

	private void playBGM() {
		if (bgm != null) {
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
	}
	
	public void stopBGM() {
		if (bgm != null) {
            bgm.stop();
        }
	}



	public void closeBGM() {
		if (bgm != null) {
            bgm.close();
            bgm = null;
        }
	}
	
	
	
	
	public static void main(String[] arg) {
		GameFoodie gameFoodie = new GameFoodie();
		gameFoodie.launch();
		
		//while(true) {
			if(isComplete) {
				try {
					Thread.sleep(5000);
					gameFoodie.closeBGM();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
//				tempScore = (""+GameImage.score);
//				setToServer = true;
//				receiveFromServer = true;
//				changePage = false;
				
				gameFoodie.closeWindow();
				gameFoodie = null;
				//break;
		//	}
		}
		
		
	}

}
