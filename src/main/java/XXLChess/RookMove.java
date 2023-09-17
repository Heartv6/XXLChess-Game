package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `RookMove` interface defines the behavior for rook movement in a chess game.
 * It provides a default method `rookAddTiles` to add the valid tiles that a rook can move to.
 */

public interface RookMove {
     /**
     * Adds the valid tiles that a rook can move to, based on the current state of the board.
     * The valid tiles are added to the specified `lsTile` ArrayList.
     * If there are any captured pieces during the movement, they are added to the `capturedTiles` ArrayList.
     * If any special tiles need to be considered, such as when a king is exposed to check,
     * they are added to the `specialTiles` ArrayList.
     *
     * @param board         The chessboard containing the tiles.
     * @param tile          The current tile representing the rook's position.
     * @param lsTile        The ArrayList to store the valid tiles that the rook can move to.
     * @param capturedTiles The ArrayList to store any captured pieces during the movement.
     * @param isWhite       A boolean indicating whether the rook belongs to the white player.
     * @param specialTiles  The ArrayList to store any special tiles that need to be considered.
     */
    public default void rookAddTiles(Board board,Tile tile, ArrayList<Tile> lsTile, ArrayList<Tile> capturedTiles, boolean isWhite
    ,ArrayList<Tile> specialTiles){
        //Consider rightpart
        for(int i = tile.getColumn() + 1; i < 14; i++){
            if (board.boardMatrix[tile.getRow()][i].getPiece() == null) {
                lsTile.add(board.boardMatrix[tile.getRow()][i]);
            }else{
                if(board.boardMatrix[tile.getRow()][i].getPiece().isWhite == ! isWhite){
                    capturedTiles.add(board.boardMatrix[tile.getRow()][i]);
                    lsTile.add(board.boardMatrix[tile.getRow()][i]);
                    if(i < 13 && board.boardMatrix[tile.getRow()][i].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[tile.getRow()][i+1]);
                    }
                    break;
                    
                }else{
                    if(i < 13){
                        specialTiles.add(board.boardMatrix[tile.getRow()][i+1]);
                    }
                    break;
                }
            }
        }
        //Consider leftpart 
        for(int i = tile.getColumn() - 1; i >= 0; i--){
            if (board.boardMatrix[tile.getRow()][i].getPiece() == null) {
                lsTile.add(board.boardMatrix[tile.getRow()][i]);
            }else{
                if(board.boardMatrix[tile.getRow()][i].getPiece().isWhite == ! isWhite){
                    capturedTiles.add(board.boardMatrix[tile.getRow()][i]);
                    lsTile.add(board.boardMatrix[tile.getRow()][i]);
                    if(i > 0 && board.boardMatrix[tile.getRow()][i].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[tile.getRow()][i-1]);
                    }
                    break;
                }else{
                    if(i > 0){
                        specialTiles.add(board.boardMatrix[tile.getRow()][i-1]);
                    }
                    
                    break;
                }
            }
        }

        //Consider toppart
        for(int i = tile.getRow() + 1; i < 14; i++){
            if (board.boardMatrix[i][tile.getColumn()].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][tile.getColumn()]);
            }else{
                if(board.boardMatrix[i][tile.getColumn()].getPiece().isWhite == ! isWhite){
                    capturedTiles.add(board.boardMatrix[i][tile.getColumn()]);
                    lsTile.add(board.boardMatrix[i][tile.getColumn()]);
                    if(i < 13 && board.boardMatrix[i][tile.getColumn()].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i+1][tile.getColumn()]);
                    }
                    
                    break;
                }else{
                    if(i < 13){
                        specialTiles.add(board.boardMatrix[i+1][tile.getColumn()]);
                    }
                    break;
                }
            }
        }

        //Consider bottompart 
        for(int i = tile.getRow() - 1; i >= 0; i--){
            if (board.boardMatrix[i][tile.getColumn()].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][tile.getColumn()]);
            }else{
                if(board.boardMatrix[i][tile.getColumn()].getPiece().isWhite == ! isWhite){
                    capturedTiles.add(board.boardMatrix[i][tile.getColumn()]);
                    lsTile.add(board.boardMatrix[i][tile.getColumn()]);
                    if(i > 0 && board.boardMatrix[i][tile.getColumn()].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i-1][tile.getColumn()]);
                    }
                    break;
                }else{
                    if (i > 0){
                        specialTiles.add(board.boardMatrix[i-1][tile.getColumn()]);
                    }
                    break;
                }
            }
        }

    }

    
    

}
