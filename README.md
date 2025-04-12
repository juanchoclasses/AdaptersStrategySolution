# Space Shooter Game - Design Patterns Demonstration

This project demonstrates the use of two important design patterns in a fun Space Shooter game:

1. **Strategy Pattern** - For different missile behaviors
2. **Adapter Pattern** - For integrating a different weapon system

## Project Structure

```
com.spaceshooter
├── game
│   └── SpaceShooterGame.java (Main class)
├── model
│   ├── Enemy.java
│   ├── GameModel.java
│   ├── Missile.java
│   └── Player.java
├── view
│   └── GameView.java
├── controller
│   └── GameController.java
├── strategy
│   ├── MissileStrategy.java (Interface)
│   ├── BasicMissileStrategy.java
│   ├── DoubleMissileStrategy.java
│   └── TargetingMissileStrategy.java
└── adapter
    ├── LaserWeapon.java (3rd-party/legacy system)
    └── LaserMissileAdapter.java (Adapter)
```

## Design Patterns Implementation

### Strategy Pattern

The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. In this game, different missile behaviors are implemented as strategies:

- `MissileStrategy` - Interface for all missile strategies
- `BasicMissileStrategy` - Fires a single missile
- `DoubleMissileStrategy` - Fires two missiles at once
- `TargetingMissileStrategy` - Fires a missile that targets enemies

### Adapter Pattern

The Adapter pattern converts the interface of a class into another interface clients expect. In this game, a `LaserWeapon` class represents a different weapon system (perhaps from a third-party library or legacy code) that doesn't match our missile strategy interface:

- `LaserWeapon` - A different weapon system with its own interface
- `LaserMissileAdapter` - Adapts the LaserWeapon to work with our MissileStrategy interface

## Game Controls

- **Left/Right Arrow Keys**: Move the player ship
- **Space**: Fire a missile
- **Strategy Buttons**: Change missile strategy
  - Basic: Fire a single missile
  - Double: Fire two missiles at once
  - Targeting: Fire a missile that targets enemies
  - Laser: Use the adapted laser weapon

## Learning Outcomes

This project helps students understand:

1. How to implement the Strategy pattern to encapsulate different behaviors
2. How to use the Adapter pattern to make incompatible interfaces work together
3. Game development concepts in Java with Swing
4. Object-oriented design principles

## Running the Game

To run the game, compile and run the `SpaceShooterGame` class:

```
javac com/spaceshooter/game/SpaceShooterGame.java
java com.spaceshooter.game.SpaceShooterGame
```

Or import the project into your favorite IDE and run it from there.