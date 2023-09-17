package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Amazon` class represents an amazon chess piece in the XXLChess game.
 * It extends the `Bishop` class and implements the `KnightMove` and `RookMove` interfaces.
 * The amazon combines the movement capabilities of both a bishop and a rook.
 */

public class Amazon extends Bishop implements KnightMove, RookMove{
    /**
     * Constructs a new `Amazon` object with the specified parameters.
     *
     * @param x         The x-coordinate of the piece on the board.
     * @param y         The y-coordinate of the piece on the board.
     * @param isPlayer  The player number (1 or -1) to which the piece belongs.
     * @param isWhite   Indicates whether the piece is white (`true`) or black (`false`).
     * @param tile      The tile on which the piece is placed.
     */
    public Amazon(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, isPlayer, isWhite, tile);
        this.value = 12;
        
    }


    /**
     * Sets the image of the amazon piece using the provided `PApplet` object.
     *
     * @param app The `PApplet` object used to load the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-amazon.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-amazon.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds the target tiles that the amazon can move to and captures any opponent pieces on those tiles.
     * The amazon can move like a bishop, a rook, and also has knight-like movement.
     *
     * @param board The `Board` object representing the game board.
     */
    public void targetTile(Board board){
            knightAddTiles(board, this.tile, this.lsTile, this.capturedTiles, this.isWhite);
            rookAddTiles(board, this.tile, this.lsTile, this.capturedTiles, this.isWhite, specialTiles);
            addTargetTile(board);

    }
}
