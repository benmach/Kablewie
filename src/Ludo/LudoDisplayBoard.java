package Ludo;

/**
 * @file LudoDisplayBoard.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * 
 * @brief Displays tiles in a grid and handles user interaction
 */

import javax.swing.*;

import java.awt.*;

/**
 * @class LudoDisplayBoard
 * @brief Displays tiles in a grid and handles user interaction
 */
public class LudoDisplayBoard extends JPanel {

	// stores the MainForm object
	private final LudoMainForm m_mainForm;

	// stores all the internal information for the board
	private final LudoBoard m_board;

	/**
	 * Constructs a LudoDisplayBoard object Calls drawBoard to set up the board
	 *
	 * @param mainForm
	 *            the frame this panel belongs to
	 * @param board
	 *            the board object with the data for the board
	 */
	public LudoDisplayBoard(LudoMainForm mainForm, LudoBoard board) {
		m_mainForm = mainForm;
		m_board = board;
		drawBoard();
	}

	/**
	 * Sets up the tiles and displays them on a grid
	 * 
	 */
	private void drawBoard() {

		// set the panel to Grid Layout and the amount of rows, columns and
		// gaps between components
		setLayout(new GridLayout(m_board.BOARD_SIZE, m_board.BOARD_SIZE,
				LudoTile.PADDING, LudoTile.PADDING));

		// set the tiles to be added in left to right order
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		m_board.setDisplay(this);
	}

	/**
	 * Get method for the variable m_mainform
	 * 
	 * @return the MainForm of the game
	 * 
	 */
	public LudoMainForm getMainForm() {
		return this.m_mainForm;
	}

	/**
	 * Get method for the variable m_board
	 * 
	 * @return the board of the game
	 * 
	 */
	public LudoBoard getBoard() {
		return this.m_board;
	}

}
