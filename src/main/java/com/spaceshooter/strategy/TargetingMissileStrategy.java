package com.spaceshooter.strategy;

import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.Missile;
import com.spaceshooter.model.TargetingMissile;
import java.util.List;
import java.util.Random;

/**
 * A missile strategy that creates missiles that target the nearest enemy.
 * This strategy implements the MissileStrategy interface and creates TargetingMissile
 * instances that will track and follow the closest enemy. If no enemies are available,
 * it falls back to creating a basic straight-flying missile.
 */
public class TargetingMissileStrategy implements MissileStrategy {
  private final List<Enemy> enemies;

  /**
   * Constructs a new TargetingMissileStrategy with the specified list of enemies.
   *
   * @param enemies the list of enemies that the missiles can target
   */
  public TargetingMissileStrategy(List<Enemy> enemies) {
    this.enemies = enemies;
    Random random = new Random();
  }

  /**
   * Creates a missile that targets the nearest enemy.
   * If there are enemies available, it finds the nearest one and creates a TargetingMissile
   * that will track it. If no enemies are available, it creates a basic straight-flying missile.
   *
   * @param x the x-coordinate where the missile will be created
   * @param y the y-coordinate where the missile will be created
   * @return a TargetingMissile that tracks the nearest enemy, or a basic Missile if no enemies are available
   */
  @Override
  public Missile createMissile(int x, int y) {
    if (!enemies.isEmpty()) {
      // Find the nearest enemy
      Enemy nearestEnemy = findNearestEnemy(x, y);
      if (nearestEnemy != null) {
        // Create a targeting missile that will track the nearest enemy
        return new TargetingMissile(x, y, nearestEnemy);
      }
    }
    // If no enemies or something went wrong, create a basic missile
    return new Missile(x, y, true);
  }

  /**
   * Finds the enemy that is closest to the specified position.
   *
   * @param x the x-coordinate to measure distance from
   * @param y the y-coordinate to measure distance from
   * @return the nearest enemy, or null if no enemies are available
   */
  private Enemy findNearestEnemy(int x, int y) {
    Enemy nearest = null;
    double minDistance = Double.MAX_VALUE;

    for (Enemy enemy : enemies) {
      double distance = calculateDistance(x, y, enemy.getX(), enemy.getY());
      if (distance < minDistance) {
        minDistance = distance;
        nearest = enemy;
      }
    }

    return nearest;
  }

  /**
   * Calculates the Euclidean distance between two points.
   *
   * @param x1 the x-coordinate of the first point
   * @param y1 the y-coordinate of the first point
   * @param x2 the x-coordinate of the second point
   * @param y2 the y-coordinate of the second point
   * @return the distance between the two points
   */
  private double calculateDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }
}
