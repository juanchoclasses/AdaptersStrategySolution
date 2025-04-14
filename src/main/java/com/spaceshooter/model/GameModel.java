package com.spaceshooter.model;

import com.spaceshooter.strategy.MissileStrategy;
import com.spaceshooter.strategy.BasicMissileStrategy;
import com.spaceshooter.strategy.DoubleMissileStrategy;
import com.spaceshooter.strategy.TargetingMissileStrategy;
import com.spaceshooter.adapter.LaserMissileAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main game model that manages the state and logic of the Space Shooter game.
 * This class handles:
 * - Player and enemy management
 * - Missile creation and tracking
 * - Collision detection
 * - Score tracking
 * - Game state (game over, debug mode, god mode)
 * - Weapon strategy management
 */
public class GameModel {
  // Live missile limits
  public static final int BASIC_MISSILE_LIMIT = Integer.MAX_VALUE;  // Infinite
  public static final int DOUBLE_MISSILE_LIMIT = Integer.MAX_VALUE;  // Infinite
  public static final int TARGETING_MISSILE_LIMIT = 1;  // Changed from 4 to 1
  public static final int LASER_MISSILE_LIMIT = 2;  // Changed from 30 to 2
  private static final int ENEMY_MOVE_INTERVAL = 30; // Move every 30 updates
  private static final int WIDTH = 600; // Game width
  private static final int ENEMY_MOVE_DOWN_AMOUNT = 50;
  private Player player;
  private List<Enemy> enemies;
  private List<Missile> missiles;
  private MissileStrategy missileStrategy;
  private Random random;
  private int score;
  private boolean gameOver = false;
  private boolean debugMode = false;
  private boolean godMode = false;
  private int leftmostX = 0;
  private int rightmostX = 0;
  // Remaining weapon counts
  private int remainingTargetingMissiles = 2;  // Start with 2 targeting missiles
  private int remainingLaserMissiles = 30;  // Start with 30 laser missiles
  // Current live missile counts
  private int basicMissilesLive;
  private int doubleMissilesLive;
  private int targetingMissilesLive;
  private int laserMissilesLive;
  private boolean movingRight = true;
  private int moveCounter = 0;
  // Amount to move down when enemies hit the edge
  private int enemyDirection = 1; // Direction of enemy movement
  private int dropCount = 0; // Track how many times enemies have dropped

  /**
   * Constructs a new GameModel with initial game state.
   * Initializes the player, enemies, missiles, and sets up the default missile strategy.
   */
  public GameModel() {
    this.player = new Player(300, 600);
    this.enemies = new ArrayList<>();
    this.missiles = new ArrayList<>();
    this.missileStrategy = new BasicMissileStrategy();
    this.random = new Random();
    this.score = 0;

    // Initialize live missile counts
    resetLiveMissileCounts();

    initializeEnemies();
  }

  private void resetLiveMissileCounts() {
    basicMissilesLive = 0;
    doubleMissilesLive = 0;
    targetingMissilesLive = 0;
    laserMissilesLive = 0;
  }

  private void initializeEnemies() {
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 8; col++) {
        enemies.add(new Enemy(50 + col * 70, 150 + row * 60, WIDTH));
      }
    }
  }

  /**
   * Updates the game state.
   * This includes:
   * - Updating missile positions and removing off-screen missiles
   * - Checking for collisions
   * - Updating enemy speeds and positions
   */
  public void update() {
    if (gameOver) {
      return;
    }

    // Update missiles
    List<Missile> missilesToRemove = new ArrayList<>();
    for (Missile missile : missiles) {
      missile.update();
      if (missile.getY() < 0) {
        missilesToRemove.add(missile);
        decrementLiveMissileCount(missile);
      }
    }
    missiles.removeAll(missilesToRemove);

    // Check for collisions
    checkCollisions();

    // Update enemy speeds every frame
    updateEnemySpeeds();

    // Move enemies
    updateEnemies();

    // Enemy shooting
    if (!enemies.isEmpty() && random.nextInt(100) < 2) {
      int index = random.nextInt(enemies.size());
      Enemy enemy = enemies.get(index);
      Missile enemyMissile = new Missile(enemy.getX() + 15, enemy.getY() + 30, false);
      missiles.add(enemyMissile);
    }

    // Check for game over
    if (enemies.isEmpty()) {
      gameOver = true;
    }
  }

  private void decrementLiveMissileCount(Missile missile) {
    if (missile instanceof TargetingMissile) {
      targetingMissilesLive--;
    } else if (missileStrategy instanceof DoubleMissileStrategy) {
      doubleMissilesLive--;
    } else if (missileStrategy instanceof LaserMissileAdapter) {
      laserMissilesLive--;
    } else {
      basicMissilesLive--;
    }
  }

  private void checkCollisions() {
    List<Missile> missilesToRemove = new ArrayList<>();
    List<Enemy> enemiesToRemove = new ArrayList<>();

    // Check if any enemy has moved below the player's ship
    for (Enemy enemy : enemies) {
      if (enemy.getY() + enemy.getHeight() >= player.getY()) {
        gameOver = true;
        return;
      }
    }

    for (Missile missile : missiles) {
      if (missile.isPlayerMissile()) {
        for (Enemy enemy : enemies) {
          if (missile.collidesWith(enemy)) {
            missilesToRemove.add(missile);
            // In god mode, missiles instantly kill aliens
            if (godMode) {
              enemiesToRemove.add(enemy);
              score += 100;
            } else {
              // Calculate damage based on missile type
              int damage = 20; // Basic missile damage
              if (missileStrategy instanceof LaserMissileAdapter) {
                damage = 40; // Laser does more damage
              } else if (missileStrategy instanceof TargetingMissileStrategy) {
                damage = 75; // Targeting missile does the most damage
              }
              enemy.takeDamage(damage);

              if (enemy.isDestroyed()) {
                enemiesToRemove.add(enemy);
                // Add more score for laser hits
                if (missileStrategy instanceof LaserMissileAdapter) {
                  score += 200; // Double points for laser hits
                } else {
                  score += 100;
                }
              }
            }
            decrementLiveMissileCount(missile);
            break;
          }
        }
      } else {
        if (missile.collidesWith(player) && !missile.isPlayerMissile()) {
          missilesToRemove.add(missile);
          player.takeDamage(
              godMode ? 1 : 20); // Enemy missiles do 1 damage in god mode, 20 normally
          if (player.isDestroyed()) {
            gameOver = true;
          }
        }
      }
    }

    missiles.removeAll(missilesToRemove);
    enemies.removeAll(enemiesToRemove);

    // Check for game over
    if (enemies.isEmpty()) {
      gameOver = true;
    }
  }

  private void updateEnemySpeeds() {
    int totalEnemies = enemies.size();
    for (Enemy enemy : enemies) {
      enemy.updateSpeed(totalEnemies, dropCount);
    }
  }

  private void updateEnemies() {
    moveCounter++;
    if (moveCounter >= ENEMY_MOVE_INTERVAL) {
      moveCounter = 0;

      // Find the leftmost and rightmost enemies
      leftmostX = Integer.MAX_VALUE;
      rightmostX = Integer.MIN_VALUE;
      for (Enemy enemy : enemies) {
        leftmostX = Math.min(leftmostX, enemy.getX());
        rightmostX = Math.max(rightmostX, enemy.getX() + enemy.getWidth());
      }

      // Check if we need to change direction and move down
      if (enemyDirection > 0 && rightmostX >= WIDTH) {
        enemyDirection = -1;
        for (Enemy enemy : enemies) {
          enemy.moveDown();
        }
        dropCount++; // Increment drop count
        updateEnemySpeeds(); // Update speeds after drop
      } else if (enemyDirection < 0 && leftmostX <= 0) {
        enemyDirection = 1;
        for (Enemy enemy : enemies) {
          enemy.moveDown();
        }
        dropCount++; // Increment drop count
        updateEnemySpeeds(); // Update speeds after drop
      }

      // Move all enemies horizontally
      for (Enemy enemy : enemies) {
        if (enemyDirection > 0) {
          enemy.moveRight();
        } else {
          enemy.moveLeft();
        }
      }
    }
  }

  /**
   * Fires a missile from the player's current position if allowed.
   * The type of missile fired depends on the current missile strategy.
   */
  public void fireMissile() {
    if (!gameOver) {
      if (canFireMissile()) {
        Missile missile = missileStrategy.createMissile(player.getX() + 20, player.getY() - 10);
        if (missile != null) {
          missiles.add(missile);
          incrementLiveMissileCount();
        }
      }
    }
  }

  private boolean canFireMissile() {
    if (missileStrategy instanceof BasicMissileStrategy) {
      return basicMissilesLive < BASIC_MISSILE_LIMIT;
    } else if (missileStrategy instanceof DoubleMissileStrategy) {
      return doubleMissilesLive < DOUBLE_MISSILE_LIMIT;
    } else if (missileStrategy instanceof TargetingMissileStrategy) {
      return targetingMissilesLive < TARGETING_MISSILE_LIMIT && remainingTargetingMissiles > 0;
    } else if (missileStrategy instanceof LaserMissileAdapter) {
      return laserMissilesLive < LASER_MISSILE_LIMIT && remainingLaserMissiles > 0;
    }
    return false;
  }

  private void incrementLiveMissileCount() {
    if (missileStrategy instanceof BasicMissileStrategy) {
      basicMissilesLive++;
    } else if (missileStrategy instanceof DoubleMissileStrategy) {
      doubleMissilesLive++;
    } else if (missileStrategy instanceof TargetingMissileStrategy) {
      targetingMissilesLive++;
      remainingTargetingMissiles--;
    } else if (missileStrategy instanceof LaserMissileAdapter) {
      laserMissilesLive++;
      remainingLaserMissiles--;
    }
  }

  /**
   * Adds a missile to the game.
   *
   * @param missile the missile to add
   */
  public void addMissile(Missile missile) {
    if (!gameOver) {
      missiles.add(missile);
    }
  }

  /**
   * Sets the current missile strategy.
   *
   * @param strategy the new missile strategy to use
   */
  public void setMissileStrategy(MissileStrategy strategy) {
    this.missileStrategy = strategy;
  }

  /**
   * Returns the player object.
   *
   * @return the player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the list of enemies.
   *
   * @return the list of enemies
   */
  public List<Enemy> getEnemies() {
    return enemies;
  }

  /**
   * Returns the list of active missiles.
   *
   * @return the list of missiles
   */
  public List<Missile> getMissiles() {
    return missiles;
  }

  /**
   * Returns the current game score.
   *
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Returns the number of live basic missiles.
   *
   * @return the number of live basic missiles
   */
  public int getBasicMissilesLive() {
    return basicMissilesLive;
  }

  /**
   * Returns the number of live double missiles.
   *
   * @return the number of live double missiles
   */
  public int getDoubleMissilesLive() {
    return doubleMissilesLive;
  }

  /**
   * Returns the number of live targeting missiles.
   *
   * @return the number of live targeting missiles
   */
  public int getTargetingMissilesLive() {
    return targetingMissilesLive;
  }

  /**
   * Returns the number of live laser missiles.
   *
   * @return the number of live laser missiles
   */
  public int getLaserMissilesLive() {
    return laserMissilesLive;
  }

  /**
   * Toggles the debug mode state.
   */
  public void toggleDebugMode() {
    debugMode = !debugMode;
  }

  /**
   * Checks if debug mode is enabled.
   *
   * @return true if debug mode is enabled, false otherwise
   */
  public boolean isDebugMode() {
    return debugMode;
  }

  /**
   * Returns the x-coordinate of the leftmost enemy.
   *
   * @return the leftmost x-coordinate
   */
  public int getLeftmostX() {
    return leftmostX;
  }

  /**
   * Returns the x-coordinate of the rightmost enemy.
   *
   * @return the rightmost x-coordinate
   */
  public int getRightmostX() {
    return rightmostX;
  }

  /**
   * Returns the current direction of enemy movement.
   *
   * @return 1 for right, -1 for left
   */
  public int getEnemyDirection() {
    return enemyDirection;
  }

  /**
   * Returns the number of times enemies have moved down.
   *
   * @return the drop count
   */
  public int getDropCount() {
    return dropCount;
  }

  /**
   * Toggles the god mode state.
   */
  public void toggleGodMode() {
    godMode = !godMode;
  }

  /**
   * Checks if god mode is enabled.
   *
   * @return true if god mode is enabled, false otherwise
   */
  public boolean isGodMode() {
    return godMode;
  }

  /**
   * Returns the number of remaining targeting missiles.
   *
   * @return the number of remaining targeting missiles
   */
  public int getRemainingTargetingMissiles() {
    return remainingTargetingMissiles;
  }

  /**
   * Returns the number of remaining laser missiles.
   *
   * @return the number of remaining laser missiles
   */
  public int getRemainingLaserMissiles() {
    return remainingLaserMissiles;
  }
}
