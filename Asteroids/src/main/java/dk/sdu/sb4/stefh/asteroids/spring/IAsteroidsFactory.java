/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids.spring;

import dk.sdu.sb4.stefh.common.data.Entity;

/**
 *
 * @author stefh
 */
public interface IAsteroidsFactory {
    int SMALL_ASTEROID = 1;
    int MEDIUM_ASTEROID = 2;
    int LARGE_ASTEROID = 3;
    
    public Entity getAsteroid(int size);
}
