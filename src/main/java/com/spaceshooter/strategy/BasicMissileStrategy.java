package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;

/**
 * A basic implementation of the MissileStrategy interface.
 * This strategy creates simple, straight-flying missiles that move upward.
 * It is the default missile behavior in the game.
 */
public class BasicMissileStrategy implements MissileStrategy {
  /**
   * Creates a new basic missile at the specified position.
   * The missile will move straight upward from its starting position.
   *
   * @param x the x-coordinate where the missile will be created
   * @param y the y-coordinate where the missile will be created
   * @return a new Missile instance that moves straight upward
   */
  @Override
  public Missile createMissile(int x, int y) {
    return new Missile(x, y, true);
  }
}
