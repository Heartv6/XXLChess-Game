package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Chancellor` class represents a chancellor piece in XXLChess game.
 * It extends the Piece class and implements the KnightMove and RookMove interfaces.
 * The Chancellor can move like a knight and a rook, combining their movement patterns.
 */
public class Chancellor extends Piece implements KnightMove,RookMove{
    
     /**
     * Constructs a new Chancellor object with the specified parameters.
     *
     * @param x         The x-coordinate of the Chancellor's position.
     * @param y         The y-coordinate of the Chancellor's position.
     * @param isPlayer  The player number (1 or -1) to which the Chancellor belongs.
     * @param isWhite   Indicates whether the Chancellor is white or not.
     * @param tile      The tile on which the Chancellor is placed.
     */
    public Chancellor(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 8.5, isPlayer, isWhite, tile);
    }

    /**
     * Sets the image of the Chancellor based on its color.
     *
     * @param app  The PApplet object used for loading the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-chancellor.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-chancellor.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Generates the target tiles for the Chancellor according to knight and rook.
     *
     * @param board  The game board object.
     */
    public void targetTile(Board board) {
    
        knightAddTiles(board, tile, lsTile, capturedTiles, isWhite);
        rookAddTiles(board, tile, lsTile, capturedTiles, isWhite,specialTiles);
        
    }
}
