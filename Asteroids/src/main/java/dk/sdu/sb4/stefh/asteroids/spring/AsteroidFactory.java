/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids.spring;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author stefh
 */
public class AsteroidFactory implements IAsteroidsFactory {
    
    private Random r;
    
    public AsteroidFactory() {
        System.out.println("Asteroids factory ready!");
        r = new Random();
    }
    
    @Override
    public Entity getAsteroid(int size) {
        switch(size) {
            case SMALL_ASTEROID:
                return getSmallAsteroid();
            case MEDIUM_ASTEROID:
                return getMediumAsteroid();
            case LARGE_ASTEROID:
                return getLargeAsteroid();
            default:
                return null;
        }
    }
    
    private Entity getSmallAsteroid() {
        Entity asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROIDS);
        asteroid.setDx(r.nextFloat()*2-1f);
        asteroid.setDy(r.nextFloat()*2-1f);
        float[] points = new float[8];
        asteroid.setHealth(1);
        asteroid.setMaxSpeed(100);
        asteroid.setRotationSpeed(r.nextInt(7)-3);
        asteroid.setSize(12, 12);
        for (int j=0; j<points.length; j++) {
            points[j] = r.nextInt(4)+3;
        }
        asteroid.setAsteroidDists(points);
        return asteroid;
    }
    
    private Entity getMediumAsteroid() {
        Entity asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROIDS);
        asteroid.setDx(r.nextFloat()*2-1f);
        asteroid.setDy(r.nextFloat()*2-1f);
        float[] points = new float[10];
        asteroid.setHealth(2);
        asteroid.setMaxSpeed(75);
        asteroid.setRotationSpeed(r.nextInt(5)-2);
        asteroid.setSize(20, 20);
        for (int j=0; j<points.length; j++) {
            points[j] = r.nextInt(6)+5;
        }
        asteroid.setAsteroidDists(points);
        return asteroid;
    }
    
    private Entity getLargeAsteroid() {
        Entity asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROIDS);
        asteroid.setDx(r.nextFloat()*2-1f);
        asteroid.setDy(r.nextFloat()*2-1f);
        float[] points = new float[12];
        asteroid.setHealth(3);
        asteroid.setMaxSpeed(20);
        asteroid.setRotationSpeed(r.nextInt(3)-1);
        asteroid.setSize(40,40);
        for (int j=0; j<points.length; j++) {
            points[j] = r.nextInt(11)+10;
        }
        asteroid.setAsteroidDists(points);
        return asteroid;
    }
}
