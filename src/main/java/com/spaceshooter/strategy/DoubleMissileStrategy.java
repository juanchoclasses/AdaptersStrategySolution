package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;
import com.spaceshooter.model.GameModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A missile strategy that creates two missiles side by side.
 * This strategy implements the MissileStrategy interface and creates a pair of missiles
 * that are offset horizontally from the firing position. Both missiles are added
 * directly to the game model.
 */
public class DoubleMissileStrategy implements MissileStrategy {
  private GameModel model;

  /**
   * Constructs a new DoubleMissileStrategy with the specified game model.
   *
   * @param model the game model to which the missiles will be added
   */
  public DoubleMissileStrategy(GameModel model) {
    this.model = model;
  }

  /**
   * Creates two missiles side by side at the specified position.
   * The missiles are created with a horizontal offset of 10 units from the center.
   * Both missiles are added directly to the game model, and this method returns null
   * since the missiles are already added to the model.
   *
   * @param x the x-coordinate of the center position where the missiles will be created
   * @param y the y-coordinate where the missiles will be created
   * @return null, since the missiles are added directly to the game model
   */
  @Override
  public Missile createMissile(int x, int y) {
    // Create two missiles side by side
    Missile leftMissile = new Missile(x - 10, y, true);
    Missile rightMissile = new Missile(x + 10, y, true);

    // Add both missiles to the game model's list
    model.addMissile(leftMissile);
    model.addMissile(rightMissile);

    // Return null since we've already added both missiles
    return null;
  }
}
