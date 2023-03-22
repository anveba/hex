package test.hex.ai;

import main.hex.Tile;
import main.hex.ai.Graph;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {


    @Test
    public void graphConstructorWorks(){
       Graph g = new Graph(5);
    }



    @Test
    public void adjacencyExistsBetweenNodesAfterConnection(){
        Graph g = new Graph(5);
        g.connect(1,2);
        assertTrue(g.adjacencyExists(1,2));
    }

    @Test
    public void nodesAreNotConnectedAfterNoConnection(){
        Graph g = new Graph(5);
        assertFalse(g.adjacencyExists(1,2));
    }

    @Test
    public void nodesGetConnectedWithFadeAfterConnectionWithFade(){
        Graph g = new Graph(5);
        g.connectWithFade(1,2,0.5);
        assertTrue(g.fadeOfAdjacency(1,2).isPresent());
        assertTrue(g.fadeOfAdjacency(1,2).get() == 0.5);
    }

    @Test
    public void nodesNotConnectedWillHaveNoFade(){
        Graph g = new Graph(5);
        assertTrue(g.fadeOfAdjacency(1,2).isEmpty());

    }

}
