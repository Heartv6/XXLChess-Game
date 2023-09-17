package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Knight` class represents a knight piece in the XXLChess game.
 * It extends Piece and implements KnightMove
 */

public class Knight extends Piece implements KnightMove{ 
   
     /**
     * Constructs a new Knight object.
     *
     * @param x         The x-coordinate of the knight on the game board.
     * @param y         The y-coordinate of the knight on the game board.
     * @param isPlayer  The player number (1 or -1) to which the knight belongs.
     * @param isWhite   Indicates whether the knight is white (true) or black (false).
     * @param tile      The initial tile of the knight.
     */
    public Knight(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 2, isPlayer, isWhite, tile);
        
    }

    /**
     * Loads and sets the image of the knight.
     *
     * @param app  The PApplet instance for rendering.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-knight.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-knight.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Determines the target tiles that the knight can move to and updates the lists of target tiles and captured tiles.
     *
     * @param board  The game board.
     */

    public void targetTile(Board board) {
        knightAddTiles(board, tile, lsTile, capturedTiles, isWhite);
        
    }
}