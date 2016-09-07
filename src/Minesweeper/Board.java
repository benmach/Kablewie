package Minesweeper;
/**
 * @file Board.java
 * @author Hal
 * @author Kit
 * @date 23 Nov 2015
 * <p>
 * @brief Stores information about the state of the Kablewie board
 */

import java.awt.*;
import java.util.Random;

/**
 * @class Board
 * @brief Stores information about the state of the Kablewie board
 */
public class Board {

    // maximum allowable board size
    private static final int MAX_BOARD_SIZE = 30;

    // stores the tiles on the board
    private final Tile[][] m_tiles;

    // holds the width and height of the board
    private final int m_boardSize;

    // holds number of bombs on board
    private final int m_bombCount;

	private DisplayBoard m_display;

	// holds the player that is currently playing the game
	private Player m_player;

    // holds number of tiles in given state
    private int m_diffused;
    private int m_hidden;
    private int m_revealed;

	private boolean m_showing;

	/**
     * Constructs board object
     *
     * @param bombCount Number of bombs to place in board
     * @param boardSize Width and height of board
     */
    public Board(int bombCount, int boardSize) {
	    Random rnd = new Random();

        // check the parameters are valid
        if ((boardSize <= 0) || (boardSize > MAX_BOARD_SIZE)) {
            throw new IllegalArgumentException("Invalid boardSize: "
		                                       + boardSize);
        }

        if ((bombCount < 0) || (bombCount >= (boardSize * boardSize))) {
            throw new IllegalArgumentException("Invalid bombCount: "
                                               + bombCount);
        }

        // initialise variables
        m_bombCount = bombCount;
        m_boardSize = boardSize;
        m_tiles = new Tile[boardSize][boardSize];

        m_diffused = 0;
        m_hidden = boardSize * boardSize;
        m_revealed = 0;

        // setup bombs
	    int bombs = 0;

	    while (bombs < bombCount) {

            // get random x,y coordinates
            int x = rnd.nextInt(boardSize);
            int y = rnd.nextInt(boardSize);

            // if that tile is not already a bomb, make it one
            if (m_tiles[x][y] == null) {
                m_tiles[x][y] = new BombTile(this, new Point(x, y));
                bombs++;
            }
        }

        // make all the other tiles empty
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {

                // check if we already set this tile to be a bomb
                if (m_tiles[x][y] == null) {
                    m_tiles[x][y] = new EmptyTile(this, new Point(x, y),
                            getAdjacentCount(x, y));
                }
            }
        }
    }

	/**
	 * Constructs Board object from a CSV formatted string
	 *
	 * @param csv the CSV formatted string
	 */
	public Board(String csv) {
		String[] lines = csv.split("\n");
		m_boardSize = (int) Math.sqrt(lines.length - 1);
		m_tiles = new Tile[m_boardSize][m_boardSize];
		m_hidden = m_boardSize * m_boardSize;

		int bombs = 0;

		for (int i = 1; i < lines.length; i++) {
			String[] line = lines[i].split(",");
			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			boolean bomb = Boolean.parseBoolean(line[2]);
			boolean diffused = Boolean.parseBoolean(line[3]);
			boolean revealed = Boolean.parseBoolean(line[4]);

			Point point = new Point(x, y);

			if (bomb) {
				m_tiles[x][y] = new BombTile(this, point);
				bombs++;
			} else {
				m_tiles[x][y] = new EmptyTile(this, point, -1);
			}

			if (revealed) {
				m_tiles[x][y].setRevealed(revealed);
				m_revealed++;
				m_hidden--;
			}

			if (diffused) {
				m_tiles[x][y].toggleDiffused();
				m_diffused++;
			}
		}

		m_bombCount = bombs;

		// update the adjacent counts
		for (int i = 0; i < m_boardSize; i++) {
			for (int j = 0; j < m_boardSize; j++) {
				if (m_tiles[i][j] instanceof EmptyTile) {
					int adjacent = getAdjacentCount(i, j);
					((EmptyTile) m_tiles[i][j]).setAdjacentBombs(adjacent);
				}
			}
		}
	}

	/**
	 * Sets the player for the board
	 *
	 * @param player the player
	 */
	public void setPlayer(Player player) {
		m_player = player;
	}

	/**
	 * Gets number of bombs on board
	 *
	 * @return Number of bombs
	 */
	public int getBombCount() {
		return m_bombCount;
	}

	/**
	 * Gets number of diffused tiles on board
	 *
	 * @return Number of diffused tiles
	 */
	public int getDiffused() {
		return m_diffused;
	}

	/**
	 * Gets number of hidden tiles on board
	 *
	 * @return Number of hidden tiles
	 */
	public int getHidden() {
		return m_hidden;
	}

	/**
	 * Gets number of revealed tiles on board
	 *
	 * @return Number of revealed tiles
	 */
	public int getRevealed() {
		return m_revealed;
	}

	/**
	 * Gets the size of the board
	 * @return the board size
	 */
	public int getBoardSize() {
		return m_boardSize;
	}

	/**
	 * Sets the display for this board
	 *
	 * @param display the display
	 */
	public void setDisplay(DisplayBoard display) {
		m_display = display;

		for (Tile[] tiles : m_tiles) {
			for (Tile tile : tiles) {
				display.add(tile);
			}
		}
	}

    /**
     * Gets a boolean indicating the game is lost
     *
     * @return True if game is lost, false otherwise
     */
    public boolean isLost() {
        for (Tile[] tileRow : m_tiles) {
            for (Tile tile : tileRow) {
                if (tile.hasLost()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets a boolean indicating the game is won
     *
     * @return True if game is won, false otherwise
     */
    public boolean isWon() {
        for (Tile[] tileRow : m_tiles) {
            for (Tile tile : tileRow) {
                if (!tile.isWinning()) {
                    return false;
                }
            }
        }

        return true;
    }
	
	/**
     * Reveals a tile
     *
     * @param tile the Tile to reveal
	 * @return true if the tile was revealed
     */
    public boolean revealTile(Tile tile) {
		boolean revealed = m_player.revealTile(tile);
		if (revealed) {

			// update the reveal and hidden counters
			m_revealed++;
			m_hidden--;
			m_display.updateHeader();

			// you can only lose from revealing, so we'll check that here
			if (isLost()) {
				m_display.gameOver(false);
			} else if (isWon()) {
				m_display.gameOver(true);
			}
		}

		return revealed;
    }

    /**
     * Used to toggle the visibility of bombs on the board
     * @param reveal set to true to show bombs, false to hide
     */
	public void revealBombs(boolean reveal) {
		m_showing = reveal;

		for (Tile[] tiles : m_tiles) {
			for (Tile tile : tiles) {
				tile.showBomb(reveal);
			}
		}
	}

	/**
	 * Gets a tile from the board
	 * @param x the x position of the tile
	 * @param y the y position of the tile
	 * @return the tile at x,y position in the board
	 */
	public Tile getTile(int x, int y) {
		return m_tiles[x][y];
	}

    /**
     * Diffuses a tile
     *
     * @param tile the Tile to diffuse
     */
    public void diffuseTile(Tile tile) {
		m_player.diffuseTile(tile);

		// update the diffused counter
		if (tile.isDiffused()) {
			m_diffused++;
			m_display.updateHeader();

			// all may now be revealed and diffused
			if (isWon()) {
				m_display.gameOver(true);
			}
		} else {
			m_diffused--;
			m_display.updateHeader();
		}

    }
	
	/**
     * Gets the number of bombs adjacent to a tile
     *
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     * @return The number of adjacent bombs
     */
    private int getAdjacentCount(int x, int y) {
        int bombs = 0;

        // loop through the adjacent x columns
        for (int i = x - 1; i <= (x + 1); i++) {

            // loop through adjacent y rows
            for (int j = y - 1; j <= (y + 1); j++) {

                // check tile is valid (e.g not off edge)
                if ((i < m_boardSize) && (i >= 0)
                        && (j < m_boardSize) && (j >= 0)) {

                    // if tile is a bomb, increment counter
                    if ((m_tiles[i][j] != null) && m_tiles[i][j].isBomb()) {
                        bombs++;
                    }
                }

            }
        }

        return bombs;
    }

	/**
     * Reveals tiles adjacent to a given tile
     *
     * @param tile the tile to reveal the adjacent tiles from
     */
    public void revealAdjacent(Tile tile) {
		int x = tile.getPosition().x;
		int y = tile.getPosition().y;

        for (int i = x - 1; i <= (x + 1); i++) {
            for (int j = y - 1; j <= (y + 1); j++) {

				// check that we aren't revealing the already revealed tile
				if ((x != i) || (y != j)) {

                	// check that the tile is in the board
                	if ((i < m_boardSize) && (i >= 0)
							&& (j < m_boardSize) && (j >= 0)) {

                        revealTile(m_tiles[i][j]);
                    }
                }
            }
        }
    }

	/**
	 * Convert this board object into a CSV string
	 * @return the CSV formatted string
	 */
    public String toCsv() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("x,y,bomb,diffused,revealed\n");

		// each tile is a line
		for (Tile[] tiles : m_tiles) {
			for (Tile tile : tiles) {
				builder.append(tile.getPosition().x + ",");
				builder.append(tile.getPosition().y + ",");
				builder.append(tile.isBomb() + ",");
				builder.append(tile.isDiffused() + ",");
				builder.append(tile.isRevealed() + "\n");
			}
		}

	    return builder.toString();
	}

    /**
     * getter for the current player object 
     * @return the player object
     */
	public Player getPlayer() {
		return m_player;
	}

	/**
	 * used to check if the board is currently showing bombs
	 * @return true if bombs are visible
	 */
	public boolean isRevealingBombs() {
		return m_showing;
	}
}
