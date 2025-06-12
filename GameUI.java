import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class GameUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameBoard board = new GameBoard();
    private AIPlayer aiPlayer;
    private boolean vsAI;
    private char currentPlayer = 'X';
    
    public GameUI() {
        vsAI = JOptionPane.showConfirmDialog(null, "Contra AI?", "MOD DE JOC", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        if(vsAI) aiPlayer = new AIPlayer('O', 'X');

        setTitle("TicTacToe");
        setLayout(new GridLayout(3,3));
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 60);
        for (int i = 0; i < 3; i++) 
            for(int j = 0; j < 3; j++) {
                JButton btn = new JButton();
                btn.setFont(font);
                int row = i, col = j;
                btn.addActionListener(e -> playerMove(row,col));
                buttons[i][j] = btn;
                add(btn);
            }
    }

    private void playerMove(int row, int col) {
        if(!board.makeMove(row, col, currentPlayer)) return;

        buttons[row][col].setText(String.valueOf(currentPlayer));
        char winner = board.checkWinner();

        if(winner != ' ') {
            showResult(winner + " a castigat!");
            return;
        }else if(board.isFull()) {
            showResult("Remiza!");
            return;
        }
        
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if(vsAI && currentPlayer == 'O') {
            aiMove();
        }
     }

     private void aiMove() {
        int[] move = aiPlayer.getBestMove(board);
        board.makeMove(move[0], move[1], 'O');
        buttons[move[0]][move[1]].setText("O");

        char winner = board.checkWinner();
        if(winner != ' ') { 
            showResult("AI-ul a castigat!");
        }else if(board.isFull()) {
            showResult("Remiza!");
        }
        currentPlayer = 'X';
     }

    private void showResult(String message) {
        JOptionPane.showMessageDialog(this, message);
        int reply = JOptionPane.showConfirmDialog(this, "Jucati din nou?", "Ai pierdut!", JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.YES_OPTION) resetGame();
        else System.exit(0);
    }

  private void resetGame() {
    int mode = JOptionPane.showConfirmDialog(
            this, "Play against AI?", "Select Mode",
            JOptionPane.YES_NO_OPTION);

    vsAI = (mode == JOptionPane.YES_OPTION);
    aiPlayer = vsAI ? new AIPlayer('O', 'X') : null;

    board.reset();
    currentPlayer = 'X';

    for (JButton[] row : buttons)
        for (JButton btn : row)
            btn.setText("");
    }
}
