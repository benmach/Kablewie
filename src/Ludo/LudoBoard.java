package Ludo;

/**
 * @file LudoBoard.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @brief Stores information about the state of the Ludo board
 */

import java.awt.*;

/**
 * @class LudoBoard
 * @brief Stores information about the state of the ludo board
 */

public class LudoBoard {

	// constants
	public static final int BOARD_SIZE = 15;
	public static final int NB_PLAYER = 4;

	// colors of the board
	private static final Color BOARD_COLOUR = Color.LIGHT_GRAY;
	protected static final Color TOUR_COLOUR = Color.DARK_GRAY;
	protected static final Color BLUE_COLOUR = new Color(38, 196, 236);
	protected static final Color RED_COLOUR = new Color(169, 17, 1);
	protected static final Color YELLOW_COLOUR = new Color(239, 216, 7);
	protected static final Color GREEN_COLOUR = new Color(9, 106, 9);

	// stores the tiles on the board
	private final LudoTile[][] m_tiles;

	private LudoDisplayBoard m_display;

	// holds all the players of the game
	private final LudoPlayer[] m_players;

	/**
	 * Constructs board object
	 *
	 * @param playerNames
	 *            String[] vector with the names of all the players
	 * @param arePlaying
	 *            boolean[] vector which says whether the players are playing or
	 *            not
	 * @param areHumans
	 *            boolean[] vector which says whether the players are human or
	 *            not
	 * 
	 */
	public LudoBoard(String[] playerNames, boolean[] arePlaying,
			boolean[] areHumans) {

		// initialise variables
		m_tiles = new LudoTile[BOARD_SIZE][BOARD_SIZE];
		m_players = new LudoPlayer[NB_PLAYER];
		Color[] playerColors = { Color.BLUE, Color.RED, Color.YELLOW,
				Color.GREEN };

		for (int i = 0; i < NB_PLAYER; i++) {
			if (areHumans[i]) {
				m_players[i] = new LudoHumanPlayer(playerNames[i],
						playerColors[i], this, arePlaying[i], false);
			} else {
				m_players[i] = new LudoComputerPlayer(playerNames[i],
						playerColors[i], this, arePlaying[i], false);
			}
		}

		setFirstCurrentPlayer();

		// make the tour tiles with the color TOUR_COLOUR
		for (int x = 0; x < 6; x++) {
			for (int y = 6; y < 9; y++) {
				m_tiles[x][y] = new LudoTile(this, new Point(x, y), TOUR_COLOUR);
			}
		}
		for (int x = 6; x < 9; x++) {
			for (int y = 0; y < 6; y++) {
				m_tiles[x][y] = new LudoTile(this, new Point(x, y), TOUR_COLOUR);
			}
		}
		for (int x = 6; x < 9; x++) {
			for (int y = 9; y < BOARD_SIZE; y++) {
				m_tiles[x][y] = new LudoTile(this, new Point(x, y), TOUR_COLOUR);
			}
		}
		for (int x = 9; x < BOARD_SIZE; x++) {
			for (int y = 6; y < 9; y++) {
				m_tiles[x][y] = new LudoTile(this, new Point(x, y), TOUR_COLOUR);
			}
		}
		// make the blue tiles
		for (int y = 1; y < 7; y++) {
			m_tiles[7][y] = new LudoTile(this, new Point(7, y), BLUE_COLOUR);
		}
		m_tiles[2][2] = new LudoTile(this, new Point(2, 2), BLUE_COLOUR);
		m_tiles[2][3] = new LudoTile(this, new Point(2, 3), BLUE_COLOUR);
		m_tiles[3][2] = new LudoTile(this, new Point(3, 2), BLUE_COLOUR);
		m_tiles[3][3] = new LudoTile(this, new Point(3, 3), BLUE_COLOUR);
		m_tiles[6][1] = new LudoTile(this, new Point(6, 1), BLUE_COLOUR);

		// make the red tiles
		for (int x = 1; x < 7; x++) {
			m_tiles[x][7] = new LudoTile(this, new Point(x, 7), RED_COLOUR);
		}
		m_tiles[2][11] = new LudoTile(this, new Point(2, 11), RED_COLOUR);
		m_tiles[2][12] = new LudoTile(this, new Point(2, 12), RED_COLOUR);
		m_tiles[3][11] = new LudoTile(this, new Point(3, 11), RED_COLOUR);
		m_tiles[3][12] = new LudoTile(this, new Point(3, 12), RED_COLOUR);
		m_tiles[1][8] = new LudoTile(this, new Point(1, 8), RED_COLOUR);

		// make the green tiles
		for (int x = 8; x < 14; x++) {
			m_tiles[x][7] = new LudoTile(this, new Point(x, 7), GREEN_COLOUR);
		}
		m_tiles[11][2] = new LudoTile(this, new Point(11, 2), GREEN_COLOUR);
		m_tiles[11][3] = new LudoTile(this, new Point(11, 3), GREEN_COLOUR);
		m_tiles[12][2] = new LudoTile(this, new Point(12, 2), GREEN_COLOUR);
		m_tiles[12][3] = new LudoTile(this, new Point(12, 3), GREEN_COLOUR);
		m_tiles[13][6] = new LudoTile(this, new Point(13, 6), GREEN_COLOUR);

		// make the yellow tiles
		for (int y = 8; y < 14; y++) {
			m_tiles[7][y] = new LudoTile(this, new Point(7, y), YELLOW_COLOUR);
		}
		m_tiles[11][11] = new LudoTile(this, new Point(11, 11), YELLOW_COLOUR);
		m_tiles[11][12] = new LudoTile(this, new Point(11, 12), YELLOW_COLOUR);
		m_tiles[12][11] = new LudoTile(this, new Point(12, 11), YELLOW_COLOUR);
		m_tiles[12][12] = new LudoTile(this, new Point(12, 12), YELLOW_COLOUR);
		m_tiles[8][13] = new LudoTile(this, new Point(8, 13), YELLOW_COLOUR);

		// make all the other tiles
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				if (m_tiles[x][y] == null) {
					m_tiles[x][y] = new LudoTile(this, new Point(x, y),
							BOARD_COLOUR);
				}
			}
		}

	}

	/**
	 * Get the current player of the game
	 *
	 * @return the LudoPlayer currently playing
	 */
	public LudoPlayer getCurrentPlayer() {
		LudoPlayer currentplayer = new LudoHumanPlayer("toto", Color.blue,
				this, true, false);
		for (int i = 0; i < NB_PLAYER; i++) {
			if (m_players[i].isCurrentlyPlaying()) {
				currentplayer = m_players[i];
			}
		}
		return currentplayer;
	}

	/**
	 * Set the value currentlyPlaying on a LudoPlayer
	 * 
	 * @param player
	 *            the LudoPlayer we want to define as the current player or not
	 * @param b
	 *            boolean indicating if we want the player to be the current
	 *            player or not
	 *
	 */
	public void setCurrentPlayer(LudoPlayer player, Boolean b) {
		player.setisCurrentlyPlaying(b);
	}

	/**
	 * Gets a tile from the board
	 * 
	 * @param x
	 *            the x position of the tile
	 * @param y
	 *            the y position of the tile
	 * @return the tile at x,y position in the board
	 */
	public LudoTile getTile(int x, int y) {
		return m_tiles[x][y];
	}

	/**
	 * Given a player, return the next player who have to play
	 *
	 * @param player
	 *            the LudoPlayer in question
	 * @return The player who plays after the LudoPlayer player
	 */
	public LudoPlayer nextPlayer(LudoPlayer player) {
		LudoPlayer nextplayer = new LudoHumanPlayer("toto", Color.blue, this,
				true, false);
		int nextIndice = getIndex(player) + 1;
		if (getIndex(player) == 3) {
			nextIndice = 0;
		}
		if (this.getPlayers()[nextIndice].isPlaying()) {
			nextplayer = this.getPlayers()[nextIndice];
		} else {
			nextplayer = nextPlayer(this.getPlayers()[nextIndice]);
		}

		return nextplayer;
	}

	/**
	 * Given a player, return his index in the m_players vector
	 *
	 * @param player
	 *            the LudoPlayer in question
	 * @return The index of the player
	 */
	public int getIndex(LudoPlayer player) {
		int indice = -1;
		for (int i = 0; i < NB_PLAYER; i++) {
			if (player.equals(this.getPlayers()[i])) {
				indice = i;
			}
		}
		return indice;
	}

	/**
	 * Set the first current player for the initialization of the game
	 */
	public void setFirstCurrentPlayer() {
		int i = 0;
		while (i < NB_PLAYER && !this.getPlayers()[i].isPlaying()) {
			i++;
		}
		this.getPlayers()[i].setisCurrentlyPlaying(true);
	}

	
	/**
	 * Get method for the variable m_tiles
	 * 
	 * @return the tile board of the game
	 */
	public LudoTile[][] getBoardTile() {
		return m_tiles;
	}

	/**
	 * Get method for the variable m_display
	 * 
	 * @return the displayboard of the game
	 */
	public LudoDisplayBoard getDisplay() {
		return m_display;
	}

	/**
	 * Set method for the variable m_display
	 * 
	 * @param display
	 *            the LudoDisplayBoard we use to instantiate
	 */
	public void setDisplay(LudoDisplayBoard display) {
		m_display = display;

		for (LudoTile[] tiles : m_tiles) {
			for (LudoTile tile : tiles) {
				display.add(tile);
			}
		}
	}

	/**
	 * Get method for the variable m_players
	 * 
	 * @return the vector containing the LudoPlayers of the game
	 */
	public LudoPlayer[] getPlayers() {
		return m_players;
	}

}
