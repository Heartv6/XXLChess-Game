package XXLChess;
import processing.core.*;
import java.util.*;

/**
 * The `Bishop` class represents a bishop chess piece in the XXLChess game.
 * It extends the `Piece` class
 */

public class Bishop extends Piece{
    /**
     * Constructs a new `Bishop` object with the specified parameters.
     *
     * @param x         The x-coordinate of the piece on the board.
     * @param y         The y-coordinate of the piece on the board.
     * @param isPlayer  The player number (1 or -1) to which the Bishop belongs.
     * @param isWhite   Indicates whether the piece is white (`true`) or black (`false`).
     * @param tile      The tile on which the piece is placed.
     */
    public Bishop(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, 3.625, isPlayer, isWhite, tile);
    }

    /**
     * Sets the image of the bishop piece using the provided `PApplet` object.
     *
     * @param app The `PApplet` object used to load the image.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-bishop.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-bishop.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds the target tiles that the bishop can move to and captures any opponent pieces on those tiles.
     *
     * @param board The `Board` object representing the game board.
     */
    public void addTargetTile(Board board) {
        //Consider lefttop
        for(int i = this.tile.getRow() + 1, j = this.tile.getColumn() - 1; i < 14 && j >= 0; i++, j--){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i < 13 && j > 0 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i + 1][j - 1]);
                    }
                    break;
                }else{
                    if(i < 13 && j > 0){
                        specialTiles.add(board.boardMatrix[i + 1][j - 1]);
                    }
                    break;
                }

            }
        }

        //Consider rightTop
        for(int i = this.tile.getRow() + 1, j = this.tile.getColumn() + 1; i < 14 && j < 14; i++, j++){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i < 13 && j < 13 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i + 1][j + 1]);
                    }
                    break;
                }else{
                    if(i < 13 && j < 13){
                        specialTiles.add(board.boardMatrix[i + 1][j + 1]);
                    }
                    break;
                }

            }
        }
        
        //Consider leftBottom
        for(int i = this.tile.getRow() - 1, j = this.tile.getColumn() - 1; i >= 0 && j >= 0; i--, j--){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i > 0 && j > 0 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i - 1][j - 1]);
                    }
                    break;
                }else{
                    if(i > 0 && j > 0){
                        specialTiles.add(board.boardMatrix[i - 1][j - 1]);
                    }
                    break;
                }

            }
        }

         //Consider rightBottom
         for(int i = this.tile.getRow() - 1, j = this.tile.getColumn() + 1; i >= 0 && j < 14; i--, j++){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i > 0 && j < 13 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i - 1][j + 1]);
                    }
                    break;
                }else{
                    if(i > 0 && j < 13){
                        specialTiles.add(board.boardMatrix[i - 1][j + 1]);
                    }
                    break;
                }

            }
        }


    }

    /**
     * Generates the target tiles for the Bishop.
     *
     * @param board  The game board object.
     */
    
    public void targetTile(Board board) {
        addTargetTile(board);
        
    }
    /**
     * Adds the target tiles that only considering leftTop and Bottomright
     * Used for blocking
     *
     * @param board The `Board` object representing the game board.
     */
    public void leftTopbottomRight(Board board) {
        for(int i = this.tile.getRow() + 1, j = this.tile.getColumn() - 1; i < 14 && j >= 0; i++, j--){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i < 13 && j > 0 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i + 1][j - 1]);
                    }
                    break;
                }else{
                    if(i < 13 && j > 0){
                        specialTiles.add(board.boardMatrix[i + 1][j - 1]);
                    }
                    break;
                }

            }
        }
        for(int i = this.tile.getRow() - 1, j = this.tile.getColumn() + 1; i >= 0 && j < 14; i--, j++){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i > 0 && j < 13 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i - 1][j + 1]);
                    }
                    break;
                }else{
                    if(i > 0 && j < 13){
                        specialTiles.add(board.boardMatrix[i - 1][j + 1]);
                    }
                    break;
                }

            }
        }
    }
    /**
     * Adds the target tiles that only considering leftBottom and Topright
     * Used for blocking
     *
     * @param board The `Board` object representing the game board.
     */
    public void leftBottomTopRight(Board board) {
        for(int i = this.tile.getRow() - 1, j = this.tile.getColumn() - 1; i >= 0 && j >= 0; i--, j--){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i > 0 && j > 0 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i - 1][j - 1]);
                    }
                    break;
                }else{
                    if(i > 0 && j > 0){
                        specialTiles.add(board.boardMatrix[i - 1][j - 1]);
                    }
                    break;
                }

            }
        }

        for(int i = this.tile.getRow() + 1, j = this.tile.getColumn() + 1; i < 14 && j < 14; i++, j++){
            if(board.boardMatrix[i][j].getPiece() == null) {
                lsTile.add(board.boardMatrix[i][j]);
            }else{
                if(board.boardMatrix[i][j].getPiece().isWhite == ! this.isWhite) {
                    lsTile.add(board.boardMatrix[i][j]);
                    capturedTiles.add(board.boardMatrix[i][j]);
                    if(i < 13 && j < 13 && board.boardMatrix[i][j].getPiece() instanceof King){
                        specialTiles.add(board.boardMatrix[i + 1][j + 1]);
                    }
                    break;
                }else{
                    if(i < 13 && j < 13){
                        specialTiles.add(board.boardMatrix[i + 1][j + 1]);
                    }
                    break;
                }

            }
        }
    }




}
