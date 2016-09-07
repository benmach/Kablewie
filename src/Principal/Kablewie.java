package Principal;
/**
 * @file Kablewie.java
 * @author Hal
 * @date 23 Nov 2015
 * 
 * @brief Launches Kablewie game
 */

import javax.swing.*;

import Minesweeper.FirstMenuForm;

import java.awt.EventQueue;

/**
 * @class Kablewie
 * @brief  Launches Kablewie game
 */
public class Kablewie {

	/**
	 * Launches MenuForm, which takes game details and then starts game
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            //Create and display menu form
            FirstMenuForm menu = new FirstMenuForm();
            menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            menu.setVisible(true);
        });
	}

}
