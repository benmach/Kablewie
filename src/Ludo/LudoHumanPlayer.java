package Ludo;

import java.awt.Color;
/**
 * @file LudoHumanPlayer.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @see LudoPlayer.java
 * @brief Creates a ludo human player
 */

/**
 * @class LudoHumanPlayer
 * @brief Creates a ludo human player
*/
public class LudoHumanPlayer extends LudoPlayer {

	/**
	 * Constructor that's used to store the player's name
	 * 
	 */
	public LudoHumanPlayer(String name, Color color, LudoBoard board, Boolean playing, Boolean currentplaying) {
		super(name, color, board, playing, currentplaying);
	}
	
}
