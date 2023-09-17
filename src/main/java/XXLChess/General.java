package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `General` class represents a general piece in XXLChess game. 
 * It extends the Piece class and implements the KnightMove interface.
 * The General can move in multiple directions and captures enemy pieces.
 */

public class General extends Piece implements KnightMove{
    
    /**
     * Constructs a new General object with the specified parameters.
     *
     * @param x         The x-coordinate of the General's position.
     * @param y         The y-coordinate of the General's position.
     * @param isPlayer  The player number (1 or -1) to which the General belongs.
     * @param isWhite   Indicates whether the General is white or not.
     * @param tile      The tile on which the General is placed.
     */
    public General(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 5, isPlayer, isWhite, tile);
        this.value = 5;
        
    }

     /**
     * Sets the image of the General based on its color.
     *
     * @param app  The PApplet object used for loading the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-knight-king.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-knight-king.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds target tiles to the possible move list based on the King's movement rules.
     * 
     * @param board  The game board object.
     */
    public void addTargetTile(Board board) {
        int[][] directions = {{1, -1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
        for (int[] direction : directions) {
            int newRow = tile.getRow() + direction[0];
            int newColumn = tile.getColumn() + direction[1];
            if (newRow >= 0 && newRow < 14 && newColumn >= 0 && newColumn < 14) {
                Tile newTile = board.boardMatrix[newRow][newColumn];
                if (newTile.getPiece() == null) {
                    lsTile.add(newTile);
                } else if (newTile.getPiece().isWhite != this.isWhite) {
                    lsTile.add(newTile);
                    capturedTiles.add(newTile);
                }
            }
        }
    }
    /**
     * Generates the target tiles for the General according to knightMove and KingMove.
     *
     * @param board  The game board object.
     */
    public void targetTile(Board board) {
        knightAddTiles(board, this.tile, this.lsTile, this.capturedTiles,this.isWhite);
        addTargetTile(board);
        
    }

}
