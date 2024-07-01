package com.gui.game;

public interface Game {
    void start();
    void play(int x, int y);
    boolean isOver();
    String getWinner();


}