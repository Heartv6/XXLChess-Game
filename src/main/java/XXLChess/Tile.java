package XXLChess;

import processing.core.*;
import java.util.*;
/**
 * The `Tile` class represents a single tile on a chessboard.
 * Each tile can have a row and column position, a piece (if any),
 * and x and y coordinates in pixels for rendering purposes.
 */

public class Tile {
    /**
    * the row of tile 
    * the column of tile
    * the piece on that tile 
    * the x position (Unit: pixels)
    * the y position (Unig: pixels)
    */
    private int row;
    private int column;
    private Piece piece;
    private int x;
    private int y;

    /**
     * Constructs a `Tile` object with the specified row and column positions.
     * The default piece on the tile is set to null.
     * The x and y positions are calculated based on the row and column.
     *
     * @param row    The row position of the tile.
     * @param column The column position of the tile.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.piece = null;
        this.x = this.column * 48;
        this.y = 624 - (this.row * 48);
    }

    

     /**
     * Sets the piece on the tile.
     *
     * @param piece The piece to be set on the tile.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Retrieves the piece on the tile.
     *
     * @return The piece on the tile.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Sets the row position of the tile.
     * Updates the y-coordinate position accordingly.
     *
     * @param row The new row position of the tile.
     */
    public void setRow(int row) {
        this.row = row;
        this.y = 624 - (this.row * 48);
    }

    /**
     * Return the row position of the tile.
     *
     * @return The row position of the tile.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Sets the column position of the tile.
     * Updates the x-coordinate position accordingly.
     *
     * @param column The new column position of the tile.
     */
    public void setColumn(int column) {
        this.column = column;
        this.x = this.column * 48;
    }

    /**
     * Return the column position of the tile.
     *
     * @return The column position of the tile.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Return the x-coordinate position of the tile.
     *
     * @return The x-coordinate position of the tile.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y-coordinate position of the tile.
     *
     * @return The y-coordinate position of the tile.
     */
    public int getY() {
        return this.y;
    }


}

    


