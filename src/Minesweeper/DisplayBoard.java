package Minesweeper;
/**
 * @file DisplayBoard.java
 * @author Victoria Charvis
 * @date 21 Nov 2015
 * <p>
 * @brief Displays tiles in a grid and handles user interaction
 */

import javax.swing.*;
import java.awt.*;

/**
 * @class DisplayBoard
 * @brief Displays tiles in a grid and handles user interaction
 */
public class DisplayBoard extends JPanel {

	// stores the MainForm object
	private final MainForm m_mainForm;

	// stores all the internal information for the board
	private final Board m_board;

	/**
	 * Constructs a DisplayBoard object
	 * Calls drawBoard to set up the board
	 *
	 * @param mainForm	the frame this panel belongs to
	 * @param board		the board object with the data for the board
	 */
	public DisplayBoard(MainForm mainForm, Board board) {
		m_mainForm = mainForm;
		m_board = board;
		drawBoard(m_board.getBoardSize());
	}

	/**
	 * Sets up the tiles and displays them on a grid
	 * 
	 * @param boardSize the width/height of the board
	 */
	private void drawBoard(int boardSize) {

		// set the panel to Grid Layout and the amount of rows, columns and
		// gaps between components
		setLayout(new GridLayout(boardSize, boardSize, Tile.PADDING,
				                 Tile.PADDING));

		// set the tiles to be added in left to right order
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		m_board.setDisplay(this);
	}

	/**
	 * Informs user game is over and asks them to play again
	 *
	 * @param won Specifies if user won the game
	 */
	public void gameOver(boolean won) {
		m_mainForm.gameOver(won);
	}

	/**
	 * Update the display header
	 */
	public void updateHeader() {
		m_mainForm.updateHeader();
	}

}
