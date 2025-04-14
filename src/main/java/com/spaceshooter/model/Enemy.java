package com.spaceshooter.model;

public class Enemy {
  private static final int WIDTH = 30;
  private static final int HEIGHT = 30;
  private static final int INITIAL_HEALTH = 100;
  private static final int BASE_SPEED = 5;
  private int x;
  private int y;
  private int health;
  private int currentSpeed;
  private int gameWidth;

  public Enemy(int x, int y, int gameWidth) {
    this.x = x;
    this.y = y;
    this.health = INITIAL_HEALTH;
    this.currentSpeed = BASE_SPEED;
    this.gameWidth = gameWidth;
  }

  public void moveLeft() {
    x -= currentSpeed;
    if (x < 0) {
      x = 0;
    }
  }

  public void moveRight() {
    x += currentSpeed;
    if (x + WIDTH > gameWidth) {
      x = gameWidth - WIDTH;
    }
  }

  public void moveDown() {
    y += currentSpeed;
  }

  public void updateSpeed(int totalEnemies, int dropCount) {
    // Base speed + (1 per drop) + (inverse of enemy count with stronger scaling)
    int baseSpeed = BASE_SPEED + dropCount;

    // If we have fewer than 6 enemies, add an aggressive speed boost
    if (totalEnemies < 6) {
      baseSpeed += (6 - totalEnemies) * 6;
    }

    // Add the inverse scaling based on total enemies
    currentSpeed = baseSpeed + (int) (20.0 / (totalEnemies + 1));
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

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getWidth() {
    return WIDTH;
  }

  public int getHeight() {
    return HEIGHT;
  }

  public int getCurrentSpeed() {
    return currentSpeed;
  }
}
