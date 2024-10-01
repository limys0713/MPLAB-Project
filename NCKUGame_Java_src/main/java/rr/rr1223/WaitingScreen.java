package rr.rr1223;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import plane.GamePlane;


public class WaitingScreen extends JFrame{
	
	public WaitingScreen() {
		init();
	}
	
	private void init() {
		this.setSize(1000,800);
		this.setTitle("NCKU ARCADE");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmed == JOptionPane.YES_OPTION) {				              	
                    dispose();
					System.exit(0);
                }
            }
        });
		
	
		//Container
		JPanel container = new JPanel();
		container.setBounds(350, 300, 400, 350);
		container.setBackground(new Color(0,0,0,0));
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		//this.setContentPane(container);
		
		//heading
		JPanel heading;
		heading = new JPanel();
		heading.setBackground(new Color(10,10,10,70));
		heading.setBounds(0,0,1000,250);
		
		Font font = new Font("Serif", Font.BOLD,100);
		JLabel title = new JLabel("NCKU ARCADE");
		title.setFont(font);
		title.setForeground(Color.WHITE);
		title.setBounds(300,100,300,100);
		heading.add(title);
		
		//background
		ImageIcon background_img = new ImageIcon("windowImgs/ncku_arcade.png");
		Image img = background_img.getImage();
		Image temp_img = img.getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
		background_img = new ImageIcon(temp_img);
		JLabel background = new JLabel("", background_img, JLabel.CENTER);
		background.add(container);
		background.add(heading);
		background.setBounds(0,0,1000,800);
		add(background);

		// //TextField
		// JTextField nameField = new JTextField();
		// nameField.setMaximumSize(new Dimension(200, 30));
		// container.add(nameField, BorderLayout.CENTER);

		// //Name label
		// JLabel nameLabel = new JLabel("Type Your Name");
		// nameLabel.setForeground(Color.white);
		// nameLabel.setBounds(350, 400, 200, 200);
		// nameLabel.setSize(200, 200);
		// container.add(nameLabel);
		
		//blank
		JLabel blank1 = new JLabel(" ");
		blank1.setSize(200, 200);
		container.add(blank1);

		//blank
		JLabel blank2 = new JLabel(" ");
		blank2.setSize(200, 200);
		container.add(blank2);

		//buttons
		JButton button1 = new JButton("Foodie");
		JButton button2 = new JButton("Tetris");
		JButton button3 = new JButton("CodeWar");
		JButton button4 = new JButton("CodeSnake");
		JButton button_exit = new JButton("Exit");// it was called button2
		//game1
		button1.setSize(200,200);
		container.add(button1);
		button1.setAlignmentX(CENTER_ALIGNMENT);
		button1.setAlignmentY(CENTER_ALIGNMENT);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.next_playing = 1;

				// if(PlayerSocket.playerState == PlayerSocket.NOT_JOINED) {
				// 	PlayerSocket.sentToServer = true;
				// 	PlayerSocket.receiveFromServer = true;
				// 	PlayerSocket.playerName = nameField.getText();
				// 	PlayerSocket.waitingScreen.setVisible(false);
				// 	title.setText("等待更多玩家加入");	
				// 	container.remove(nameField);
				// 	container.remove(button1);
				// 	container.remove(button2);
				// 	container.remove(nameLabel);
				// }	
			}	
		});
		//blank
		JLabel blank3 = new JLabel(" ");
		blank3.setSize(200, 200);
		container.add(blank3);
		//game 2
		button2.setSize(200,200);
		container.add(button2);
		button2.setAlignmentX(CENTER_ALIGNMENT);
		button2.setAlignmentY(CENTER_ALIGNMENT);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.next_playing = 2;
				
			}	
		});
		//blank
		JLabel blank4 = new JLabel(" ");
		blank4.setSize(200, 200);
		container.add(blank4);
		//game 3
		button3.setSize(200,200);
		container.add(button3);
		button3.setAlignmentX(CENTER_ALIGNMENT);
		button3.setAlignmentY(CENTER_ALIGNMENT);
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.next_playing = 3;
			}	
		});
		//blank
		JLabel blank5 = new JLabel(" ");
		blank5.setSize(200, 200);
		container.add(blank5);
		//game 4
		button4.setSize(200,200);
		container.add(button4);
		button4.setAlignmentX(CENTER_ALIGNMENT);
		button4.setAlignmentY(CENTER_ALIGNMENT);
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSocket.next_playing = 4;
			}	
		});
		//blank
		JLabel blank6 = new JLabel(" ");
		blank6.setSize(200, 200);
		container.add(blank6);
		//exit
		container.add(button_exit);
		button_exit.setAlignmentX(CENTER_ALIGNMENT);
		button_exit.setAlignmentY(CENTER_ALIGNMENT);
		button_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});	
	
	}
}
