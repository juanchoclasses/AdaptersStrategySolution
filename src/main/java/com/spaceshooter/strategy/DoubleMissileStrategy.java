package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;
import com.spaceshooter.model.GameModel;
import java.util.ArrayList;
import java.util.List;

public class DoubleMissileStrategy implements MissileStrategy {
  private GameModel model;

  public DoubleMissileStrategy(GameModel model) {
    this.model = model;
  }

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
