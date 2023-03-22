package test.hex.ai;

import main.hex.ai.Graph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridGraphTest {


    @Test
    public void nodesGetConnectedWithFadeAfterConnectionWithFadeXY(){
        Graph g = new Graph(5);
        g.connectWithFade(1,2,0.5);
        assertTrue(g.fadeOfAdjacency(1,2).isPresent());
        assertEquals(0.5, g.fadeOfAdjacency(1, 2).get(), 0.0);
    }

    @Test
    public void nodesNotConnectedWillHaveNoFade(){
        Graph g = new Graph(5);
        assertTrue(g.fadeOfAdjacency(1,2).isEmpty());

    }


}
