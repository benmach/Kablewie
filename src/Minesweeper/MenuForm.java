package Minesweeper;
/**
 * @file MenuForm.java
 * @author Luke
 * @date 20 Nov 2015
 * @brief Gets information for the next game, shows a dialogue
 * where the player enters their name, the board size and the
 * number of mines for the upcoming game.
 */

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @class MenuForm
 * @brief Gets information for the next game, shows a dialogue
 * where the player enters their name, the board size and the
 * number of mines for the upcoming game.
 */
public class MenuForm extends JFrame {

    // Path of icon
    private static final String ICON_PATH = "icon.png";

    // Input constraints
    private static final int MIN_BOARD_SIZE = 1;
    private static final int MAX_BOARD_SIZE = 30;
    private static final int DEFAULT_BOARD_SIZE = 10;
    private static final int BOARD_SIZE_INCREMENT = 1;

    private static final int MIN_BOMB_COUNT = 0;
	private static final int MAX_BOMB_COUNT = 150;
    private static final int DEFAULT_BOMB_COUNT = 10;
    private static final int BOMB_COUNT_INCREMENT = 1;

    // MenuForm dimensions
    private static final int FORM_WIDTH = 600;
    private static final int FORM_HEIGHT = 400;

	private static final int PADDING = 20;

	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 30;

	private static final int LABEL_WIDTH = 200;
	private static final int LABEL_HEIGHT = 20;

    // Spinner widgets
    private final JSpinner boardSizeSpinner;
    private final JSpinner bombCountSpinner;
			
    /**
     * Constructs MenuForm
     */
    public MenuForm() {

        // Set MenuForm size and title, and centre window
        setSize(FORM_WIDTH, FORM_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");
		
        // Set icon
        ImageIcon icon = new ImageIcon(ICON_PATH);
        setIconImage(icon.getImage());

        // Create JPanel content pane with absolute layout
        JPanel form = new JPanel(null);
        setContentPane(form);

        // Setup labels
        JLabel welcomeLabel = new JLabel("Welcome to Minesweeper !",
                                         SwingConstants.CENTER);
	    welcomeLabel.setLocation((FORM_WIDTH - LABEL_WIDTH) / 2, PADDING);
	    welcomeLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(welcomeLabel);

        JLabel playerNameLabel = new JLabel("Enter your name:",
                                            SwingConstants.RIGHT);
	    playerNameLabel.setLocation(PADDING * 4, (PADDING + LABEL_HEIGHT) * 2);
	    playerNameLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(playerNameLabel);

        JLabel boardSizeLabel = new JLabel("Board height and width:",
                                           SwingConstants.RIGHT);
	    boardSizeLabel.setLocation(PADDING * 4, (PADDING + LABEL_HEIGHT) * 3);
	    boardSizeLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(boardSizeLabel);

        JLabel bombCountLabel = new JLabel("Mine Count:",SwingConstants.RIGHT);
	    bombCountLabel.setLocation(PADDING * 4, (PADDING + LABEL_HEIGHT) * 4);
	    bombCountLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(bombCountLabel);

        // Setup player name entry text box
        JTextField playerNameField = new JTextField();
	    playerNameField.setDocument(new JTextFieldLimit(20));
	    playerNameField.setLocation((PADDING * 5) + LABEL_WIDTH,
			    (PADDING + LABEL_HEIGHT) * 2);
	    playerNameField.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(playerNameField);

        // Setup board size entry spinner
        SpinnerModel boardSizeModel = new SpinnerNumberModel(
                DEFAULT_BOARD_SIZE, MIN_BOARD_SIZE, MAX_BOARD_SIZE,
                BOARD_SIZE_INCREMENT);
        boardSizeSpinner = new JSpinner(boardSizeModel);
	    boardSizeSpinner.setLocation((PADDING * 5) + LABEL_WIDTH,
			    (PADDING + LABEL_HEIGHT) * 3);
	    boardSizeSpinner.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(boardSizeSpinner);

        // Setup bomb count entry spinner
        SpinnerModel bombCountModel = new SpinnerNumberModel(
                DEFAULT_BOMB_COUNT, MIN_BOMB_COUNT,
                (DEFAULT_BOARD_SIZE * DEFAULT_BOARD_SIZE) - 1,
                BOMB_COUNT_INCREMENT);
        bombCountSpinner = new JSpinner(bombCountModel);
	    bombCountSpinner.setLocation((PADDING * 5) + LABEL_WIDTH,
			    (PADDING + LABEL_HEIGHT) * 4);
	    bombCountSpinner.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(bombCountSpinner);

        // Add listener to keep correct bomb count maximum
        boardSizeSpinner.addChangeListener(e -> updateBombSpinner());

		// Setup load game button
		JButton loadGameButton = new JButton("Load Game");
	    loadGameButton.setLocation((FORM_WIDTH / 2) - BUTTON_WIDTH - PADDING,
			    (PADDING + LABEL_HEIGHT) * 6);
	    loadGameButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		form.add(loadGameButton);

		loadGameButton.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.addChoosableFileFilter(new SaveFileFilter());
			int result = chooser.showOpenDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				File saveFile = chooser.getSelectedFile();
				loadGame(saveFile);
			}
		});

        // Setup start button
        JButton startButton = new JButton("Start Game");
	    startButton.setLocation((FORM_WIDTH / 2) + PADDING,
			    (PADDING + LABEL_HEIGHT) * 6);
	    startButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        form.add(startButton);

        startButton.addActionListener(e -> {
			Board board = new Board((int) boardSizeSpinner.getValue(),
					(int) bombCountSpinner.getValue());
			board.setPlayer(new HumanPlayer(playerNameField.getText()));
            MainForm main = new MainForm(board, playerNameField.getText(), 0);
            setVisible(false);
            main.setVisible(true);
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	/**
	 * Updates the bomb count spinner min-max
	 */
	private void updateBombSpinner() {
		int boardSize = (int) boardSizeSpinner.getValue();
		int currentBombCount = (int) bombCountSpinner.getValue();

		if (currentBombCount > ((boardSize * boardSize) - 1)) {
			currentBombCount = (boardSize * boardSize) - 1;
		}

		bombCountSpinner.setModel(new SpinnerNumberModel(
				currentBombCount, MIN_BOMB_COUNT,
				Math.min((boardSize * boardSize) - 1,
                         MAX_BOMB_COUNT), 1));
	}

	/**
	 * Load a game from the specified file
	 * @param file the file containing the save data
	 */
	private void loadGame(File file) {
		String firstLine = null;
		StringBuilder builder = new StringBuilder();
		try (Scanner in = new Scanner(file)) {
			while (in.hasNext()) {

				// first line contains display details
				if (firstLine == null) {
					in.nextLine(); // header title
					firstLine = in.nextLine();
					in.nextLine(); // blank line
				} else {

					// all other lines are CSV
					builder.append(in.nextLine());
					if (in.hasNext()) {
						builder.append('\n');
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Could not load game from file specified!",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		// read display args
		String[] args = firstLine.split(",");
		String playerName = args[0];
		long elapsedTime = Long.parseLong(args[1]);
		boolean revealed = Boolean.parseBoolean(args[2]);
		boolean computerPlayer = Boolean.parseBoolean(args[3]);
		int computerSpeed = Integer.parseInt(args[4]);

		// create player
		Player player;
		if (computerPlayer) {
			player = new ComputerPlayer();
			((ComputerPlayer) player).setSpeed(computerSpeed);
		} else {
			player = new HumanPlayer(playerName);
		}

		// create board and forms
		Board board = new Board(builder.toString());
		MainForm main = new MainForm(board, playerName, elapsedTime);
		board.setPlayer(player);
		board.revealBombs(revealed);

		setVisible(false);
		main.setVisible(true);
	}

}
