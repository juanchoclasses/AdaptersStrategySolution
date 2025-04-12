package com.spaceshooter.adapter;

/**
 * LaserWeapon is a completely different system that doesn't match our strategy interface.
 * It represents a third-party or legacy system that we want to integrate.
 */
public class LaserWeapon {
    
    /**
     * Fires a laser beam from the specified coordinates.
     * 
     * @param sourceX The x-coordinate of the source of the laser.
     * @param sourceY The y-coordinate of the source of the laser.
     * @return A LaserBeam object representing the fired laser beam.
     */
    public LaserBeam fireLaser(int sourceX, int sourceY) {
        return new LaserBeam(sourceX, sourceY);
    }
} 