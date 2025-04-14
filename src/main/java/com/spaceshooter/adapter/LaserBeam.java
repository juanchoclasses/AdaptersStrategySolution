package com.spaceshooter.adapter;

/**
 * LaserBeam represents a beam of focused energy, completely different from regular missiles.
 * This is part of the "third-party" laser weapon system.
 */
public class LaserBeam {
  private static final int BEAM_WIDTH = 5;
  private static final int BEAM_HEIGHT = 30;
  private static final int BEAM_SPEED = 15;
  private static final int BEAM_POWER = 100;
  private int sourceX;
  private int sourceY;

  /**
   * Constructs a new LaserBeam at the specified coordinates.
   *
   * @param sourceX the x-coordinate of the beam's source
   * @param sourceY the y-coordinate of the beam's source
   */
  public LaserBeam(int sourceX, int sourceY) {
    this.sourceX = sourceX;
    this.sourceY = sourceY;
  }

  /**
   * Moves the laser beam upward at its defined speed.
   */
  public void move() {
    sourceY -= BEAM_SPEED; // Move upward
  }

  /**
   * Returns the x-coordinate of the beam's source.
   *
   * @return the x-coordinate of the beam's source
   */
  public int getSourceX() {
    return sourceX;
  }

  /**
   * Returns the y-coordinate of the beam's source.
   *
   * @return the y-coordinate of the beam's source
   */
  public int getSourceY() {
    return sourceY;
  }

  /**
   * Returns the width of the laser beam.
   *
   * @return the width of the laser beam
   */
  public int getWidth() {
    return BEAM_WIDTH;
  }

  /**
   * Returns the height of the laser beam.
   *
   * @return the height of the laser beam
   */
  public int getHeight() {
    return BEAM_HEIGHT;
  }

  /**
   * Returns the power level of the laser beam.
   *
   * @return the power level of the laser beam
   */
  public int getPower() {
    return BEAM_POWER;
  }

  /**
   * Checks if the laser beam has moved off the screen.
   *
   * @return true if the beam is off screen, false otherwise
   */
  public boolean isOffScreen() {
    return sourceY < 0;
  }

  /**
   * Checks if the laser beam intersects with a given rectangle.
   *
   * @param x the x-coordinate of the rectangle
   * @param y the y-coordinate of the rectangle
   * @param width the width of the rectangle
   * @param height the height of the rectangle
   * @return true if the beam intersects with the rectangle, false otherwise
   */
  public boolean intersectsWith(int x, int y, int width, int height) {
    return sourceX < x + width &&
        sourceX + BEAM_WIDTH > x &&
        sourceY < y + height &&
        sourceY + BEAM_HEIGHT > y;
  }
} 