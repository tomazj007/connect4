package connect4;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

public class Connect4 {
 
        /**
        *       Program:        Connect4.java
        *       Purpose:        Stacking disk game for 2 players
        *       Creator:        Chris Clarke
        *       Created:        19.08.2007
        *       Modified:       29.11.2012 (JFrame)
        */     
 
        public static void main(String[] args) {
                Connect4JFrame frame = new Connect4JFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}
 
class Connect4JFrame extends JFrame implements ActionListener {
	 
	
    private Button          btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    private Label           lblSpacer, stPotez;
    MenuItem                newMI, exitMI, redMI, yellowMI, ranPlay, humPlay;
    int[][]                 theArray;
    boolean                 end=false;
    boolean                 gameStart;
    public static final int BLANK = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;

    public static final int MAXROW = 6;     // 6 rows
    public static final int MAXCOL = 7;     // 7 columns

    public static final String SPACE = "                  "; // 18 spaces

    int activeColour = RED;
    
    int randomPlayer = 0;
    
    int firstMoves = 1;
    int secondMoves = 0;
   
    public Connect4JFrame() {
            setTitle("Connect4 by Chris Clarke");
            MenuBar mbar = new MenuBar();
            Menu fileMenu = new Menu("File");
            newMI = new MenuItem("New");
            newMI.addActionListener(this);
            fileMenu.add(newMI);
            exitMI = new MenuItem("Exit");
            exitMI.addActionListener(this);
            fileMenu.add(exitMI);
            mbar.add(fileMenu);
            Menu optMenu = new Menu("Options");
            redMI = new MenuItem("Red starts");
            redMI.addActionListener(this);
            optMenu.add(redMI);
            yellowMI = new MenuItem("Yellow starts");
            yellowMI.addActionListener(this);
            optMenu.add(yellowMI);
            mbar.add(optMenu);
            Menu players = new Menu("Players");
            ranPlay = new MenuItem("Nakljuèni");
            ranPlay.addActionListener(this);
            players.add(ranPlay);
            humPlay = new MenuItem("Èloveški");
            humPlay.addActionListener(this);
            players.add(humPlay);
            mbar.add(players);
           
            
            setMenuBar(mbar);

            // Build control panel.
            Panel panel = new Panel();
            
            
            btn1 = new Button("1");
            btn1.addActionListener(this);
            panel.add(btn1);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn2 = new Button("2");
            btn2.addActionListener(this);
            panel.add(btn2);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn3 = new Button("3");
            btn3.addActionListener(this);
            panel.add(btn3);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn4 = new Button("4");
            btn4.addActionListener(this);
            panel.add(btn4);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn5 = new Button("5");
            btn5.addActionListener(this);
            panel.add(btn5);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn6 = new Button("6");
            btn6.addActionListener(this);
            panel.add(btn6);
            lblSpacer = new Label(SPACE);
            panel.add(lblSpacer);

            btn7 = new Button("7");
            btn7.addActionListener(this);
            panel.add(btn7);

           
            
            add(panel, BorderLayout.NORTH);
           
            
            
            //second panel
            Panel moves = new Panel();
            stPotez = new Label(String.valueOf(firstMoves));
            moves.add(stPotez);
            JLabel movesText = new JLabel("no moves");
            movesText.setAlignmentX(20);
            movesText.setAlignmentY(30);
           
            movesText.setText("dd"+String.valueOf(firstMoves));
            moves.add(movesText);
            add(moves, BorderLayout.SOUTH);
            
            initialize();
            // Set to a reasonable size.
            setSize(1024, 768);
    } // Connect4

    public void initialize() {
            theArray=new int[MAXROW][MAXCOL];
            for (int row=0; row<MAXROW; row++)
                    for (int col=0; col<MAXCOL; col++)
                            theArray[row][col]=BLANK;
            gameStart=false;
    } // initialize

    public void paint(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
            for (int row=0; row<MAXROW; row++)
                    for (int col=0; col<MAXCOL; col++) {
                            if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
                            if (theArray[row][col]==RED) g.setColor(Color.RED);
                            if (theArray[row][col]==YELLOW) g.setColor(Color.YELLOW);
                            g.fillOval(160+100*col, 100+100*row, 100, 100);
                    }
            check4(g);
            Board board = new Board();
            
            for(int i= 0; i < 6; i++){
            	System.out.println(Arrays.toString(theArray[i]));
            	for(int j=0; j< 7; j++){
            	
            	if(theArray[i][j] == 2){
            	 board.set(j, Board.MARK_BLACK);
            	}
            	else if(theArray[i][j] == 1){
            	 board.set(j, Board.MARK_RED);
            	}
            }
            }
            System.out.println(board.get(4,6 ) + "printiing");// morem videt kaj shrani not....
            board.display();
            
           Minmax_test minmax = new Minmax_test(board, 9);
           int mark = Board.MARK_RED;
           int col = minmax.alphaBeta(mark);
          // if(activeColour == 1)
           //putDisk(col+1);
           
           System.out.println("board col " + col);
           System.out.println("board analized " + minmax.getBoardsAnalyzed());
           board.display();
            
    } // paint

    public void putDisk(int n) {
    // put a disk on top of column n
            // if game is won, do nothing
            if (end) return;
            gameStart=true;
            int row;
            n--;
            for (row=0; row<MAXROW; row++)
                    if (theArray[row][n]>0) break;
            if (row>0) {
                    theArray[--row][n]=activeColour;
                    if (activeColour==RED){
                    		firstMoves = firstMoves + 1; 
                    	
                    		System.out.println(firstMoves);
                            activeColour=YELLOW;
                    }else{
                    	secondMoves =+ 1;
                        activeColour=RED;
                        System.out.println(firstMoves);
                        
                    }
                    repaint();
            }
    }

    public void displayWinner(Graphics g, int n) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier", Font.BOLD, 100));
            if (n==RED)
                    g.drawString("Red wins!", 100, 400);
            else
                    g.drawString("Yellow wins!", 100, 400);
            end=true;
    }

    public void check4(Graphics g) {
    // see if there are 4 disks in a row: horizontal, vertical or diagonal
            // horizontal rows
            for (int row=0; row<MAXROW; row++) {
                    for (int col=0; col<MAXCOL-3; col++) {
                            int curr = theArray[row][col];
                            if (curr>0
                             && curr == theArray[row][col+1]
                             && curr == theArray[row][col+2]
                             && curr == theArray[row][col+3]) {
                                    displayWinner(g, theArray[row][col]);
                            }
                    }
            }
            // vertical columns
            for (int col=0; col<MAXCOL; col++) {
                    for (int row=0; row<MAXROW-3; row++) {
                            int curr = theArray[row][col];
                            if (curr>0
                             && curr == theArray[row+1][col]
                             && curr == theArray[row+2][col]
                             && curr == theArray[row+3][col])
                                    displayWinner(g, theArray[row][col]);
                    }
            }
            // diagonal lower left to upper right
            for (int row=0; row<MAXROW-3; row++) {
                    for (int col=0; col<MAXCOL-3; col++) {
                            int curr = theArray[row][col];
                            if (curr>0
                             && curr == theArray[row+1][col+1]
                             && curr == theArray[row+2][col+2]
                             && curr == theArray[row+3][col+3])
                                    displayWinner(g, theArray[row][col]);
                    }
            }
            // diagonal upper left to lower right
            for (int row=MAXROW-1; row>=3; row--) {
                    for (int col=0; col<MAXCOL-3; col++) {
                            int curr = theArray[row][col];
                            if (curr>0
                             && curr == theArray[row-1][col+1]
                             && curr == theArray[row-2][col+2]
                             && curr == theArray[row-3][col+3])
                                    displayWinner(g, theArray[row][col]);
                    }
            }
            if(randomPlayer == 1){
            randomPlayer();
            }
    } // end check4
    
    public void randomPlayer(){
    	if(activeColour  == RED){
    		double value = Math.random();
    		System.out.println(value);
    		System.out.println(Arrays.deepToString(theArray) + "array");
    		
    		if(value > 0.86){
    			if(checkRow(0)){
    			putDisk(1);
    			}
    			randomPlayer();
    		}
    		else if(value < 0.86 && value > 0.72){
    			if(checkRow(1)){
    				putDisk(2);	
    			}
    			randomPlayer();
    		}
    		else if(value < 0.72 && value >0.58){
    			if(checkRow(2)){
    			putDisk(3);
    			}
    			randomPlayer();
    		}
    		else if(value < 0.58 && value > 0.44){
    			if(checkRow(3)){
    			putDisk(4);
    			}
    			randomPlayer();
    		}
    		else if(value < 0.44 && value > 0.3){
    			if(checkRow(4)){
    			putDisk(5);
    			}
    			randomPlayer();
    		}
    		else if(value < 0.3 && value > 0.16){
    			if(checkRow(5)){
    			putDisk(6);
    			}
    			randomPlayer();
    		}
    		else{
    			if(checkRow(6))
    			putDisk(7);
    			
    			randomPlayer();
    		}
    	}
    }
    
    public boolean checkRow(int slot){
    	
    	if(theArray[0][slot] == 0){
    		return true;
    	}
    	
    	return false;
    }

    public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn1)
                    putDisk(1);
            else if (e.getSource() == btn2)
                    putDisk(2);
            else if (e.getSource() == btn3)
                    putDisk(3);
            else if (e.getSource() == btn4)
                    putDisk(4);
            else if (e.getSource() == btn5)
                    putDisk(5);
            else if (e.getSource() == btn6)
                    putDisk(6);
            else if (e.getSource() == btn7)
                    putDisk(7);
            else if (e.getSource() == newMI) {
                    end=false;
                    initialize();
                    repaint();
            } else if (e.getSource() == exitMI) {
                    System.exit(0);
            }else if(e.getSource() == ranPlay ) {
            		randomPlayer = 1;
            }else if(e.getSource() == humPlay ) {
        		randomPlayer = 0;
            }else if (e.getSource() == redMI) {
                    // don't change colour to play in middle of game
                    if (!gameStart) activeColour=RED;
            } else if (e.getSource() == yellowMI) {
                    if (!gameStart) activeColour=YELLOW;
            }
    } // end ActionPerformed

} // class