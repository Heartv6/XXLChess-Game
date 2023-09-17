package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Rook` class represents a rook piece in a chess game.
 * It extends the `Piece` class and implements the `RookMove` interface.
 */

public class Rook extends Piece implements RookMove{
    /**
     * Constructs a `Rook` object with the specified coordinates, player, color, and initial tile.
     *
     * @param x       The x-coordinate of the rook on the chessboard.
     * @param y       The y-coordinate of the rook on the chessboard.
     * @param isPlayer An integer indicating the player to which the rook belongs.
     * @param isWhite A boolean indicating whether the rook is white or black.
     * @param tile    The initial tile occupied by the rook.
     */
    public Rook(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 5.25, isPlayer, isWhite, tile);
    }

    /**
     * Loads and sets the image for the rook based on its color.
     *
     * @param app The PApplet object for rendering.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-rook.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-rook.png");
            this.sprite.resize(48,48);
        }
    }

   
     /**
     * Determines the valid target tiles for the rook based on its movement rules.
     * Used for piece blocking
     *
     * @param board The chessboard containing the tiles.
     */
    public void targetTile(Board board) {
        rookAddTiles(board, tile, lsTile, capturedTiles, isWhite,specialTiles);
        
        
        
    }
     /**
     * Considers the top and bottom movement of the rook and adds the valid tiles.
     * Updates the `lsTile`, `capturedTiles`, and `specialTiles` ArrayLists accordingly.
     *
     * @param board The chessboard containing the tiles.
     */
    public void topBottom(Board board){
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
     /**
     * Considers the left and right movement of the rook and adds the valid tiles.
     * Used for piece blocking
     *
     * @param board The chessboard containing the tiles.
     */

    public void leftRight(Board board) {
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
    }




}
