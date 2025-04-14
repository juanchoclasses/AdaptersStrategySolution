package com.spaceshooter.adapter;

import com.spaceshooter.model.Missile;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.strategy.MissileStrategy;

/**
 * Adapter that allows the LaserWeapon to be used as a MissileStrategy.
 * This demonstrates the Adapter pattern by converting the LaserWeapon interface
 * to match the MissileStrategy interface.
 *
 * TODO: Implement this adapter to convert the LaserWeapon into a MissileStrategy.
 * The laser weapon has the following unique properties:
 * 1. Creates a laser beam that moves upward
 * 2. Has fixed width and height
 * 3. Can check for collisions with enemies
 */
public class LaserMissileAdapter implements MissileStrategy {
  // TODO: Add necessary fields

  // TODO: Implement constructor

  @Override
  public Missile createMissile(int x, int y) {
    // TODO: Implement this method to:
    // 1. Create a new LaserBeam using the weapon
    // 2. Return an anonymous Missile class that:
    //    - Updates position based on beam movement
    //    - Uses beam dimensions for size
    //    - Handles collisions using beam's intersection check
    return null;
  }
}
