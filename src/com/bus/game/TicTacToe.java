package com.bus.game;
import com.gui.game.Game;

public class TicTacToe implements Game{
    private char[][] board;
    private char currentPlayer;
    private char nextPlayer;
    private boolean gameOver;
    private String winner;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        gameOver = false;
        winner = null;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    @Override
    public void start() {
        initializeBoard();
        currentPlayer = 'X';
        gameOver = false;
        winner = null;
    }

    @Override
    public void play(int x, int y) {
        if (!gameOver && board[x][y] == '-') {
            board[x][y] = currentPlayer;
            if (checkWin()) {
                gameOver = true;
                nextPlayer = (currentPlayer == 'X') ? '0' : 'X';
                winner = String.valueOf(nextPlayer);
            } else if (isBoardFull()) {
                gameOver = true;
                winner = "Draw";
            }
            currentPlayer = (currentPlayer == 'X') ? '0' : 'X';
        }
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    @Override
    public String getWinner() {
        return winner;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getNextPlayer() {
        return nextPlayer;
    }

    private boolean checkWin() {
        // Verifica filas, columnas y diagonales
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
