package XXLChess;

import processing.core.*;
import java.util.*;



/**
 * The abstract `Piece` class represents a chess piece.
 * It provides common properties and methods for different types of chess pieces.
 */

public abstract class Piece implements Cloneable{
    //Properties
    protected int x;
    protected int y;
    protected double value;
    protected int isPlayer;
    protected boolean isWhite;
    /**
     * The piece is alive or not
     */
    protected boolean isAlive;
    /**
     * The speed of piece and maxMoveTime
     */
    protected double speed;
    protected int maxMoveTime;
    protected boolean isPressed;
    protected Tile tile;

    /**
     * whether the tiles of piece need to be clear
     */
    protected boolean need_clear = false;
     /**
     * The list of all valid tiles
     */
    protected ArrayList<Tile> lsTile;
     /**
     * The list of cpatured tiles
     */
    protected ArrayList<Tile> capturedTiles;
    /**
     * The list of two tiles (original and targetTile)
     */
    protected ArrayList<Tile> historyTiles;
    /**
     * Difference between the piece and targettile
     */
    protected int differenceX;
    protected int differenceY;

    protected boolean moved;
    // Castleing
    protected Piece castlerookLeft;
    protected Piece castlerookRight;

    protected ArrayList<Tile> specialTiles;
    // The piece is pin or not
    protected boolean pin;
  



    protected PImage sprite;

     /**
     * Constructs a `Piece` object with the specified coordinates, value, player, color, and initial tile.
     *
     * @param x         The x-coordinate of the piece on the chessboard.
     * @param y         The y-coordinate of the piece on the chessboard.
     * @param value     The value of the piece.
     * @param isPlayer  An integer indicating the player to which the piece belongs (1 for player, -1 for enemy).
     * @param isWhite   A boolean indicating whether the piece is white or black.
     * @param tile      The initial tile occupied by the piece.
     */
    public Piece(int x, int y, double value, int isPlayer, boolean isWhite, Tile tile) {
        this.x = tile.getColumn() * 48;
        this.y = 624 - (tile.getRow() * 48);
        this.value = value;
        this.isPlayer = isPlayer;
        this.isWhite = isWhite;
        this.tile = tile;
        this.isAlive = true;
        this.speed = 0;
        this.maxMoveTime = 0;
        this.isPressed = false;
        this.lsTile = new ArrayList<>();
        this.capturedTiles = new ArrayList<>();
        this.historyTiles = new ArrayList<>();
        this.specialTiles = new ArrayList<>();
        this.moved = false;
        this.pin = false;
    }

    /**
     * Clones the current `Piece` object.
     *
     * @return A new instance of the cloned `Piece` object.
     */

    @Override
    public Piece clone() {
        try {
            Piece clonedPiece = (Piece) super.clone();
            
            // Deep copy the ArrayList objects
            clonedPiece.lsTile = new ArrayList<>(this.lsTile);
            clonedPiece.capturedTiles = new ArrayList<>(this.capturedTiles);
            clonedPiece.historyTiles = new ArrayList<>(this.historyTiles);
            clonedPiece.specialTiles = new ArrayList<>(this.specialTiles);
            
            // Perform deep cloning for any other reference types in the class
            
            return clonedPiece;
        } catch (CloneNotSupportedException e) {
            // Handle the exception if cloning is not supported
            return null;
        }   
    }
    

    /**
     * Sets the image of the piece.
     *
     * @param app The PApplet object for rendering.
     */
    public abstract void setImage(PApplet app);

    /**
     * Determines the valid target tiles for the piece based on its movement rules.
     * Adds the valid tiles to the `lsTile` ArrayList.
     *
     * @param board The chessboard containing the tiles.
     */
    public abstract void targetTile(Board board);

    /**
     * See rook class
     * @param board the Board object
     */
    public void topBottom(Board board){

    }

    /**
     * See rook class
     * @param board the Board object
     */
    public void leftRight(Board board) {

    }

    /**
     * See bishop class
     * @param board the Board object
     */
    public void leftBottomTopRight(Board board){

    }

    /**
     * See bishop class
     * @param board the Board object
     */
    public void leftTopbottomRight(Board board){

    }

    /**
     * Checks if the piece is pinned on the board.
     * A piece is considered pinned if it cannot move without leaving its own king under attack.
     * 
     * @param board The board on which the piece is placed.
     */
    public void Pin(Board board){
        this.checkPin(board);
        if (this.pin){
            this.lsTile.clear();
            this.capturedTiles.clear();
        }
    }

     /**
     * Moves the piece to the specified target tile on the board.
     * Simulate all possible moves of this chess piece on the current board.
     * Check if the king will be checked if this piece go to these tiles.
     * If all the places he goes will make the current king check, then the piece is pinned.
     * 
     * @param board The board on which the piece is placed.
     */
    public void checkPin(Board board) {
        Piece simPiece = (Piece) this.clone();
        Board simBoard = (Board) board.clone();
        simPiece.lsTile.clear();
        simPiece.capturedTiles.clear();
        simPiece.targetTile(simBoard);
        int counter = 0;
        Tile originTile = this.tile;

        for(Tile tile : simPiece.lsTile) {
            simBoard.boardMatrix[originTile.getRow()][originTile.getColumn()].setPiece(null);
            Piece originPiece = simBoard.boardMatrix[tile.getRow()][tile.getColumn()].getPiece();
            simBoard.boardMatrix[tile.getRow()][tile.getColumn()].setPiece(simPiece);   
            simPiece.tile = tile;
            simBoard.addAllTiles();
            
            if (this.isPlayer == 1){
                if(simBoard.allEnemyTile.contains(simBoard.playerKing.tile)){
                    counter += 1;
                }
            }else if (this.isPlayer == -1) {
                if(simBoard.allPlayerTile.contains(simBoard.enemyKing.tile)){
                    counter += 1;
                }
            } 
            simBoard.boardMatrix[originTile.getRow()][originTile.getColumn()].setPiece(simPiece);
            simBoard.boardMatrix[tile.getRow()][tile.getColumn()].setPiece(originPiece);   
            simPiece.tile = originTile;
        
        } 
        
        if(counter > 0){
            this.pin = true;
        }else{
            this.pin = false;
        }
        
        
    }
    
    
    
  

    // Handle logic
     /**
     * Moves the piece to the specified target tile on the board.
     * The x value and y value will be updated according to the speed of piece
     * 
     * @param targetTile The target tile where the piece should be moved.
     * @param board The board on which the piece is placed.
     */

    public void tick(Tile targetTile,Board board) {
        if(targetTile == null || targetTile == this.tile || this == null){
            return;
        }
        int distanceX = Math.abs(this.x - targetTile.getX());
        int distanceY = Math.abs(this.y - targetTile.getY());
        double maxMoveDistance = this.speed * this.maxMoveTime * 60;
        
        
        // Calculate the distance to see where the piece is moving to 
        if (this.x - targetTile.getX() == 0 && this.y - targetTile.getY() != 0){
            differenceX = 0;
            differenceY = (this.y - targetTile.getY()) / (Math.abs(this.y - targetTile.getY()));
        }else if (this.y - targetTile.getY() == 0 && this.x - targetTile.getX() != 0){
            differenceY = 0;
            differenceX = (this.x - targetTile.getX()) / (Math.abs(this.x - targetTile.getX()));
        
        }else if(this.x - targetTile.getX() == 0 && this.y - targetTile.getY() == 0){
            differenceX = 0;
            differenceY = 0;
        }
        else{
            differenceX = (this.x - targetTile.getX()) / (Math.abs(this.x - targetTile.getX()));
            differenceY = (this.y - targetTile.getY()) / (Math.abs(this.y - targetTile.getY()));
        }
        
        //Check if the piece goto the target tile 
        if (this.y >= targetTile.getY() && this.y <= targetTile.getY() + 48 && 
            this.x >= targetTile.getX() && this.x <= targetTile.getX() + 48) {
            // Change the piece in original tile and current tile 
            this.x = targetTile.getX();
            this.y = targetTile.getY();
            historyTiles.add(this.tile);
            historyTiles.add(targetTile);
            //Set up the moved to be true
            this.moved = true;
            // The original tile will set Piece to null
            board.boardMatrix[this.tile.getRow()][this.tile.getColumn()].setPiece(null);
            this.tile = board.boardMatrix[targetTile.getRow()][targetTile.getColumn()];
            // Eat the piece if the target tile has piece and in different color
            if(targetTile.getPiece() != null && targetTile.getPiece().isWhite == !this.isWhite){
                targetTile.getPiece().isAlive = false;
                board.enemyCollection.remove(targetTile.getPiece());
                board.playerCollection.remove(targetTile.getPiece());
                for(Tile tile : capturedTiles){
                    lsTile.remove(tile);
                }
                
            }
            board.boardMatrix[targetTile.getRow()][targetTile.getColumn()].setPiece(this);
            return;
        }
        // Kinght move, calculate the speed to make move smooth 
        if (distanceY != distanceX){
            if(distanceX > distanceY){
                double speedY = (distanceY / distanceX) * this.speed;
                this.x -= this.speed * differenceX;
                this.y -= speedY * differenceY;
                 
            }else{
                double speedX = (distanceX / distanceY) * this.speed;
                this.x -= speedX * differenceX;
                this.y -= this.speed * differenceY;
            }
        }
        // Speed remain same if the time of moving doesn't exceed the maxMoveTime
        if (distanceX <= maxMoveDistance && distanceY <= maxMoveDistance){
            if (this.y == targetTile.getY() && this.x != targetTile.getX()){
                this.x -= this.speed * differenceX;
    
            }else if(this.x == targetTile.getX() && this.y != targetTile.getY()){
                this.y -= this.speed * differenceY;
            }else{
                this.x -= this.speed * differenceX;
                this.y -= this.speed * differenceY;
            }
        }else{
            // piece will speed up to maintain the maxMoveTime
            double speedX = (distanceX / maxMoveDistance) * this.speed;
            double speedY = (distanceY / maxMoveDistance) * this.speed;
            if (this.y == targetTile.getY() && this.x != targetTile.getX()){
                this.x -= speedX * differenceX;
    
            }else if(this.x == targetTile.getX() && this.y != targetTile.getY()){
                this.y -= speedY * differenceY;
            }else{
                this.x -= speedX * differenceX;
                this.y -= speedY * differenceY;
            }
            
        }
    
       
        


    }

   
    /**
     * Checks and handles the graphical representation of the piece on the board.
     * Draw the histroyTiles of piece
     * Draw the lsTiles and captureTiles of Piece if it is pressed
     * 
     * @param app The PApplet object for rendering the graphics.
     */
    public void check(PApplet app) {
     
        for (Tile tile : historyTiles){
            app.fill(0xFFAAA23A);
            app.rect(tile.getX(), tile.getY(), 48, 48);
            
        }
        if (this.isPressed){
            
            app.fill(0xFF698A4C);
            app.rect(this.tile.getX(), this.tile.getY(), 48, 48);
            for (Tile tile : lsTile){
                app.fill(0xFFAAD2DD,200);
                app.rect(tile.getX(), tile.getY(), 48, 48);
            }
            for (Tile tile : capturedTiles){
                app.fill(0xFFFFA466);
                app.rect(tile.getX(), tile.getY(), 48, 48);
            }
           
            

        }else{
            this.lsTile.clear();
            this.capturedTiles.clear();
        }
        
    }


    // Handling graphics
     /**
     * Draws the graphical representation of the piece on the board.
     * 
     * @param app The PApplet object for rendering the graphics.
     */
    public void draw(PApplet app) {
        // Only draw the piece if it is still alive
        if (this.isAlive){
            app.image(this.sprite, this.x, this.y);
        }
    
    }

     /**
     * Gets the x-coordinate of the piece.
     * 
     * @return The x-coordinate of the piece.
     */
    public int getX() {
        return this.x;
    }

     /**
     * Gets the y-coordinate of the piece.
     * 
     * @return The y-coordinate of the piece.
     */
    public int getY() {
        return this.y;
    }

     /**
     * Gets the list of tiles the piece can move to.
     * 
     * @return The list of tiles the piece can move to.
     */
    public ArrayList<Tile> getLsTile(){
        return this.lsTile;
    }

    /**
     * Sets the speed of the piece.
     * 
     * @param speed The speed of the piece.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

     /**
     * Sets the maximum move time of the piece.
     * 
     * @param maxMoveTime The maximum move time of the piece.
     */
    public void setMaxMoveTime(int maxMoveTime) {
        this.maxMoveTime = maxMoveTime;
    }

     /**
     * Gets the player to which the piece belongs.
     * 
     * @return The player to which the piece belongs.
     */
    public int getIsPlayer() {
        return this.isPlayer;
    }

    /**
     * Gets the value of the piece.
     * 
     * @return The value of the piece.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Checks if the piece is white.
     * 
     * @return True if the piece is white, false otherwise.
     */
    public boolean getIsWhite() {
        return this.isWhite;
    }

    /**
     * Marks the piece as dead.
     * 
     * @param isAlive The status of the piece (alive or dead).
     */
    public void die(boolean isAlive) {
        this.isAlive = false;
    }

    /**
     * Checks if the piece is alive.
     * 
     * @return True if the piece is alive, false otherwise.
     */
    public boolean getIsAlive() {
        return this.isAlive;
    }

    /**
     * Presses the piece.
     * Sets the isPressed flag to true.
     */
    public void press() {
        this.isPressed = true;
    }

    /**
     * Unpresses the piece.
     * Sets the isPressed flag to false.
     */
    public void unpress() {
        this.isPressed = false;
    }
    
    /**
     * Checks if the piece is pressed.
     * 
     * @return True if the piece is pressed, false otherwise.
     */
    public boolean getPress(){
        return this.isPressed;
    }

 

    


}
