package Minesweeper;
/**
 * @file JTextFieldLimit.java
 * @author Luke
 * @date 13/03/2016
 * @see MenuForm.java
 * @brief Creates a document with a max string length
 */

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @class JTextFieldLimit
 * @brief Creates a document with a max string length
 */
public class JTextFieldLimit extends PlainDocument {

	//Stores the limit of the text feild
    private final int limit;

    /**
     * Constructor of setting the limit of the text feild
     * @param limit
     */
    public JTextFieldLimit(int limit) {
        this.limit = limit;
    }

    /**
     * inserts the string if it does not exceed the character limit
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}