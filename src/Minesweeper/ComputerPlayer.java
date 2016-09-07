package Minesweeper;
/**
 * @file ComputerPlayer.java
 * @author Luke
 * @author Benjamin Machecourt
 * @date 21/11/2015
 * @brief Creates a computer player and contains logic for when it
 * makes its move, as well as storing the speed of its moves
 */

/**
 * @class ComputerPlayer
 * @brief Creates a computer player and contains logic for when it
 * makes its move, as well as storing the speed of its moves
 */
public class ComputerPlayer extends Player {

	//Stores the speed for the computer to take turns
	private int m_speed;

	/**
	 * Constructor for making the ComputerPlayer object
	 */
	public ComputerPlayer() {
		super("Computer");
	}

	/**
	 * throws an error when a computer tries to diffuse a tile
	 */
	@Override
	public void diffuseTile(Tile tile) {
		throw new UnsupportedOperationException("Computer Player cannot " +
				"diffuse tiles!");
	}

	/**
	 * set the speed for the computer to take turns
	 * @param speed the delay for the computer to take turns
	 */
	public void setSpeed(int speed) {
		m_speed = speed;
	}

	/**
	 * gets the current speed that the computer is taking turns
	 * @return the delay speed that the computer is taking turns
	 */
	public int getSpeed() {
		return m_speed;
	}

	/**
	 * reveals a random tile on the board
	 * @param board the board object to reveal the tile on
	 */
	public void makeRandomMove(Board board) {
		Tile tile;

		// keep getting random unrevealed tiles instead of the one given
		// until we reveal a tile
		do {
			int x = (int) (Math.random() * board.getBoardSize());
			int y = (int) (Math.random() * board.getBoardSize());
			tile = board.getTile(x, y);
		} while (!board.revealTile(tile));
	}

}
