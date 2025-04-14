package com.spaceshooter.strategy;

import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.Missile;
import com.spaceshooter.model.TargetingMissile;
import java.util.List;
import java.util.Random;

/**
 * This strategy creates a missile that targets the nearest enemy.
 */
public class TargetingMissileStrategy implements MissileStrategy {
  private final List<Enemy> enemies;

  public TargetingMissileStrategy(List<Enemy> enemies) {
    this.enemies = enemies;
    Random random = new Random();
  }

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

  private double calculateDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }
}
