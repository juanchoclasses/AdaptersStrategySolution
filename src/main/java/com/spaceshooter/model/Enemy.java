package com.spaceshooter.model;

public class Enemy {
    private int x;
    private int y;
    private int health;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int INITIAL_HEALTH = 100;
    
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = INITIAL_HEALTH;
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
}
