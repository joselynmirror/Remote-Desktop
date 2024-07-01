package com.bus.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class TicTacToeServer extends JFrame {
    private static final int PORT = 12345;
    private JButton[][] buttons = new JButton[3][3];
    private TicTacToe game;
    private Socket player1, player2;
    private PrintWriter output1, output2;


    public TicTacToeServer() {
        setTitle("Tic Tac Toe Server");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));
        game = new TicTacToe();
        initializeButtons();
        setVisible(true);
        startServer();
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                add(buttons[i][j]);
            }
        }
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Servidor iniciado.");
                player1 = serverSocket.accept();
                player2 = serverSocket.accept();

                output1 = new PrintWriter(player1.getOutputStream(), true);
                output2 = new PrintWriter(player2.getOutputStream(), true);

                game.start();
                handleGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleGame() {
        boolean player1Turn = true;
        try {
            BufferedReader input1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            BufferedReader input2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

            while (!game.isOver()) {
                PrintWriter currentOutput = player1Turn ? output2 : output1;
                BufferedReader currentInput = player1Turn ? input2 : input1;

                currentOutput.println("Tu turno");
                String move = currentInput.readLine();
                String[] parts = move.split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                game.play(x, y);
                updateBoard(x, y, game.getCurrentPlayer() == 'X' ? 'O' : 'X');

                output1.println(x + "," + y + "," + game.getCurrentPlayer());
                output2.println(x + "," + y + "," + game.getCurrentPlayer());

                player1Turn = !player1Turn;
            }

            String result = "El juego ha terminado. Ganador: " + game.getWinner();

            output1.println(result);
            output2.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBoard(int x, int y, char player) {

        buttons[x][y].setText(String.valueOf(player));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeServer::new);
    }
}
