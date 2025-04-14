package com.spaceshooter.adapter;

import com.spaceshooter.model.Missile;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.Player;
import com.spaceshooter.strategy.MissileStrategy;

/**
 * Adapter that allows the LaserWeapon to be used as a MissileStrategy.
 * This demonstrates the Adapter pattern by converting the LaserWeapon interface
 * to match the MissileStrategy interface.
 *
 * The laser weapon has the following unique properties:
 * 1. Creates a laser beam that moves upward
 * 2. Has fixed width and height
 * 3. Can check for collisions with enemies
 */
public class LaserMissileAdapter implements MissileStrategy {
  private final LaserWeapon laserWeapon;

  /**
   * Constructs a new LaserMissileAdapter with the specified laser weapon.
   *
   * @param laserWeapon the laser weapon to adapt
   */
  public LaserMissileAdapter() {
    this.laserWeapon = new LaserWeapon();
  }

  /**
   * Creates a new missile that wraps a laser beam.
   * The missile will:
   * - Update position based on beam movement
   * - Use beam dimensions for size
   * - Handle collisions using beam's intersection check
   *
   * @param initialX the x-coordinate where the missile should be created
   * @param initialY the y-coordinate where the missile should be created
   * @return a new missile that wraps a laser beam
   */
  @Override
  public Missile createMissile(int initialX, int initialY) {
    LaserBeam beam = laserWeapon.fireLaser(initialX, initialY);
    return new Missile(initialX, initialY, true) {
      @Override
      public void update() {
        beam.move();
        this.x = beam.getSourceX();
        this.y = beam.getSourceY();
      }

      @Override
      public boolean collidesWith(Player player) {
        return beam.intersectsWith(player.getX(), player.getY(), player.getWidth(),
            player.getHeight());
      }

      @Override
      public boolean collidesWith(Enemy enemy) {
        return beam.intersectsWith(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
      }

      @Override
      public int getWidth() {
        return beam.getWidth();
      }

      @Override
      public int getHeight() {
        return beam.getHeight();
      }
    };
  }
}
