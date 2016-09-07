package Minesweeper;
/**
 * @file EmptyTile.java
 * @author Fatima
 * @date 24/11/15
 * @see Tile.java
 * @brief Handles the logic for when empty tiles are clicked
 * along with animation.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @class EmptyTile
 * @brief Handles the logic for when empty tiles are clicked
 * along with animation.
 */
public class EmptyTile extends Tile {

	// Colour constant
	private static final Color REVEALED_COLOUR = Color.WHITE;
	
	// Animation constants
	private static final int ANIMATION_TIME = 300;
	private static final int ANIMATION_DELAY = 30;
	
	// Text colours
	private static final Color[] COLORS = {Color.BLUE, Color.GREEN, Color.RED,
										   Color.CYAN, Color.MAGENTA,
										   Color.YELLOW, Color.BLACK,
										   Color.ORANGE};

	//Stores how many bombs are around this tile
	private int m_adjacentBombs;
	
	// Animation timer
	private Timer m_animation;

	// Current background colour
	private Color m_colour;

	/**
	 * Constructs object
	 *
	 * @param board the Board this tile belongs to
	 * @param position Position of current tile on board
	 * @param adjacentBombs Number of bombs adjacent to tile
	 */
	public EmptyTile(Board board, Point position, int adjacentBombs) {
		super(board, position, false);
		setDoubleBuffered(true);
		m_adjacentBombs = adjacentBombs;
	}

	/**
	 * Gets the number of bombs adjacent to this tile
	 *
	 * @return Number of adjacent bombs
	 */
	@Override
	public int getAdjacentBombs() {
		return m_adjacentBombs;
	}

	/**
	 * Sets the number of bombs adjacent to this
	 * @param adjacentBombs the number of adjacent bombs
	 */
	public void setAdjacentBombs(int adjacentBombs) {
		m_adjacentBombs = adjacentBombs;
	}

	/**
	 * Determines whether this tile will cause the game to be lost
	 *
	 * @return false because tile has no mine
	 */
	@Override
	public boolean hasLost() {
		return false;
	}

	/**
	 * Determines whether this tile allows the game to be won
	 *
	 * @return true if tile is revealed
	 */
	@Override
	public boolean isWinning() {
		return isRevealed();
	}

	/**
	 * Draws current tile on graphics object
	 *
	 * @param g Graphics object to draw tile on
	 */
	@Override
	public void draw(Graphics g) {

		// Draw tile with appropriate background colour
		if (isRevealed()) {
			draw(g, REVEALED_COLOUR, true);
		} else if (isDiffused()) {
			draw(g, DIFFUSED_COLOUR, true);
		} else {
			draw(g, HIDDEN_COLOUR, true);
		}
	}

	/**
	 * Reveals tile, if it is not already revealed, and displays animation
	 */
	@Override
	public boolean reveal() {

		// Can't reveal twice or while diffused
		if(isRevealed() || isDiffused()) {
			return false;
		}

		setRevealed(true);

		// reveal any adjacent tiles
		if (m_adjacentBombs == 0) {
			revealAdjacent();
		}
		
		// Store start time and animation start time
		long startTime = System.currentTimeMillis();
		Color startColor = (m_colour == null) ? Color.BLACK : m_colour;
		
		// Create animation timer
		m_animation = new Timer(ANIMATION_DELAY, e -> {
			
			// Work out time elapsed since start of animation
			long elapsed = System.currentTimeMillis() - startTime;
			
			if(elapsed >= ANIMATION_TIME) {
				// if animation is past its time, stop timer and show
				// revealed colour
				draw(getGraphics(), REVEALED_COLOUR, true);
				m_animation.stop();
			} else {

				// Work out how close to completion animation is
				double complete = elapsed / (double)ANIMATION_TIME;
				
				// Move colour components to destination colour based
				// on how close the animation is to completion
				int red = startColor.getRed() + (int)(complete *
						(REVEALED_COLOUR.getRed() - startColor.getRed()));
						  
				int blue = startColor.getBlue() + (int)(complete *
						(REVEALED_COLOUR.getBlue() - startColor.getBlue()));
						  
				int green = startColor.getGreen() + (int)(complete *
						(REVEALED_COLOUR.getGreen() - startColor.getGreen()));

				draw(getGraphics(), new Color(red, green, blue), false);
			}
			
		});
		
		m_animation.setInitialDelay(0);
		m_animation.start();

		return true;
	}
	
	/**
	 * Draws current tile on graphics object
	 *
	 * @param g Graphics object to draw tile on
	 * @param colour Background colour to use
	 * @param drawNumber Specifies whether to draw number in tile
	 */
	private void draw(Graphics g, Color colour, boolean drawNumber) {

		g.setColor(colour);
		g.fillRect(0, 0, TILE_LENGTH, TILE_LENGTH);
		m_colour = colour;

		if (isRevealed() && (m_adjacentBombs > 0) && drawNumber) {
			g.setColor(COLORS[m_adjacentBombs - 1]);
			Rectangle2D size = g.getFontMetrics().getStringBounds(
					String.valueOf(m_adjacentBombs), g);
			int width = (int) ((Tile.TILE_LENGTH - size.getWidth()) / 2);
			int height = (int) size.getHeight();
			g.drawString(String.valueOf(m_adjacentBombs), width, height);
		}
	}
}
	
	
