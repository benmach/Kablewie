package Ludo;

/**
 * @file LudoMainForm.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * 
 * @brief Displays the main form of the game
 */

import javax.swing.*;

import Minesweeper.DisplayBoard;
import Minesweeper.FirstMenuForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @class LudoMainForm
 * @brief Displays the main form of the game
 */
public class LudoMainForm extends JFrame implements ActionListener {

	// Path of icon
	private static final String ICON_PATH = "icon.png";
	private static final String FORM_NAME = "Ludo";

	// Dimensions of header
	private static final int HEADER_WIDTH = 600;
	private static final int HEADER_HEIGHT = 75;

	// Animation speed
	private static final int ANIMATION_SPEED = 5;

	// header that displays information to user
	private final LudoDisplayHeader m_header;

	// ludo board
	private final LudoDisplayBoard m_displayboard;

	// panel which displays the name and the type of the players
	private JPanel m_playersPanel;

	private Timer m_animation;

	// JMenu objects
	private final JMenuBar m_JMenubar;
	private final JMenu m_fileJMenu;
	private final JMenu m_helpJMenu;
	private final JMenuItem m_restartJMenuItem;
	private final JMenuItem m_mainmenuJMenuItem;
	private final JMenuItem m_exitJMenuItem;
	private final JMenuItem m_rulesJMenuItem;

	/**
	 * Constructor for creating the mainform object
	 * 
	 * @param board
	 *            the board object to include in the form
	 * @param elapsedTime
	 *            the time that has past in the current game, useful when
	 *            loading a game
	 */
	public LudoMainForm(LudoBoard board, long elapsedTime) {
		super(FORM_NAME);

		// initializing the JMenuBar
		m_JMenubar = new JMenuBar();
		m_fileJMenu = new JMenu("File");
		m_helpJMenu = new JMenu("Help");
		m_restartJMenuItem = new JMenuItem("Restart the game");
		m_restartJMenuItem.addActionListener(this);
		m_mainmenuJMenuItem = new JMenuItem("Back to the main menu");
		m_mainmenuJMenuItem.addActionListener(this);
		m_exitJMenuItem = new JMenuItem("Exit");
		m_exitJMenuItem.addActionListener(this);
		m_rulesJMenuItem = new JMenuItem("Rules");
		m_rulesJMenuItem.addActionListener(this);

		m_fileJMenu.add(m_restartJMenuItem);
		m_fileJMenu.add(m_mainmenuJMenuItem);
		m_fileJMenu.add(m_exitJMenuItem);
		m_helpJMenu.add(m_rulesJMenuItem);
		m_JMenubar.add(m_fileJMenu);
		m_JMenubar.add(m_helpJMenu);
		setJMenuBar(m_JMenubar);

		// initializing the panels
		m_displayboard = new LudoDisplayBoard(this, board);

		m_header = new LudoDisplayHeader(elapsedTime, board);
		

		m_playersPanel = this.playersPanel();

		// packing the different panels together
		setupForm();
		
		
	}

	/**
	 * method to create the form and display it
	 */
	private void setupForm() {

		// get form size
		int boardLength = (m_displayboard.getBoard().BOARD_SIZE * (LudoTile.TILE_LENGTH + LudoTile.PADDING))
				+ LudoTile.PADDING;

		// Set icon
		ImageIcon icon = new ImageIcon(ICON_PATH);
		setIconImage(icon.getImage());

		// create content panel
		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new Dimension(HEADER_WIDTH, HEADER_HEIGHT
				+ boardLength));
		setContentPane(panel);
		pack();

		setLocationRelativeTo(null);

		// adding display header
		m_header.setBounds((getWidth() - HEADER_WIDTH) / 2, 0, HEADER_WIDTH,
				HEADER_HEIGHT);
		panel.add(m_header);

		// determine the board's x-coordinate
		int boardX = (HEADER_WIDTH - boardLength) / 2;

		// adding the players panel
		m_playersPanel.setBounds(0, HEADER_HEIGHT + boardLength / 4, boardX,
				HEADER_HEIGHT * 2);
		panel.add(m_playersPanel);

		// adding the board
		m_displayboard.setBounds(boardX, HEADER_HEIGHT, boardLength, boardLength);
		panel.add(m_displayboard);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Animate the display when the game is over
	 */
	public void gameOver() {

		// Show animation
		m_animation = new Timer(1, e -> {
			int boardTop = m_displayboard.getY() + ANIMATION_SPEED;
			if (boardTop <= getHeight()) {
				m_displayboard.setLocation(m_displayboard.getX(), boardTop);
			} else {

				// If animation is finished ask user to play again
				m_animation.stop();
				showRestart();
			}
		});
		m_animation.start();
		m_animation.setInitialDelay(0);
		m_header.stopTimer();

	}

	/**
	 * Prompts the user if they want to play again
	 *
	 */
	private void showRestart() {
		String prompt;

		// Ask if user wants to play again

		prompt = m_displayboard.getBoard().getCurrentPlayer().getPlayerName()
				+ " won! Do you want to play again?";

		int answer = JOptionPane.showConfirmDialog(this, prompt, "Ludo",
				JOptionPane.YES_NO_OPTION);
		dispose();

		// If user said yes, show new game, else exit
		if (answer == JOptionPane.YES_OPTION) {
			LudoMenu menu = new LudoMenu();
			menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			menu.setVisible(true);
		} else {
			System.exit(0);
		}
	}

	/**
	 * Creates a panel to see the playing players, their type and the current
	 * player
	 * 
	 * @return the JPanel containing the names and types of the playing players
	 *
	 */
	public JPanel playersPanel() {
		LudoBoard board = m_displayboard.getBoard();
		JPanel m_playersPanel = new JPanel();
		m_playersPanel.setLayout(new FlowLayout());

		for (int i = 0; i < board.NB_PLAYER; i++) {
			if (board.getPlayers()[i].isPlaying()) {
				JLabel m_playerLabel = new JLabel(
						board.getPlayers()[i].getPlayerName() + " ("
								+ board.getPlayers()[i].printType() + ")");
				m_playerLabel.setForeground(board.getPlayers()[i].getColor());
				if (board.getPlayers()[i].isCurrentlyPlaying()) {
					m_playerLabel.setBorder(BorderFactory.createLineBorder(
							Color.BLACK, 2));
				}
				m_playersPanel.add(m_playerLabel);
			}

		}

		return m_playersPanel;
	}

	/**
	 * Update the display of the players panel (when the current player is
	 * changed)
	 *
	 */
	public void updateDisplay() {
		m_playersPanel = playersPanel();
		this.setupForm();
	}

	/**
	 * handle the listeners of the menu
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_exitJMenuItem) {
			System.exit(0);
		}
		if (e.getSource() == m_mainmenuJMenuItem) {
			this.setVisible(false);
			// Create and display menu form
			FirstMenuForm newMenu = new FirstMenuForm();
			newMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			newMenu.setVisible(true);
		}
		if (e.getSource() == m_restartJMenuItem) {
			this.setVisible(false);
			// Create and display Ludo
			String[] playerNames = new String[m_displayboard.getBoard().NB_PLAYER];
			boolean[] arePlaying = new boolean[m_displayboard.getBoard().NB_PLAYER];
			boolean[] areHumans = new boolean[m_displayboard.getBoard().NB_PLAYER];

			for (int i = 0; i < m_displayboard.getBoard().NB_PLAYER; i++) {
				playerNames[i] = m_displayboard.getBoard().getPlayers()[i]
						.getPlayerName();
				arePlaying[i] = m_displayboard.getBoard().getPlayers()[i].isPlaying();
				areHumans[i] = ((String) m_displayboard.getBoard().getPlayers()[i]
						.printType()).equals("Human");
			}
			LudoBoard board = new LudoBoard(playerNames, arePlaying, areHumans);
			LudoMainForm main = new LudoMainForm(board, 0);
			main.setVisible(true);
		}
		if (e.getSource() == m_rulesJMenuItem) {
			JOptionPane rules = new JOptionPane();
			rules.showMessageDialog(
					null,
					"Overview \n\n Two, three, or four may play. At the beginning of the game, each player's tokens are out of play and staged in one of the large corner areas of the board in the player's colour.\n When able to, the players will enter their tokens one per time on their respective starting squares, and proceed to race them clockwise around the board along the game track.\n When reaching the square below his home column, a player continues by racing tokens up the column to the finishing square. Entry to the finishing square requires a precise roll from the player.\n The first to bring all their tokens to the finish wins the game.\n\nGameplay\n\n Each player rolls the die, the highest roller begins the game. The players alternate turns in a clockwise direction.\n To enter a token into play from its staging area to its starting square, a player must roll a 6. If the player has no tokens yet in play and does not roll a 6, the turn passes to the next player.\n Once a player has one or more tokens in play, he selects a token and moves it forward along the track the number of squares indicated by the die roll.\n Players must always move a token according to the die value rolled, and if no move is possible, pass their turn to the next player.\n When a player rolls a 6 he may choose to advance a token already in play, or alternatively, he may enter another staged token to its starting square.\n The rolling of a 6 earns the player an additional bonus roll in that turn. If the additional roll results in a 6 again, the player earns an additional bonus roll.\n If the third roll is also a 6, the player may not move a token and the turn immediately passes to the next player.\n A player may not end his move on a square he already occupies. If the advance of a token ends on a square occupied by an opponents token, the opponent token is returned to its owners yard.\n The returned token may only be reentered into play when the owner again rolls a 6.\n\n\n Source : Wikipedia",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Get method for the variable m_header
	 * 
	 * @return the displayheader of the game
	 */
	public LudoDisplayHeader getM_header() {
		return m_header;
	}

	/**
	 * Get method for the variable m_board
	 * 
	 * @return the displayboard of the game
	 */
	public LudoDisplayBoard getM_displayboard() {
		return m_displayboard;
	}

	/**
	 * Get method for the variable m_playersPanel
	 * 
	 * @return the JPanel used to display the players
	 */
	public JPanel getM_playersPanel() {
		return m_playersPanel;
	}

	/**
	 * Set method for the variable m_playersPanel
	 * 
	 */
	public void setM_playersPanel(JPanel m_playersPanel) {
		this.m_playersPanel = m_playersPanel;
	}

	/**
	 * Get method for the variable m_animation
	 * 
	 * @return the timer for the animation
	 */
	public Timer getM_animation() {
		return m_animation;
	}

	/**
	 * Set method for the variable m_animation
	 * 
	 */
	public void setM_animation(Timer m_animation) {
		this.m_animation = m_animation;
	}

	/**
	 * Get method for the variable m_JMenuBar
	 * 
	 * @return the JMenuBar containing all the functionalities we desire
	 */
	public JMenuBar getM_JMenubar() {
		return m_JMenubar;
	}

	/**
	 * Get method for the variable m_fileJMenu
	 * 
	 * @return the JMenu used for the file
	 */
	public JMenu getM_fileJMenu() {
		return m_fileJMenu;
	}

	/**
	 * Get method for the variable m_helpJMenu
	 * 
	 * @return the JMenu used for the help
	 */
	public JMenu getM_helpJMenu() {
		return m_helpJMenu;
	}

	/**
	 * Get method for the variable m_restartJMenuItem
	 * 
	 * @return the JMenuItem used for the restart
	 */
	public JMenuItem getM_restartJMenuItem() {
		return m_restartJMenuItem;
	}

	/**
	 * Get method for the variable m_mainmenuJMenuItem
	 * 
	 * @return the JMenuItem used for the mainmenu
	 */
	public JMenuItem getM_mainmenuJMenuItem() {
		return m_mainmenuJMenuItem;
	}

	/**
	 * Get method for the variable m_exitJMenuItem
	 * 
	 * @return the JMenuItem used for the exit
	 */
	public JMenuItem getM_exitJMenuItem() {
		return m_exitJMenuItem;
	}

	/**
	 * Get method for the variable m_rulesJMenuItem
	 * 
	 * @return the JMenuItem used for the rules
	 */
	public JMenuItem getM_rulesJMenuItem() {
		return m_rulesJMenuItem;
	}

}
