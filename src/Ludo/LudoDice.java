package Ludo;

/**
 * @file LudoDice.java
 * @author Benjamin Machecourt
 * @date 04/04/2016
 * @brief Creates a dice which values are between 1 and 6 
 */

/**
 * @class LudoDice
 * @brief Creates a dice which values are between 1 and 6
 */
public class LudoDice {

	// constant
	public static final int DICE_SIZE = 6;

	// hold the value of the dice
	private int m_value;

	/**
	 * Constructs dice object with a random value
	 */
	public LudoDice() {
		int v = (int) (Math.random() * DICE_SIZE) + 1;
		this.m_value = v;
	}

	/**
	 * Constructs dice object
	 *
	 * @param value
	 *            The value of the dice
	 */
	public LudoDice(int value) {
		this.m_value = value;
	}

	/**
	 * Get method for the variable m_value
	 * 
	 * @return the value of the dice
	 * 
	 */
	public int getValue() {
		return this.m_value;
	}

	/**
	 * Set method for the variable m_value
	 * 
	 * @param v
	 *            the value we use to instantiate
	 */
	public void setValue(int v) {
		this.m_value = v;
	}

}
