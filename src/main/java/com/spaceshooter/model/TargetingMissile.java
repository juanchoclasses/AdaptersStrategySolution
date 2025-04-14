package com.spaceshooter.model;

/**
 * A specialized missile that can track and follow a target enemy.
 * This missile extends the basic Missile class and adds homing capabilities.
 * It calculates the direction to its target and smoothly turns towards it while moving.
 */
public class TargetingMissile extends Missile {
  private static final double TURN_RATE = 0.1; // How quickly the missile can turn
  private Enemy target;
  private double currentDirection; // Current direction in radians

  /**
   * Constructs a new targeting missile at the specified position with a target enemy.
   *
   * @param x the initial x-coordinate of the missile
   * @param y the initial y-coordinate of the missile
   * @param target the enemy that the missile will track
   */
  public TargetingMissile(int x, int y, Enemy target) {
    super(x, y, true);
    this.target = target;
    this.currentDirection = -Math.PI / 2; // Start moving upward
  }

  /**
   * Updates the missile's position and direction.
   * If a target is set, the missile will calculate the direction to the target
   * and smoothly turn towards it while moving. If no target is set,
   * it behaves like a normal missile moving upward.
   */
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
      while (angleDiff > Math.PI) {
        angleDiff -= 2 * Math.PI;
      }
      while (angleDiff < -Math.PI) {
        angleDiff += 2 * Math.PI;
      }

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