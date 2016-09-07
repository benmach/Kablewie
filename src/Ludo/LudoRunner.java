package Ludo;

import java.awt.Color;


import java.awt.Point;


import javax.swing.JPanel;

/**
 * @file LudoRunner.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @brief Class implementing the runners which will play on the board for the players
 */

/**
 * @class LudoRunner
 * @brief Class implementing the runners which will play on the board for the players
 */

public class LudoRunner extends JPanel {

	// constants
	public final static int TOUR_SIZE = 60;

	// variables
	private final int m_number;
	private final LudoPlayer m_player;
	private final LudoBoard m_board;
	public final Point[] m_tour;

	private int m_position;

	/**
	 * Constructor that's used to store the player's name
	 * 
	 * @param player
	 *            LudoPlayer who owns the runner
	 * @param num
	 *            Number of the runner
	 * @param board
	 *            LudoBoard of the game
	 */
	public LudoRunner(LudoPlayer player, int num, LudoBoard board) {

		// initialize the variables
		m_position = 0;
		m_board = board;
		m_number = num;
		m_player = player;
		m_tour = new Point[TOUR_SIZE];

		// construct the tour if the player's color is blue
		if (this.getColor() == Color.BLUE) {
			if (num == 1) {
				m_tour[0] = new Point(2, 2);
			}
			if (num == 2) {
				m_tour[0] = new Point(2, 3);
			}
			if (num == 3) {
				m_tour[0] = new Point(3, 2);
			} else if (num == 4) {
				m_tour[0] = new Point(3, 3);
			}
			int inc = 0;
			for (int y = 1; y < 6; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			for (int x = 5; x >= 0; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			inc++;
			m_tour[inc] = new Point(0, 7);
			for (int x = 0; x < 6; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			for (int y = 9; y < 15; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 14);
			for (int y = 14; y >= 9; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			for (int x = 9; x < 15; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			inc++;
			m_tour[inc] = new Point(14, 7);
			for (int x = 14; x >= 9; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			for (int y = 5; y >= 0; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 0);
			inc++;
			m_tour[inc] = new Point(6, 0);
			inc++;
			m_tour[inc] = new Point(6, 1);
			for (int y = 1; y < 7; y++) {
				inc++;
				m_tour[inc] = new Point(7, y);
			}

		}
		// construct the tour if the player's color is green
		if (this.getColor() == Color.GREEN) {
			if (num == 1) {
				m_tour[0] = new Point(11, 2);
			}
			if (num == 2) {
				m_tour[0] = new Point(11, 3);
			}
			if (num == 3) {
				m_tour[0] = new Point(12, 2);
			} else if (num == 4) {
				m_tour[0] = new Point(12, 3);
			}
			int inc = 0;
			for (int x = 13; x >= 9; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			for (int y = 5; y >= 0; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 0);
			for (int y = 0; y < 6; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			for (int x = 5; x >= 0; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			inc++;
			m_tour[inc] = new Point(0, 7);
			for (int x = 0; x < 6; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			for (int y = 9; y < 15; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 14);
			for (int y = 14; y >= 9; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			for (int x = 9; x < 15; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			inc++;
			m_tour[inc] = new Point(14, 7);
			inc++;
			m_tour[inc] = new Point(14, 6);
			inc++;
			m_tour[inc] = new Point(13, 6);
			for (int x = 13; x >= 8; x--) {
				inc++;
				m_tour[inc] = new Point(x, 7);
			}
		}
		// construct the tour if the player's color is yellow
		if (this.getColor() == Color.YELLOW) {
			if (num == 1) {
				m_tour[0] = new Point(11, 11);
			}
			if (num == 2) {
				m_tour[0] = new Point(11, 12);
			}
			if (num == 3) {
				m_tour[0] = new Point(12, 11);
			} else if (num == 4) {
				m_tour[0] = new Point(12, 12);
			}
			int inc = 0;
			for (int y = 13; y >= 9; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			for (int x = 9; x < 15; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			inc++;
			m_tour[inc] = new Point(14, 7);
			for (int x = 14; x >= 9; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			for (int y = 5; y >= 0; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 0);
			for (int y = 0; y < 6; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			for (int x = 5; x >= 0; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			inc++;
			m_tour[inc] = new Point(0, 7);
			for (int x = 0; x < 6; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			for (int y = 9; y < 15; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 14);
			inc++;
			m_tour[inc] = new Point(8, 14);
			inc++;
			m_tour[inc] = new Point(8, 13);
			for (int y = 13; y >= 8; y--) {
				inc++;
				m_tour[inc] = new Point(7, y);
			}
		}
		// construct the tour if the player's color is red
		else if (this.getColor() == Color.RED) {
			if (num == 1) {
				m_tour[0] = new Point(2, 11);
			}
			if (num == 2) {
				m_tour[0] = new Point(2, 12);
			}
			if (num == 3) {
				m_tour[0] = new Point(3, 11);
			} else if (num == 4) {
				m_tour[0] = new Point(3, 12);
			}
			int inc = 0;
			for (int x = 1; x < 6; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			for (int y = 9; y < 15; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 14);
			for (int y = 14; y >= 9; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			for (int x = 9; x < 15; x++) {
				inc++;
				m_tour[inc] = new Point(x, 8);
			}
			inc++;
			m_tour[inc] = new Point(14, 7);
			for (int x = 14; x >= 9; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			for (int y = 5; y >= 0; y--) {
				inc++;
				m_tour[inc] = new Point(8, y);
			}
			inc++;
			m_tour[inc] = new Point(7, 0);
			for (int y = 0; y < 6; y++) {
				inc++;
				m_tour[inc] = new Point(6, y);
			}
			for (int x = 5; x >= 0; x--) {
				inc++;
				m_tour[inc] = new Point(x, 6);
			}
			inc++;
			m_tour[inc] = new Point(0, 7);
			inc++;
			m_tour[inc] = new Point(0, 8);
			inc++;
			m_tour[inc] = new Point(1, 8);
			for (int x = 1; x < 7; x++) {
				inc++;
				m_tour[inc] = new Point(x, 7);
			}
		}
	}

	/**
	 * says whether the runner is at his player base or not
	 * 
	 * @return a boolean
	 */
	public boolean isBased() {
		return this.getPosition() == 0;
	}

	/**
	 * place the runner at his player base
	 * 
	 */
	public void setBased() {
		this.m_position = 0;
	}

	/**
	 * get the color of the runner according to his player color
	 * 
	 * @return the color of the runner
	 * 
	 */
	public Color getColor() {
		return m_player.getColor();
	}

	/**
	 * give the next position of the runner in his tour
	 * 
	 * @return the integer corresponding to the next position of the runner
	 * 
	 */
	public int getNextPosition() {
		int diceValue = m_board.getDisplay().getMainForm().getM_header()
				.getDice().getValue();
		int nextPos = this.getPosition() + diceValue;
		if (diceValue == 6) {
			if (this.isBased()) {
				nextPos = 1;
			}
		}
		return nextPos;
	}

	/**
	 * get method for the variable m_number
	 * 
	 * @return the number of the runner in the array of the corresponding player
	 * 
	 */
	public int getNumber() {
		return m_number;
	}

	/**
	 * get method for the variable m_player
	 * 
	 * @return the LudoPlayer using this runner
	 * 
	 */
	public LudoPlayer getPlayer() {
		return m_player;
	}

	/**
	 * get method for the variable m_board
	 * 
	 * @return the board on which the runner is placed
	 * 
	 */
	public LudoBoard getBoard() {
		return m_board;
	}

	/**
	 * get method for the variable m_tour
	 * 
	 * @return the array of Point the runner can move on
	 * 
	 */
	public Point[] getTour() {
		return m_tour;
	}

	/**
	 * get method for the variable m_position
	 * 
	 * @return the position of the runner
	 * 
	 */
	public int getPosition() {
		return m_position;
	}

	/**
	 * set method for the variable m_position
	 * 
	 * @param pos
	 *            the integer to instantiate the position of the runner
	 * 
	 */
	public void setPosition(int pos) {
		m_position = pos;
	}

}
