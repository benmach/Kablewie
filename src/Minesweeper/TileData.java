package Minesweeper;
/**
 * @file TileData.java
 * @author Kit Manners
 * @date 18/02/2016
 * @brief A serializable version of the Tile class which only contains
 * data about the type and state of the Tile
 */
import java.awt.*;
import java.io.Serializable;

/**
 * @class TileData
 * @brief A serializable version of the Tile class which only contains
 * data about the type and state of the Tile
 */
public class TileData implements Serializable {

	private final Point position;
	private final boolean bomb;
	private final boolean revealed;
	private final boolean diffused;
	private final int adjacentBombs;

	/**
	 * Creates a TileData object out of a Tile instance
	 * @param tile the tile to get the data from
	 */
	public TileData(Tile tile) {
		position = tile.getPosition();
		bomb = tile.isBomb();
		revealed = tile.isRevealed();
		diffused = tile.isDiffused();
		adjacentBombs = tile.getAdjacentBombs();
	}

	/**
	 * Creates a Tile object from the data this object holds. The object
	 * returned will be equal to the object provided in the constructor
	 *
	 * @param board the board the Tile belongs to
	 * @return the Tile object
	 */
	public Tile toTile(Board board) {
		Tile tile;

		// create the correct instance
		if (bomb) {
			tile = new BombTile(board, position);
		} else {
			tile = new EmptyTile(board, position, adjacentBombs);
		}

		// apply any user changes
		tile.setRevealed(revealed);

		if (diffused) {
			tile.toggleDiffused();
		}

		return tile;
	}

}
