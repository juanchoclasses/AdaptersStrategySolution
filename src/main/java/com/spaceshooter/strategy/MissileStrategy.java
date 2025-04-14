package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;

public interface MissileStrategy {
  /**
   * Creates a missile at the specified position.
   *
   * @param x The x-coordinate of the missile.
   * @param y The y-coordinate of the missile.
   * @return The created missile.
   */
  Missile createMissile(int x, int y);
}
