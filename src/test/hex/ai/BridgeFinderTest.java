package test.hex.ai;

import main.hex.ai.Bridge;
import main.hex.ai.BridgeFinder;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BridgeFinderTest {


    @Test
    public void bridgeFinderFindsLevelOneVerticalBridgeAndNothingElse(){
        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),1,0);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),2,2);



        ArrayList<Bridge> bridges = BridgeFinder.findLevelOneBridges(b,TileColour.PLAYER2);
        assertEquals("Found incorrect number of bridges", 1, bridges.size());

    }

    @Test
    public void bridgeFinderFindsLevelOneHorizontalUpwardsBridgeAndNothingElse(){
        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),0,1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),1,0);



        ArrayList<Bridge> bridges = BridgeFinder.findLevelOneBridges(b,TileColour.PLAYER2);
        assertEquals("Found incorrect number of bridges", 1, bridges.size());

    }
    @Test
    public void bridgeFinderFindsLevelOneHorizontalDownwardsBridgeAndNothingElse(){
        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),0,1);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),2,2);



        ArrayList<Bridge> bridges = BridgeFinder.findLevelOneBridges(b,TileColour.PLAYER2);
        assertEquals("Found incorrect number of bridges", 1, bridges.size());

    }
}
