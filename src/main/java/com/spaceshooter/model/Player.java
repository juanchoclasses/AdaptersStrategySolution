package com.spaceshooter.model;

public class Player {
    private int x;
    private int y;
    private int health;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int GAME_WIDTH = 600;
    private static final int INITIAL_HEALTH = 100;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = INITIAL_HEALTH;
    }
    
    public void moveLeft() {
        x = Math.max(0, x - 10);
    }
    
    public void moveRight() {
        x = Math.min(GAME_WIDTH - WIDTH, x + 10);
    }
    
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
    
    public boolean isDestroyed() {
        return health <= 0;
    }
    
    public int getHealth() {
        return health;
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
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
