package gameteris;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import rr.rr1223.PlayerSocket;

public class Tetris extends JFrame {
	
	static final int game_row = 26;
	static final int game_column = 12;
	static int time = 150;
	static int score = 0;
	
	static JTextArea[][] text;
	static int[][] data;
	JLabel label_state;
	static JLabel label_score;
	boolean is_running;
	Block block;
//	Port port;
	public static boolean isComplete = false;
	Clip bgm = null;
	
	
	public void initWindow() {
		
		this.setSize(400, 600);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Tetris");
		
	}
	
	public void initGamePanel() {
		JPanel game_main = new JPanel();
		game_main.setLayout(new GridLayout(game_row, game_column, 1, 1));
		
		for (int i = 0; i < text.length; i++) {
			for (int j = 0; j <text[i].length; j++) {
				
				text[i][j] = new JTextArea(game_row, game_column);
				text[i][j].setBackground(Color.WHITE);
				
				
				if (j == 0 || j == text[i].length-1 || i == text.length-1) {
					text[i][j].setBackground(Color.MAGENTA);
					data[i][j] = 1;
				}
				
				text[i][j].setEditable(false);
				game_main.add(text[i][j]);
			}
		}
		
		this.setLayout(new BorderLayout());
		this.add(game_main, BorderLayout.CENTER);
		
	}
	
	public void initExplainPanel() {
		
		JPanel explain_left = new JPanel();
		JPanel explain_right = new JPanel();
		explain_left.setLayout(new GridLayout(4, 1));
		explain_right.setLayout(new GridLayout(2, 1));
		
		explain_left.add(new JLabel("Press, block rotate"));
		explain_left.add(new JLabel("Press, block shift left"));
		explain_left.add(new JLabel("Press, block shift right"));
		explain_left.add(new JLabel("Press, block drop faster"));
		
		label_state.setForeground(Color.RED);
		
		explain_right.add(label_state);
		explain_right.add(label_score);
		
		this.add(explain_left, BorderLayout.WEST);
		this.add(explain_right, BorderLayout.EAST);
	}
	
	public Tetris() {
//		text = new JTextArea[game_row][game_column];
//		data = new int[game_row][game_column];
//		label_state = new JLabel("State: playing!!");
//		label_score = new JLabel("Score: 0");
//		block = new Block();
//	
//		
//		initGamePanel();
//		initExplainPanel();
//		initWindow();
//		block.initBlock();
//
//		
//		is_running = true;
	}
	
	public void launch() {
		text = new JTextArea[game_row][game_column];
		data = new int[game_row][game_column];
		label_state = new JLabel("State: playing!!");
		label_score = new JLabel("Score: 0");
		block = new Block();
	
		
		initGamePanel();
		initExplainPanel();
		initWindow();
		block.initBlock();
		loadBGM("sound/tetris.wav");
		playBGM();

		
		is_running = true;
		
		this.addWindowListener(new WindowAdapter() {
					
		            public void windowClosing(WindowEvent e) {
		                int confirmed = JOptionPane.showConfirmDialog(
		                        null,
		                        "Are you sure you want to exit?",
		                        "Confirm Exit",
		                        JOptionPane.YES_NO_OPTION
		                );
		
		                if (confirmed == JOptionPane.YES_OPTION) {
		                	isComplete = true;
		                	closeBGM();
		                    dispose();
		         //           PlayerSocket.next_playing = 0;
		                }
		            }
		 });
		
		
		while(is_running) {
			game_run();
			//port.getData();
		}
		isComplete = true;
		closeBGM();
		//PlayerSocket.next_playing = 0;
		label_state.setText("State: game over");
	}
	
	public void game_run() {
		block.ranRect();
		
		for (int i = 0; i < game_row; i++) {
			try {
				Thread.sleep(time);
				
				if (!block.canFall(block.x, block.y)) {
					
					block.changeData(block.x, block.y);
					
					for (int j = block.x; j < (block.x+4); j++) {
						
						int sum = 0;
						for (int k = 1; k < game_column-1; k++) {
							if (data[j][k] == 1) {
								sum++;
							}
						}
						
						if (sum == game_column-2) {
							block.removeRow(j);
						}
						
						if (j == game_row-2) break;
					}
					
					for (int j = 1; j < (game_column-1); j++) {
						if (data[3][j] == 1) {
							is_running = false;
							break;
						}
					}
					break;
				} else {
					block.x++;
					block.fall(block.x, block.y);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void left() {
		
			if (!is_running || block.y <= 1) {
				return;
			}
			
			
			int temp = 0x8000;
			for (int i = block.x; i < block.x+4; i++) {
				for (int j = block.y; j < block.y+4; j++) {
					if ((temp & block.rect) != 0) {
						if (i <= game_row-2 && j <= game_column-2 && data[i][j-1] == 1) {
							return;
						}
					}
					temp >>= 1;
				}
			}
			
			block.clear(block.x, block.y);
			block.y--;
			block.draw(block.x, block.y);
		
	}
	
	public void right() {

		int temp = 0x8000;
//		int m = block.x;
//		int n = block.y;
		//int num = 1;	
		
		if (!is_running ) {
			return;
		}
		//|| num >= game_column-2
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				if ((temp & block.rect) != 0) {
//					if (n > num) {
//						num = n;
//					}
//				}
//				n++;
//				temp >>= 1;
//			}
//			m++;
//			n = n-4;
//		}
//		
		
		temp = 0x8000;
		for (int i = block.x; i < block.x+4; i++) {
			for (int j = block.y; j < block.y+4; j++) {
				if ((temp & block.rect) != 0) {
					if (i <= game_row-2 && j <= game_column-2 && data[i][j+1] == 1) {
						return;
					}
				}
				temp >>= 1;
			}
		}
		
		block.clear(block.x, block.y);
		block.y++;
		block.draw(block.x, block.y);
		
	}	
	
	public void down() {	
		if (!is_running || !block.canFall(block.x, block.y)) {
			return;
		}
		
		block.clear(block.x, block.y);
		block.x++;
		block.draw(block.x, block.y);
		
	}	
	
	public void up() {
		
		if (!is_running) {
			return;
		}
		
		int old;
		for (old = 0; old < Block.allRect.length; old++) {
			if (block.rect == Block.allRect[old]) {
				break;
			}
		}
		
		int next;
		
		if (old == 6) {
			return;
		}
		
		block.clear(block.x, block.y);
		
		if (old == 0 || old == 7) {
			next = Block.allRect[old == 0 ? 7:0];
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		
		if (old == 1 || (old >= 8 && old <= 10)) {
			
			if (old == 1) {
				next = Block.allRect[8];
			} else {
				next = Block.allRect[old+1 > 10 ? 1 : old+1];
			}
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		

		if (old == 2 || (old >= 11 && old <= 13)) {
			
			if (old == 2) {
				next = Block.allRect[11];
			} else {
				next = Block.allRect[old+1 > 13 ? 2 : old+1];
			}
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		

		if (old == 3 || (old >= 14 && old <= 16)) {
			
			if (old == 3) {
				next = Block.allRect[14];
			} else {
				next = Block.allRect[old+1 > 16 ? 3 : old+1];
			}
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		
		if (old == 4 || old == 17) {
			next = Block.allRect[old == 4 ? 17:4];
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		
		if (old == 5 || old == 18) {
			next = Block.allRect[old == 5 ? 18:5];
			
			if(block.canTurn(next, block.x, block.y)) {
				block.rect = next;
			}
		}
		
		block.draw(block.x, block.y);
		
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
