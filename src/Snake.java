import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;


import javax.swing.*;

public class Snake {
	
	static final int SCREEN_WIDTH = 600;									
	static final int SCREEN_HEIGHT = 700;
	static final int GAME_DIMENSIONS = SCREEN_HEIGHT - SCREEN_WIDTH;
	static final int UNIT_SIZE = 25; 																
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; 	 	
	static final int SPEED_DELAY = 75; 	 	
	
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];	
	int bodyparts = 6; 							 							
	int applesEaten;														
	int appleX, appleY; 
	int counter = 0;
	char direction = 'R';
	boolean running = false;
	Timer timer; 
	Random random; 
	Image FOOD; 
	
	JFrame frame = new JFrame("Snake"); 
	JPanel panelCont = new JPanel();  // card layout panel
	
	JPanel titlePanel = new JPanel(); 
	JPanel instructionsPanel = new JPanel();
	JPanel settingsPanel = new JPanel(); 
	JPanel gamePanel = new JPanel(); 
	CardLayout cl = new CardLayout(); // create a new instance of card layout 
	
	
	public Snake() {
		
		//card layout for display
		panelCont.setLayout(cl);
		panelCont.add(titlePanel, "1");
		panelCont.add(instructionsPanel, "2");
		panelCont.add(new GamePanel(), "3");
			
		cl.show(panelCont, "1");
		
		//title panel
		titlePanel.setPreferredSize(new Dimension (SCREEN_WIDTH,SCREEN_HEIGHT));
		titlePanel.setBackground(new Color(40,40,40));
		titlePanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Play Snake!");
		lblTitle.setBounds(140, 47, 380, 77);
		lblTitle.setFont(new Font("Krungthep", Font.BOLD, 60));
		lblTitle.setForeground(Color.WHITE);
		titlePanel.add(lblTitle);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Snake.class.getResource("/gameImages/snakepic.png")));
		lblNewLabel.setBounds(160, 114, 266, 420);
		titlePanel.add(lblNewLabel);
		
		JButton instructionsBtn = new JButton("INSTRUCTIONS");
		instructionsBtn.setBackground(Color.WHITE);
		instructionsBtn.setForeground(new Color(80, 80, 80));
		instructionsBtn.setFont(new Font("Krungthep", Font.BOLD, 21));
		instructionsBtn.setBounds(90, 560, 200, 55);
		titlePanel.add(instructionsBtn); 
		
		JButton gameBtn = new JButton("PLAY NOW!");
		gameBtn.setBackground(new Color(154, 205, 50));
		gameBtn.setForeground(new Color(80, 80, 80));
		gameBtn.setFont(new Font("Krungthep", Font.BOLD, 21));
		gameBtn.setBounds(301, 560, 200, 55);
		titlePanel.add(gameBtn);
		
		
		instructionsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panelCont, "2");
			}
			
		});
		
		gameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panelCont, "3");
			}	
		});
		
		//instructions panel
		instructionsPanel.setPreferredSize(new Dimension (SCREEN_WIDTH,SCREEN_HEIGHT));
		instructionsPanel.setBackground(new Color(40,40,40));
		instructionsPanel.setLayout(null);
		
		JLabel lblTitleI = new JLabel("Instructions!");
		lblTitleI.setBounds(170, 33, 303, 77);
		lblTitleI.setFont(new Font("Krungthep", Font.PLAIN, 40));
		lblTitleI.setForeground(Color.WHITE);
		instructionsPanel.add(lblTitleI);
		
		JButton btnBack = new JButton("< Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panelCont, "1");
			}
		});
		btnBack.setBounds(200, 631, 208, 44);
		instructionsPanel.add(btnBack);
		
		JTextArea txtrSnakeIsA = new JTextArea();
		txtrSnakeIsA.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		txtrSnakeIsA.setText("Snake is a very simple game to play! To play Snake, use the "
							+ "\narrow keys on your keyboard to move the Snake. The up \narrow moves "
							+ "the snake upwards, the down arrow moves \nthe snake downwards, the right "
							+ "arrow moves the snake \ntowards the right, and the left arrow moves the snake\n "
							+ "towards the left. By moving the snake, try to eat as many \nfruits as possible. "
							+ "However, you must avoid eating your own \ntail or hitting the walls or borders of "
							+ "the screen! As you eat \nmore fruits, your tail becomes longer, so it makes "
							+ "it harder \nto avoid your tail!\n\nTip: Use the grid to judge when you "
							+ "should turn or move the \nsnake!\n\nGood luck and have fun!\n");
		txtrSnakeIsA.setForeground(Color.WHITE);
		txtrSnakeIsA.setBackground(new Color(40,40,40));
		txtrSnakeIsA.setEditable(false);
		txtrSnakeIsA.setBounds(38, 122, 528, 428);
		instructionsPanel.add(txtrSnakeIsA);
		
		
	frame.getContentPane().add(panelCont);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.pack();
	frame.setVisible(true);
	frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new Snake(); 
			}
		});
	}
}