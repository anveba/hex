package main.hex.ai;



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
