# Space Shooter - Strategy & Adapter Patterns Exercise

## Exercise Overview
This exercise focuses on implementing and understanding the Strategy and Adapter design patterns in a Space Shooter game. The game currently has four different types of missiles (Basic, Double, Targeting, and Laser) that demonstrate these patterns.

## Current Implementation
- **Basic Missile**: Simple straight-flying missile
- **Double Missile**: Fires two missiles side by side
- **Targeting Missile**: Tracks and follows the nearest enemy
- **Laser Missile**: Uses the Adapter pattern to adapt a third-party laser system

## Exercise Tasks

### Task 1: Implement a New Missile Strategy
Create a new missile strategy called `SpreadMissileStrategy` that:
1. Fires three missiles in a spread pattern (center, left, and right)
2. The side missiles should angle outward slightly
3. Implement the `MissileStrategy` interface
4. Add a new button to the UI to select this strategy

### Task 2: Create a New Adapter
Create a new adapter called `PlasmaMissileAdapter` that:
1. Adapts a third-party plasma weapon system
2. The plasma weapon should have different behavior from existing weapons
3. Implement the adapter pattern to make it work with our missile system
4. Add a new button to the UI to select this weapon

### Task 3: Code Review
1. Identify where the Strategy pattern is used in the code
2. Explain how the Adapter pattern is implemented
3. Discuss the benefits of using these patterns in this context

## Hints
- Look at the existing `MissileStrategy` implementations for guidance
- Study how the `LaserMissileAdapter` works with the third-party laser system
- The UI controls are in `GameView` and the strategy selection is in `GameController`

## Getting Started
1. Clone this repository
2. Study the existing code structure
3. Implement the new strategies and adapter
4. Test your implementation

## Submission
Submit your completed exercise with:
1. The new `SpreadMissileStrategy` implementation
2. The new `PlasmaMissileAdapter` implementation
3. Updated UI code
4. A brief explanation of your implementation choices