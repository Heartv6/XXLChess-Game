package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * Represents the movement behavior of a knight in chess.
 */

public interface KnightMove {
    /**
     * Adds the target tiles that a knight can move to based on its current position.
     *
     * @param board         The game board.
     * @param tile          The current tile of the knight.
     * @param lsTile        The list to store the target tiles.
     * @param capturedTiles The list to store the captured tiles.
     * @param isWhite       Indicates whether the knight is white (true) or black (false).
     */
    public default void knightAddTiles(Board board,Tile tile, ArrayList<Tile> lsTile, ArrayList<Tile> capturedTiles, boolean isWhite){
        if (tile.getRow() - 1 >= 0 && tile.getColumn() + 2 < 14 &&
            (board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 2].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 2].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 1][tile.getColumn() + 2]);
        }
        if (tile.getRow() - 1 >= 0 && tile.getColumn() - 2 >= 0 &&
            (board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 2].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 2].getPiece().isWhite == !isWhite)) {
            lsTile.add(board.boardMatrix[tile.getRow() - 1][tile.getColumn() - 2]);
        }
        if (tile.getRow() + 1 < 14 && tile.getColumn() + 2 < 14 &&
            (board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 2].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 2].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 1][tile.getColumn() + 2]);;
        }
        if (tile.getRow() + 1 < 14 && tile.getColumn() - 2 >= 0 &&
            (board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 2].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 2].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 1][tile.getColumn() - 2]);
        }
        if (tile.getRow() + 2 < 14 && tile.getColumn() - 1 >= 0 &&
            (board.boardMatrix[tile.getRow() + 2][tile.getColumn() - 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 2][tile.getColumn() - 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 2][tile.getColumn() - 1]);
        }
        if (tile.getRow() + 2 < 14 && tile.getColumn() + 1 < 14 &&
            (board.boardMatrix[tile.getRow() + 2][tile.getColumn() + 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() + 2][tile.getColumn() + 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() + 2][tile.getColumn() + 1]);
        }
        if (tile.getRow() - 2 >= 0 && tile.getColumn() + 1 < 14 &&
            (board.boardMatrix[tile.getRow() - 2][tile.getColumn() + 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 2][tile.getColumn() + 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 2][tile.getColumn() + 1]);
        }
        if (tile.getRow() - 2 >= 0 && tile.getColumn() - 1 >= 0 &&
            (board.boardMatrix[tile.getRow() - 2][tile.getColumn() - 1].getPiece() == null ||
            board.boardMatrix[tile.getRow() - 2][tile.getColumn() - 1].getPiece().isWhite == !isWhite)){
            lsTile.add(board.boardMatrix[tile.getRow() - 2][tile.getColumn() - 1]);
        }


        for(Tile tile1 : lsTile) {
            if (tile1.getPiece() != null) {
                if(tile1.getPiece().isWhite == ! isWhite){

                    capturedTiles.add(tile1);
                }
            }
        }

    }
    
}
