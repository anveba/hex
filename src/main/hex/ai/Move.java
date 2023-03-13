package main.hex.ai;

public class Move implements Comparable<Move> {

    private int index;

    private double value;

    public static final Move MAX_VALUE_MOVE = new Move(-1,Double.POSITIVE_INFINITY);
    public static final Move MIN_VALUE_MOVE = new Move(-1,Double.NEGATIVE_INFINITY);


    public Move(int index, double value){
        this.index = index;
        this.value = value;
    }

    public Move max(Move o){
        if(this.value >= o.value){
            return this;
        }
        return o;
    }

    public Move min(Move o){
        if(this.value <= o.value){
            return this;
        }
        return o;
    }

    @Override
    public int compareTo(Move o) {
        return Double.compare(value,o.value);
    }
}
