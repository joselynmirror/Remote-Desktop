package com.bus.game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class TicTacToeClient extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    private JButton[][] buttons = new JButton[3][3];
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public TicTacToeClient() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        initializeButtons();
        connectToServer();

        setVisible(true);
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                int x = i, y = j;
                buttons[i][j].addActionListener(e -> makeMove(x, y));
                add(buttons[i][j]);
            }
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenToServer() {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                if (message.startsWith("Tu turno")) {
                    enableButtons(true);
                } else if (message.startsWith("El juego ha terminado")) {
                    JOptionPane.showMessageDialog(this, message);
                    break;
                } else {
                    String[] parts = message.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    String player = parts[2];
                    buttons[x][y].setText(player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeMove(int x, int y) {
        output.println(x + " " + y);
        enableButtons(false);
    }

    private void enableButtons(boolean enable) {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setEnabled(enable && button.getText().isEmpty());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeClient::new);
    }
}
