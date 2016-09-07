package Minesweeper;
/**
 * @file DisplayHeader.java
 * @author Will
 * @author Hal
 * @date 1 Dec 2015
 * @brief Shows information to user about Kablewie board
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/** @class DisplayHeader
 *  @brief Shows information to user about Kablewie board
 */
public class DisplayHeader extends JPanel
		implements ActionListener, ChangeListener {

	// intervals for timer
	private static final int TIMER_INTERVAL = 1;

	// computer speed spinner speeds
	private static final int DEFAULT_COMPUTER_SPEED = 1;
	private static final int MIN_COMPUTER_SPEED = 0;
	private static final int MAX_COMPUTER_SPEED = 10;

	// reveal mines button text
	private static final String REVEAL_MINES = "Reveal Mines";
	private static final String HIDE_MINES = "Hide Mines";

	// widgets to display
	private final JLabel m_playerNameLabel;
	private final JLabel m_mineCountLabel;
	private final JLabel m_diffusedCountLabel;
	private final JLabel m_hiddenCountLabel;
	private final JLabel m_revealedCountLabel;
	private final JLabel m_timerLabel;
	private final JToggleButton m_computerButton;
	private final JButton m_saveGameButton;
	private final JToggleButton m_revealButton;
	private final JSlider m_computerSpeedSlider;

	// timer object
	private final Timer m_computerTimer;

	// data relating to the game timer
	private final Timer m_timer;
	private long m_startTime;

	//board object
	private Board m_board;

	/**
	 * Constructs object and sets up widgets
	 * @param playerName Name of player
	 * @param elapsedTime Time since the game start (0 for a new game)
	 */
	public DisplayHeader(String playerName, long elapsedTime) {
		super(null);

		// set up layout manager
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		c.weightx = 1.0;

		// initialise widgets
		m_playerNameLabel = new JLabel(playerName);
		layout.addLayoutComponent(m_playerNameLabel, c);
		add(m_playerNameLabel);

		m_diffusedCountLabel = new JLabel();
		layout.addLayoutComponent(m_diffusedCountLabel, c);
		add(m_diffusedCountLabel);

		m_revealedCountLabel = new JLabel();
		layout.addLayoutComponent(m_revealedCountLabel, c);
		add(m_revealedCountLabel);

		m_revealButton = new JToggleButton(REVEAL_MINES);
		layout.addLayoutComponent(m_revealButton, c);
		m_revealButton.addActionListener(this);
		add(m_revealButton);

		c.gridwidth = GridBagConstraints.REMAINDER; // last in row
		m_computerButton = new JToggleButton("Computer Player");
		layout.addLayoutComponent(m_computerButton, c);
		m_computerButton.addActionListener(this);
		add(m_computerButton);

		c.gridwidth = 1; // new row
		m_mineCountLabel = new JLabel();
		layout.addLayoutComponent(m_mineCountLabel, c);
		add(m_mineCountLabel);

		m_hiddenCountLabel = new JLabel();
		layout.addLayoutComponent(m_hiddenCountLabel, c);
		add(m_hiddenCountLabel);

		m_timerLabel = new JLabel();
		layout.addLayoutComponent(m_timerLabel, c);
		add(m_timerLabel);

		m_saveGameButton = new JButton("Save Game");
		layout.addLayoutComponent(m_saveGameButton, c);
		m_saveGameButton.addActionListener(this);
		add(m_saveGameButton);

		m_computerSpeedSlider = new JSlider(MIN_COMPUTER_SPEED,
				MAX_COMPUTER_SPEED);
		m_computerSpeedSlider.setValue(DEFAULT_COMPUTER_SPEED);
		m_computerSpeedSlider.setPaintTicks(true);
		m_computerSpeedSlider.setPaintLabels(true);
		m_computerSpeedSlider.setMajorTickSpacing(MAX_COMPUTER_SPEED / 5);
		m_computerSpeedSlider.setMinorTickSpacing(1);
		layout.addLayoutComponent(m_computerSpeedSlider, c);
		m_computerSpeedSlider.addChangeListener(this);
		add(m_computerSpeedSlider);

		// setup timer
		m_startTime = System.currentTimeMillis() - elapsedTime;

		m_timer = new Timer(TIMER_INTERVAL, e -> updateTimer());
		m_timer.start();

		m_computerTimer = new Timer(DEFAULT_COMPUTER_SPEED * 1000, e ->
				runComputerTimer());
	}

	/**
	 * Sets number of mines
	 *
	 * @param bombCount Number of mines
	 */
	public void setBombCount(int bombCount) {
		m_mineCountLabel.setText("Mines: " + bombCount);
	}

	/**
	 * Sets number of diffused bombs shown
	 *
	 * @param diffused Number of diffused tiles
	 */
	public void setDiffused(int diffused) {
		m_diffusedCountLabel.setText("Diffused: " + diffused);
	}

	/**
	 * Sets number of hidden tiles
	 *
	 * @param hidden Number of hidden tiles
	 */
	public void setHidden(int hidden) {
		m_hiddenCountLabel.setText("Hidden: " + hidden);
	}

	/**
	 * Sets number of revealed tiles
	 *
	 * @param revealed Number of revealed tiles
	 */
	public void setRevealed(int revealed) {
		m_revealedCountLabel.setText("Revealed: " + revealed);
	}
	
	/**
	* Stops the timer
	*/
	public void stopTimer() {
		m_timer.stop();
	}

	/**
	 * Method to take computer turns according to set delay
	 */
	private void runComputerTimer() {
		m_computerTimer.setDelay(m_computerSpeedSlider.getValue() * 1000);
		Player player = m_board.getPlayer();

		// only make a move if the player is a computer and the game isn't over
		if ((player instanceof HumanPlayer) || !m_timer.isRunning()) {
			m_computerTimer.stop();
		} else {
			ComputerPlayer computerPlayer = (ComputerPlayer) player;
			computerPlayer.makeRandomMove(m_board);
			computerPlayer.setSpeed(m_computerSpeedSlider.getValue());
		}
	}
	
	/**
	 * Updates timer with elapsed time
	 */
	private void updateTimer() {

		// calculate time since start
		long elapsed = System.currentTimeMillis() - m_startTime;
		long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) - (hours * 60);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) - (hours * 60)
				       - (minutes * 60);

		// show the time since start in HH:MM:SS format
		m_timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes,
										   seconds));
	}

	/**
	 * Set the internal board for this game so we can save it if requested
	 * @param board the board
	 */
	public void setBoard(Board board) {
		m_board = board;
		if (m_board.isRevealingBombs()) {
			m_revealButton.setSelected(true);
			m_revealButton.setText(HIDE_MINES);
		}

		if (m_board.getPlayer() instanceof ComputerPlayer) {
			m_computerTimer.start();
			int speed = ((ComputerPlayer) m_board.getPlayer()).getSpeed();
			m_computerSpeedSlider.setValue(speed);
			m_computerButton.setSelected(true);
		}

		updateDisplay();
	}

	/**
	 * Method to update the header measurements 
	 */
	public void updateDisplay() {
		setBombCount(m_board.getBombCount());
		setHidden(m_board.getHidden());
		setRevealed(m_board.getRevealed());
		setDiffused(m_board.getDiffused());
	}

	/**
	 * Handles all input of the display header
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_saveGameButton) {

			// get current time so as to pause the game timer
			long time = System.currentTimeMillis();
			m_timer.stop();

			if (m_board != null) {

				// create file chooser with our file filter
				JFileChooser chooser = new JFileChooser();
				SaveFileFilter filter = new SaveFileFilter();
				chooser.addChoosableFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				// attempt to save the file
				int result = chooser.showSaveDialog(this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File saveFile = chooser.getSelectedFile();
					saveFile = filter.toSaveFile(saveFile);

					long elapsedTime = time - m_startTime;
					boolean success = saveGame(saveFile, elapsedTime);

					// report back to the user
					if (success) {
						JOptionPane.showMessageDialog(this, "Game saved!",
								"Success", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this,
								"Could not save game!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			// go back in time to when the save button was pressed
			long pausedTime = System.currentTimeMillis() - time;
			m_startTime += pausedTime;
			m_timer.start();
		} else if (e.getSource() == m_computerButton) {
			if (!m_computerButton.isSelected()) {

				// once selected, always selected!
				m_computerButton.setSelected(true);
			} else if (!m_computerTimer.isRunning()) {
				ComputerPlayer player = new ComputerPlayer();
				m_board.setPlayer(player);
				player.setSpeed(m_computerSpeedSlider.getValue());
				m_computerTimer.setDelay(m_computerSpeedSlider.getValue() * 1000);
				m_computerTimer.start();
			}
		} else if (e.getSource() == m_revealButton) {
			m_board.revealBombs(m_revealButton.isSelected());
			if (m_revealButton.isSelected()) {
				m_revealButton.setText(HIDE_MINES);
			} else {
				m_revealButton.setText(REVEAL_MINES);
			}
		}
	}

	/**
	 * Saves the game to the specified file, also taking note of how
	 * long the game has already gone on for
	 *
	 * @param saveFile the file to save the game to
	 * @param elapsedTime how long the game has gone on for (in milliseconds)
	 * @return true if the save was successful
	 */
	private boolean saveGame(File saveFile, long elapsedTime) {

		// override any previous file that might have been selected
		if (saveFile.exists()) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to override this file?",
					"Confirmation", JOptionPane.YES_NO_OPTION);

			// return if user clicked NO
			if (response == 1) {
				return false;
			}

			// get out of here if the file can't be deleted
			if (!saveFile.delete()) {
				return false;
			}
		}

		// create the new save file
		try {
			if (!saveFile.createNewFile()) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// write to file
		try (FileWriter writer = new FileWriter(saveFile)) {
			writer.append("name,time,revealed,computer,speed\n\n");
			writer.append(m_playerNameLabel.getText() + ",");
			writer.append(elapsedTime + ",");
			writer.append(m_revealButton.isSelected() + ",");
			writer.append(m_computerButton.isSelected() + ",");
			writer.append(m_computerSpeedSlider.getValue() + "\n");
			writer.append(m_board.toCsv());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * handles the action on moving the slider
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		m_computerTimer.setDelay(m_computerSpeedSlider.getValue());
	}
}
