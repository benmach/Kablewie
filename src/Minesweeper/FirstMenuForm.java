package Minesweeper;
/**
 * @file FirstMenuForm.java
 * @author Benjamin Machecourt
 * @date 3 April 2016
 * @brief Shows a dialogue where the player chooses the game he wants to play
 */

import javax.swing.*;

import ConnectFour.P4;
import Ludo.LudoMenu;

/**
 * @class FirstMenuForm
 * @brief Shows a dialogue where the player chooses the game he wants to play
 */
public class FirstMenuForm extends JFrame{

	// MenuForm dimensions
    private static final int FORM_WIDTH = 600;
    private static final int FORM_HEIGHT = 200;

	private static final int PADDING = 60;

	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 30;

	private static final int LABEL_WIDTH = 200;
	private static final int LABEL_HEIGHT = 20;
	
	   /**
     * Constructs FirstMenuForm
     */
    public FirstMenuForm() {
    	// Set MenuForm size and title, and centre window
        setSize(FORM_WIDTH, FORM_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Kablewie");
        
        // Create JPanel content pane with absolute layout
        JPanel form = new JPanel(null);
        setContentPane(form);
        
        // Setup labels
        JLabel welcomeLabel = new JLabel("Welcome to Kablewie!",
                                         SwingConstants.CENTER);
	    welcomeLabel.setLocation(FORM_WIDTH / 2-LABEL_WIDTH/2, PADDING/2);
	    welcomeLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
        form.add(welcomeLabel);
        
        // Setup start button
        JButton startMinesweeperButton = new JButton("Start Minesweeper");
	    startMinesweeperButton.setLocation(FORM_WIDTH / 2 - BUTTON_WIDTH/2 - BUTTON_WIDTH - PADDING/3 ,
			    (PADDING + LABEL_HEIGHT));
	    startMinesweeperButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        form.add(startMinesweeperButton);

        startMinesweeperButton.addActionListener(e -> {
        	this.setVisible(false);
        	//Create and display menu form
            MenuForm menu = new MenuForm();
            menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            menu.setVisible(true);
        });
        
        // Setup start button
        JButton startLudoButton = new JButton("Start Ludo");
	    startLudoButton.setLocation(FORM_WIDTH/2 -BUTTON_WIDTH/2,
			    (PADDING + LABEL_HEIGHT) );
	    startLudoButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        form.add(startLudoButton);

        startLudoButton.addActionListener(e -> {
        	this.setVisible(false);
        	//Create and display MenuLudo
        	LudoMenu menu = new LudoMenu();
            menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            menu.setVisible(true);
        });
     
        // Setup start button
        JButton startConnectFourButton = new JButton("Start connect four");
        startConnectFourButton.setLocation(FORM_WIDTH/2 - BUTTON_WIDTH/2 + BUTTON_WIDTH + PADDING/3, (PADDING + LABEL_HEIGHT));
	    startConnectFourButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        form.add(startConnectFourButton);

        startConnectFourButton.addActionListener(e -> {
        	this.setVisible(false);
        	//Create and display menu form
        	P4 jeu=new P4();
    		jeu.setSize(310,340);
    		jeu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    		jeu.setVisible(true);
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    }
}
