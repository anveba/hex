package test.hex.ai;

import main.hex.ai.graph.Edge;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EdgeTest {


    @Test
    public void EdgeHasSamePropertiesAsGiven(){
        int from = 1;
        int to = 2;
        double fade = 0.5;

        Edge e = new Edge(from,to,fade);

        assertEquals(from,e.getFrom());
        assertEquals(to,e.getTo());
        assertTrue(fade == e.getWeight());
    }


}
