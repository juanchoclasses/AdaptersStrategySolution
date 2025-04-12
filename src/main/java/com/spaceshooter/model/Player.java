package com.spaceshooter.model;

public class Player {
    private int x;
    private int y;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int GAME_WIDTH = 600;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void moveLeft() {
        x = Math.max(0, x - 10);
    }
    
    public void moveRight() {
        x = Math.min(GAME_WIDTH - WIDTH, x + 10);
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return WIDTH;
    }
    
    public int getHeight() {
        return HEIGHT;
    }
}
