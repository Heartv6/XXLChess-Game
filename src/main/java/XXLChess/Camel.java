package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Camel` class represents a camel piece in XXLChess game.
 * It extends the Piece class.
 * The Camel can move in a specific pattern and capture enemy pieces.
 */

public class Camel extends Piece{
     /**
     * Constructs a new Camel object with the specified parameters.
     *
     * @param x         The x-coordinate of the Camel's position.
     * @param y         The y-coordinate of the Camel's position.
     * @param isPlayer  The player number (1 or -1) to which the Camel belongs.
     * @param isWhite   Indicates whether the Camel is white or not.
     * @param tile      The tile on which the Camel is placed.
     */

    public Camel(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 3, isPlayer, isWhite, tile);
        
    }

    /**
     * Sets the image of the amazon piece using the provided `PApplet` object.
     *
     * @param app The `PApplet` object used to load the image.
     */

    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-camel.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-camel.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds target tiles to the possible move list based on the Camel's movement rules.
     *
     * @param board  The game board object.
     */
    public void addTargetTile(Board board) {
        // Check and add target tiles based on specific conditions
        if (tile.getRow() - 1 >= 0 && tile.getColumn() + 3 < 14 &&
            (board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 3].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 3].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 3]);
        }
        if (tile.getRow() - 1 >= 0 && tile.getColumn() - 3 >= 0 &&
            (board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 3].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 3].getPiece().isWhite == !isWhite)) {
            lsTile.add(board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 3]);
        }
        if (tile.getRow() + 1 < 14 && tile.getColumn() + 3 < 14 &&
            (board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 3].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 3].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 3]);;
        }
        if (tile.getRow() + 1 < 14 && tile.getColumn() - 3 >= 0 &&
            (board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 3].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 3].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 3]);
        }
        if (tile.getRow() + 3 < 14 && tile.getColumn() - 1 >= 0 &&
            (board.boardMatrix[tile.getRow() + 3][tile.getColumn() - 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 3][tile.getColumn() - 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 3][tile.getColumn() - 1]);
        }
        if (tile.getRow() + 3 < 14 && tile.getColumn() + 1 < 14 &&
            (board.boardMatrix[tile.getRow() + 3][tile.getColumn() + 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 3][tile.getColumn() + 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 3][tile.getColumn() + 1]);
        }
        if (tile.getRow() - 3 >= 0 && tile.getColumn() + 1 < 14 &&
            (board.boardMatrix[tile.getRow() - 3][tile.getColumn() + 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 3][tile.getColumn() + 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 3][tile.getColumn() + 1]);
        }
        if (tile.getRow() - 3 >= 0 && tile.getColumn() - 1 >= 0 &&
            (board.boardMatrix[tile.getRow() - 3][tile.getColumn() - 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 3][tile.getColumn() - 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 3][tile.getColumn() - 1]);
        }

        for(Tile tile1 : lsTile) {
            if (tile1.getPiece() != null) {
                if(tile1.getPiece().isWhite == ! isWhite){
                    capturedTiles.add(tile1);
                }
            }
        }

    }

    /**
     * Generates the target tiles for the Camel.
     *
     * @param board  The game board object.
     */
    public void targetTile(Board board) {
        addTargetTile(board);
        
    }
}