package XXLChess;

import processing.core.PApplet;
import processing.event.MouseEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TileTest {

    @Test
    public void testSetPieceAndGetPiece() {
        Tile tile = new Tile(2, 3);
        Piece piece = new Pawn(0,0,1,true,tile);
        
        tile.setPiece(piece);
        assertEquals(piece, tile.getPiece());
    }

    @Test
    public void testSetRowAndGetRow() {
        Tile tile = new Tile(2, 3);
        
        tile.setRow(4);
        assertEquals(4, tile.getRow());
    }

    @Test
    public void testSetColumnAndGetColumn() {
        Tile tile = new Tile(2, 3);
        
        tile.setColumn(5);
        assertEquals(5, tile.getColumn());
    }

    @Test
    public void testGetX() {
        Tile tile = new Tile(2, 3);
        
        assertEquals(144, tile.getX());
    }

    @Test
    public void testGetY() {
        Tile tile = new Tile(2, 3);
        
        assertEquals(432, tile.getY());
    }
}