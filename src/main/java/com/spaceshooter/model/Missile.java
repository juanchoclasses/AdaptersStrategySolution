package com.spaceshooter.model;

public class Missile {
  private static final int WIDTH = 10;
  private static final int HEIGHT = 20;
  protected int x;
  protected int y;
  private boolean playerMissile;
  private int speed;

  public Missile(int x, int y, boolean playerMissile) {
    this.x = x;
    this.y = y;
    this.playerMissile = playerMissile;
    this.speed = playerMissile ? -10 : 5; // Player missiles go up, enemy missiles go down
  }

  public void update() {
    y += speed;
  }

  public boolean collidesWith(Player player) {
    return x < player.getX() + player.getWidth() &&
        x + WIDTH > player.getX() &&
        y < player.getY() + player.getHeight() &&
        y + HEIGHT > player.getY();
  }

  public boolean collidesWith(Enemy enemy) {
    return x < enemy.getX() + enemy.getWidth() &&
        x + WIDTH > enemy.getX() &&
        y < enemy.getY() + enemy.getHeight() &&
        y + HEIGHT > enemy.getY();
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

  public boolean isPlayerMissile() {
    return playerMissile;
  }
}
