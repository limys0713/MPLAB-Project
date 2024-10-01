package piano;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.*;

public class Piano extends JFrame implements ActionListener, KeyListener{
	
	public static final int WIDTH = 1060;
	public static final int HEIGHT = 500;
	int button_size = 21;
	int count = 0;
	boolean pause = true;
	boolean auto = false;

	JButton	[]button = new JButton[button_size];
	AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
	Image offScreenImage = null;
	Image bgImg = Toolkit.getDefaultToolkit().getImage("imgs/bg.png");
	Note bgObj = new Note(bgImg, 0, 0, 0, 0, 0, this);
	List<Obj> objList = new ArrayList<>();
	List<Obj> removeList = new ArrayList<>();

	char arrChar[] = {'q','w','e','r','t','y','d',
				  	  'f','g','h','j','k','l','z',
				  	  'x','c','v','b','n','m',','};
	
	int arrNote[] = {         4,   4,   3,   4,   6,   7,   8,   4,   4,   3,
						   	  4,   3,   3,   1,   4,   4,   3,   4,   6,   7,
						   	  8,   8,   8,   7,   6,   3,   4,   4,   3,   4,
						   	  6,   7,   8,   4,   4,   3,   4,   3,   3,   1,
						   	  4,   4,   3,   4,   6,   7,   8,   8,   7,   8, //50
						   	  7,   6,   4,   6,   5,   4,   3,   3,   4,   3,
						   	  1,   0,   1,   1,   3,   7,   5,   6,   5,   3,
						   	  4,   6,   5,   4,   3,   3,   4,   3,   1,   0,
						   	  1,   1,   3,   4,   4,   4,   6,   7,   5,   4, //90
						   	  6,   7,   7,   8,   8,   8,  10,  11,   7,   6,
						   	  8,   4,   6,   7,   7,   8,   8,   8,   9,   8,
						   	  7,   6,   6,   4,   6,   7,   7,   8,   8,   8,
						     10,  11,   7,   6,   8,   4,   6,   9,   8,   7,
						   	  6,   7,   6,   8,  10,  11,  11,  13,  14,  14, //140
						     15,  15,  15,  17,  18,  14,  13,  15,  11,  13,
						  	 14,  14,  15,  15,  15,  16,  15,  14,  13,  13,
						  	 11,  13,  14,  14,  15,  15,  15,  17,  18,  14,
						  	 13,  15,  11,  13,  16,  15,  14,  13,  14,  13, //180
						     15,  17,  18,  14,  15,  11,  10,  11,  10,  14,
						     15,  11,  10,  11,  10,  14,  15,  11,  10,  11,
						     10,  13,  12,  11,  10,  14,  15,  11,  10,  11,
						     10,  14,  15,  11,  10,  11,  10,  14,  15,  17,
						     20,  19,  20,  19,  18,  17,  15,  14,  15,  11,
						     10,  11,  10,  14,  15,  11,  10,  11,  10,  14,
						     15,  11,  10,  11,  10,  13,  12,  11,  10,   8,
						      7,   8,  10,  11,  10,   8,   7,   4,   6,   7, //260
						      8,   8,  11,   8,  11,   8,  10,   8,  11};
	
	int arrDelay[] ={   20, 260, 260, 160, 160, 160, 160, 160, 260, 260, 160,
							160, 160, 160, 160, 260, 260, 160, 160, 160, 160,
							160, 300,  50, 260, 300, 300, 260, 260, 160, 160,
							160, 160, 160, 260, 260, 160, 160, 160, 160, 160,
							260, 260, 160, 160, 160, 160, 160, 300,  50,  50, //50
							160, 320, 320, 320, 320, 320, 320, 160,  50,  50,
							160, 160, 650, 320, 320, 320, 320, 320, 160, 160,
						    650, 320, 320, 320, 320, 160,  50,  50, 160, 160,
							320, 160, 160, 260, 260, 160, 320, 320, 950, 160, //90
							160, 260, 260, 160, 450, 160, 160, 160, 160, 160,
							320, 160, 160, 260, 260, 160, 450, 160, 160, 160,
							160, 160, 320, 160, 160, 260, 260, 160, 450, 160,
							160, 160, 160, 160, 300, 160, 160, 300, 300, 300,
							300, 160, 160, 160, 160, 300, 160, 160, 260, 260, //140
							160, 450, 160, 160, 160, 160, 160, 300, 160, 160,
							260, 260, 160, 450, 160, 160, 160, 160, 160, 300,
							160, 160, 260, 260, 160, 450, 160, 160, 160, 160,
							160, 300, 160, 160, 320, 320, 320, 320, 160, 160, //180
							160, 160, 650, 170, 160,  70,  70,  70,  70, 170,
							160,  70,  70,  70,  70, 170, 160,  70,  70,  70,
							 70, 170, 170, 170, 170, 170, 160,  70,  70,  70,
							 70, 170, 160,  70,  70,  70,  70, 170, 170, 170,
							170,  50,  50,  50, 170, 170, 170, 170, 160,  70,
							 70,  70,  70, 170, 160,  70,  70,  70,  70, 170,
							160,  70,  70,  70,  70, 170, 170, 170, 170,  70,
							 70,  70,  70,  70,  70,  70,  70, 170, 170, 170, //260
							 170,  0, 240,   0, 240,   0, 160,   0, 650};
	
	public Piano() {
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.setTitle("Piano");
		this.addKeyListener(this);

		objList.add(bgObj);
		for (int i = 0; i < button_size; i++) {
			button[i] = new JButton(""+((i+1)%7+1));
			button[i].setBounds(i*50, 400, 50, 50);
			button[i].setBackground(Color.WHITE);
			//this.add(button[i]);
			button[i].addActionListener(this);
			Btn.btnList.add(new Btn(Btn.btnImage, i*50, 430, 50, 50, ""+((i+1)%7+1), this));
			objList.add(Btn.btnList.get(Btn.btnList.size()-1));
		}
		
	}
	
	public void launch(Piano piano) {

		while (true) {
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!pause) break;
		}
		
		int k = 140;
		while (true) {
			repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (k < arrNote.length) {
				count++;
				if (count*10 >= arrDelay[k]) {
					//button[array[k]].doClick();
					playGame(arrNote[k]);
					k++;
					count = 0;
				}
					
			}
			for (int j = 0; j < Note.noteList.size(); j++) {
				if (Note.noteList.get(j).getY() == 420) {
					int x = Note.noteList.get(j).getX()/50;
					if (auto) {
						click(x);
						Btn.btnList.get(x).setImg(Btn.btn2Image);
						Btn.btnList.get(x).setDelete(1);
					}
					removeList.add(Note.noteList.get(j));
						//button[x].doClick();
						//simulateKeyPress(this, arrChar[x]);
				}
			}
			while(pause) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = createImage(WIDTH, HEIGHT);
		}
		Graphics gImage = offScreenImage.getGraphics();
		gImage.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < objList.size(); i++) {
			objList.get(i).paintSelf(gImage);
		}
		for (int i = 0; i < Btn.btnList.size(); i++) {
			if (Btn.btnList.get(i).getDelete() > 100) {
				Btn.btnList.get(i).setImg(Btn.btnImage);
				Btn.btnList.get(i).setDelete(0);
			}
			Btn.btnList.get(i).setDelete(Btn.btnList.get(i).getDelete()*2);
		}
		objList.removeAll(removeList);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button[0]) {
			click(0);
		}
		if(e.getSource() == button[1]) {
			click(1);
		}
		if(e.getSource() == button[2]) {
			click(2);
		}
		if(e.getSource() == button[3]) {
			click(3);
		}
		if(e.getSource() == button[4]) {
			click(4);
		}
		if(e.getSource() == button[5]) {
			click(5);
		}
		if(e.getSource() == button[6]) {
			click(6);
		}
		
		if(e.getSource() == button[7]) {
			click(7);
		}
		if(e.getSource() == button[8]) {
			click(8);
		}
		if(e.getSource() == button[9]) {
			click(9);
		}
		if(e.getSource() == button[10]) {
			click(10);
		}
		if(e.getSource() == button[11]) {
			click(11);
		}
		if(e.getSource() == button[12]) {
			click(12);
		}
		if(e.getSource() == button[13]) {
			click(13);
		}
		
		if(e.getSource() == button[14]) {
			click(14);
		}
		if(e.getSource() == button[15]) {
			click(15);
		}
		if(e.getSource() == button[16]) {
			click(16);
		}
		if(e.getSource() == button[17]) {
			click(17);
		}
		if(e.getSource() == button[18]) {
			click(18);
		}
		if(e.getSource() == button[19]) {
			click(19);
		}
		if(e.getSource() == button[20]) {
			click(20);
		}
	}
	
	public void click(int n) {
		switch(n) {
		case 0:
			playNotes("notes/3d.wav");
			break;
		case 1:
			playNotes("notes/3e.wav");
			break;
		case 2:
			playNotes("notes/3f.wav");
			break;
		case 3:
			playNotes("notes/3g.wav");
			break;
		case 4:
			playNotes("notes/4a.wav");
			break;
		case 5:
			playNotes("notes/4b.wav");
			break;
		case 6:
			playNotes("notes/4c.wav");
			break;
		case 7:
			playNotes("notes/4d.wav");
			break;
		case 8:
			playNotes("notes/4e.wav");
			break;
		case 9:
			playNotes("notes/4f.wav");
			break;
		case 10:
			playNotes("notes/4g.wav");
			break;
		case 11:
			playNotes("notes/5a.wav");
			break;
		case 12:
			playNotes("notes/5b.wav");
			break;
		case 13:
			playNotes("notes/5c.wav");
			break;
		case 14:
			playNotes("notes/5d.wav");
			break;
		case 15:
			playNotes("notes/5e.wav");
			break;
		case 16:
			playNotes("notes/5f.wav");
			break;
		case 17:
			playNotes("notes/5g.wav");
			break;
		case 18:
			playNotes("notes/6a.wav");
			break;
		case 19:
			playNotes("notes/6b.wav");
			break;
		case 20:
			playNotes("notes/6c.wav");
			break;
		default:
			break;
		}
	}
	
	public void playGame(int x) {
		Note.noteList.add(new Note(Note.noteImage, x*50, 0, 50, 50, 5, this));
		objList.add(Note.noteList.get(Note.noteList.size()-1));
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		
		for (int i = 0; i < arrChar.length; i++) {
			if (e.getKeyChar() == arrChar[i]) {
				click(i);
				Btn.btnList.get(i).setImg(Btn.btn2Image);
				Btn.btnList.get(i).setDelete(1);
			}
		}
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			pause = !pause;
		}
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			auto = !auto;
		}
	}

	public void keyReleased(KeyEvent e) {
	}
	
	private void playNotes(String soundPath) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundPath).getAbsoluteFile());
			Clip notes = AudioSystem.getClip();
			notes.open(ais);
			notes.start();

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
	        e.printStackTrace();
	    }
	}

}
