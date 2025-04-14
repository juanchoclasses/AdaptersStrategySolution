package com.spaceshooter.model;

/**
 * Represents an enemy spaceship in the game.
 * Enemies can move left, right, and down, and have health that can be reduced by taking damage.
 * Their speed increases as the game progresses and fewer enemies remain.
 */
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

  /**
   * Constructs a new enemy at the specified position.
   *
   * @param x the initial x-coordinate of the enemy
   * @param y the initial y-coordinate of the enemy
   * @param gameWidth the width of the game area, used for boundary checking
   */
  public Enemy(int x, int y, int gameWidth) {
    this.x = x;
    this.y = y;
    this.health = INITIAL_HEALTH;
    this.currentSpeed = BASE_SPEED;
    this.gameWidth = gameWidth;
  }

  /**
   * Moves the enemy to the left by its current speed.
   * The enemy will not move beyond the left edge of the game area.
   */
  public void moveLeft() {
    x -= currentSpeed;
    if (x < 0) {
      x = 0;
    }
  }

  /**
   * Moves the enemy to the right by its current speed.
   * The enemy will not move beyond the right edge of the game area.
   */
  public void moveRight() {
    x += currentSpeed;
    if (x + WIDTH > gameWidth) {
      x = gameWidth - WIDTH;
    }
  }

  /**
   * Moves the enemy downward by its current speed.
   */
  public void moveDown() {
    y += currentSpeed;
  }

  /**
   * Updates the enemy's speed based on the number of remaining enemies and drop count.
   * Speed increases when:
   * - There are fewer enemies (more aggressive)
   * - The enemy has dropped more times
   * - There is an inverse scaling based on total enemies
   *
   * @param totalEnemies the current number of enemies in the game
   * @param dropCount the number of times the enemies have moved down
   */
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

  /**
   * Reduces the enemy's health by the specified amount of damage.
   * Health cannot go below 0.
   *
   * @param damage the amount of damage to take
   */
  public void takeDamage(int damage) {
    health = Math.max(0, health - damage);
  }

  /**
   * Checks if the enemy has been destroyed (health is 0 or less).
   *
   * @return true if the enemy is destroyed, false otherwise
   */
  public boolean isDestroyed() {
    return health <= 0;
  }

  /**
   * Returns the current health of the enemy.
   *
   * @return the enemy's current health
   */
  public int getHealth() {
    return health;
  }

  /**
   * Returns the x-coordinate of the enemy.
   *
   * @return the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the x-coordinate of the enemy.
   *
   * @param x the new x-coordinate
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Returns the y-coordinate of the enemy.
   *
   * @return the y-coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the y-coordinate of the enemy.
   *
   * @param y the new y-coordinate
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Returns the width of the enemy.
   *
   * @return the width
   */
  public int getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the enemy.
   *
   * @return the height
   */
  public int getHeight() {
    return HEIGHT;
  }

  /**
   * Returns the current speed of the enemy.
   *
   * @return the current speed
   */
  public int getCurrentSpeed() {
    return currentSpeed;
  }
}
