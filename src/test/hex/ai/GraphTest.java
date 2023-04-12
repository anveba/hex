package test.hex.ai;

import main.hex.ai.graph.Graph;

import main.hex.ai.graph.SignalGraphHeuristic;
import org.junit.Test;

import static org.junit.Assert.*;

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
        g.connectWithWeight(1,2,0.5);
        assertTrue(g.fadeOfAdjacency(1,2).isPresent());
        assertTrue(g.fadeOfAdjacency(1,2).get() == 0.5);
    }

    @Test
    public void nodesNotConnectedWillHaveNoFade(){
        Graph g = new Graph(5);
        assertTrue(g.fadeOfAdjacency(1,2).isEmpty());

    }

    @Test
    public void signalHeuristicForStraightLineGraphWith1FadeInEachEdgeIs1(){
        Graph g = new Graph(3);
        SignalGraphHeuristic s = new SignalGraphHeuristic();
        g.connectWithWeight(0,1,1);
        g.connectWithWeight(0,2,1);
        assertEquals(s.computeGraphHeuristic(g),1,0.0);

    }

}
