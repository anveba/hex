package test.hex.ai;

import main.hex.ai.graph.Graph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridGraphTest {


    @Test
    public void nodesGetConnectedWithFadeAfterConnectionWithFadeXY(){
        Graph g = new Graph(5);
        g.connectWithWeight(1,2,0.5);
        assertTrue(g.weightOfAdjacency(1,2).isPresent());
        assertEquals(0.5, g.weightOfAdjacency(1, 2).get(), 0.0);
    }

    @Test
    public void nodesNotConnectedWillHaveNoFade(){
        Graph g = new Graph(5);
        assertTrue(g.weightOfAdjacency(1,2).isEmpty());

    }


}
