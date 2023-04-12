package test.hex.ai;

import main.hex.ai.graph.GridGraph;
import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import org.junit.Test;

public class DijkstraConnectionTest {


    @Test
    public void connectorConnectsAgentColouredTilesWithZeroWeight(){

        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.RED), 1,1);
        b.setTileAtPosition(new Tile(TileColour.RED), 1,2);
        GridGraph g = new GridGraph(5);
        DijkstraBasedTileConnector d = new DijkstraBasedTileConnector();

        d.connectTiles(g,b,1,1,1,2,TileColour.RED);
        assert(g.weightOfAdjacency(g.xyToNum(1,1),g.xyToNum(1,2)).get() == 0.0);


    }


    @Test
    public void connectorDoesNotConnectNonAgentColouredTiles(){

        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.BLUE), 1,1);
        b.setTileAtPosition(new Tile(TileColour.BLUE), 1,2);
        b.setTileAtPosition(new Tile(TileColour.BLUE), 1,3);
        b.setTileAtPosition(new Tile(TileColour.WHITE), 1,4);
        b.setTileAtPosition(new Tile(TileColour.BLUE), 1,3);
        b.setTileAtPosition(new Tile(TileColour.RED), 1,0);
        GridGraph g = new GridGraph(5);
        DijkstraBasedTileConnector d = new DijkstraBasedTileConnector();

        d.connectTiles(g,b,1,1,1,2,TileColour.RED);
        d.connectTiles(g,b,1,3,1,4,TileColour.RED);
        d.connectTiles(g,b,1,3,1,0,TileColour.RED);
        assert(!g.adjacencyExists(g.xyToNum(1,1),g.xyToNum(1,2)));
        assert(!g.adjacencyExists(g.xyToNum(1,3),g.xyToNum(1,4)));
        assert(!g.adjacencyExists(g.xyToNum(1,3),g.xyToNum(1,0)));
    }

    @Test
    public void connectorConnectsPartiallyWhiteTilesWithGreaterThanZeroWeight(){
        Board b = new Board(5);
        b.setTileAtPosition(new Tile(TileColour.RED), 1,1);
        b.setTileAtPosition(new Tile(TileColour.WHITE), 1,2);
        b.setTileAtPosition(new Tile(TileColour.WHITE), 1,3);

        GridGraph g = new GridGraph(5);
        DijkstraBasedTileConnector d = new DijkstraBasedTileConnector();

        d.connectTiles(g,b,1,1,1,2,TileColour.RED);
        d.connectTiles(g,b,1,2,1,3,TileColour.RED);

        assert(g.weightOfAdjacency(g.xyToNum(1,1),g.xyToNum(1,2)).get() > 0.0);
        assert(g.weightOfAdjacency(g.xyToNum(1,2),g.xyToNum(1,3)).get() > 0.0);

    }
}
