package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
import processing.core.*;

public class PieceTest {
    
    public void generatePiece(PApplet app){
        Board board1 = new Board();
        board1.gereratePiece();
        try{
        board1.setPiece("level1","white");
        }catch(Exception e){
            System.out.println("Layout is not correct!");
        }
        
        //Load image for enemy and player
        for(Piece player : board1.playerCollection) {
            player.setImage(app);
            player.setSpeed(6);
            player.setMaxMoveTime(1);
        }
        for(Piece enemy : board1.enemyCollection) {
            enemy.setImage(app);
            enemy.setSpeed(6);
            enemy.setMaxMoveTime(1);
        }

    }

    public void generatePiece2(){
        App app = new App();
        Board board2 = new Board();
        board2.gereratePiece();
        try{
        board2.setPiece("level1","black");
        }catch(Exception e){
            System.out.println("Layout is not correct!");
        }
        
        //Load image for enemy and player
        for(Piece player : board2.playerCollection) {
            player.setImage(app);
            player.setSpeed(6);
            player.setMaxMoveTime(1);
            player.targetTile(board2);
        }
        for(Piece enemy : board2.enemyCollection) {
            enemy.setImage(app);
            enemy.setSpeed(6);
            enemy.setMaxMoveTime(1);
            enemy.targetTile(board2);
        }

    }
}
