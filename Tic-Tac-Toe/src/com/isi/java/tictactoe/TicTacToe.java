package com.isi.java.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class TicTacToe implements ActionListener, ActionPerformed, CheckWin, ShowGame {
	
	private JFrame frame;
	final String VERSION = "1.0-BETA";
	
	
	//Setting up ALL the variables
	JFrame window = new JFrame("Tic-Tac-Toe " + VERSION);

	JMenuBar mnuMain = new JMenuBar();
	
	JMenuItem mnuNewGame = new JMenuItem("New Game");
	JMenuItem mnuInstruction = new JMenuItem("Instructions");
	JMenuItem mnuAbout = new JMenuItem("About");
	JMenuItem mnuExit = new JMenuItem("Exit");
			

	JButton btn1v1 = new JButton("Player vs Player");
	JButton btn1vCPU = new JButton("Player vs CPU");
	JButton btnBack = new JButton("<--back");
	
	JButton btnEmpty[] = new JButton[10];

	JPanel pnlNewGame = new JPanel();
	JPanel pnlNorth = new JPanel();
	JPanel pnlSouth = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlPlayingField = new JPanel();
	
	JLabel lblTitle = new JLabel("        Tic-Tac-Toe");
	
	JTextArea txtMessage = new JTextArea();

	final int winCombo[][] = new int[][] {
		{1, 2, 3}, {1, 4, 7}, {1, 5, 9},
		{4, 5, 6}, {2, 5, 8}, {3, 5, 7},
		{7, 8, 9}, {3, 6, 9}
		/*Horizontal Wins*/ /*Vertical Wins*/ /*Diagonal Wins*/
	};
	
	final int X = 412;
	final int Y = 268;
	final int color = 190;
	private final JLabel lblCopyright = new JLabel("\u00A9");
	
	boolean inGame = false;
	boolean win = false;
	boolean btnEmptyClicked = false;
	String message;
	int turn = 1;
	int wonNumber1 = 1;
	int wonNumber2 = 1;
	int wonNumber3 = 1;

	public TicTacToe() {                                 //Setting game properties and layout and style...
		
		window.setSize(642, 523);
		window.setLocation(450, 260);
		window.setResizable(true);
		window.getContentPane().setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting Panel layouts and properties
		pnlNewGame.setLayout(new GridLayout(2, 1, 2, 10));
		pnlNorth.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   //change mouse to hand sign when you hover to menu bar.
		pnlNorth.setToolTipText("This is a Menu Bar");                  //set the tool tip bar if you hover on menu
		pnlNorth.setBorder(new TitledBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)), "MENU", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 255))); //use matteborder for menu heading and put color
		
		pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlSouth.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));   //change mouse to move cursor sign when you hover to game screen.
		pnlSouth.setToolTipText("This is the game screen ");
		pnlSouth.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)), "Game Screen", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 128, 128))); // same as above
		pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));

		pnlNorth.setBackground(new Color(192, 192, 192));
		pnlSouth.setBackground(new Color(192, 192, 192));

		pnlTop.setBackground(new Color(color, color, color));
		pnlBottom.setBackground(new Color(color, color, color));

		pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlNewGame.setBackground(Color.blue);
		mnuNewGame.setFont(new Font("Segoe UI", Font.BOLD, 14));

		//Adding menu items to menu bar
		mnuMain.add(mnuNewGame);
		mnuInstruction.setFont(new Font("Segoe UI", Font.BOLD, 14));
		mnuMain.add(mnuInstruction);
		mnuAbout.setFont(new Font("Segoe UI", Font.BOLD, 14));
		mnuMain.add(mnuAbout);
		mnuExit.setFont(new Font("Segoe UI", Font.BOLD, 14));
		mnuMain.add(mnuExit);//---->Menu Bar Complete

		//Adding buttons to NewGame panel
		pnlNewGame.add(btn1v1);
		pnlNewGame.add(btn1vCPU);

		//Adding Action Listener to all the Buttons and Menu Items
		mnuNewGame.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuInstruction.addActionListener(this);
		mnuAbout.addActionListener(this);
		btn1v1.addActionListener(this);
		btn1vCPU.addActionListener(this);
		btnBack.addActionListener(this);

		//Setting up the playing field
		pnlPlayingField.setLayout(new GridLayout(3, 3, 2, 2));
		pnlPlayingField.setLayout(new GridLayout(4, 4, 2, 2));
		pnlPlayingField.setBackground(Color.black);
		for(int i=1; i<=9; i++) {
			btnEmpty[i] = new JButton();
			btnEmpty[i].setBackground(new Color(220, 220, 220));
			btnEmpty[i].addActionListener(this);
			pnlPlayingField.add(btnEmpty[i]);
		}
		//Adding everything needed to pnlNorth and pnlSouth
		pnlNorth.add(mnuMain);
		lblTitle.setIcon(new ImageIcon("C:\\ISI\\Java\\Java projects\\Tic-Tac-Toe\\Tic-Tac-Toe-Game-grey.png"));
		lblTitle.setIconTextGap(-50);
		lblTitle.setFont(new Font("Source Serif Pro Black", Font.BOLD | Font.ITALIC, 29));    //The GameSceen Tic-Tac-Toe is bold italic fonted and sized(29).
		pnlSouth.add(lblTitle);

		//Adding to window and Showing window
		window.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		window.getContentPane().add(pnlSouth, BorderLayout.CENTER);
		lblCopyright.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCopyright.setLocation(new Point(60, 0));
		
		pnlSouth.add(lblCopyright);
		window.setVisible(true);
	}

	//-------------------START OF ACTION PERFORMED CLASS-------------------------//
	@Override
	public void actionPerformed(ActionEvent click) {
		Object source = click.getSource();
		for(int i=1; i<=9; i++) {
			if(source == btnEmpty[i] && turn < 10) {
				btnEmptyClicked = true;
				if(!(turn % 2 == 0))
					btnEmpty[i].setText("X");
				else
					btnEmpty[i].setText("O");
				btnEmpty[i].setEnabled(false);
				pnlPlayingField.requestFocus();
				turn++;
			}
		}
		if(btnEmptyClicked) {
			checkWin();
			btnEmptyClicked = false;
		}
		if(source == mnuNewGame) {
			clearPanelSouth();
			pnlSouth.setLayout(new GridLayout(2, 1, 2, 5));
			pnlTop.add(pnlNewGame);
			pnlBottom.add(btnBack);
			pnlSouth.add(pnlTop);
			pnlSouth.add(pnlBottom);

		}
		else if(source == btn1v1) {
			if(inGame) {
				int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
						"your current game will be lost..." + "\n" +
						"Are you sure you want to continue?",
						"Quit Game?" ,JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					inGame = false;
				}
			}
			if(!inGame) {
				btnEmpty[wonNumber1].setBackground(new Color(220, 220, 220));
				btnEmpty[wonNumber2].setBackground(new Color(220, 220, 220));
				btnEmpty[wonNumber3].setBackground(new Color(220, 220, 220));
				turn = 1;
				for(int i=1; i<10; i++) {
					btnEmpty[i].setText("");
					btnEmpty[i].setEnabled(true);
				}
				win = false;
				showGame();

			}
		}
		else if(source == btn1vCPU) {
			JOptionPane.showMessageDialog(null, "Coming Soon!!");
		}
		else if(source == mnuExit) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
					"Exit Game" ,JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		else if(source == mnuInstruction || source == mnuAbout) {
			clearPanelSouth();
			String message = "";
			txtMessage.setBackground(new Color(color, color, color));
			if(source == mnuInstruction) {
				message = "Instructions:\n\n" +
						"Your goal is to be the first player to get 3 X's or O's in a\n" +
						"row. (horizontally, diagonally, or vertically)";
			} else {
				message = "About:\n\n" +
						"Title: Tic-Tac-Toe\n" +
						"Author: Gokaran Shubham and Sanjay\n" +
						"Version: " + VERSION + "\n";
			}
			txtMessage.setEditable(false);
			txtMessage.setText(message);
			pnlSouth.setLayout(new GridLayout(2, 1, 2, 5));
			pnlTop.add(txtMessage);
			pnlBottom.add(btnBack);
			pnlSouth.add(pnlTop);
			pnlSouth.add(pnlBottom);
		}
		else if(source == btnBack) {
			if(inGame)
				showGame();
			else {
				clearPanelSouth();
				pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
				pnlNorth.setVisible(true);
				pnlSouth.add(lblTitle);
			}
		}
		pnlSouth.setVisible(false);
		pnlSouth.setVisible(true);
	}
	//-------------------END OF ACTION PERFORMED CLASS-------------------------//

	/*
----------------------------------
Start of all the other methods. |
----------------------------------
	 */
	@Override
	public void showGame() { // Shows the Playing Field
		// *IMPORTANT*- Does not start out brand new (meaning just shows what it had before)
		clearPanelSouth();
		inGame = true;
		pnlSouth.setLayout(new BorderLayout());
		pnlSouth.add(pnlPlayingField, BorderLayout.CENTER);
		pnlPlayingField.requestFocus();
	}

	@Override
	public void checkWin() { // checks if there are 3 symbols in a row vertically, diagonally, or horizontally.
		// then shows a message and disables buttons.
		for(int i=0; i<7; i++) {
			if(
					!"".equals(btnEmpty[winCombo[i][0]].getText()) &&
					btnEmpty[winCombo[i][0]].getText().equals(btnEmpty[winCombo[i][1]].getText()) &&
					// if {1 == 2 && 2 == 3}
					btnEmpty[winCombo[i][1]].getText().equals(btnEmpty[winCombo[i][2]].getText())
					/*
The way this checks the if someone won is:
First: it checks if the btnEmpty[x] is not equal to an empty string- x being the array number
inside the multi-dementional array winCombo[checks inside each of the 7 sets][the first number]
Second: it checks if btnEmpty[x] is equal to btnEmpty[y]- x being winCombo[each set][the first number]
y being winCombo[each set the same as x][the second number] (So basically checks if the first and
second number in each set is equal to each other)
Third: it checks if btnEmtpy[y] is equal to btnEmpty[z]- y being the same y as last time and z being
winCombo[each set as y][the third number]
Conclusion: So basically it checks if it is equal to the btnEmpty is equal to each set of numbers
					 */
					) {
				win = true;
				wonNumber1 = winCombo[i][0];
				wonNumber2 = winCombo[i][1];
				wonNumber3 = winCombo[i][2];
				btnEmpty[wonNumber1].setBackground(Color.white);
				btnEmpty[wonNumber2].setBackground(Color.white);
				btnEmpty[wonNumber3].setBackground(Color.white);
				break;
			}
		}
		if(win || (!win && turn>9)) {
			if(win) {
				if(turn % 2 == 0)
					message = "X has won!";
				else
					message = "O has won!";
				win = false;
			} else if(!win && turn>9) {
				message = "Both players have tied!\nBetter luck next time.";
			}
			JOptionPane.showMessageDialog(null, message);
			for(int i=1; i<=9; i++) {
				btnEmpty[i].setEnabled(false);
			}
		}
	}

	public void clearPanelSouth() { //Removes all the possible panels
		//that pnlSouth, pnlTop, pnlBottom
		//could have.
		pnlSouth.remove(lblTitle);
		pnlSouth.remove(pnlTop);
		pnlSouth.remove(pnlBottom);
		pnlSouth.remove(pnlPlayingField);
		pnlTop.remove(pnlNewGame);
		pnlTop.remove(txtMessage);
		pnlBottom.remove(btnBack);
	}

	
}
