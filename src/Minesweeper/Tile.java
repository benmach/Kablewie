package Minesweeper;
/**
 * @file Tile.java
 * @author Fatima
 * @date 24/11/15
 * @brief Is an abstract implementation of the tile classes.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @class Tile
 * @brief Is an abstract implementation of the tile classes.
 */
public abstract class Tile extends JPanel implements MouseListener {
	
	// size constants
	public static final int PADDING = 2;
	public static final int TILE_LENGTH = 20;

	//The board object
	private final Board m_board;

	// Position and state of tile
	private final boolean m_bomb;
	private final Point m_position;

	//tile state booleans
	private boolean m_diffused;
	private boolean m_revealed;
	private boolean m_showing;

	// Colour constants
	protected static final Color DIFFUSED_COLOUR = Color.BLACK;
	protected static final Color HIDDEN_COLOUR = Color.LIGHT_GRAY;

	/**
	 * Constructs tile object
	 *
	 * @param board The Board this tile belongs to
	 * @param position Position of tile
	 * @param bomb Whether current tile is bombed
	 */
	protected Tile(Board board, Point position, boolean bomb) {
		m_board = board;
		m_position = position;
		m_bomb = bomb;

		addMouseListener(this);
	}

	/**
	 * Gets number of bombs adjacent to current tile
	 *
	 * @return Number of adjacent bombs
	 */
	public int getAdjacentBombs() {
		return -1;
	}

	/**
	 * Gets the position of the current tile
	 *
	 * @return Position of tile
	 */
	public Point getPosition() {
		return m_position;
	}
	
	/**
	 * Sets whether the tile is revealed
	 *
	 * @param revealed Whether tile is revealed
	 */
	protected void setRevealed(boolean revealed) {
		m_revealed = revealed;
	}

	/**
	 * Sets whether the tile is diffused
	 *
	 * @param diffused Whether tile is diffused
	 */
	protected void setDiffused(boolean diffused) {
		m_diffused = diffused;
	}

	/**
	 * Gets whether the tile is showing if it is a bomb
	 *
	 * @return True if the tile is showing if it is a bomb
	 */
	public boolean isShowingBomb() {
		return m_showing;
	}

	/**
	 * Sets whether the tile should show if it is a bomb
	 *
	 * @param showing Whether the tile should show if it is a bomb
	 */
	public void setShowingBomb(boolean showing) {
		m_showing = showing;
	}

	/**
	 * Gets whether the tile is bombed
	 *
	 * @return True if tile is bombed, false otherwise
	 */
	public boolean isBomb() {
		return m_bomb;
	}

	/**
	 * Gets whether the tile is diffused
	 *
	 * @return True if tile is diffused, false otherwise
	 */
	public boolean isDiffused() {
		return m_diffused;
	}

	/**
	 * Gets whether the tile is revealed
	 *
	 * @return True if tile is revealed, false otherwise
	 */
	public boolean isRevealed() {
		return m_revealed;
	}
	
	/**
	 * Gets whether the tile allows the game to be won
	 *
	 * @return True if tile allows game to be won, false otherwise
	 */
	public abstract boolean isWinning();

	/**
	 * Draws current tile
	 */
	protected abstract void draw(Graphics g);

	/**
	 * Gets whether the tile will cause the game to be lost
	 *
	 * @return True if tile causes the game to be lost, false otherwise
	 */
	public abstract boolean hasLost();
	
	/**
	 * Reveals tile, if it is not already revealed or diffused
	 *
	 * @return True if the tile was revealed
	 */
	public boolean reveal() {
	
		// a tile can only be revealed once and only when not diffused
		if (m_revealed || m_diffused) {
			return false;
		}
		
		m_revealed = true;

		draw(getGraphics());

		// reveal any adjacent tiles
		if (getAdjacentBombs() == 0) {
			revealAdjacent();
		}

		return true;
	}

	/**
	 * Sets whether the tile should show if is it a bomb and redraws it
	 *
	 * @param showing true if the tile should show if it is a bomb
	 */
	public void showBomb(boolean showing) {
		setShowingBomb(showing);
		draw(getGraphics());
	}
	
	/**
	 * Toggles whether tile is diffused, if it is not already revealed
	 */
	public void toggleDiffused() {

		// can't diffuse a revealed tile
		if (m_revealed) {
			return;
		}

		// toggle the diffused boolean
		m_diffused = !m_diffused;

		Graphics g = getGraphics();

		// Check if graphics is null as the Tile might not belong
		// to a GUI yet if restoring from save
		if (g != null) {
			draw(g);
		}
	}
	
	/**
	 * Reveal the tiles adjacent to this tile
	 */
	public void revealAdjacent() {
		m_board.revealAdjacent(this);
	}
	/**
	 * Either reveals or diffuses the tile
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (m_board.getPlayer() instanceof HumanPlayer) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				m_board.revealTile(this);
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				m_board.diffuseTile(this);
			}
		}
	}

	/**
	 * Paints tile
	 *
	 * @param g Graphics to paint tile to
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	// Unused event handlers

	/**
	 * Unused event handler
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	/**
	 * Unused event handler
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}
	/**
	 * Unused event handler
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}
	/**
	 * Unused event handler
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
	
	
