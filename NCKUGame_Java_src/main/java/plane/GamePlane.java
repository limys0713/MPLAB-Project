package plane;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.*;

import rr.rr1223.IsComplete;
import rr.rr1223.PlayerSocket;

public class GamePlane extends JFrame {
	
	public static int state = 0;
	public static int score = 0;
	public static int time = 100;
	public static int width = 1000;
	public static int height = 800;
	public static boolean isComplete = false;
	int count = 1;
	int enemyCount = 0;
	boolean stoneSpawned = false;
	long stoneSpawnTime = 0;
	static boolean stopSound = false;
	
	Image offScreen = null;
	Clip bgm = null;
	
	BgObj bgObj = new BgObj(GameImage.bgImg, 0, -6400, 2);
	public PlaneObj planeObj = new PlaneObj(GameImage.playerImg, 350, 400, 80, 80, 0, this);
	public BossObj bossObj = null;
	public FoodObj foodObj = null;
	public StoneObj stoneObj = null;
	
	public void launch() {
		
		// window settings
        this.setFocusable(true);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setTitle("飛機大戰");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		GameImage.gameObjList.add(bgObj);
		GameImage.gameObjList.add(planeObj);
		
		showRules();
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1 && state == 0) {
					state = 1;
					loadBGM("sound/planeBGM.wav");
					playBGM();
				}
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					switch (state) {
					case 1:
						state = 2;
						stopBGM();
						break;
					case 2:
						state = 1;
						playBGM();
						break;
					default:
						break;
					}
				}
			}
		});
		
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                    // Perform any cleanup tasks or save data before exiting
                //	PlayerSocket.next_playing = 0;
                	
                	isComplete = true;
                	IsComplete.planeIsComplete = true;
                	//closeBGM();
                    dispose();
                    //stopSound = true;
                    //playSoundEffect("sound/lose.wav");
                   
                }
            }
        });
		
		while (true) {	
			if (state == 1) {
				createObj();
				repaint();
				count++;
			}
			else if (state == 3) {
				closeBGM();
				playSoundEffect("sound/lose.wav");
				score = 1;
				repaint();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isComplete = true;
				break;
			}
			else if (state == 4) {
				closeBGM();
				playSoundEffect("sound/win.wav");
				score += 30;
				score += time/2;
				repaint();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isComplete = true;
				break;
			}			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g) {
		if (offScreen == null) {
			offScreen = createImage(width, height);
		}
		Graphics gImage = offScreen.getGraphics();
		gImage.fillRect(0, 0, width, height);
		if (state == 0) {
			gImage.drawImage(GameImage.bgImg, 0, 0, width, height, this);
			gImage.drawImage(GameImage.bossImg, width/2 - GameImage.bossImg.getWidth(this)/2, height/5, null);
			gImage.drawImage(GameImage.playerImg, width/2 - GameImage.playerImg.getWidth(this)/2, height*3/5, null);
			GameImage.drawWord(gImage, "Click to Start", Color.YELLOW, 40, width/2 - 120, height/2);
		}
		if (state == 1) {
			GameImage.gameObjList.addAll(GameImage.explodeObjList);
			for (int i = 0; i < GameImage.gameObjList.size(); i++) {
				GameImage.gameObjList.get(i).paintSelf(gImage);
			}
			GameImage.gameObjList.removeAll(GameImage.removeList);
		}
		if (state == 2) {
	        // Game paused state
	    }
		if (state == 3) {
			gImage.drawImage(GameImage.loseImg, planeObj.getX(), planeObj.getY(),null);
			GameImage.drawWord(gImage, "GAME OVER", Color.RED, 50, width/2 - 150, height/2 - 40);
			GameImage.drawWord(gImage, "Total score: " + score, Color.RED, 50, width/2 - 150, height/2 + 40);
		}
		if (state == 4) {
			gImage.drawImage(GameImage.explodeImg, bossObj.getX() + 40, bossObj.getY() - 50,null);
			GameImage.drawWord(gImage, "GAME CLEAR", Color.RED, 50, width/2 - 150, height/2 - 40);
			GameImage.drawWord(gImage, "Total score: " + score, Color.RED, 50, width/2 - 150, height/2 + 40);
		}

		GameImage.drawWord(gImage, "Time: " + time, Color.GREEN, 40, 20, 100);
		GameImage.drawWord(gImage, "Score: " + score, Color.GREEN, 40, width - 200, 100);
		g.drawImage(offScreen, 0, 0, null);
	}
	
	public static void playSoundEffect(String soundPath) {
	    try {
	        Clip soundEffect = AudioSystem.getClip();
	        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath));
	        soundEffect.open(ais);
	        soundEffect.start();
		    if (stopSound) {
		    	soundEffect.close();
		    	soundEffect = null;
		    }

	    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void stop() {
		switch (state) {
		case 1:
			state = 2;
			stopBGM();
			break;
		case 2:
			state = 1;
			playBGM();
			break;
		default:
			break;
		}
	}
	
	private void createObj() {
		if ( count%ShellObj.shellInterval == 0) {
			GameImage.shellObjList.add(new ShellObj(GameImage.shellImg, planeObj.getX()+50, planeObj.getY()- 8, 15, 30, ShellObj.shellSpeed, this));
			GameImage.gameObjList.add(GameImage.shellObjList.get(GameImage.shellObjList.size()-1));
			playSoundEffect("sound/shoot.wav");
		}

		if (enemyCount > 5 && foodObj == null) {
			foodObj = new FoodObj(GameImage.foodImg, (int)(Math.random()*15 + 2)*50, 0, 150, 115, 4, this);
			GameImage.gameObjList.add(foodObj);
		}
		
		if (enemyCount > 15 && bossObj == null) {
			bossObj = new BossObj(GameImage.bossImg, width/2 - 150, 40, 270, 100, 5, this);
			GameImage.gameObjList.add(bossObj);
		}
		
		if (count%40 == 0) {
			time--;
			if (time == 0) {
				state = 3;
			}
		}
		
		if (bossObj != null && bossObj.life <= 30 && stoneObj == null) {
			stopBGM();
			loadBGM("sound/stoneBGM.wav");
			playBGM();
			stoneObj = new StoneObj(GameImage.stoneImg, width/2 - 700, -2000, 1400, 1100, 12, this);
			GameImage.gameObjList.add(stoneObj);
	        stoneSpawned = true;
	        stoneSpawnTime = System.currentTimeMillis();
		}
		 // Check if stone has spawned and if 5 seconds have passed
	    if (stoneSpawned && System.currentTimeMillis() - stoneSpawnTime >= 5000) {
	        // Resume spawning of enemies and bullets
	        if (count % 30 == 0) {
	            GameImage.enemyObjList.add(new EnemyObj(GameImage.enemyImg[(int)(Math.random() * 7)], (int)(Math.random() * 18 + 1) * 50, 0, 60, 60, 5, this));
	            GameImage.gameObjList.add(GameImage.enemyObjList.get(GameImage.enemyObjList.size() - 1));
	            enemyCount++;
	        }

	        if (count % 20 == 0) {
	            GameImage.bulletObjList.add(new BulletObj(GameImage.bulletImg, bossObj.getX() + 30, bossObj.getY() + 85, 25, 25, 8, this));
	            GameImage.gameObjList.add(GameImage.bulletObjList.get(GameImage.bulletObjList.size() - 1));
	            GameImage.bulletObjList.add(new BulletObj(GameImage.bulletImg, bossObj.getX() + 210, bossObj.getY() + 85, 25, 25, 8, this));
	            GameImage.gameObjList.add(GameImage.bulletObjList.get(GameImage.bulletObjList.size() - 1));
	        }
	    } else {
	        // Pause spawning of enemies and bullets
	        if (count > 100 && count % 35 == 0 && !stoneSpawned) {
	            GameImage.enemyObjList.add(new EnemyObj(GameImage.enemyImg[(int)(Math.random() * 7)], (int)(Math.random() * 18 + 1) * 50, 0, 60, 60, 5, this));
	            GameImage.gameObjList.add(GameImage.enemyObjList.get(GameImage.enemyObjList.size() - 1));
	            enemyCount++;
	        }

	        if (bossObj != null && count % 25 == 0 && !stoneSpawned) {
	            GameImage.bulletObjList.add(new BulletObj(GameImage.bulletImg, bossObj.getX() + 30, bossObj.getY() + 85, 25, 25, 8, this));
	            GameImage.gameObjList.add(GameImage.bulletObjList.get(GameImage.bulletObjList.size() - 1));
	            GameImage.bulletObjList.add(new BulletObj(GameImage.bulletImg, bossObj.getX() + 210, bossObj.getY() + 85, 25, 25, 8, this));
	            GameImage.gameObjList.add(GameImage.bulletObjList.get(GameImage.bulletObjList.size() - 1));
	        }
	    }
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
	
	private void stopBGM() {
		if (bgm != null) {
            bgm.stop();
        }
	}
	
	private void closeBGM() {
		if (bgm != null) {
            bgm.close();
            bgm = null;
        }
	}
	
	private void showRules() {
	    // Create a separate dialog to show the rules
	    JDialog rulesDialog = new JDialog(this, "Rules", Dialog.ModalityType.APPLICATION_MODAL);
	    rulesDialog.setSize(500, 250);
	    rulesDialog.setLocationRelativeTo(this);

	    JTextArea rulesTextArea = new JTextArea();
	    rulesTextArea.setText("Game Rules:\n\n" +
	            "1. Use the mouse to control the plane.\n" +
	            "2. Avoid enemy objects and boss projectiles.\n" +
	            "3. Get hit will become invulnerable and can't shoot for a while.\n" +
	            "4. Collect power-ups.\n" +
	            "5. Destroy enemy objects to earn points(40 the most).\n" +
	            "6. Kill the boss as soon as you can.\n" +
	            "7. Press space to pause.\n\n");

	    rulesTextArea.setEditable(false);
	    rulesTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
	    rulesTextArea.setForeground(Color.WHITE); 
	    rulesTextArea.setBackground(Color.DARK_GRAY);

	    rulesDialog.add(rulesTextArea);
	    rulesDialog.setVisible(true);
	}

}
