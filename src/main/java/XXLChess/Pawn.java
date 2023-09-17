package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `Pawn` class rpresents a Pawn piece in the chess game.
 * It extends Piece
 */
public class Pawn extends Piece {

    /**
     * Constructs a Pawn object.
     *
     * @param x         The x-coordinate of the piece.
     * @param y         The y-coordinate of the piece.
     * @param isPlayer  The player to which the piece belongs (1 for player , -1 for enemy).
     * @param isWhite   Indicates whether the piece is white (true) or black (false).
     * @param tile      The tile on the board where the piece is located.
     */
    public Pawn(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 1, isPlayer, isWhite, tile);
    }

    /**
     * Sets the image of the Pawn piece based on its color.
     *
     * @param app   The PApplet object for loading the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-pawn.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-pawn.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds the target tiles that the Pawn can move to.
     * The pawn can move two block if it is not moved before.
     * 
     * @param board The game board.
     */
    public void addTargetTile(Board board) {
        if (moved == true) {
            Tile targetTile = board.boardMatrix[tile.getRow() + 1 * this.isPlayer][tile.getColumn()];
            if (board.boardMatrix[targetTile.getRow()][targetTile.getColumn()].getPiece() == null) {
                lsTile.add(targetTile);
            }
        
        }else if (moved == false) {
            if (board.boardMatrix[tile.getRow() + 1 * this.isPlayer][tile.getColumn()].getPiece() != null){
                return;
            }
            else{
                ArrayList<Tile> targetTiles = new ArrayList<>();
                targetTiles.add(board.boardMatrix[tile.getRow() + 1 * this.isPlayer][tile.getColumn()]);
                targetTiles.add(board.boardMatrix[tile.getRow() + 2 * this.isPlayer][tile.getColumn()]);
                for(Tile tile : targetTiles) {
                    if (board.boardMatrix[tile.getRow()][tile.getColumn()].getPiece() == null){
                        lsTile.add(tile);
                    }
                }

            }

        }

        //Capturin the tiles
        // Only add tiles that is in the range of 14
        if (this.tile.getColumn() == 0) {
            specialTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
            if (board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1].getPiece() != null){
                if(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1].getPiece().isWhite == !this.isWhite){
                    lsTile.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
                    capturedTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
                }
                
            }

        }else if(this.tile.getColumn() == 13) {
            specialTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
            if (board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1].getPiece() != null) {
                if(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1].getPiece().isWhite == !this.isWhite){
                    lsTile.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
                    capturedTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
                }
                
            }
        }else{
            specialTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
            specialTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
            if (board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1].getPiece() != null){
                if(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1].getPiece().isWhite == !this.isWhite){
                    lsTile.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
                    capturedTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() + 1]);
                }
                
            }
            if (board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1].getPiece() != null) {
                if(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1].getPiece().isWhite == !this.isWhite){
                    lsTile.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
                    capturedTiles.add(board.boardMatrix[this.tile.getRow() + 1 * this.isPlayer][this.tile.getColumn() - 1]);
                }
                
            }
        }


              
    }

    /**
     * Calculates the target tiles that the Pawn can move to.
     *
     * @param board The game board.
     */
    public void targetTile(Board board) {
        addTargetTile(board);
        
    } 

    
    
}
