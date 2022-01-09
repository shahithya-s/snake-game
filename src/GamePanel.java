import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
	
	//global variables
	static final int SCREEN_WIDTH = 600;									
	static final int GAME_HEIGHT = 700;	
	static final int GAME_DIMENSIONS = GAME_HEIGHT - SCREEN_WIDTH;
	static final int UNIT_SIZE = 25; 																
	static final int GAME_UNITS = (SCREEN_WIDTH*GAME_HEIGHT)/UNIT_SIZE; 	 	
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
	Random random = new Random(); 
	Image FOOD; 
	
	//set color to random color each time
	int red = random.nextInt(215);
	int green = random.nextInt(215); 
	int blue = random.nextInt(215);
	
	Color light = new Color(red, green, blue);
	Color dark = new Color(red + 40, green + 40, blue + 40);
	
	public GamePanel() {
		
		setForeground(new Color(255, 255, 255));
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, GAME_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		
		this.setSize(getPreferredSize());
		setLayout(null);
		
		this.addKeyListener(new MyKeyAdapter());
		
		JButton startGamebtn = new JButton("START GAME");
		startGamebtn.setBackground(Color.WHITE);
		startGamebtn.setForeground(new Color(80, 80, 80));
		startGamebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
				startGamebtn.setVisible(false);
			}
		});
		startGamebtn.setBounds(250, 325, 117, 29);
		add(startGamebtn);
	}
	
	public void startGame() {
		x[0] = (int)(SCREEN_WIDTH/2);
		y[0] = (int)((GAME_HEIGHT-GAME_DIMENSIONS)/2) + GAME_DIMENSIONS;
		newFood();
		running = true; 
		timer = new Timer(SPEED_DELAY, this);
		timer.start();
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		
		if(running) {
			
			//drawing horizontal grid lines
			for (int i = GAME_HEIGHT/UNIT_SIZE; i > GAME_DIMENSIONS/UNIT_SIZE - 1; i--) {
				g.setColor(new Color(40,40,40));
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			
			//drawing vertical grid lines
			for (int i = 0; i < (GAME_HEIGHT/UNIT_SIZE); i++) {
				g.setColor(new Color(40,40,40));
				g.drawLine(i*UNIT_SIZE, GAME_DIMENSIONS, i*UNIT_SIZE, GAME_HEIGHT);
			}
			
			//drawing apple 
			ImageIcon ione = new ImageIcon("src/gameImages/apple.png");
			FOOD = ione.getImage();
			g.drawImage(FOOD, appleX, appleY, UNIT_SIZE, UNIT_SIZE, null);
	        
			
			//drawing snake
			for (int i = 0; i < bodyparts; i++) {
				if (i == 0) {
					g.setColor(light);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				
				else {
					g.setColor(dark);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
		
		else if (!running && appleX > 0) {
			gameOver(g);
		}
		
		//draw message
		g.setColor(Color.WHITE);
		g.setFont(new Font("Krungthep", Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Eat all of the Fruits!", (SCREEN_WIDTH - (metrics.stringWidth("Eat all of the Fruits!" + 10))), (g.getFont().getSize() + 30));
		g.drawImage(FOOD, (SCREEN_WIDTH - ((metrics.stringWidth("Eat all of the Fruits!")+ 40))), (g.getFont().getSize() + 15), g.getFont().getSize(), g.getFont().getSize(), null);
		
		
		//draw score
		g.setColor(Color.red);
		g.setFont(new Font("Krungthep", Font.BOLD, 25));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - (metrics1.stringWidth("Score: " + applesEaten + 50))), (g.getFont().getSize() + 50));
	}
	
	public void newFood() {
		appleX= random.nextInt(((int)SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY= random.nextInt(((int)GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
			
			//validation for if apple goes out of 'game board'
			if (appleY < GAME_DIMENSIONS || appleY > GAME_HEIGHT) {
				newFood();
			}
			
			//validation for if snake overlaps apple 
			for (int i = bodyparts; i > 0; i--) {
				if (appleX == x[i] || appleY == y[i]) {
					newFood();
				}
			}	
	}
	
	public void move() {
		for(int i = bodyparts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if ((x[0] == appleX && y[0] == appleY)) {
			bodyparts++;
			applesEaten++;
			newFood();
		}
	}
	
	public void checkCollisions() {
		//checks if head collides with body
		for (int i = bodyparts; i > 0; i--) {
			if ((x[0] == x[i] && y[0] == y[i])) {
				running = false;
			}
		}
		
		//check if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		
		//check if head touches right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		//check if head touches top border
		if (y[0] < GAME_DIMENSIONS) {
			running = false;
		}
		
		//check if head touches bottom border
		if (y[0] > GAME_HEIGHT) {
			running = false; 
		}
		
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver (Graphics g) {
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Krungthep", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER.", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER!"))/2, GAME_HEIGHT/2);
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			
			case KeyEvent.VK_DOWN:
				if(direction != 'U' ) {
					direction = 'D';
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
}
