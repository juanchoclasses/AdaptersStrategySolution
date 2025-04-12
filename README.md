# Space Shooter - Strategy & Adapter Patterns Exercise

## Understanding the Missile System

The game's missile system consists of two main components:

1. **Missile Class**: The base class for all missiles in the game
   - Tracks position (x, y)
   - Handles movement (speed)
   - Manages collision detection
   - Can be either player-fired or enemy-fired

2. **TargetingMissile Class**: A specialized missile that can track enemies
   - Extends the base Missile class
   - Maintains a target enemy
   - Calculates and updates its direction to follow the target
   - Uses trigonometry to smoothly turn towards its target

## The Strategy Pattern in Action

The Strategy pattern is used to define different ways of creating and firing missiles. Here's how it works:

1. **MissileStrategy Interface**: Defines the contract for all missile strategies
   - Single method: `createMissile(x, y)`
   - Each strategy implements this method differently

2. **Concrete Strategies**:
   - **BasicMissileStrategy**: Creates a single straight-flying missile
   - **DoubleMissileStrategy**: Creates two missiles side by side
   - **TargetingMissileStrategy**: Creates a missile that tracks the nearest enemy

3. **How it's Used**:
   - The `GameModel` maintains a current missile strategy
   - When the player fires, it uses the current strategy to create missiles
   - The strategy can be changed at runtime (using keyboard keys X, C, V)

## Missile Updates in Flight

After missiles are created, they need to be updated every game tick. Here's how it works:

1. **Basic Missiles**:
   - Move in a straight line (up for player missiles, down for enemy missiles)
   - Speed is constant
   - Simple position update: y += speed

2. **Targeting Missiles**:
   - More complex movement using trigonometry
   - Process:
     1. Calculate direction to target (using atan2)
     2. Determine angle difference between current and desired direction
     3. Smoothly turn towards target (using a turn rate)
     4. Move in the new direction
   - Key features:
     - Maintains current direction in radians
     - Uses turn rate to limit how quickly it can change direction
     - If target is destroyed, reverts to straight movement

3. **Update Process**:
   - `GameModel.update()` calls `missile.update()` for each missile
   - Missiles that go off-screen are removed
   - Collision detection happens after movement updates

## Detailed Missile Position Updates

The missile position updates are handled through several layers of the game system:

1. **Main Update Loop**:
   - In `GameModel.update()`, there's a loop that updates all missiles:
   ```java
   for (Missile missile : missiles) {
       missile.update();
       if (missile.getY() < 0) {
           missilesToRemove.add(missile);
           decrementLiveMissileCount(missile);
       }
   }
   ```

2. **Basic Missile Updates**:
   - In `Missile.java`, the base class has a simple update method:
   ```java
   public void update() {
       y += speed;
   }
   ```
   - Player missiles have speed = -10 (moving up)
   - Enemy missiles have speed = 5 (moving down)

3. **Targeting Missile Updates**:
   - In `TargetingMissile.java`, the update is more complex:
   ```java
   @Override
   public void update() {
       if (target != null) {
           // Calculate direction to target
           double targetX = target.getX() + target.getWidth() / 2;
           double targetY = target.getY() + target.getHeight() / 2;
           double desiredDirection = Math.atan2(targetY - y, targetX - x);
           
           // Smoothly turn towards target
           double angleDiff = desiredDirection - currentDirection;
           // Normalize angle difference to [-PI, PI]
           while (angleDiff > Math.PI) angleDiff -= 2 * Math.PI;
           while (angleDiff < -Math.PI) angleDiff += 2 * Math.PI;
           
           // Turn towards target
           currentDirection += Math.signum(angleDiff) * Math.min(Math.abs(angleDiff), TURN_RATE);
           
           // Move in current direction
           int speed = 5;
           x += Math.cos(currentDirection) * speed;
           y += Math.sin(currentDirection) * speed;
       } else {
           // If no target, move upward like a normal missile
           super.update();
       }
   }
   ```

4. **Laser Missile Updates**:
   - In `LaserMissileAdapter.java`, the update is handled through an anonymous class:
   ```java
   return new Missile(x, y, true) {
       @Override
       public void update() {
           if (currentBeam != null) {
               currentBeam.move();
               x = currentBeam.getSourceX();
               y = currentBeam.getSourceY();
           }
       }
   };
   ```

The update process happens every game tick (every 20ms as defined in `GameController` with `DELAY = 20`). After updating positions, the game checks for collisions and removes missiles that go off-screen.

## Exercise 1: Understanding the Strategy Pattern

### UML Diagram for Missile Strategy
```
+------------------+       +------------------------+
| <<interface>>    |       | Missile               |
| MissileStrategy  |       +------------------------+
+------------------+       | - x: int              |
| +createMissile() |       | - y: int              |
+------------------+       | - playerMissile: bool |
         ^                 | - speed: int          |
         |                 +------------------------+
         |                           ^
         |                           |
+------------------+       +------------------------+
| BasicMissile     |       | TargetingMissile      |
| Strategy         |       +------------------------+
+------------------+       | - target: Enemy       |
| +createMissile() |       | - currentDirection    |
+------------------+       +------------------------+
         ^
         |
+------------------+
| DoubleMissile    |
| Strategy         |
+------------------+
| +createMissile() |
+------------------+
```

### Instructions
1. Study the following files to understand how the Strategy pattern is implemented:
   - `src/main/java/com/spaceshooter/strategy/MissileStrategy.java` (Interface)
   - `src/main/java/com/spaceshooter/strategy/BasicMissileStrategy.java`
   - `src/main/java/com/spaceshooter/strategy/DoubleMissileStrategy.java`
   - `src/main/java/com/spaceshooter/strategy/TargetingMissileStrategy.java`
   - `src/main/java/com/spaceshooter/model/Missile.java`
   - `src/main/java/com/spaceshooter/model/TargetingMissile.java`

2. Pay attention to:
   - How each strategy implements the `createMissile()` method
   - How the `DoubleMissileStrategy` creates two missiles
   - How the `TargetingMissileStrategy` tracks and follows enemies
   - The relationship between the `Missile` base class and `TargetingMissile` subclass 