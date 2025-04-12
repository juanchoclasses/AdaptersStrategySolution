package com.spaceshooter.adapter;

import com.spaceshooter.model.Missile;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.strategy.MissileStrategy;

/**
 * Adapter that allows the LaserWeapon to be used as a MissileStrategy.
 * This demonstrates the Adapter pattern by converting the LaserWeapon interface
 * to match the MissileStrategy interface.
 */
public class LaserMissileAdapter implements MissileStrategy {
    private LaserWeapon laserWeapon;
    private LaserBeam currentBeam;
    
    public LaserMissileAdapter() {
        this.laserWeapon = new LaserWeapon();
    }
    
    @Override
    public Missile createMissile(int x, int y) {
        // Use the LaserWeapon to create a LaserBeam
        currentBeam = laserWeapon.fireLaser(x, y);
        
        // Create an adapter missile that will delegate to the laser beam
        return new Missile(x, y, true) {
            @Override
            public void update() {
                if (currentBeam != null) {
                    currentBeam.move();
                    x = currentBeam.getSourceX();
                    y = currentBeam.getSourceY();
                }
            }
            
            @Override
            public int getWidth() {
                return currentBeam != null ? currentBeam.getWidth() : super.getWidth();
            }
            
            @Override
            public int getHeight() {
                return currentBeam != null ? currentBeam.getHeight() : super.getHeight();
            }
            
            @Override
            public boolean collidesWith(Enemy enemy) {
                return currentBeam != null && 
                       currentBeam.intersectsWith(enemy.getX(), enemy.getY(), 
                                                enemy.getWidth(), enemy.getHeight());
            }
        };
    }
    
    public LaserBeam getCurrentBeam() {
        return currentBeam;
    }
}
