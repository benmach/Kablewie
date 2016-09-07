package Ludo;

/**
 * @file LudoTile.java
 * @author Benjamin Machecourt
 * @date 06/04/2016
 * @brief Is an abstract implementation of the tile classes.
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
 * @class LudoTile
 * @brief Is an abstract implementation of the tile classes.
 */
public class LudoTile extends JPanel implements MouseListener {

	// size constants
	private final static int NB_RUNNERS = 4;
	public static final int PADDING = 2;
	public static final int TILE_LENGTH = 20;

	// The board object
	private final LudoBoard m_board;

	// Position and state of tile
	private final Point m_position;
	private final Color m_color;

	/**
	 * Constructs tile object
	 *
	 * @param board
	 *            The Board this tile belongs to
	 * @param position
	 *            Position of tile
	 * @param color
	 *            Color of the Tile
	 */
	public LudoTile(LudoBoard board, Point position, Color color) {
		m_board = board;
		m_position = position;
		m_color = color;

		addMouseListener(this);
	}

	/**
	 * Draws current tile, with the runner on it if there is one
	 * 
	 * @param g
	 *            the graphics of the tile
	 * 
	 */
	protected void draw(Graphics g) {
		g.setColor(m_color);
		g.fillRect(0, 0, TILE_LENGTH, TILE_LENGTH);

		for (int i = 0; i < m_board.NB_PLAYER; i++) {
			if (m_board.getPlayers()[i].isPlaying()) {
				for (int j = 0; j < NB_RUNNERS; j++) {
					LudoRunner runner = m_board.getPlayers()[i].getRunners()[j];
					if (runner.getTour()[runner.getPosition()].equals(this
							.getPosition())) {
						drawRunner(g, runner,
								m_board.getPlayers()[i].getColor());
					}
				}

			}
		}
	}

	/**
	 * Draws the runner on the tile
	 * 
	 * @param g
	 *            the graphics of the tile
	 * @param runner
	 *            the runner which is on the tile
	 * @param color
	 *            the color of the tile
	 * 
	 */
	public void drawRunner(Graphics g, LudoRunner runner, Color color) {
		g.setColor(color);
		Rectangle2D size = g.getFontMetrics().getStringBounds(
				String.valueOf(runner.getNumber()), g);
		int width = (int) ((LudoTile.TILE_LENGTH - size.getWidth()) / 2);
		int height = (int) size.getHeight();
		g.drawString(String.valueOf(runner.getNumber()), width, height);
	}

	/**
	 * Paints tile
	 *
	 * @param g
	 *            Graphics to paint tile to
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

	/**
	 * If there is a runner on the tile, move it according to the value of the
	 * dice (if it's possible)
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		LudoPlayer p = m_board.getCurrentPlayer();
		int diceValue = m_board.getDisplay().getMainForm().getM_header()
				.getDice().getValue();

		if (p instanceof LudoPlayer) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				for (int j = 0; j < NB_RUNNERS; j++) {
					if (p.getRunners()[j].getTour()[p.getRunners()[j]
							.getPosition()].equals(this.getPosition())) {

						LudoTile finaltile = this
								.getBoard()
								.getTile(
										p.getRunners()[j].getTour()[p
												.getRunners()[j].TOUR_SIZE - 1].x,
										p.getRunners()[j].getTour()[p
												.getRunners()[j].TOUR_SIZE - 1].y);

						if (this.equals(finaltile)) {
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(null,
									"This runner is already arrived !",
									"Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (diceValue == 0) {
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(null,
									"You didn't launch the dice !", "Warning",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (p.getRunners()[j].getPosition() >= 53
								&& p.getRunners()[j].getPosition() + diceValue != p
										.getRunners()[j].TOUR_SIZE - 1) {
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(
									null,
									"Entry to the finishing square requires a precise roll !",
									"Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (p.getRunners()[j].isBased() && diceValue != 6) {
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(
									null,
									"You must have a 6 to move this runner out of the staging area !",
									"Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						LudoTile newtile = this
								.getBoard()
								.getTile(
										p.getRunners()[j].getTour()[p
												.getRunners()[j]
												.getNextPosition()].x,
										p.getRunners()[j].getTour()[p
												.getRunners()[j]
												.getNextPosition()].y);

						if (newtile.runnerSamePlayerOnIt()
								&& !newtile.equals(finaltile)) {
							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(
									null,
									"You can't move on a square you already occupy !",
									"Warning", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (newtile.runnerOtherPlayerOnIt()) {
							LudoRunner otherRunner = newtile
									.getrunnerOtherPlayerOnIt();
							otherRunner.setPosition(0);
							LudoTile baseTile = this
									.getBoard()
									.getTile(
											otherRunner.getTour()[otherRunner
													.getPosition()].x,
											otherRunner.getTour()[otherRunner
													.getPosition()].y);
							baseTile.drawRunner(this.getGraphics(),
									otherRunner, otherRunner.getColor());
							this.update(this.getGraphics());
							baseTile.update(baseTile.getGraphics());

							JOptionPane warning = new JOptionPane();
							warning.showMessageDialog(
									null,
									"Bam ! You replace your enemy's runner at his base",
									"Warning", JOptionPane.WARNING_MESSAGE);
						}

						p.getRunners()[j].setPosition(p.getRunners()[j]
								.getNextPosition());
						newtile.drawRunner(this.getGraphics(),
								p.getRunners()[j], p.getColor());
						this.update(this.getGraphics());
						newtile.update(newtile.getGraphics());
						this.displayWon();

						m_board.setCurrentPlayer(p, false);
						m_board.setCurrentPlayer(m_board.nextPlayer(p), true);

						m_board.getDisplay().getMainForm()
								.updateDisplay();
						m_board.getDisplay().getMainForm().getM_header()
								.allowNewDice();

						m_board.getDisplay().getMainForm().getM_header()
								.getDice().setValue(0);
						m_board.getDisplay().getMainForm().getM_header()
								.getresultLabel().setText("Result : " + 0);

						m_board.getDisplay().getMainForm().getM_header().launchCurrentComputer();

					}
				}
			}
		}
	}

	/**
	 * Display won if the current player has won the game
	 */
	public void displayWon() {
		LudoPlayer p = m_board.getCurrentPlayer();
		if (p.gameIsWon()) {
			this.getBoard().getDisplay().getMainForm().gameOver();
		}
	}

	/**
	 * Says whether there is a runner who belongs to the same player on the tile
	 * the runner wants to move
	 * 
	 * @return a boolean
	 * 
	 */
	public boolean runnerSamePlayerOnIt() {
		boolean b = false;
		LudoPlayer p = m_board.getCurrentPlayer();
		for (int j = 0; j < NB_RUNNERS; j++) {
			if (p.getRunners()[j].getTour()[p.getRunners()[j].getPosition()]
					.equals(this.getPosition())) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * Says whether there is a runner who belongs to another player on the tile
	 * the runner wants to move
	 * 
	 * @return a boolean
	 * 
	 */
	public boolean runnerOtherPlayerOnIt() {
		boolean b = false;
		LudoPlayer p = m_board.getCurrentPlayer();
		for (int i = 0; i < m_board.NB_PLAYER; i++) {
			LudoPlayer player = m_board.getPlayers()[i];
			if (player.isPlaying() && !player.equals(p)) {
				for (int j = 0; j < NB_RUNNERS; j++) {
					if (player.getRunners()[j].getTour()[player.getRunners()[j]
							.getPosition()].equals(this.getPosition())) {
						b = true;
					}
				}
			}
		}
		return b;
	}

	/**
	 * (after calling runnerOtherPlayerOnIt) Give the runner in question
	 * 
	 * @return the LudoRunner which is on the tile we want to move the runner
	 * 
	 */
	public LudoRunner getrunnerOtherPlayerOnIt() {
		LudoPlayer p = m_board.getCurrentPlayer();
		LudoRunner runner = new LudoRunner(p, 7, m_board);
		for (int i = 0; i < m_board.NB_PLAYER; i++) {
			LudoPlayer player = m_board.getPlayers()[i];
			if (player.isPlaying() && !player.equals(p)) {
				for (int j = 0; j < NB_RUNNERS; j++) {
					if (player.getRunners()[j].getTour()[player.getRunners()[j]
							.getPosition()].equals(this.getPosition())) {
						runner = player.getRunners()[j];
					}
				}
			}
		}
		return runner;
	}

	/**
	 * Get method for the variable m_board
	 * 
	 * @return the board of the game
	 */
	public LudoBoard getBoard() {
		return this.m_board;
	}

	/**
	 * Get method for the variable m_position
	 * 
	 * @return the position of the tile
	 *
	 */
	public Point getPosition() {
		return m_position;
	}

	/**
	 * Get method for the variable m_color
	 * 
	 * @return the color of the tile
	 * 
	 */
	public Color getColor() {
		return this.m_color;
	}
}
