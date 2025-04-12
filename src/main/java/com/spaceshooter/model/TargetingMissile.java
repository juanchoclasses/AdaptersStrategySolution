package com.spaceshooter.model;

public class TargetingMissile extends Missile {
    private Enemy target;
    private static final double TURN_RATE = 0.1; // How quickly the missile can turn
    private double currentDirection; // Current direction in radians
    
    public TargetingMissile(int x, int y, Enemy target) {
        super(x, y, true);
        this.target = target;
        this.currentDirection = -Math.PI / 2; // Start moving upward
    }
    
    @Override
    public void update() {
        if (target != null) {
            // Calculate direction to target
            double targetX = target.getX() + target.getWidth() / 2;
            double targetY = target.getY() + target.getHeight() / 2;
            double desiredDirection = Math.atan2(targetY - y, targetX - x);
            
            // Smoothly turn towards target
            double angleDiff = desiredDirection - currentDirection;
            // Normalize angle difference to [-PI, PI]
            while (angleDiff > Math.PI) angleDiff -= 2 * Math.PI;
            while (angleDiff < -Math.PI) angleDiff += 2 * Math.PI;
            
            // Turn towards target
            currentDirection += Math.signum(angleDiff) * Math.min(Math.abs(angleDiff), TURN_RATE);
            
            // Move in current direction
            int speed = 5;
            x += Math.cos(currentDirection) * speed;
            y += Math.sin(currentDirection) * speed;
        } else {
            // If no target, move upward like a normal missile
            super.update();
        }
    }
} 