package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Archbishop` class represents an archbishop chess piece in the XXLChess game.
 * It extends the `Bishop` class and implements the `KnightMove` interface to include knight-like movement.
 */
public class Archbishop extends Bishop implements KnightMove{

    /**
     * Constructs a new `Archbishop` object with the specified parameters.
     *
     * @param x         The x-coordinate of the piece on the board.
     * @param y         The y-coordinate of the piece on the board.
     * @param isPlayer  The player number (1 or -1) to which the archibishop belongs.
     * @param isWhite   Indicates whether the piece is white (`true`) or black (`false`).
     * @param tile      The tile on which the piece is placed.
     */
    public Archbishop(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, isPlayer, isWhite, tile);
        this.value = 7.5;
    }

    /**
     * Sets the image of the archbishop piece using the provided `PApplet` object.
     *
     * @param app The `PApplet` object used to load the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-archbishop.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-archbishop.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds the target tiles that the archbishop can move to and captures any opponent pieces on those tiles.
     * The archbishop can move like a bishop and also has knight-like movement.
     *
     * @param board The `Board` object representing the game board.
     */
    public void targetTile(Board board) {
        knightAddTiles(board, this.tile, this.lsTile, this.capturedTiles, isWhite);
        addTargetTile(board);
        
        
    }

}
