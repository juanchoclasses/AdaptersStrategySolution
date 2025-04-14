package com.spaceshooter.strategy;

import com.spaceshooter.model.Missile;

public class BasicMissileStrategy implements MissileStrategy {
  @Override
  public Missile createMissile(int x, int y) {
    return new Missile(x, y, true);
  }
}
