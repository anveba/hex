package main.hex.ai;

import java.util.Comparator;

/*
Author Nikolaj
Comparator class that allows us to compare moves
Used for sorting moves
 */

public class AIMoveComparator implements Comparator<AIMove> {
    @Override
    public int compare(AIMove o1, AIMove o2) {
        return Double.compare(o2.getValue(),o1.getValue());
    }
}
