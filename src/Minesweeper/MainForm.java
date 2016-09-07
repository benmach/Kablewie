package Minesweeper;
/**
 * @file MainForm.java
 * @author Alex
 * @date 25 Nov 2015
 * 
 * @brief Displays the main form of the game
 */

import javax.swing.*;
import java.awt.*;

/**
 * @class MainForm
 * @brief Displays the main form of the game
 */
public class MainForm extends JFrame {
	
	// Path of icon
	private static final String ICON_PATH = "icon.png";
	private static final String FORM_NAME = "Kablewie";

	//Dimensions of header
	private static final int HEADER_WIDTH = 600;
	private static final int HEADER_HEIGHT = 75;
	
	//Animation speed
	private static final int ANIMATION_SPEED = 5;
	
	// header that displays information to user
	private final DisplayHeader m_header;

	// Kablewie board
	private final DisplayBoard m_board;
	
	private final String m_playerName;
	private final int m_boardSize;
	private Timer m_animation;

	/**
	 * Constructor for creating the mainform object
	 * @param board the board object to include in the form
	 * @param playerName the player's name to be displayed in the header
	 * @param elapsedTime the time that has past in the current game, 
	 * 		useful when loading a game
	 */
	public MainForm(Board board, String playerName, long elapsedTime) {
		super(FORM_NAME);
		m_playerName = playerName;
		m_boardSize = board.getBoardSize();

		m_header = new DisplayHeader(playerName, elapsedTime);
		m_header.setBoard(board);

		m_board = new DisplayBoard(this, board);

		setupForm();
	}

	/**
	 * method to create the form and display it
	 */
	private void setupForm() {

		// get form size
		int boardLength = (m_boardSize * (Tile.TILE_LENGTH + Tile.PADDING))
				+ Tile.PADDING;
		int width = Math.max(HEADER_WIDTH, boardLength);

		//Set icon
		ImageIcon icon = new ImageIcon(ICON_PATH);
		setIconImage(icon.getImage());

		// create content panel
		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new Dimension(width, HEADER_HEIGHT
				+ boardLength));
		setContentPane(panel);
		pack();

		setLocationRelativeTo(null);

		m_header.setBounds((getWidth() - HEADER_WIDTH) / 2, 0, width, HEADER_HEIGHT);
		panel.add(m_header);

		// determine the board's x-coordinate
		int boardX;
		if (width > boardLength) {
			boardX = (HEADER_WIDTH - boardLength) / 2;
		} else {
			boardX = 0;
		}

		m_board.setBounds(boardX, HEADER_HEIGHT, boardLength, boardLength);
		panel.add(m_board);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	* Informs user game is over and asks them to play again
	*
	* @param won Specifies if user won the game
	*/
	public void gameOver(boolean won) {

		//Show animation
		m_animation = new Timer(1, e -> {
			int boardTop = m_board.getY() + ANIMATION_SPEED;
			if(boardTop <= getHeight()) {
				m_board.setLocation(m_board.getX(), boardTop);
			} else {

				//If animation is finished ask user to play again
				m_animation.stop();
				showRestart(won);
			}
		});

		m_animation.start();
		m_animation.setInitialDelay(0);
		m_header.stopTimer();

	}

	/**
	 * Prompts the user if they want to play again
	 *
	 * @param won Whether the previous game was won
	 */
	private void showRestart(boolean won) {
		String prompt;

		// Ask if user wants to play again
		if(won) {
			prompt = "You won! Do you want to play again?";
		} else {
			prompt = "You lost! Do you want to play again?";
		}

		int answer = JOptionPane.showConfirmDialog(this, prompt, "Kablewie",
				JOptionPane.YES_NO_OPTION);
		dispose();

		// If user said yes, show new game, else exit
		if(answer == JOptionPane.YES_OPTION) {
			Board board = new Board(m_boardSize, m_boardSize);
			board.setPlayer(new HumanPlayer(m_playerName));
			MainForm newGame = new MainForm(board, m_playerName, 0);
			newGame.setVisible(true);
		} else {
			System.exit(0);
		}
	}

	/**
	 * Update the display header
	 */
	public void updateHeader() {
		m_header.updateDisplay();
	}
}
