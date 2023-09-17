package XXLChess;

import processing.core.*;
import java.util.*;

/**
 * The `King` class represents a king piece in the XXLChess game.
 * It extends Piece
 */

public class King extends Piece{
    // LeftCastle
    public boolean castleLeft = false;
    // RightCastle
    public boolean castleRight = false;
    public ArrayList<Tile> possibleTiles = new ArrayList<>();

    /**
     * Constructs a new King object.
     *
     * @param x         The x-coordinate of the king on the game board.
     * @param y         The y-coordinate of the king on the game board.
     * @param isPlayer  The player number (1 or -1) to which the king belongs.
     * @param isWhite   Indicates whether the king is white (true) or black (false).
     * @param tile      The initial tile of the king.
     */
    public King(int x, int y, int isPlayer, boolean isWhite, Tile tile) {
        super(x, y, Double.POSITIVE_INFINITY, isPlayer, isWhite, tile);
        
    }

    /**
     * Loads and sets the image of the king.
     *
     * @param app  The PApplet instance for rendering.
     */
    public void setImage(PApplet app) {
        if (this.isWhite == true){
            this.sprite = app.loadImage("src/main/resources/XXLChess/w-king.png");
            this.sprite.resize(48,48);

        }else{
            this.sprite = app.loadImage("src/main/resources/XXLChess/b-king.png");
            this.sprite.resize(48,48);
        }
    }

    /**
     * Adds the target tiles that the king can move to and updates the lists of target tiles and captured tiles.
     * The king can not move to the place that is in danger 
     * @param board  The game board.
     */
    public void addTargetTile(Board board) {
        // Add all the positions 
        int[][] directions = {{1, -1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
        for (int[] direction : directions) {
            int newRow = tile.getRow() + direction[0];
            int newColumn = tile.getColumn() + direction[1];
            if (newRow >= 0 && newRow < 14 && newColumn >= 0 && newColumn < 14) {
                Tile newTile = board.boardMatrix[newRow][newColumn];
                
                if (newTile.getPiece() == null) {
                    // If there is no piece on that tile, add it to the lsTile
                    lsTile.add(newTile);
                    possibleTiles.add(newTile);
                } else if (newTile.getPiece().isWhite != this.isWhite) {
                    // If there is a piece that color is different between this piece
                    // Add it to the capture tiles
                    lsTile.add(newTile);
                    capturedTiles.add(newTile);
                    possibleTiles.add(newTile);
                }
            }
        }
        //Castleing
        if (this.moved == false) {
            for(int i = tile.getColumn() + 1; i < 14; i++){
                if (board.boardMatrix[this.tile.getRow()][i].getPiece() == null) {
                    ;
                }else if(board.boardMatrix[this.tile.getRow()][i].getPiece() instanceof Rook &&
                        board.boardMatrix[this.tile.getRow()][i].getPiece().isWhite == this.isWhite &&
                        board.boardMatrix[this.tile.getRow()][i].getPiece().moved == false) {
                    lsTile.add(board.boardMatrix[this.tile.getRow()][this.tile.getColumn()+ 2]);
                    castlerookRight = board.boardMatrix[this.tile.getRow()][i].getPiece();
                    

                }else{
                    break;
                }
                
            }
            for(int i = tile.getColumn() - 1; i >= 0; i--){
                if (board.boardMatrix[this.tile.getRow()][i].getPiece() == null) {
                    ;
                }else if(board.boardMatrix[this.tile.getRow()][i].getPiece() instanceof Rook &&
                        board.boardMatrix[this.tile.getRow()][i].getPiece().isWhite == this.isWhite &&
                        board.boardMatrix[this.tile.getRow()][i].getPiece().moved == false) {
                    lsTile.add(board.boardMatrix[this.tile.getRow()][this.tile.getColumn()- 2]);
                    castlerookLeft = board.boardMatrix[this.tile.getRow()][i].getPiece();
                    
                    
                }else{
                    break;
                }
                
            }
        }
        
        // Checking for king not moving to danger
        int count = lsTile.size();
        ArrayList<Tile> removedForPlayer = new ArrayList<>();
        ArrayList<Tile> removedForEnemy = new ArrayList<>();
        this.addPossibleTiles(board);
        // Check if that tile is currently one of the all possibleTiles in enemy/player collection
        for(int i = 0; i < count; i++) {
             if(isPlayer == 1){
                if (board.allEnemyTile.contains(lsTile.get(i)) || board.specialEnemyTile.contains(lsTile.get(i))){
                   removedForPlayer.add(lsTile.get(i));
                }else if(board.enemyKing.possibleTiles.contains(lsTile.get(i))) {
                    removedForPlayer.add(lsTile.get(i));
                }
             
            }else{
                if (board.allPlayerTile.contains(lsTile.get(i)) || board.specialPlayerTile.contains(lsTile.get(i))){
                    removedForEnemy.add(lsTile.get(i));
                    
                }else if(board.playerKing.possibleTiles.contains(lsTile.get(i))) {
                    removedForEnemy.add(lsTile.get(i));
                }
                
            }
           
        }
        if(isPlayer == 1){
            lsTile.removeAll(removedForPlayer); 
        
        }else{
            lsTile.removeAll(removedForEnemy);
        
        }
       
        
        

        
    }

    /**
     * Returns a list of pieces that are contributing to a check on the king.
     *
     * @param board  The game board.
     * @return       The list of pieces contributing to a check on the king.
     */

    public ArrayList<Piece> contributingCheck(Board board) {
        ArrayList<Piece> contributePiece = new ArrayList<>();
        this.addPossibleTiles(board);
        possibleTiles.add(this.tile);
        if(this.isPlayer == 1){
            for(Piece piece : board.enemyCollection){
                Piece simPiece = (Piece) piece.clone();
                simPiece.targetTile(board);
                simPiece.lsTile.retainAll(possibleTiles);
                if(simPiece.lsTile.size()!= 0) {
                    contributePiece.add(simPiece);
                }
            }
            return contributePiece;
        }else{
            for(Piece piece : board.playerCollection){
                Piece simPiece = (Piece) piece.clone();
                simPiece.targetTile(board);
                simPiece.lsTile.retainAll(possibleTiles);
                if(simPiece.lsTile.size()!= 0) {
                    contributePiece.add(simPiece);
                }
            }
            return contributePiece;

        }
    }

    /**
     * Adds the possible target tiles that the king can move to.
     *
     * @param board  The game board.
     */
    
    public void addPossibleTiles(Board board) {
        possibleTiles = new ArrayList<>();
        int[][] directions = {{1, -1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}, {-1, 1}, {-1, 0}, {-1, -1}};
        for (int[] direction : directions) {
            int newRow = tile.getRow() + direction[0];
            int newColumn = tile.getColumn() + direction[1];
            if (newRow >= 0 && newRow < 14 && newColumn >= 0 && newColumn < 14) {
                Tile newTile = board.boardMatrix[newRow][newColumn];
                if (newTile.getPiece() == null) {
                    possibleTiles.add(newTile);
                } else if (newTile.getPiece().isWhite != this.isWhite) {
                    possibleTiles.add(newTile);
                }
            }
        }
        
    }

    /**
     * Determines the target tiles that the king can move to and updates the lists of target tiles and captured tiles.
     *
     * @param board  The game board.
     */
    public void targetTile(Board board) {
        addTargetTile(board);
        
    }
}