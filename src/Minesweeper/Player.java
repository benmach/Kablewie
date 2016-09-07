package Minesweeper;
/**
 * @file Player.java
 * @author Christopher Lovering
 * @date 21/11/2015
 * @brief Creates a player and stores there name for both human
 * and AI player upon further implementation
 */

/**
 * @class Player
 * @brief Creates a player and stores there name for both human
 * and AI player upon further implementation
 */
public abstract class Player {

	/** The name of the player */
	private final String m_playerName;

	/**
	 * Constructor to create the player object
	 * @param name the name of the player
	 */
	protected Player(String name) {
		m_playerName = name;
	}

	/**
	 * Gets the player name 
	 * @return the player name
	 */
	public String getPlayerName() {
		return m_playerName;
	}

	/**
	 * Contains the logic for what happens when the player
	 * reveals a tile
	 *
	 * @param tile the tile that is being revealed
	 * @return true if the tile was revealed
	 */
	public boolean revealTile(Tile tile) {
		return tile.reveal();
	}

	/**
	 * Contains the logic for what happens when the player
	 * diffuses a tile
	 *
	 * @param tile the tile that is being diffused
	 */
	public void diffuseTile(Tile tile) {

		// can't diffuse revealed tiles
		if (tile.isRevealed()) {
			return;
		}

		tile.toggleDiffused();
	}
}
