package Ludo;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * @file LudoComputerPlayer.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @brief Creates a ludo computer player and contains logic for when it
 * makes its move, as well as storing the speed of its moves
 */

/**
 * @class LudoComputerPlayer
 * @brief Creates a ludo computer player and contains logic for when it makes
 *        its move, as well as storing the speed of its moves
 */
public class LudoComputerPlayer extends LudoPlayer {
	
	private static Robot m_robot;
	
	
	/**
	 * Constructor for making the ComputerPlayer object
	 */
	public LudoComputerPlayer(String name, Color color, LudoBoard board,
			Boolean playing, Boolean currentplaying) {
		super(name, color, board, playing, currentplaying);
	}

	/**
	 * make a runner move, and pass the turn if no move is possible
	 * 
	 * @param board
	 *            the board object to move the runner on
	 * @throws AWTException
	 */

	public void makeRandomMove() {
		
		try {
			m_robot = new Robot();
		} catch (Exception failed) {
			System.err.println("Failed instantiating Robot: " + failed);
		}	
		this.getBoard().getDisplay().getMainForm().getM_header().launchDice();
		
		this.moveRunner(m_robot);

	}
	/**
	 * makes a runner move from his base
	 * 
	 * @param robot
	 *            the robot we use for moving the mouse
	 *            
	 */
	public void moveFromBase(Robot robot) {
		LudoTile finaltile = this
				.getBoard()
				.getTile(
						this.getRunners()[0].getTour()[this.getRunners()[0].TOUR_SIZE - 1].x,
						this.getRunners()[0].getTour()[this.getRunners()[0].TOUR_SIZE - 1].y);
		int i = 0;
		boolean found = false;
		while (!found && i < 4) {

			LudoTile newtile = this
					.getBoard()
					.getTile(
							this.getRunners()[i].getTour()[this.getRunners()[i]
									.getNextPosition()].x,
							this.getRunners()[i].getTour()[this.getRunners()[i]
									.getNextPosition()].y);

			if (newtile.runnerSamePlayerOnIt() && !newtile.equals(finaltile)) {
				found = true;
			} else if (this.getRunners()[i].isBased()) {
				found = true;
				LudoTile tile = this
						.getBoard()
						.getTile(
								this.getRunners()[i].getTour()[this
										.getRunners()[i].getPosition()].x,
								this.getRunners()[i].getTour()[this
										.getRunners()[i].getPosition()].y);
				this.mouseGlide(tile.getLocationOnScreen().x + tile.getWidth()
						/ 2, tile.getLocationOnScreen().y + tile.getHeight()
						/ 2, 2000, 50);
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
			i++;
		}
	}
	/**
	 * makes a runner move on his tour
	 * 
	 * @param robot
	 *            the robot we use for moving the mouse
	 *            
	 */
	public boolean moveOnTour(Robot robot) {
		int i = 0;
		boolean found = false;
		while (!found && i < 4) {
			if(this.getRunners()[i].getPosition() >= 53
					&& this.getRunners()[i].getNextPosition()  != this
							.getRunners()[i].TOUR_SIZE - 1){
				i++;
			}
			if (!this.getRunners()[i].isBased()) {
				found = true;
				LudoTile tile = this
						.getBoard()
						.getTile(
								this.getRunners()[i].getTour()[this
										.getRunners()[i].getPosition()].x,
								this.getRunners()[i].getTour()[this
										.getRunners()[i].getPosition()].y);
				this.mouseGlide(tile.getLocationOnScreen().x + tile.getWidth()
						/ 2, tile.getLocationOnScreen().y + tile.getHeight()
						/ 2, 2000, 50);
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
			i++;
		}
		return found;
	}
	/**
	 * makes the mouse move and click on the pass button
	 * 
	 * @param robot
	 *            the robot we use for moving the mouse
	 *            
	 */
	public void movePassButton(Robot robot) {
		JButton passButton = this.getBoard().getDisplay().getMainForm()
				.getM_header().getM_passButton();
		this.mouseGlide(
				passButton.getLocationOnScreen().x + passButton.getWidth() / 2,
				passButton.getLocationOnScreen().y + passButton.getHeight()*2,
				2000, 50);
		this.mouseGlide(
				passButton.getLocationOnScreen().x + passButton.getWidth() / 2,
				passButton.getLocationOnScreen().y + passButton.getHeight() / 2,
				2000, 50);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	/**
	 * makes a runner move if possible
	 * 
	 * @param robot
	 *            the robot we use for moving the mouse
	 *            
	 */
	public void moveRunner(Robot robot) {
		int diceValue = this.getBoard().getDisplay().getMainForm()
				.getM_header().getDice().getValue();
		if (diceValue == 0) {
			JOptionPane warning = new JOptionPane();
			warning.showMessageDialog(null, "You didn't launch the dice !",
					"Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (diceValue == 6) {
			this.moveFromBase(robot);
		} else {
			boolean found = this.moveOnTour(robot);
			if (found == false) {
				this.movePassButton(robot);
			}
		}
	}
	/**
	 * makes the mouse move to a point
	 * 
	 * @param x2
	 *            the new x position
	 * @param y2
	 *            the new y position
	 * @param t
	 *            the time to do the moving
	 * @param n	
	 * 			  number of steps to perform the move
	 *            
	 *            
	 */
	public void mouseGlide(int x2, int y2, int t, int n) {
		try {
			Robot r = new Robot();
			int x1 = MouseInfo.getPointerInfo().getLocation().x;
			int y1 = MouseInfo.getPointerInfo().getLocation().y;
			double dx = (x2 - x1) / ((double) n);
			double dy = (y2 - y1) / ((double) n);
			double dt = t / ((double) n);
			for (int step = 1; step <= n; step++) {
				Thread.sleep((int) dt);
				r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}