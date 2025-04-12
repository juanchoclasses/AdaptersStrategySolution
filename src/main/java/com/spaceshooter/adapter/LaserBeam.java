package com.spaceshooter.adapter;

/**
 * LaserBeam represents a beam of focused energy, completely different from regular missiles.
 * This is part of the "third-party" laser weapon system.
 */
public class LaserBeam {
    private int sourceX;
    private int sourceY;
    private static final int BEAM_WIDTH = 5;
    private static final int BEAM_HEIGHT = 30;
    private static final int BEAM_SPEED = 15;
    private static final int BEAM_POWER = 100;
    
    public LaserBeam(int sourceX, int sourceY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
    }
    
    public void move() {
        sourceY -= BEAM_SPEED; // Move upward
    }
    
    public int getSourceX() {
        return sourceX;
    }
    
    public int getSourceY() {
        return sourceY;
    }
    
    public int getWidth() {
        return BEAM_WIDTH;
    }
    
    public int getHeight() {
        return BEAM_HEIGHT;
    }
    
    public int getPower() {
        return BEAM_POWER;
    }
    
    public boolean isOffScreen() {
        return sourceY < 0;
    }
    
    public boolean intersectsWith(int x, int y, int width, int height) {
        return sourceX < x + width &&
               sourceX + BEAM_WIDTH > x &&
               sourceY < y + height &&
               sourceY + BEAM_HEIGHT > y;
    }
} 