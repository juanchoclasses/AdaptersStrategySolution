package com.spaceshooter.model;

/**
 * Represents the player's spaceship in the Space Shooter game.
 * The player can move left and right within the game boundaries and has health that can be damaged.
 * The player's position and health status are tracked and can be queried.
 */
public class Player {
  private static final int WIDTH = 40;
  private static final int HEIGHT = 40;
  private static final int GAME_WIDTH = 600;
  private static final int INITIAL_HEALTH = 100;
  private int x;
  private int y;
  private int health;

  /**
   * Constructs a new player at the specified position with full health.
   *
   * @param x the initial x-coordinate of the player
   * @param y the initial y-coordinate of the player
   */
  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.health = INITIAL_HEALTH;
  }

  /**
   * Moves the player to the left by 10 units, but not beyond the left edge of the game.
   */
  public void moveLeft() {
    x = Math.max(0, x - 10);
  }

  /**
   * Moves the player to the right by 10 units, but not beyond the right edge of the game.
   */
  public void moveRight() {
    x = Math.min(GAME_WIDTH - WIDTH, x + 10);
  }

  /**
   * Applies damage to the player's health.
   * The health cannot go below 0.
   *
   * @param damage the amount of damage to apply
   */
  public void takeDamage(int damage) {
    health = Math.max(0, health - damage);
  }

  /**
   * Checks if the player has been destroyed (health is 0 or less).
   *
   * @return true if the player is destroyed, false otherwise
   */
  public boolean isDestroyed() {
    return health <= 0;
  }

  /**
   * Returns the player's current health.
   *
   * @return the current health value
   */
  public int getHealth() {
    return health;
  }

  /**
   * Returns the x-coordinate of the player.
   *
   * @return the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the x-coordinate of the player.
   *
   * @param x the new x-coordinate
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Returns the y-coordinate of the player.
   *
   * @return the y-coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the y-coordinate of the player.
   *
   * @param y the new y-coordinate
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Returns the width of the player's spaceship.
   *
   * @return the width
   */
  public int getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the player's spaceship.
   *
   * @return the height
   */
  public int getHeight() {
    return HEIGHT;
  }
}
