package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

import org.checkerframework.checker.units.qual.C;

/**
     * The main app to start the game.
     *  Including all the keyboard and mouse detection.
     *  The main game loop is contain in draw method
     */

public class App extends PApplet {

    /**
     * initialise the width , height , spritesize , FPS , bottombar and randomGenerator of window 
     */
    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;
    
    public static final int FPS = 60;


	public Random rand;
    // Check if the player is click or not 
    public boolean click = false;
    public String configPath;
    public String playerIsWhite;
    public boolean test = false;

    // The layout of board and message font
    public String layout;
    public PFont message;

    // Player's speed and maxMoveTime 
    public double playerSpeed;
    public int maxMoveTime;
    
    // Player targetTile and enemyTargetTile
    public Tile targetTile;
    public Tile enemyTargetTile;
    
    //The board object
    public Board board;

    // Gameover condition
    public boolean gameOver;

    // Check and checkmate condition
    public boolean playerCheck = false;
    public boolean enemyCheck = false;
    public boolean playerCheckMate = false;
    public boolean enemyCheckMate =false;
    public boolean staleMate = false;

    // Must defind King is used to detect wheather player select the wrong piece during check
    public boolean mustDefineKing = true;
    public int flashCount= 0;
    public int flashDuration = 30;

    // Current moving piece for player and enemy
    public Piece playerPiece;
    public boolean enemyCheckFlag;
    public Piece enemyPiece;
    
    // Round control
    public Piece currentMovingPiece;
    public Piece currentMovingEnemy = new Pawn(0,0,1,true,new Tile(0,0));
    // Round control
    public boolean playerRound = true;
    public boolean enemySelect = true;

    //Time control
    public int playerTime;
    public int enemyTime;
    public int playerCounter = 0;
    public int enemyCounter = 0;
    public int playerIncrement;
    public int enemyIncrement;

    //Castleing
    public boolean rightCastle = false;;
    public boolean leftCastle = false;
    public Tile rookCastleTile;

    //Sound effect
    Clip bgmClip;
    Clip goClip;
    Clip loseClip;
    Clip eatClip;
    Clip selectCilp;
    Clip winClip;
    Clip checkClip;


    public App() {
        // Construt the objects here
        this.configPath = "config.json";
        this.board = new Board();
        this.rand = new Random();
        this.targetTile = null;
        

    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        // Set up the gameOver flag
        gameOver = false;
        //Set up the message
        message = createFont("Arial",16,true);

		// load config
        JSONObject conf = loadJSONObject(new File(this.configPath));
        layout = conf.getString("layout");
        playerIsWhite = conf.getString("player_colour");
        playerSpeed = conf.getDouble("piece_movement_speed");
        maxMoveTime = conf.getInt("max_movement_time");
        if (playerIsWhite.equals("white")) {
            playerRound = true;
        }else{
            playerRound = false;
        }
        JSONObject timeControl = conf.getJSONObject("time_controls");
        JSONObject playerObject = timeControl.getJSONObject("player");
        JSONObject cpu = timeControl.getJSONObject("cpu");
        playerTime = playerObject.getInt("seconds");
        enemyTime = cpu.getInt("seconds");
        playerIncrement = playerObject.getInt("increment");
        enemyIncrement = cpu.getInt("increment");

        // Generate all the pieces ny reading the layout
       
        board.gereratePiece();
        try{
        board.setPiece(layout,playerIsWhite);
        }catch(Exception e){
            System.out.println("Layout is not correct!");
            stop();
        }
        
        //Load image for enemy and player
        for(Piece player : board.playerCollection) {
            player.setImage(this);
            player.setSpeed(playerSpeed);
            player.setMaxMoveTime(maxMoveTime);
        }
        for(Piece enemy : board.enemyCollection) {
            enemy.setImage(this);
            enemy.setSpeed(playerSpeed);
            enemy.setMaxMoveTime(maxMoveTime);
        }

        //Load backgroundmusic and sound effect 
        String bgm = "src/main/resources/bgm.wav";
        String eat = "src/main/resources/eat.wav";
        String go = "src/main/resources/go.wav";
        String lose = "src/main/resources/lose.wav";
        String select = "src/main/resources/select.wav";
        String winwin = "src/main/resources/winwin.wav";
        String checkclip = "src/main/resources/warn.wav";

        try {
            File soundFile1 = new File(bgm);
            AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(soundFile1);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream1);
            

            File soundFile2 = new File(go);
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(soundFile2);
            goClip = AudioSystem.getClip();
            goClip.open(audioInputStream2);

            File soundFile3 = new File(select);
            AudioInputStream audioInputStream3 = AudioSystem.getAudioInputStream(soundFile3);
            selectCilp = AudioSystem.getClip();
            selectCilp.open(audioInputStream3);

            File soundFile4 = new File(eat);
            AudioInputStream audioInputStream4 = AudioSystem.getAudioInputStream(soundFile4);
            eatClip = AudioSystem.getClip();
            eatClip.open(audioInputStream4);

            File soundFile5 = new File(winwin);
            AudioInputStream audioInputStream5 = AudioSystem.getAudioInputStream(soundFile5);
            winClip = AudioSystem.getClip();
            winClip.open(audioInputStream5);

            File soundFile6 = new File(lose);
            AudioInputStream audioInputStream6 = AudioSystem.getAudioInputStream(soundFile6);
            loseClip = AudioSystem.getClip();
            loseClip.open(audioInputStream6);

            File soundFile7 = new File(checkclip);
            AudioInputStream audioInputStream7 = AudioSystem.getAudioInputStream(soundFile7);
            checkClip = AudioSystem.getClip();
            checkClip.open(audioInputStream7);
            

        }catch(Exception e) {
            e.printStackTrace();
        }
        bgmClip.start();        
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if (key == 'e') {
            gameOver = true;
            bgmClip.stop();
        }
        if (gameOver == true) {
            if (key == 'r'){
                playerCheckMate = false;
                enemyCheckMate = false;
                PApplet.main("XXLChess.App");
            }
        }
        if (key == 'm') {
            bgmClip.setFramePosition(0);
            bgmClip.start();
        }

    }
    
   
    /**
     * Receive mouse press signal from the mouse.
     * Mainly used for controling the piece
    */
    @Override
    public void mousePressed(MouseEvent e) {
      
        for(Piece player : board.playerCollection) {
        //Check if the mouse has been click yet 
            if (!this.click && playerRound){
                if(e.getY() >= player.getY() && e.getY() <= player.getY()+ 48 &&
                e.getX() >= player.getX() && e.getX() <= player.getX()+ 48){
                    // Sound effect when player choose a piece
                    selectCilp.setFramePosition(0);
                    selectCilp.start();
                    player.press();
                    playerPiece = player;
                    board.addAllTiles();

                    // Check all avaliable move option by using the legalMove function
                    if (enemyPiece != null){
                        ArrayList<Piece> legalPiece = board.playerLegalMove1(board.playerKing, enemyPiece);
                        if(legalPiece.size() == 0 || legalPiece.size() == 0) {
                            enemyCheckFlag = true;
                        }
                    }

                    // If king is currently checked by enemy, only give the player legal move 
                    if(enemyCheck == true && enemyPiece != null && ! (player instanceof King))  {
                        ArrayList<Piece> legalPiece = board.playerLegalMove1(board.playerKing, enemyPiece);
                        ArrayList<Tile> legalTile = board.playerLegalMove2(board.playerKing, enemyPiece); 
                       
                        
                        for(int i = 0; i < legalPiece.size(); i ++) {
                            if(legalPiece.get(i) == player){
                                if(legalTile.get(i).getPiece() == null){
                                    player.lsTile.add(legalTile.get(i));
                                }else{
                                    player.lsTile.add(legalTile.get(i));
                                    player.capturedTiles.add(legalTile.get(i));

                                }
                               
                            }

                        }
                        
                    // If not in checking, just add tiles normally but need to check whether it is pin or not 
                    }else{ 
                        player.targetTile(this.board);
                        if (!(player instanceof King)){
                            player.Pin(this.board);
                        }
    
                    
                                        
                    }
                    //Checkmate and stalemate condition
                    if (player instanceof King && player.lsTile.isEmpty() && enemyCheckFlag && enemyCheck){ 
                        enemyCheckMate = true;
                    }else if(player instanceof King && player.lsTile.isEmpty() && enemyCheckFlag){
                        staleMate = true;
                    }else{
                        enemyCheckFlag = false;
                    }
                   
                        
                    // Play the sound effect and check if the current piece is having legal move during check.
                    if(enemyCheck == true)
                    if(player.lsTile.size() == 0 && enemyCheck == true){
                        checkClip.setFramePosition(0);
                        checkClip.start();
                        mustDefineKing = true;
                    }else{
                        mustDefineKing = false;
                    }
                    this.click = true;  
                    
                }
            }
            // If the mouse pressed piece, give the choice and set isPressed equal to false
            else{
                if (player.isPressed) {
                    if (player.lsTile.size() == 0){
                        player.isPressed = false;
                    }
                    for (Tile tile : player.lsTile) {
                        if((e.getY() >= tile.getY() && e.getY() <= tile.getY() + 48 &&
                        e.getX() >= tile.getX() && e.getX() <= tile.getX()+ 48)) {
                            currentMovingPiece = player;
                            this.targetTile = tile;
                            player.isPressed = false;
                            player.need_clear = true;
                            break;
                            
                        }else{
                            //not showing the color
                            player.isPressed = false;
                            
                                    
                        }
                    }
                    this.click = false;
                
                }    
            }
            // Clear all the tiles after one move
            if (player.need_clear){
                player.lsTile.clear();
                player.capturedTiles.clear();
                player.historyTiles.clear();
                player.need_clear = false;
            }
        }
        
        
    
       
}

   

    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {
        //Main game loop 
        // Play the background music 
        if (playerCheck) {
            checkClip.setFramePosition(0);
            checkClip.start();
        }
        //Win or lose condition and show the message correctly
        if (staleMate) {
            gameOver = true;
            textFont(message,16);                  
            fill(255,0,0);                     
            text("Stalemate - draw",672,350);
            return;
        }
        if (playerTime == 0) {
            loseClip.start();
            gameOver = true;
            textFont(message,16);                  
            fill(255,0,0);                     
            text("You lost on time",672,300);
            text("Press r to restart",672,350);
            text("the game",672,370);
            return;
        }else if (enemyTime == 0) {
            winClip.start();
            gameOver = true;
            textFont(message,16);                  
            fill(255,0,0);                     
            text("You won on time",672,300);
            text("Press r to restart",672,350);
            text("the game",672,370);
            return;
        }else if (playerCheckMate || ! board.enemyKing.isAlive) {
            winClip.start();
            gameOver = true;
            textFont(message,16);                  
            fill(255,0,0);                     
            text("You won by ",672,350);
            text("checkmate",672,370);
            text("Press r to restart",672,390);
            text("the game",672,410);
            ArrayList<Piece> contributionList = new ArrayList<>();
            contributionList = board.enemyKing.contributingCheck(board);
            for(Piece piece : contributionList) {
                this.fill(0xFFFFA466);
                this.rect(piece.tile.getX(), piece.tile.getY(), 48, 48);
                piece.draw(this);
            }
            return;

        }else if (enemyCheckMate || ! board.playerKing.isAlive) {
            loseClip.start();
            gameOver = true;
            textFont(message,16);                  
            fill(255,0,0);                     
            text("You lost by ",672,350);
            text("checkmate",672,370);
            text("Press r to restart",672,390);
            text("the game",672,410);
            ArrayList<Piece> contributionList = new ArrayList<>();
            contributionList = board.playerKing.contributingCheck(board);
            for(Piece piece : contributionList) {
                this.fill(0xFFFFA466);
                this.rect(piece.tile.getX(), piece.tile.getY(), 48, 48);
                piece.draw(this);
            }
          
            return;
        }
        if (gameOver) {
            textFont(message,16);                  
            fill(255,0,0);                     
            text("You resigned",672,360);
            text("Press r to restart",672,380);
            text("the game",672,400);
            return;
        }

        
  
        
   

        //Initialize the board
        board.initializeBoard(this);
        
        //Setup the timer
        int playerSeconds = playerTime % 60;
        int playerMinute = playerTime / 60;
        String displayPlayerTime;
        String displayEnemyTime;
        if (playerSeconds < 10) {
            displayPlayerTime = String.format("%d:0%s",playerMinute, playerSeconds);
        }else{
            displayPlayerTime = String.format("%d:%s",playerMinute, playerSeconds);
        }
        int enemySeconds = enemyTime % 60;
        int enemyMinute = enemyTime / 60;
         if (enemySeconds < 10) {
            displayEnemyTime = String.format("%d:0%s",enemyMinute, enemySeconds);
        }else{
            displayEnemyTime = String.format("%d:%s",enemyMinute, enemySeconds);
        }
        //Display the time
        textFont(message,17);                  
        fill(255,255,255);   
        text("Player's time",680,550);
        text("CPU's time",680,50);
        textFont(message,40);      
        text(displayPlayerTime, 680, 600);
        text(displayEnemyTime, 680, 100);
        
        //Check message
        if((enemyCheck || playerCheck) && playerCheckMate != true && enemyCheckMate != true){
            textFont(message,25);          
            fill(255,0,0);                     
            text("Check !",690,300);
            
        }
        if(enemyCheck) {
            
            
            if (mustDefineKing) {
                
                textFont(message,14);          
                fill(255,0,0);                     
                text("You must defend",680,350);
                text("your king !",680,370);
                // If we still need to flash
                if (flashCount < 3 * 2 * flashDuration) {
                    if ((flashCount / flashDuration) % 2 == 0) {
                        // Flash: fill with red color
                        fill(0xFFFF0000);
                    } else {
                        // No flash: fill with black color
                        fill(0);
                    }
                    flashCount++;
                } else {
                    // After 3 flashes, stop flashing and fill with red color
                    fill(0xFFFF0000);
                    mustDefineKing = false;
                    flashCount = 0;
                }
            } else {
                // If we don't need to flash, just fill with red color
                fill(0xFFFF0000);
            }
            // Draw the rectangle
            rect(board.playerKing.getX(), board.playerKing.getY(), 48, 48);
        }else if (playerCheck) {
            fill(0xFFFF0000);
            rect(board.enemyKing.getX(),board.enemyKing.getY(),48,48);
            
            
        }

   
        // Decrease the time for player and enemy
        if (playerRound == true) {
            playerCounter++;
            if(playerCounter == 60){
                playerTime --;
                playerCounter = 0;
            }
                
        }
        if (playerRound == false){
            enemyCounter++;
            if(enemyCounter == 60){
                enemyTime --;
                enemyCounter = 0;
            }
        }
        
       
      
        if(test) {
            currentMovingPiece = board.aiMove2();
            playerPiece = currentMovingPiece;
            currentMovingPiece.targetTile(board);
            if(currentMovingPiece.capturedTiles.size() != 0) {
                targetTile = currentMovingPiece.capturedTiles.get(rand.nextInt(currentMovingEnemy.capturedTiles.size()));
            }else if (currentMovingEnemy.lsTile.size() != 0) {
                targetTile = currentMovingPiece.lsTile.get(rand.nextInt(currentMovingEnemy.lsTile.size()));
            }
            
            
        }
        //Player turn
        if (currentMovingPiece != null && playerRound == true) {
            
            playerPiece = currentMovingPiece;
           
        
            //Castling
            if(currentMovingPiece.tile.getColumn() + 2 < 14 && currentMovingPiece.tile.getColumn() -2 >=0){
                if (targetTile == 
                board.boardMatrix[currentMovingPiece.tile.getRow()][currentMovingPiece.tile.getColumn() + 2]){
                rightCastle = true;
                rookCastleTile = board.boardMatrix[currentMovingPiece.tile.getRow()][currentMovingPiece.tile.getColumn() + 1];
            }else if(targetTile == 
                board.boardMatrix[currentMovingPiece.tile.getRow()][currentMovingPiece.tile.getColumn() - 2]){
                leftCastle = true;
                rookCastleTile = board.boardMatrix[currentMovingPiece.tile.getRow()][currentMovingPiece.tile.getColumn() - 1];
                }
            }
            //Eat sound effect 
            boolean eat = false;
            if (targetTile.getPiece() != null){
                eat = true;
            }
            currentMovingPiece.tick(targetTile,this.board);
           
            if(currentMovingPiece.tile == targetTile) {
                if (eat){
                    eatClip.setFramePosition(0);
                    eatClip.start();
                }else{
                    goClip.setFramePosition(0);
                    goClip.start();

                }
                
                
                //Castleing
                if(currentMovingPiece instanceof King) {
                    if (rightCastle == true && leftCastle == false) {
                        while(currentMovingPiece.castlerookRight.tile != rookCastleTile){
                            currentMovingPiece.castlerookRight.tick(rookCastleTile,this.board);
                        }
                        
                    }else if(rightCastle == false && leftCastle == true){
                        while(currentMovingPiece.castlerookLeft.tile != rookCastleTile){
                            currentMovingPiece.castlerookLeft.tick(rookCastleTile,this.board);
                        }
                       
                    }
                }
                //Check if the enemy King is under check
                playerCheck = board.Checking(playerPiece);
                if(enemyPiece != null) {
                    enemyCheck = board.Checking(enemyPiece);
                }
                playerTime += playerIncrement;
                enemySelect = true;
                playerRound = false;
                currentMovingPiece = null;
                currentMovingEnemy = new Pawn(0,0,1,true,new Tile(0,0));
                for(Piece piece : board.enemyCollection) {
                    piece.historyTiles.clear();
                    
                }
            }
        // Enemy turn
        }else if(playerRound == false && currentMovingEnemy != null) {
            if(enemySelect) {
                enemyTime += enemyIncrement;
                enemySelect = false;
                if(playerCheck){
                    // Only move legally during check
                    ArrayList<Piece> legalPiece = board.enemyLegalMove1(board.enemyKing, playerPiece);
                    ArrayList<Tile> legalTile = board.enemyLegalMove2(board.enemyKing, playerPiece);
                    board.enemyKing.targetTile(board);
                   
                    if(legalPiece.size() != 0 && legalTile.size() != 0){
                        int index = rand.nextInt(legalPiece.size());
                        currentMovingEnemy = legalPiece.get(index);
                        enemyTargetTile = legalTile.get(index);
                    }else if( board.enemyKing.lsTile.size() != 0) {
                        currentMovingEnemy = board.enemyKing;
                        enemyTargetTile = board.enemyKing.lsTile.get(rand.nextInt(board.enemyKing.lsTile.size()));
                        board.enemyKing.lsTile.clear();
                        board.enemyKing.capturedTiles.clear();

                    }else{
                        playerCheckMate = true;
                    }
                    
                }else{
                    // if it is not in check, choose aiMove.
                    currentMovingEnemy = board.aiMove();
                    enemyPiece = currentMovingEnemy;
                    currentMovingEnemy.targetTile(board);
                    if(currentMovingEnemy.capturedTiles.size() != 0) {
                        enemyTargetTile = currentMovingEnemy.capturedTiles.get(rand.nextInt(currentMovingEnemy.capturedTiles.size()));
                    }else if (currentMovingEnemy.lsTile.size() != 0) {
                        enemyTargetTile = currentMovingEnemy.lsTile.get(rand.nextInt(currentMovingEnemy.lsTile.size()));
                    }
                }
            }
            currentMovingEnemy.tick(enemyTargetTile,this.board);
            if(currentMovingEnemy.tile == enemyTargetTile) {
                if(enemyTargetTile.getPiece() != null && enemyTargetTile.getPiece().isWhite == ! currentMovingEnemy.isWhite){
                    
                    enemyTargetTile.getPiece().isAlive = false;
                }
                
                //Check if the enemy King and player King is under check
                enemyCheck = board.Checking(enemyPiece);
                playerCheck = board.Checking(playerPiece);
                playerRound = true;
                currentMovingEnemy.lsTile.clear();
                currentMovingEnemy.capturedTiles.clear();
                currentMovingEnemy = null;
                for(Piece piece : board.playerCollection) {
                    piece.historyTiles.clear();
                    
                }
        
                
            }

        }
        
        
       

       
        ArrayList<Piece> removedForPlayer = new ArrayList<>();
        ArrayList<Piece> removedForEnemy = new ArrayList<>();
         // Draw the enemy
        int countj = board.enemyCollection.size();
        for(int j = 0; j < countj; j++) {
            if(board.enemyCollection.get(j).isAlive == false) {
                removedForEnemy.add(board.enemyCollection.get(j));
            }
            
            
            board.enemyCollection.get(j).check(this);
            board.enemyCollection.get(j).draw(this);
            
        }
        // Remove the dead enemy from collection
        board.enemyCollection.removeAll(removedForEnemy);

        // Draw the player 
        int counti = board.playerCollection.size();
        for(int i = 0; i < counti; i++) {
            if(board.playerCollection.get(i).isAlive == false) {
                removedForPlayer.add(board.playerCollection.get(i));
            }
         
            if(playerPiece != null && playerPiece.capturedTiles.size() != 0) {
                for(Tile tile : playerPiece.capturedTiles){
                    if (tile.getPiece() != null && tile.getPiece().isAlive == true) {
                        tile.getPiece().draw(this);
                    }
                }
            }
            
            board.playerCollection.get(i).check(this);
            board.playerCollection.get(i).draw(this);
            
            
        }
        board.playerCollection.removeAll(removedForPlayer);

        for(Piece piece : board.enemyCollection){
            piece.draw(this);
            
        }

        
        // Pawn promote to queen 
        board.pawnPromte(this, playerSpeed, maxMoveTime);
       
       
        
 

        
       
    }
	
	// Add any additional methods or attributes you want. Please put classes in different files.


    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
        
    }

}
