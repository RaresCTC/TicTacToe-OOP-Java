public class AIPlayer {
    private char ai, human;
    
    public AIPlayer(char ai, char human) {
        this.ai = ai;
        this.human = human;
    }

    public int[] getBestMove(GameBoard board) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1,-1};

        for(int i = 0; i < 3; i++)
            for(int j= 0; j < 3; j++) {
                if(board.getCell(i,j) == ' ') {
                    board.setCell(i,j,ai);
                    int score = minimax(board, false);
                    board.setCell(i,j,' ');
                    if(score > bestScore) {
                        bestScore = score;
                        move = new int[]{i,j};
                    }
                }
            }
        return move;
    }

    private int minimax(GameBoard board, boolean isMaximizing) {
        char winner = board.checkWinner();
        if(winner == ai) return 10;
        if(winner == human) return -10;
        if(board.isFull()) return 0;

        if(isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 3; j++)
                    if(board.getCell(i, j) == ' ') {
                        board.setCell(i, j, ai);
                        int eval = minimax(board, false);
                        board.setCell(i, j, ' ');
                        maxEval = Math.max(maxEval, eval);
                    }
            return maxEval;          
        }else {
            int minEval = Integer.MAX_VALUE;
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 3; j++) 
                    if(board.getCell(i,j) == ' ') {
                        board.setCell(i, j, human);
                        int eval = minimax(board, true);
                        board.setCell(i, j, ' ');
                        minEval = Math.min(minEval, eval);
                    }
            return minEval;
        }
    }
} 
