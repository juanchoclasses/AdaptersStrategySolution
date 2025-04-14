package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;

/**
 * Interface defining the strategy pattern for creating missiles in the Space Shooter game.
 * Different implementations of this interface can create missiles with varying behaviors,
 * such as basic straight-flying missiles, targeting missiles, or multiple missiles.
 * This allows for flexible and extensible missile creation without modifying existing code.
 */
public interface MissileStrategy {
  /**
   * Creates a missile at the specified position.
   * The specific behavior and type of missile created depends on the implementing class.
   *
   * @param x The x-coordinate where the missile will be created
   * @param y The y-coordinate where the missile will be created
   * @return The created missile, or null if the strategy handles missile creation internally
   */
  Missile createMissile(int x, int y);
}
