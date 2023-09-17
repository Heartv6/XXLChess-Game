package XXLChess;

import processing.core.*;
import java.util.*;
import java.io.*;

/**
 * The Board class represents the game board for XXLChess.
 * It contains a matrix of tiles, collections of player and enemy pieces,
 * and provides methods for initializing the board, setting up the pieces,
 * and cloning the board for AI algorithms.
 */
public class Board implements Cloneable{
   // The matrix of tiles representing the game board
  public Tile[][] boardMatrix;
  // The size of each tile in pixels
  private int tileSize = 48;
  // Collection of player pieces
  public ArrayList<Piece> playerCollection;
  // Collection of enemy pieces
  public ArrayList<Piece> enemyCollection;
  // All tiles that can go by player pieces
  public ArrayList<Tile> allPlayerTile;
  // All tiles that can go by enemy pieces
  public ArrayList<Tile> allEnemyTile;
  // Tiles with special properties occupied by player pieces
  public ArrayList<Tile> specialPlayerTile;
  // Tiles with special properties occupied by enemy pieces
  public ArrayList<Tile> specialEnemyTile;
  // The player's king piece
  public King playerKing;
  // The enemy's king piece
  public King enemyKing;

  // Flag indicating if the player is white
  public boolean playerWhite;

  /**
   * Constructs a new Board object.
   *  With 14 * 14 matrix that contain all the tile on the board
   */
  public Board() {
    boardMatrix = new Tile[14][14];
    playerCollection = new ArrayList<>();
    enemyCollection = new ArrayList<>();
    

  }


   /**
   * Clones the Board object.
   *
   * @return A cloned copy of the Board object.
   */
  public Board clone() {
    try {
      Board clonedBoard = (Board) super.clone();
      clonedBoard.boardMatrix = this.cloneBoardMatrix();
      clonedBoard.playerCollection = clonePieces(this.playerCollection);
      clonedBoard.enemyCollection = clonePieces(this.enemyCollection);
      // Clone other collections and objects as needed

      return clonedBoard;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
}


  /**
   * Clones the board matrix.
   *
   * @return A cloned copy of the board matrix.
   */
  private Tile[][] cloneBoardMatrix() {
    Tile[][] clonedMatrix = new Tile[boardMatrix.length][];
    for (int i = 0; i < boardMatrix.length; i++) {
        clonedMatrix[i] = boardMatrix[i].clone();
    }
    return clonedMatrix;
  }

  /**
   * Clones a list of pieces.
   *
   * @param pieces The list of pieces to clone.
   * @return A cloned copy of the list of pieces.
   */
  private ArrayList<Piece> clonePieces(ArrayList<Piece> pieces) {
    ArrayList<Piece> clonedPieces = new ArrayList<>();
    for (Piece piece : pieces) {
        clonedPieces.add(piece.clone());
    }
    return clonedPieces;
  }
  

  /**
   * Initializes the game board.
   *
   * @param app The PApplet object for rendering.
   */
  public void initializeBoard(PApplet app) {
    
      
      int y = 672 - tileSize;
      int x = 0;
      // Draw the board by the specification
      for (int i = 0; i < boardMatrix.length; i++) { 
          for (int j = 0; j < boardMatrix[i].length; j++) { 
            if ((i + j) % 2 == 0) {
              app.fill(0xFFB58863);
              app.rect(x, y, tileSize, tileSize);
              x += tileSize;
            }else {
              app.fill(0xFFF0D9B5);
              app.rect(x, y, tileSize, tileSize);
              x += tileSize;
            }
          }
          x = 0;
          y -= tileSize;
        }
     
      app.fill(192,192,192);
      app.rect(672, 0, 120, 672);
      

      

  }

  /**
   * Generates all the tiles on the board 
   */
  public void gereratePiece(){
    for (int i = 0; i < boardMatrix.length; i++) { 
      for (int j = 0; j < boardMatrix[i].length; j++) { 
        boardMatrix[i][j] = new Tile(i, j);
        boardMatrix[i][j] = new Tile(i, j);
      }
    

    }
    
  }

  /**
   * Sets up the pieces on the board based on the provided layout.
   *
   * @param layout         The layout file specifying the positions of the pieces.
   * @param playerIsWhite  The color of the player's pieces ("white" or "black").
   */
  public void setPiece(String layout, String playerIsWhite) {
    if (playerIsWhite.toLowerCase().equals("white")) {
      playerWhite = true;
    }else if (playerIsWhite.toLowerCase().equals("black")) {
      playerWhite = false;
    }

    try {
      int row = 13;
      File f = new File(layout);
      Scanner sc = new Scanner(f);
      while (sc.hasNextLine()) {
        String data = sc.nextLine();
        String[] strOut = data.split("");
        for(int column = 0; column < strOut.length; column++ ) {
          if (strOut[column].equals("R")) {
            if (playerWhite == true){
              // -1 represent it is not a player
              Rook rook = new Rook(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(rook);
              enemyCollection.add(rook);
            }else{
              // 1 represent it is a player
              Rook rook = new Rook(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(rook);
              playerCollection.add(rook);
            }
          }else if (strOut[column].equals("r")){
            if (playerWhite == true){
              Rook rook = new Rook(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(rook);
              playerCollection.add(rook);
            }else{
              Rook rook = new Rook(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(rook);
              enemyCollection.add(rook);
            }
            
          }else if (strOut[column].equals("N")){
            if (playerWhite == true){
              Knight knight = new Knight(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(knight);
              enemyCollection.add(knight);
            }else{
              Knight knight = new Knight(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(knight);
              playerCollection.add(knight);
            }
            
          }else if (strOut[column].equals("n")){
            if (playerWhite == true){
              Knight knight = new Knight(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(knight);
              playerCollection.add(knight);
            }else{
              Knight knight = new Knight(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(knight);
              enemyCollection.add(knight);
            }
            
          }else if (strOut[column].equals("B")){
            if (playerWhite == true){
              Bishop bishop = new Bishop(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(bishop);
              enemyCollection.add(bishop);
            }else{
              Bishop bishop = new Bishop(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(bishop);
              playerCollection.add(bishop);
            }
            
          }else if (strOut[column].equals("b")){
            if (playerWhite == true){
              Bishop bishop = new Bishop(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(bishop);
              playerCollection.add(bishop);
            }else{
              Bishop bishop = new Bishop(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(bishop);
              enemyCollection.add(bishop);
            }
            
          }else if (strOut[column].equals("H")){
            if (playerWhite == true){
              Archbishop archbishop = new Archbishop(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(archbishop);
              enemyCollection.add(archbishop);
            }else{
              Archbishop archbishop = new Archbishop(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(archbishop);
              playerCollection.add(archbishop);
            }
            
          }else if (strOut[column].equals("h")){
            if (playerWhite == true){
              Archbishop archbishop = new Archbishop(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(archbishop);
              playerCollection.add(archbishop);
            }else{
              Archbishop archbishop = new Archbishop(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(archbishop);
              enemyCollection.add(archbishop);
            }
            
          }else if (strOut[column].equals("C")){
            if (playerWhite == true){
              Camel camel = new Camel(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(camel);
              enemyCollection.add(camel);
            }else{
              Camel camel = new Camel(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(camel);
              playerCollection.add(camel);
            }
            
          }else if (strOut[column].equals("c")){
            if (playerWhite == true){
              Camel camel = new Camel(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(camel);
              playerCollection.add(camel);
            }else{
              Camel camel = new Camel(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(camel);
              enemyCollection.add(camel);
            }
            
          }else if (strOut[column].equals("G")){
            if (playerWhite == true){
              General general = new General(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(general);
              enemyCollection.add(general);
            }else{
              General general = new General(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(general);
              playerCollection.add(general);
            }
            
          }else if (strOut[column].equals("g")){
            if (playerWhite == true){
              General general = new General(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(general);
              playerCollection.add(general);
            }else{
              General general = new General(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(general);
              enemyCollection.add(general);
            }
            
          }else if (strOut[column].equals("A")){
            if (playerWhite == true){
              Amazon amazon = new Amazon(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(amazon);
              enemyCollection.add(amazon);
            }else{
              Amazon amazon = new Amazon(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(amazon);
              playerCollection.add(amazon);
            }
            
          }else if (strOut[column].equals("a")){
            if (playerWhite == true){
              Amazon amazon = new Amazon(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(amazon);
              playerCollection.add(amazon);
            }else{
              Amazon amazon = new Amazon(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(amazon);
              enemyCollection.add(amazon);
            }
            
          }else if (strOut[column].equals("K")){
            if (playerWhite == true){
              enemyKing = new King(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(enemyKing);
              enemyCollection.add(enemyKing);
              
            }else{
              playerKing = new King(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(playerKing);
              playerCollection.add(playerKing);
              
            }
            
          }else if (strOut[column].equals("k")){
            if (playerWhite == true){
              playerKing = new King(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(playerKing);
              playerCollection.add(playerKing);
            }else{
              enemyKing = new King(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(enemyKing);
              enemyCollection.add(enemyKing);
              
            }
            
          }else if (strOut[column].equals("Q")){
            if (playerWhite == true){
              Queen queen = new Queen(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(queen);
              enemyCollection.add(queen);
            }else{
              Queen queen = new Queen(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(queen);
              playerCollection.add(queen);
            }
            
          }else if (strOut[column].equals("q")){
            if (playerWhite == true){
              Queen queen = new Queen(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(queen);
              playerCollection.add(queen);
            }else{
              Queen queen = new Queen(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(queen);
              enemyCollection.add(queen);
            }
            
          }else if (strOut[column].equals("E")){
            if (playerWhite == true){
              Chancellor chancellor = new Chancellor(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(chancellor);
              enemyCollection.add(chancellor);
            }else{
              Chancellor chancellor = new Chancellor(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(chancellor);
              playerCollection.add(chancellor);
            }
            
          }else if (strOut[column].equals("e")){
            if (playerWhite == true){
              Chancellor chancellor = new Chancellor(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(chancellor);
              playerCollection.add(chancellor);
            }else{
              Chancellor chancellor = new Chancellor(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(chancellor);
              enemyCollection.add(chancellor);
            }
            
          }else if (strOut[column].equals("P")){
            if (playerWhite == true){
              Pawn pawn = new Pawn(0, 0, -1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(pawn);
              enemyCollection.add(pawn);
            }else{
              Pawn pawn = new Pawn(0, 0, 1, false, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(pawn);
              playerCollection.add(pawn);
            }
            
          }else if (strOut[column].equals("p")){
            if (playerWhite == true){
              Pawn pawn = new Pawn(0, 0, 1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(pawn);
              playerCollection.add(pawn);
            }else{
              Pawn pawn = new Pawn(0, 0, -1, true, boardMatrix[row][column]);
              boardMatrix[row][column].setPiece(pawn);
              enemyCollection.add(pawn);
            }
            
          }

        }
        row -= 1;
      }

    } catch (FileNotFoundException e) {
      System.out.println("Not an empty file.");
      e.printStackTrace();
    }
  

  }
  
/**
 * Determines the legal moves for all player piece to block or capture the checking piece.
 * 
 * @param playerKing The player's king piece.
 * @param currentCheckEnemy The piece that is checking the player's king.
 * @return An ArrayList of pieces that can legally move to block or capture the checking piece.
 */
  public ArrayList<Piece> playerLegalMove1(Piece playerKing, Piece currentCheckEnemy) {
    // Get all the avaliable piece that can move to block or eat the checking piece
    ArrayList<Piece> blockPiece = new ArrayList<>();
    currentCheckEnemy.lsTile.clear();
    currentCheckEnemy.capturedTiles.clear();
    currentCheckEnemy.targetTile(this);
    Piece testQueen;
    int dx = Math.abs(playerKing.tile.getRow() - currentCheckEnemy.tile.getRow());
    int dy = Math.abs(playerKing.tile.getColumn() - currentCheckEnemy.tile.getColumn());
    boolean knightMove = false;
    // Generate rook, bishop to modify the board and get the tile common to two chess pieces
    if(playerKing.tile.getRow() == currentCheckEnemy.tile.getRow() ||
       playerKing.tile.getColumn() == currentCheckEnemy.tile.getColumn()) {
      testQueen = new Rook(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      if(playerKing.tile.getRow() == currentCheckEnemy.tile.getRow()) {
        testQueen.leftRight(this);
      }else{
        testQueen.topBottom(this);
      }
    }else if (dx == dy){
      testQueen = new Bishop(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      if((playerKing.tile.getRow() < currentCheckEnemy.tile.getRow() &&
          playerKing.tile.getColumn() < currentCheckEnemy.tile.getColumn())||
          (playerKing.tile.getRow() > currentCheckEnemy.tile.getRow() &&
          playerKing.tile.getColumn() > currentCheckEnemy.tile.getColumn())){
            testQueen.leftBottomTopRight(this);
          }else{
            testQueen.leftTopbottomRight(this);
          }
    }else{
      testQueen = new Pawn(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      knightMove = true;
    }
    if(knightMove) {
      testQueen.lsTile.add(currentCheckEnemy.tile);
    }else{
      boardMatrix[playerKing.tile.getRow()][playerKing.tile.getColumn()].setPiece(testQueen);
      testQueen.lsTile.retainAll(currentCheckEnemy.lsTile);
      testQueen.lsTile.add(currentCheckEnemy.tile);

    }
    // Check whether that piece can go to that common tile to block or eat
    for(Piece piece : this.playerCollection) {
    
      piece.targetTile(this);
     
      for(Tile tile : testQueen.lsTile) {
        
        if(piece.lsTile.contains(tile)){
          blockPiece.add(piece);
        }
        
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
      
    }
   
    boardMatrix[playerKing.tile.getRow()][playerKing.tile.getColumn()].setPiece(playerKing);
    
    


    return blockPiece;
}
  /**
   * Determines the legal moves for all player piece to block or capture the checking piece.
   * 
   * @param playerKing The player's king piece.
   * @param currentCheckEnemy The piece that is checking the player's king.
   * @return An ArrayList of tiles that a piece  can legally move to block or capture the checking piece.
   */
  public ArrayList<Tile> playerLegalMove2(Piece playerKing, Piece currentCheckEnemy) {
    ArrayList<Tile> blockTile = new ArrayList<>();
    currentCheckEnemy.lsTile.clear();
    currentCheckEnemy.capturedTiles.clear();
    currentCheckEnemy.targetTile(this);
    Piece testQueen;
    int dx = Math.abs(playerKing.tile.getRow() - currentCheckEnemy.tile.getRow());
    int dy = Math.abs(playerKing.tile.getColumn() - currentCheckEnemy.tile.getColumn());
    boolean knightMove = false;
    if(playerKing.tile.getRow() == currentCheckEnemy.tile.getRow() ||
       playerKing.tile.getColumn() == currentCheckEnemy.tile.getColumn()) {
      testQueen = new Rook(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      if(playerKing.tile.getRow() == currentCheckEnemy.tile.getRow()) {
        testQueen.leftRight(this);
      }else{
        testQueen.topBottom(this);
      }
    }else if (dx == dy){
      testQueen = new Bishop(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      if((playerKing.tile.getRow() < currentCheckEnemy.tile.getRow() &&
          playerKing.tile.getColumn() < currentCheckEnemy.tile.getColumn())||
          (playerKing.tile.getRow() > currentCheckEnemy.tile.getRow() &&
          playerKing.tile.getColumn() > currentCheckEnemy.tile.getColumn())){
            testQueen.leftBottomTopRight(this);
          }else{
            testQueen.leftTopbottomRight(this);
          }
    }else{
      testQueen = new Pawn(0,0,playerKing.isPlayer,playerKing.isWhite,playerKing.tile);
      knightMove = true;
    }
    if(knightMove) {
      testQueen.lsTile.add(currentCheckEnemy.tile);
    }else{
      boardMatrix[playerKing.tile.getRow()][playerKing.tile.getColumn()].setPiece(testQueen);
      //testQueen.targetTile(this);
      testQueen.lsTile.retainAll(currentCheckEnemy.lsTile);
      testQueen.lsTile.add(currentCheckEnemy.tile);

    }
   
    
    
    
    for(Piece piece : this.playerCollection) {
    
      piece.targetTile(this);
      for(Tile tile : testQueen.lsTile) {
        
        if(piece.lsTile.contains(tile)){

          blockTile.add(tile);
          
        }
        
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
      
    }
   
    boardMatrix[playerKing.tile.getRow()][playerKing.tile.getColumn()].setPiece(playerKing);

    return blockTile;
}

  /**
   * Determines the legal moves for all enemy piece to block or capture the checking piece.
   * 
   * @param enemyKing The player's king piece.
   * @param currentCheckPlayer The piece that is checking the enemy 's king.
   * @return An ArrayList of pieces that can legally move to block or capture the checking piece.
   */
  public ArrayList<Piece> enemyLegalMove1(Piece enemyKing, Piece currentCheckPlayer) {
    ArrayList<Piece> blockPiece = new ArrayList<>();
    currentCheckPlayer.lsTile.clear();
    currentCheckPlayer.capturedTiles.clear();
    currentCheckPlayer.targetTile(this);

    Piece testQueen;
    int dx = Math.abs(enemyKing.tile.getRow() - currentCheckPlayer.tile.getRow());
    int dy = Math.abs(enemyKing.tile.getColumn() - currentCheckPlayer.tile.getColumn());
    boolean knightMove = false;
    if(enemyKing.tile.getRow() == currentCheckPlayer.tile.getRow() ||
      enemyKing.tile.getColumn() == currentCheckPlayer.tile.getColumn()) {
      testQueen = new Rook(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
      if(enemyKing.tile.getRow() == currentCheckPlayer.tile.getRow()) {
        testQueen.leftRight(this);
      }else{
        testQueen.topBottom(this);
      }
    }else if (dx == dy){
      testQueen = new Bishop(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
        if((enemyKing.tile.getRow() < currentCheckPlayer.tile.getRow() &&
        enemyKing.tile.getColumn() < currentCheckPlayer.tile.getColumn())||
        (enemyKing.tile.getRow() > currentCheckPlayer.tile.getRow() &&
        enemyKing.tile.getColumn() > currentCheckPlayer.tile.getColumn())){
          testQueen.leftBottomTopRight(this);
        }else{
          testQueen.leftTopbottomRight(this);
        }
    }else{
      testQueen = new Pawn(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
      knightMove = true;
    }
    if(knightMove) {
      testQueen.lsTile.add(currentCheckPlayer.tile);
    }else{
      boardMatrix[enemyKing.tile.getRow()][enemyKing.tile.getColumn()].setPiece(testQueen);
      
      testQueen.lsTile.retainAll(currentCheckPlayer.lsTile);
      testQueen.lsTile.add(currentCheckPlayer.tile);

    }
  
    for(Piece piece : this.enemyCollection) {
      piece.targetTile(this);

      for(Tile tile : testQueen.lsTile) {
        
        if(piece.lsTile.contains(tile)){

          blockPiece.add(piece);
          
        }
        
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
      
    }
    boardMatrix[enemyKing.tile.getRow()][enemyKing.tile.getColumn()].setPiece(enemyKing);
    

    return blockPiece;
  }
  /**
   * Determines the legal moves for all enemy piece to block or capture the checking piece.
   * 
   * @param enemyKing The enemy's king piece.
   * @param currentCheckPlayer The piece that is checking the enemy's king.
   * @return An ArrayList of tiles that a piece can legally move to block or capture the checking piece.
   */
  public ArrayList<Tile> enemyLegalMove2(Piece enemyKing, Piece currentCheckPlayer) {
    ArrayList<Tile> blockTile = new ArrayList<>();
    currentCheckPlayer.lsTile.clear();
    currentCheckPlayer.capturedTiles.clear();
    currentCheckPlayer.targetTile(this);
    Piece testQueen;
    int dx = Math.abs(enemyKing.tile.getRow() - currentCheckPlayer.tile.getRow());
    int dy = Math.abs(enemyKing.tile.getColumn() - currentCheckPlayer.tile.getColumn());
    boolean knightMove = false;
    if(enemyKing.tile.getRow() == currentCheckPlayer.tile.getRow() ||
      enemyKing.tile.getColumn() == currentCheckPlayer.tile.getColumn()) {
      testQueen = new Rook(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
      if(enemyKing.tile.getRow() == currentCheckPlayer.tile.getRow()) {
        testQueen.leftRight(this);
      }else{
        testQueen.topBottom(this);
      }
    }else if (dx == dy){
      testQueen = new Bishop(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
      if((enemyKing.tile.getRow() < currentCheckPlayer.tile.getRow() &&
        enemyKing.tile.getColumn() < currentCheckPlayer.tile.getColumn())||
        (enemyKing.tile.getRow() > currentCheckPlayer.tile.getRow() &&
        enemyKing.tile.getColumn() > currentCheckPlayer.tile.getColumn())){
          testQueen.leftBottomTopRight(this);
        }else{
          testQueen.leftTopbottomRight(this);
        }
    }else{
      testQueen = new Pawn(0,0,enemyKing.isPlayer,enemyKing.isWhite,enemyKing.tile);
      knightMove = true;
    }
    if(knightMove) {
      testQueen.lsTile.add(currentCheckPlayer.tile);
    }else{
      boardMatrix[enemyKing.tile.getRow()][enemyKing.tile.getColumn()].setPiece(testQueen);
      testQueen.lsTile.retainAll(currentCheckPlayer.lsTile);
      testQueen.lsTile.add(currentCheckPlayer.tile);

    }
    for(Piece piece : this.enemyCollection) {
      piece.targetTile(this);
      for(Tile tile : testQueen.lsTile) {
        
        if(piece.lsTile.contains(tile)){

          blockTile.add(tile);
          
        }
        
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
      
    }
    boardMatrix[enemyKing.tile.getRow()][enemyKing.tile.getColumn()].setPiece(enemyKing);
   

    return blockTile;
}
  
  
  /**
   * Generates the AI move by selecting a piece from the enemy collection and determining its target tiles.
   * If the piece has captured tiles, it is returned as the move. Otherwise, a random piece is selected, and if
   * it has captured tiles or legal moves, it is returned as the move. The process continues until a suitable move is found.
   *
   * @return The selected piece for the AI move.
   */
  public Piece aiMove() {
    
    for(Piece piece : enemyCollection) {
      piece.targetTile(this);
      if(piece.capturedTiles.size() != 0) {
        return piece;
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
    }
    
   
    Random rand = new Random();
    while (true){
      Piece randomPiece = enemyCollection.get(rand.nextInt(enemyCollection.size()));
      randomPiece.checkPin(this);
      if(randomPiece.pin == true) {
        continue;
      }
      randomPiece.targetTile(this);
      if(randomPiece.capturedTiles.size() != 0) {
        return randomPiece;
      }else if(randomPiece.lsTile.size() != 0) {
        return randomPiece;
      }
      randomPiece.lsTile.clear();
      randomPiece.capturedTiles.clear();
    }
  }

  public Piece aiMove2() {
    
    for(Piece piece : playerCollection) {
      piece.targetTile(this);
      if(piece.capturedTiles.size() != 0) {
        return piece;
      }
      piece.lsTile.clear();
      piece.capturedTiles.clear();
    }
    
   
    Random rand = new Random();
    while (true){
      Piece randomPiece = playerCollection.get(rand.nextInt(playerCollection.size()));
      randomPiece.checkPin(this);
      if(randomPiece.pin == true) {
        continue;
      }
      randomPiece.targetTile(this);
      if(randomPiece.capturedTiles.size() != 0) {
        return randomPiece;
      }else if(randomPiece.lsTile.size() != 0) {
        return randomPiece;
      }
      randomPiece.lsTile.clear();
      randomPiece.capturedTiles.clear();
    }
  }
  /**
   * Promotes a pawn to a queen for both the player and enemy collections.
   * If a pawn reaches the specific row of the board, it is replaced by a queen piece.
   *
   * @param app          The PApplet instance for drawing.
   * @param speed        The speed of the promoted queen.
   * @param maxMoveTime  The maximum time allowed for the queen's move.
   * @return The promoted queen piece.
   */
  public Piece pawnPromte(PApplet app, double speed,int maxMoveTime) {
    for(int i = 0; i < playerCollection.size(); i++){
      if(playerCollection.get(i) instanceof Pawn && playerCollection.get(i).tile.getRow() == 7) {
        Piece player = playerCollection.get(i);
        Queen queen = new Queen(0, 0, player.isPlayer, player.isWhite, player.tile);
        queen.setImage(app);
        queen.setSpeed(speed);
        queen.setMaxMoveTime(maxMoveTime);
        boardMatrix[player.tile.getRow()][player.tile.getColumn()].setPiece(queen);
        playerCollection.add(queen);
        playerCollection.remove(player);
        return queen;
              
        
      }
    }

    for(int i = 0; i < enemyCollection.size(); i++){
      if(enemyCollection.get(i) instanceof Pawn && enemyCollection.get(i).tile.getRow() == 6) {
        Piece enemy = enemyCollection.get(i);
        Queen queen = new Queen(0, 0, enemy.isPlayer, enemy.isWhite, enemy.tile);
        queen.setImage(app);
        queen.setSpeed(speed);
        queen.setMaxMoveTime(maxMoveTime);
        enemyCollection.remove(enemyCollection.get(i));
        enemyCollection.add(queen);
        boardMatrix[enemy.tile.getRow()][enemy.tile.getColumn()].setPiece(queen);
        return queen;
      }
    }
    return null;


  }
  /**
   * Populates all tile collections for both player and enemy pieces.
   * Updates the collections with the target tiles of the pieces.
   */

  public void addAllTiles() {
      specialEnemyTile = new ArrayList<>();
      specialPlayerTile = new ArrayList<>();
      allPlayerTile = new ArrayList<>();
      allEnemyTile = new ArrayList<>();
      for(Piece piece: playerCollection) {
        Piece piece1 = (Piece)(piece.clone());
        piece1.targetTile(this);
        if(piece1 instanceof Pawn) {
          allPlayerTile.addAll(piece1.specialTiles);
          piece1.specialTiles.clear();
        }else{
          allPlayerTile.addAll(piece1.lsTile);
          specialPlayerTile.addAll(piece1.specialTiles);
        }
        
        piece1.lsTile.clear();
        piece1.capturedTiles.clear();
     
      }
      for(Piece piece: enemyCollection) {
        Piece piece1 = (Piece)(piece.clone());
        piece1.targetTile(this);
        if(piece1 instanceof Pawn) {
          allEnemyTile.addAll(piece1.specialTiles);
          piece1.specialTiles.clear();
        }else{
          allEnemyTile.addAll(piece1.lsTile);
          specialEnemyTile.addAll(piece1.specialTiles);
        }
        
        piece1.lsTile.clear();
        piece1.capturedTiles.clear();
        
      }
  }
  /**
   * Checks if a piece is currently in a checking position.
   *
   * @param piece The piece to check for checking condition.
   * @return True if the piece is in a checking position, false otherwise.
   */
  public boolean Checking(Piece piece){
      addAllTiles();
      if(piece != null && piece.isPlayer == -1){
        if (allEnemyTile.contains(playerKing.tile)){
          return true;
        }else{
          return false;
        }
      }else if (piece != null && piece.isPlayer == 1 ){
        if (allPlayerTile.contains(enemyKing.tile)){
          return true;
        }else{
          return false;
        }
      }
      return false;
  }


  
}
