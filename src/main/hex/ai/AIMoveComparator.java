package main.hex.ai;

import java.util.Comparator;

public class AIMoveComparator implements Comparator<AIMove> {
    @Override
    public int compare(AIMove o1, AIMove o2) {
        return Double.compare(o1.getValue(),o2.getValue());
    }
}
