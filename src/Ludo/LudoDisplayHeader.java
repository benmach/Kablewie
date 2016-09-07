package Ludo;

/**
 * @file LudoDisplayHeader.java
 * @author Benjamin Machecourt
 * @date 06/04/2016
 //* @brief Shows information to user about Kablewie board
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

/**
 * @class LudoDisplayHeader
 * @brief Shows information to user about Kablewie board
 */
public class LudoDisplayHeader extends JPanel implements ActionListener,
		ChangeListener, KeyListener {

	// intervals for timer
	private static final int TIMER_INTERVAL = 1;

	// computer speed spinner speeds
	private static final int DEFAULT_COMPUTER_SPEED = 1;
	private static final int MIN_COMPUTER_SPEED = 0;
	private static final int MAX_COMPUTER_SPEED = 10;

	// button strings
	private static final String LAUNCH_DICE = "Launch Dice";
	private static final String PASS = "Pass turn";

	// widgets to display
	private final JLabel m_timerLabel;
	private final JLabel m_resultLabel;
	private final JButton m_launchButton;
	private final JButton m_passButton;
	private final JSlider m_computerSpeedSlider;

	// dice object
	private LudoDice m_dice;

	// data relating to the game timer
	private final Timer m_computerTimer;
	private final Timer m_timer;
	private final long m_startTime;
	private boolean m_pause;

	// board object
	private final LudoBoard m_board;

	/**
	 * Constructs object and sets up widgets
	 * 
	 * @param elapsedTime
	 *            Time since the game start (0 for a new game)
	 * @param board
	 *            LudoBoard the board displayed
	 */
	public LudoDisplayHeader(long elapsedTime, LudoBoard board) {

		// initialise variable
		m_board = board;

		// set up layout manager
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		c.weightx = 1.0;

		// initialise widgets

		JLabel m_timeLabel = new JLabel("Time");
		layout.addLayoutComponent(m_timeLabel, c);
		add(m_timeLabel);

		JLabel m_computerLabel = new JLabel("Computer speed");
		layout.addLayoutComponent(m_computerLabel, c);
		add(m_computerLabel);

		m_launchButton = new JButton(LAUNCH_DICE);
		layout.addLayoutComponent(m_launchButton, c);
		m_launchButton.addActionListener(this);
		add(m_launchButton);

		m_passButton = new JButton(PASS);
		layout.addLayoutComponent(m_passButton, c);
		m_passButton.addActionListener(this);
		add(m_passButton);

		c.gridy = 1;

		m_timerLabel = new JLabel();
		layout.addLayoutComponent(m_timerLabel, c);
		add(m_timerLabel);

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

		m_dice = new LudoDice(0);
		m_resultLabel = new JLabel("Result : " + m_dice.getValue());
		layout.addLayoutComponent(m_resultLabel, c);
		add(m_resultLabel);

		// setup timer
		m_startTime = System.currentTimeMillis() - elapsedTime;

		m_timer = new Timer(TIMER_INTERVAL, e -> updateTimer());
		m_timer.start();

		m_computerTimer = new Timer(DEFAULT_COMPUTER_SPEED,
				e -> runComputerTimer());
		
		m_pause=false;
		launchCurrentComputer();
		
	}

	public void runComputerTimer() {
		if (this.getM_board().getCurrentPlayer() instanceof LudoComputerPlayer) {
			LudoComputerPlayer player = (LudoComputerPlayer) this.getM_board()
					.getCurrentPlayer();
			player.makeRandomMove();
		}
	}

	/**
	 * Stops the timer
	 */
	public void stopTimer() {
		m_timer.stop();
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
	 * Handles all input of the display header
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_launchButton) {
			m_dice = new LudoDice();
			m_resultLabel.setText("Result : " + m_dice.getValue());
			m_launchButton.setEnabled(false);
		}
		if (e.getSource() == m_passButton) {
			LudoPlayer p = m_board.getCurrentPlayer();
			m_board.setCurrentPlayer(p, false);
			m_board.setCurrentPlayer(m_board.nextPlayer(p), true);

			m_board.getDisplay().getMainForm().updateDisplay();
			m_launchButton.setEnabled(true);
			m_dice.setValue(0);
			m_resultLabel.setText("Result : " + m_dice.getValue());
			launchCurrentComputer();
		}
	}

	/**
	 * Method to permit at the computer player to launch the dice (the result
	 * label doesnt refresh correctly)
	 */
	public void launchDice() {
		m_dice = new LudoDice();
		m_resultLabel.setText("Result : " + m_dice.getValue());
		m_launchButton.setEnabled(false);
		m_resultLabel.revalidate();
		m_resultLabel.repaint();
	}

	/**
	 * If the current player is a computer player, then launch the computer
	 * timer
	 */
	public void launchCurrentComputer() {
		if (this.getM_board().getCurrentPlayer() instanceof LudoComputerPlayer) {
			LudoComputerPlayer player = (LudoComputerPlayer) this.getM_board()
					.getCurrentPlayer();
			m_computerTimer
					.setDelay(getM_computerSpeedSlider().getValue() * 1000);

			if (!m_pause) {
				m_computerTimer.start();
			}
		}
	}

	/**
	 * handles the action on moving the slider
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		m_computerTimer.setDelay(getM_computerSpeedSlider().getValue());
	}

	/**
	 * Enable the button to launch a new dice
	 */
	public void allowNewDice() {
		m_launchButton.setEnabled(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 1017) {
			m_pause = !m_pause;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get method for the variable m_resultLabel
	 * 
	 * @return the JLabel containing the result of the dice
	 */
	public JLabel getresultLabel() {
		return m_resultLabel;
	}

	/**
	 * Get method for the variable m_dice
	 * 
	 * @return the dice of the game
	 */
	public LudoDice getDice() {
		return m_dice;
	}

	/**
	 * Set method for the variable m_dice
	 * 
	 * @param the
	 *            dice to instantiate
	 */
	public void setDice(LudoDice dice) {
		m_dice = dice;
	}

	/**
	 * Get method for the variable m_timerLabel
	 * 
	 * @return the timer displaying the elapsed time since the beginning of the
	 *         game
	 */
	public JLabel getM_timerLabel() {
		return m_timerLabel;
	}

	/**
	 * Get method for the variable m_launchButton
	 * 
	 * @return the JButton used to launch the dice
	 */
	public JButton getM_launchButton() {
		return m_launchButton;
	}

	/**
	 * Get method for the variable m_passButton
	 * 
	 * @return the JButton used to pass a turn
	 */
	public JButton getM_passButton() {
		return m_passButton;
	}

	/**
	 * Get method for the variable m_computerSpeedSlider
	 * 
	 * @return the JSlider used to set the speed of the computer players
	 */
	public JSlider getM_computerSpeedSlider() {
		return m_computerSpeedSlider;
	}

	/**
	 * Get method for the variable m_timer
	 *
	 * @return the timer of the game
	 */
	public Timer getM_timer() {
		return m_timer;
	}

	/**
	 * Get method for the variable m_startTime
	 * 
	 * @return the starting time of the game
	 */
	public long getM_startTime() {
		return m_startTime;
	}

	/**
	 * Get method for the variable m_board
	 * 
	 * @return the LudoBoard of the game
	 */
	public LudoBoard getM_board() {
		return m_board;
	}

}
