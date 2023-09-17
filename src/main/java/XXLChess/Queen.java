package XXLChess;

import processing.core.*;
import java.util.*;
/**
 * The `Queen` class represents a queen piece in a chess game.
 * It extends the `Bishop` class and implements the `RookMove` interface.
 */

public class Queen extends Bishop implements RookMove{
    
    /**
     * Constructs a `Queen` object with the specified coordinates, player, color, and initial tile.
     *
     * @param x       The x-coordinate of the queen on the chessboard.
     * @param y       The y-coordinate of the queen on the chessboard.
     * @param isPlayer An integer indicating the player to which the queen belongs.
     * @param isWhite A boolean indicating whether the queen is white or black.
     * @param tile    The initial tile occupied by the queen.
     */
    public Queen(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, isPlayer, isWhite, tile);
        this.value = 9.5;
        
    }

     /**
     * Loads and sets the image for the queen based on its color.
     *
     * @param app The PApplet object for rendering.
     */

    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-queen.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-queen.png");
            this.sprite.resize(48,48);
        }
    }
    /**
     * Determines the valid target tiles for the queen based on its movement rules(Bishop + Rook).
     * Adds the valid tiles to the `lsTile` ArrayList.
     *
     * @param board The chessboard containing the tiles.
     */
    public void targetTile(Board board) {
        rookAddTiles(board, this.tile, this.lsTile, this.capturedTiles,this.isWhite,specialTiles);
        addTargetTile(board);
        
    }

    

}

