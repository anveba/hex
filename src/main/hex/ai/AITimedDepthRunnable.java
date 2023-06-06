package main.hex.ai;

/*
Author Nikolaj

Runnable class to run AI with time limit, using multithreading
This class is not used in the final product, as we are unsure about the safety of multi threading
 */

public class AITimedDepthRunnable implements Runnable{
    AIMove bestMove;
    AI ai;

    public AITimedDepthRunnable(AI ai){
        this.bestMove = null;
        this.ai = ai;
    }
    @Override
    public void run() {
        int depth = 2;
        while (true){
            this.bestMove = ai.getBestMoveWithDepth(depth);
            depth++;
        }

    }

    public  AIMove getBestMove(){
        return bestMove;
    }
}
