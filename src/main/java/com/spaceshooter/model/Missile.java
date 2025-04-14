package com.spaceshooter.model;

/**
 * Represents a missile in the Space Shooter game.
 * Missiles can be fired by either the player or enemies, and move in different directions accordingly.
 * This class handles missile movement and collision detection with players and enemies.
 */
public class Missile {
  private static final int WIDTH = 10;
  private static final int HEIGHT = 20;
  protected int x;
  protected int y;
  private boolean playerMissile;
  private int speed;

  /**
   * Constructs a new missile at the specified position.
   *
   * @param x the initial x-coordinate of the missile
   * @param y the initial y-coordinate of the missile
   * @param playerMissile true if this is a player-fired missile, false if enemy-fired
   */
  public Missile(int x, int y, boolean playerMissile) {
    this.x = x;
    this.y = y;
    this.playerMissile = playerMissile;
    this.speed = playerMissile ? -10 : 5; // Player missiles go up, enemy missiles go down
  }

  /**
   * Updates the missile's position based on its speed.
   * Player missiles move upward, enemy missiles move downward.
   */
  public void update() {
    y += speed;
  }

  /**
   * Checks if this missile collides with a player.
   *
   * @param player the player to check collision with
   * @return true if the missile collides with the player, false otherwise
   */
  public boolean collidesWith(Player player) {
    return x < player.getX() + player.getWidth() &&
        x + WIDTH > player.getX() &&
        y < player.getY() + player.getHeight() &&
        y + HEIGHT > player.getY();
  }

  /**
   * Checks if this missile collides with an enemy.
   *
   * @param enemy the enemy to check collision with
   * @return true if the missile collides with the enemy, false otherwise
   */
  public boolean collidesWith(Enemy enemy) {
    return x < enemy.getX() + enemy.getWidth() &&
        x + WIDTH > enemy.getX() &&
        y < enemy.getY() + enemy.getHeight() &&
        y + HEIGHT > enemy.getY();
  }

  /**
   * Returns the x-coordinate of the missile.
   *
   * @return the x-coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the y-coordinate of the missile.
   *
   * @return the y-coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Returns the width of the missile.
   *
   * @return the width
   */
  public int getWidth() {
    return WIDTH;
  }

  /**
   * Returns the height of the missile.
   *
   * @return the height
   */
  public int getHeight() {
    return HEIGHT;
  }

  /**
   * Checks if this missile was fired by the player.
   *
   * @return true if this is a player-fired missile, false if enemy-fired
   */
  public boolean isPlayerMissile() {
    return playerMissile;
  }
}
