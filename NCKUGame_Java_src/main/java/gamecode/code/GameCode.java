package gamecode.code;



import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import rr.rr1223.PlayerSocket;

public class GameCode extends JFrame {
	
	
	public GameCode() {
		 setFocusableWindowState(true);
	}
	Panel p = new Panel();
	private int width = 1000;
	private int height = 801;
	Clip bgm = null;
	
	public void launch() {
		
		loadBGM("sound/piano.wav");
		playBGM();
		
		setFocusableWindowState(true);
		
		
		this.addKeyListener(new KeyAdapter() {        
 			public void keyPressed(KeyEvent e) {
	    		super.keyPressed(e);
	    		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
	    			if(Panel.isOverlap) {
	    				Panel.isOverlap = false;
	    				p.initDraw();
	    			}else {
	    				Panel.isStart = !Panel.isStart;
	    			}
	    			
	    			repaint();
	    		}
	    		if(e.getKeyCode() == 38) {
	    			Panel.direction = "U";
	    		}
	    		if(e.getKeyCode() == 40) {
	    			Panel.direction = "D";
	    		}
	    		if(e.getKeyCode() == 37) {
	    			Panel.direction = "L";
	    		}
	    		if(e.getKeyCode() == 39) {
	    			Panel.direction = "R";
	    		}
	    	}
 		});
		//avoid
		requestFocusInWindow();
        this.setFocusable(true);
        
	    this.setVisible(true);
		this.setSize(width, height);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {
                	Panel.isComplete = true;
                	closeBGM();
                    dispose();
                 //   PlayerSocket.next_playing = 0;
                }
            }
        });
	    this.add(p);
	    this.setVisible(true);
	    showRules();
	    //開始計時
	    while(!Panel.isComplete) {
	    	if(Panel.foodIndex == 24) 
	    		Panel.isComplete = true;
	    	if(Panel.isStart) 
	    		Panel.elapsedSeconds = Panel.elapsedSeconds + 1;
	    	
	    	try {
				Thread.sleep(1200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	    }
	}
	
	public void stop() {
		if(Panel.isOverlap) {
			Panel.isOverlap = false;
			p.initDraw();
		}else {
			Panel.isStart = !Panel.isStart;
		}
		
		repaint();
	}
	
	
	private void showRules() {
	    // Create a separate dialog to show the rules
	    JDialog rulesDialog = new JDialog(this, "Rules", Dialog.ModalityType.APPLICATION_MODAL);
	    rulesDialog.setSize(500, 225);
	    rulesDialog.setLocationRelativeTo(this);
	    JTextArea rulesTextArea = new JTextArea();
	    rulesTextArea.setText(" Game Rules :\n\n" +
	            " 1. Goal: Collect \" System.out.print(\"NCKU\") \"    \n" +
	            " 2. Elapsed Time Equals To Your Score   \n" +
	            " 3. Arrow keys control the direction. Press Space to pause   \n" +
	            " 4. If you eat the wrong letter (symbol), you will lose one letter (symbol).   \n" +
	            " 5. If you collide with your own body, you will need to restart.   \n" +
	            " 6. If you exceeds 180 sec, it will be considered failure.   \n\n" 
	            );

	    rulesTextArea.setEditable(false);
	    rulesTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
	    

	    rulesTextArea.setForeground(Color.WHITE); 
	    rulesTextArea.setBackground(Color.DARK_GRAY);

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
	
	

}
