package Ludo;

import java.awt.Color;

/**
 * @file LudoPlayer.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @brief Creates a ludo player and stores there name for both human
 * and AI player upon further implementation
 */

/**
 * @class LudoPlayer
 * @brief Creates a ludo player and stores there name for both human and AI
 *        player upon further implementation
 */
public abstract class LudoPlayer {

	// constants
	private final static int TOUR_SIZE = 60;
	private final static int NB_RUNNERS = 4;

	// variables
	private final String m_playerName;
	private final Boolean m_isplaying;
	private final Color m_color;
	private final LudoBoard m_board;
	private final LudoRunner[] m_runners;

	private Boolean m_isCurrentlyPlaying;

	/**
	 * Constructor to create the player object
	 * 
	 * @param name
	 *            String name of the player
	 * @param color
	 *            Color of the player
	 * @param board
	 *            LudoBoard on which the player is playing
	 * @param playing
	 *            Boolean determines whether the player is playing this game
	 * @param currentplaying
	 *            Boolean determines whether the player is currently playing
	 * 
	 */
	protected LudoPlayer(String name, Color color, LudoBoard board,
			Boolean playing, Boolean currentplaying) {
		m_playerName = name;
		m_color = color;
		m_board = board;
		m_isplaying = playing;
		m_isCurrentlyPlaying = currentplaying;
		m_runners = new LudoRunner[NB_RUNNERS];
		if (playing) {
			for (int j = 0; j < NB_RUNNERS; j++) {
				m_runners[j] = new LudoRunner(this, j + 1, board);
			}
		}

	}

	/**
	 * method which determine if the player has won the game
	 * 
	 * @return a boolean saying if the game is over
	 * 
	 */
	public boolean gameIsWon() {
		return (this.getRunners()[0].getPosition() == TOUR_SIZE - 1
				&& this.getRunners()[1].getPosition() == TOUR_SIZE - 1
				&& this.getRunners()[2].getPosition() == TOUR_SIZE - 1 && this
					.getRunners()[3].getPosition() == TOUR_SIZE - 1);
	}

	/**
	 * method which print a string representing the type of the player, human or
	 * computer
	 * 
	 * @return a String determining the type of the player
	 * 
	 */
	public String printType() {
		String s;
		if (this instanceof LudoHumanPlayer) {
			s = "Human";
		} else {
			s = "Computer";
		}
		return s;
	}

	/**
	 * Get method for the variable m_playerName
	 * 
	 * @return a String containing the name of the player
	 * 
	 */
	public String getPlayerName() {
		return m_playerName;
	}

	/**
	 * Get method to know whether the player is playing the game
	 * 
	 * @return a boolean saying if the player is playing
	 */
	public Boolean isPlaying() {
		return m_isplaying;
	}

	/**
	 * Get method for the variable m_color
	 * 
	 * @return the color of the player
	 * 
	 */
	public Color getColor() {
		return this.m_color;
	}

	/**
	 * Get method for the variable m_board
	 * 
	 * @return the ludoboard of the game
	 * 
	 */
	public LudoBoard getBoard() {
		return this.m_board;
	}

	/**
	 * Get method for the variable m_runners
	 * 
	 * @return the runners of the player in an array
	 * 
	 */
	public LudoRunner[] getRunners() {
		return m_runners;
	}

	/**
	 * Get method to know whether the player is currently playing
	 * 
	 * @return a boolean
	 * 
	 */
	public Boolean isCurrentlyPlaying() {
		return m_isCurrentlyPlaying;
	}

	/**
	 * Set method for the variable m_isCurrentlyPlaying
	 * 
	 * @param b
	 *            the boolean used to instantiate
	 * 
	 */
	public void setisCurrentlyPlaying(Boolean b) {
		this.m_isCurrentlyPlaying = b;

	}

}
