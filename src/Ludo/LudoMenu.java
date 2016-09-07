package Ludo;

/**
 * @file MenuLudo.java
 * @author Benjamin Machecourt
 * @date 03/04/2016
 * @brief Shows a dialogue where we set the players for the ludo game
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Minesweeper.JTextFieldLimit;

/**
 * @class MenuLudo
 * @brief Shows a dialogue where we set the players for the ludo game
 */

public class LudoMenu extends JFrame implements ActionListener {

	// MenuForm dimensions
	private static final int FORM_WIDTH = 600;
	private static final int FORM_HEIGHT = 450;

	private static final int PADDING = 20;

	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 30;

	private static final int LABEL_WIDTH = 200;
	private static final int LABEL_HEIGHT = 20;

	public static final int NB_PLAYER = 4;

	// variables

	// array of toggle button used to decide if this player will play
	private final JToggleButton[] m_playerButton;

	// array of combobox used to decide whether the players are human or not
	private final JComboBox<String>[] m_playerBox;

	// array of field used to write the name of the players
	private final JTextField[] m_playerNameField;

	// button used to launch the ludo game
	private final JButton m_startLudoButton;

	// array of boolean used to enable the JToggleButton m_playerButton
	private final boolean[] m_PlayerBoolean;

	// array of JLabel used to write the word "Name" before the name of the
	// player
	private final JLabel[] m_playerNameLabel;

	/**
	 * Constructs MenuLudo
	 */
	public LudoMenu() {

		// initialize variables
		m_playerButton = new JToggleButton[NB_PLAYER];
		m_PlayerBoolean = new boolean[NB_PLAYER];
		m_playerBox = new JComboBox[NB_PLAYER];
		m_playerNameField = new JTextField[NB_PLAYER];
		m_playerNameLabel = new JLabel[NB_PLAYER];
		Color[] playerColors = { Color.BLUE, Color.RED, Color.YELLOW,
				Color.GREEN };

		// Set MenuForm size and title, and centre window
		setSize(FORM_WIDTH, FORM_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Ludo");

		// Create JPanel content pane with absolute layout
		JPanel form = new JPanel(null);
		setContentPane(form);

		// Setup labels
		JLabel welcomeLabel = new JLabel("Welcome to Ludo !",
				SwingConstants.CENTER);
		welcomeLabel.setLocation((FORM_WIDTH - LABEL_WIDTH) / 2, PADDING / 2);
		welcomeLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		form.add(welcomeLabel);

		String[] elements = new String[] { "Human", "Computer" };

		for (int i = 0; i < NB_PLAYER; i++) {
			m_PlayerBoolean[i] = false;
			m_playerButton[i] = new JToggleButton("Player" + (i + 1));
			m_playerButton[i].setBackground(playerColors[i]);
			m_playerButton[i].setSize(LABEL_WIDTH, LABEL_HEIGHT);
			m_playerButton[i].addActionListener(this);
			m_playerButton[i].setSelected(!m_PlayerBoolean[i]);
			form.add(m_playerButton[i]);

			m_playerBox[i] = new JComboBox<>(elements);
			m_playerBox[i].setSize(LABEL_WIDTH, LABEL_HEIGHT);
			m_playerBox[i].setEnabled(m_PlayerBoolean[i]);
			form.add(m_playerBox[i]);

			m_playerNameLabel[i] = new JLabel("Name :");
			m_playerNameLabel[i].setSize(LABEL_WIDTH, LABEL_HEIGHT);
			form.add(m_playerNameLabel[i]);

			// Setup player name entry text box
			m_playerNameField[i] = new JTextField();
			m_playerNameField[i].setDocument(new JTextFieldLimit(10));
			m_playerNameField[i].setText("Player" + (i + 1));
			m_playerNameField[i].setSize(LABEL_WIDTH / 2, LABEL_HEIGHT);
			m_playerNameField[i].setEnabled(m_PlayerBoolean[i]);
			form.add(m_playerNameField[i]);
		}
		// Player 1 location
		m_playerButton[0].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 2);
		m_playerBox[0].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 3);
		m_playerNameLabel[0].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 4);
		m_playerNameField[0].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 + PADDING, (PADDING + LABEL_HEIGHT) * 4);

		// Player 2 location
		m_playerButton[1].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 2);
		m_playerBox[1].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 3);
		m_playerNameLabel[1].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 4);
		m_playerNameField[1].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 5 * PADDING, (PADDING + LABEL_HEIGHT) * 4);

		// Player 3 location
		m_playerButton[2].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 5);
		m_playerBox[2].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 6);
		m_playerNameLabel[2].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 2 * PADDING, (PADDING + LABEL_HEIGHT) * 7);
		m_playerNameField[2].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				+ LABEL_WIDTH / 2 + 5 * PADDING, (PADDING + LABEL_HEIGHT) * 7);

		// Player 4 location
		m_playerButton[3].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 5);
		m_playerBox[3].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 6);
		m_playerNameLabel[3].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 - 2 * PADDING, (PADDING + LABEL_HEIGHT) * 7);
		m_playerNameField[3].setLocation(((FORM_WIDTH - LABEL_WIDTH) / 2)
				- LABEL_WIDTH / 2 + PADDING, (PADDING + LABEL_HEIGHT) * 7);

		// Player 1 plays by default
		m_playerButton[0].setSelected(false);
		m_playerBox[0].setEnabled(true);
		m_playerNameField[0].setEnabled(true);

		// Setup start button
		m_startLudoButton = new JButton("Start Game");
		m_startLudoButton.setLocation((FORM_WIDTH / 2) - BUTTON_WIDTH / 2,
				(PADDING + LABEL_HEIGHT) * 8);
		m_startLudoButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		m_startLudoButton.addActionListener(this);
		form.add(m_startLudoButton);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean anybodyPlay = false;
		for (int i = 0; i < NB_PLAYER; i++) {
			if (e.getSource() == m_playerButton[i]) {
				m_PlayerBoolean[i] = !m_PlayerBoolean[i];
				m_playerBox[i].setEnabled(m_PlayerBoolean[i]);
				m_playerNameField[i].setEnabled(m_PlayerBoolean[i]);
			}
		}

		if (e.getSource() == m_startLudoButton) {
			for (int i = 0; i < NB_PLAYER; i++) {
				if (!m_playerButton[i].isSelected()) {
					anybodyPlay = true;
					if (m_playerNameField[i].getText().equals("")) {
						JOptionPane warning = new JOptionPane();
						warning.showMessageDialog(null,
								"You must enter a name for Player " + (i + 1)
										+ " !", "Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}
			if (anybodyPlay == false) {
				JOptionPane warning = new JOptionPane();
				warning.showMessageDialog(null,
						"You must choose at least one player !", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			this.setVisible(false);
			String[] playerNames = new String[NB_PLAYER];
			boolean[] arePlaying = new boolean[NB_PLAYER];
			boolean[] areHumans = new boolean[NB_PLAYER];

			for (int i = 0; i < NB_PLAYER; i++) {
				playerNames[i] = m_playerNameField[i].getText();
				arePlaying[i] = !m_playerButton[i].isSelected();
				areHumans[i] = ((String) m_playerBox[i].getSelectedItem())
						.equals("Human");
			}
			LudoBoard board = new LudoBoard(playerNames, arePlaying, areHumans);
			LudoMainForm main = new LudoMainForm(board, 0);
			main.setVisible(true);

		}

	}

	/**
	 * Get method for the variable m_playerButton
	 * 
	 * @return a JToggleButton array
	 * 
	 */
	public JToggleButton[] getM_playerButton() {
		return m_playerButton;
	}

	/**
	 * Get method for the variable m_playerBox
	 * 
	 * @return a JComboBox array
	 * 
	 */
	public JComboBox<String>[] getM_playerBox() {
		return m_playerBox;
	}

	/**
	 * Get method for the variable m_playerNameField
	 * 
	 * @return a JTextField array
	 * 
	 */
	public JTextField[] getM_playerNameField() {
		return m_playerNameField;
	}

	/**
	 * Get method for the variable m_startLudoButton
	 * 
	 * @return a JButton
	 * 
	 */
	public JButton getM_startLudoButton() {
		return m_startLudoButton;
	}

	/**
	 * Get method for the variable m_PlayerBoolean
	 * 
	 * @return an array of boolean
	 * 
	 */
	public boolean[] getM_PlayerBoolean() {
		return m_PlayerBoolean;
	}

	/**
	 * Get method for the variable m_playerNameLabel
	 * 
	 * @return a JLabel array
	 * 
	 */
	public JLabel[] getM_playerNameLabel() {
		return m_playerNameLabel;
	}

}
