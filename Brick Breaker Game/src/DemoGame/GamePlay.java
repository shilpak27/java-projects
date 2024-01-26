package DemoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener {

    // declering vairable
    private boolean play = false; // Game will not play automatic
    private int score=0;

    private int totalbricks = 21;
    private Timer timer;
    private int delay = 8;
    private int ballposx = 120;// ball postion x
    private int ballposy = 350;// ball postion y
    private int ballxdir = -1;// dir x
    private int ballydir = -2;// dir y
    private int playerx = 350;//
    private MapGenerator map;

    // creating constructor
    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();
        
        map=new MapGenerator(3,7);
    }

    public void paint(Graphics g) {
        // background color
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // border
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(0, 3, 3, 592);
        g.fillRect(691, 3, 3, 592);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerx, 550, 100, 8);
        
        //bricks
        map.draw((Graphics2D)g);

        // ball
        g.setColor(Color.red);
        g.fillOval(ballposx, ballposy, 20, 20);
        
        //score
        g.setColor(Color.green);
        g.drawString("Score:+score", 550, 30);
        g.setFont(new Font("Serif",Font.BOLD,20));

        //gameover
        if(ballposy>=570) {
        	play=false;
        	ballxdir=0;
        	ballydir=0;
        	
        	g.setColor(Color.green);
        	g.setFont(new Font("serif",Font.BOLD,30));
        	g.drawString("GameOver!!,score: "+score, 200,300);
        	
        	g.setFont(new Font("serif",Font.BOLD,25));
        	g.drawString("Press Enter to Restart", 250,350);
        	 	
        }
        if(totalbricks<=0) {
        	play=false;
        	ballxdir=0;
        	ballydir=0;
        	
        	g.setColor(Color.green);
        	g.setFont(new Font("serif",Font.BOLD,30));
        	g.drawString("You won!!,score: "+score, 200,300);
        	
        	g.setFont(new Font("serif",Font.BOLD,25));
        	g.drawString("Press Enter to Restart", 250,350);
        }
    }

    private void moveLeft() {
    	play=true;
        playerx -= 20;
    }

    private void moveRight() {
    	play=true;
        playerx += 20;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
            if (playerx <= 0) {
                playerx = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerx >= 600) {
                playerx = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode()==KeyEvent.VK_ENTER) {
        	if(!play) {
        		score=0;
        		totalbricks=21;
        		ballposx=120;
        		ballposy=350;
        		ballxdir=-1;
        		ballydir=-2;
        		playerx=320;
        		
        		map=new MapGenerator(3,7);
        	}
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(play) {
    		
    		if (ballposx<=0) {
    			ballxdir=-ballxdir;
    		}
    		if (ballposx>=670) {
    			ballxdir=-ballxdir;
    		}
    		if (ballposy<=0) {
    			ballydir=-ballydir;
    		}
    		
    		Rectangle ballRect=new Rectangle(ballposx,ballposy,20,20);
    		Rectangle paddleRect=new Rectangle(playerx,550,100,8);
    		
    		
    		if (ballRect.intersects(paddleRect)) {
    			ballydir=-ballydir;
    		}
    		
    		
    		a:for(int i=0;i<map.map.length;i++) {
    			for(int j=0;j<map.map.length;i++) {
    				if(map.map[i][j]>0) {
    					int width=map.brickwidth;
    					int height=map.brickHeight;
    					
    					int brickxpos=80+j*width;
    					int brickypos=50+i*height;
    					
    					Rectangle brickRect=new Rectangle(brickxpos,brickypos,width,height);
    					if(ballRect.intersects(brickRect)){
    						map.setBrick(0, i, j);
    						totalbricks--;
    						score+=5;
    						
    						if(ballposx+19<=brickxpos || ballposx+1>=brickxpos+width) {
    							ballxdir=-ballxdir;
    						}
    						else {
    							ballydir=-ballydir;
    						}
    						
    						break a;
    					}
    				}
    			}
    		}
    			
    			
    		ballposx+=ballxdir;
    		ballposy+=ballydir;
    	}
    	repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
}
