package Minesweeper;
/**
 * @file Tile.java
 * @author Fatima
 * @date 24/11/15
 * @see Tile.java
*/

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @class BombTile
 * @brief Stores the location of the bomb tiles and
 * how to animate them.
 */
public class BombTile extends Tile {

	/**
	 * Constructs object
	 *
	 * @param board the Board this tile b
	 * @param position Position of tile in board
	 */
	public BombTile(Board board, Point position) {
		super(board, position, true);
	}

	/**
	 * used to draw the contents of the tile
	 */
	@Override
	public void draw(Graphics g) {
		if (g == null) {
			return;
		}

		if (isRevealed()) {
			g.setColor(Color.RED);
		} else if (isDiffused()) {
			g.setColor(DIFFUSED_COLOUR);
		} else {
			g.setColor(HIDDEN_COLOUR);
		}

		g.fillRect(0, 0, TILE_LENGTH, TILE_LENGTH);

		if (isRevealed() || isShowingBomb()) {
			if (isDiffused()) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}

			Rectangle2D size = g.getFontMetrics().getStringBounds("*", g);
			int width = (int) ((Tile.TILE_LENGTH - size.getWidth()) / 2);
			int height = (int) size.getHeight();
			g.drawString("*", width, height);
		}
	}

	/**
	 * Determines whether this tile will cause the game to be lost
	 *
	 * @return True if the tile has been revealed, false otherwise
	 */
	@Override
	public boolean hasLost() {
		return isRevealed();
	}
	
	/**
	 * Determines whether this tile will allow the game to be won
	 *
	 * @return True if tile is diffused, false otherwise
	 */
	@Override
	public boolean isWinning() {
		return isDiffused();
	}

}

