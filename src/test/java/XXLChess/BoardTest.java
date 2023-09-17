package XXLChess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;
import processing.core.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testInitializeBoard() {
        // Test if the board is initialized correctly
        // Verify that the boardMatrix is not null
        assertNotNull(board.boardMatrix);

        // Verify the size of the boardMatrix
        assertEquals(14, board.boardMatrix.length);
        assertEquals(14, board.boardMatrix[0].length);

        // Verify that the playerCollection and enemyCollection are empty initially
        assertTrue(board.playerCollection.isEmpty());
        assertTrue(board.enemyCollection.isEmpty());
    }

    @Test
    public void testClone() {
        // Test cloning the board
        Board clonedBoard = board.clone();

        // Verify that the cloned board is not the same object as the original board
        assertNotSame(board, clonedBoard);

        // Verify that the boardMatrix is cloned correctly
        assertArrayEquals(board.boardMatrix, clonedBoard.boardMatrix);

        // Verify that the playerCollection and enemyCollection are cloned correctly
        assertNotSame(board.playerCollection, clonedBoard.playerCollection);
        assertNotSame(board.enemyCollection, clonedBoard.enemyCollection);
        assertEquals(board.playerCollection.size(), clonedBoard.playerCollection.size());
        assertEquals(board.enemyCollection.size(), clonedBoard.enemyCollection.size());
    }

    @Test
    public void testClonePlayerCollection() {
        // Test cloning the playerCollection
        board.playerCollection.add(new Pawn(0,0,1,true,new Tile(2,5)));
        board.playerCollection.add(new Pawn(0,0,1,true,new Tile(2,5)));
        Board clonedBoard = board.clone();

        // Verify that the cloned playerCollection is not the same object as the original playerCollection
        assertNotSame(board.playerCollection, clonedBoard.playerCollection);

        // Verify that the cloned playerCollection has the same size as the original playerCollection
        assertEquals(board.playerCollection.size(), clonedBoard.playerCollection.size());

        // Verify that the pieces in the cloned playerCollection are not the same objects as the pieces in the original playerCollection
        for (int i = 0; i < board.playerCollection.size(); i++) {
            assertNotSame(board.playerCollection.get(i), clonedBoard.playerCollection.get(i));
        }
    }

    @Test
    public void testGeneratePiece(PApplet app) {
        // Test generating the pieces on the board
        board.initializeBoard(app);
        board.gereratePiece();
        assertNotNull(board.playerCollection);
        assertNotNull(board.enemyCollection);
        board.playerWhite = false;
        board.setPiece("level1.txt", "white");
        board.setPiece("level2.txt", "black");
       


        // Verify that all tiles on the boardMatrix are not null
        for (int i = 0; i < board.boardMatrix.length; i++) {
            for (int j = 0; j < board.boardMatrix[i].length; j++) {
                assertNotNull(board.boardMatrix[i][j]);
            }
        }
    }

   


    

}