/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids;

import dk.sdu.sb4.stefh.asteroids.spring.AsteroidsFactoryConfig;
import dk.sdu.sb4.stefh.asteroids.spring.IAsteroidsFactory;
import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author stefh
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class AsteroidControlSystem implements IEntityProcessingService {
    
    private List<Entity> asteroids = new ArrayList<>();
    private Random r = new Random();
    private ApplicationContext spring;
    private IAsteroidsFactory factory;
    
    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if(spring == null) {
            spring = new AnnotationConfigApplicationContext(AsteroidsFactoryConfig.class);
        }
        factory = (IAsteroidsFactory) spring.getBean("asteroidsFactory");
        if(asteroids.size() <= 0) {
            spawnAsteroids(gameData, world);
        }
        if(entity.getType() == EntityType.ASTEROIDS) {
            if(entity.isDestroyed()) {
                destroyAsteroid(world, entity);
            }
            update(gameData, entity);
            setShape(entity);
        }
    }
    
    private void update(GameData g, Entity a) {
        a.setRadians(a.getRadians() + a.getRotationSpeed() * g.getDelta());
        
        float x = a.getX() + a.getDx() * a.getMaxSpeed() * g.getDelta();
        float y = a.getY() + a.getDy() * a.getMaxSpeed() * g.getDelta();
        a.setPosition(x, y);
    }
    
    private void spawnAsteroids(GameData game, Map<String, Entity> world) {
        for(int i=0; i<10; i++) {
            int size = r.nextInt(10);
            float[] points;
            Entity asteroid;
            if(size < 2) {         // Small asteroid
                asteroid = factory.getAsteroid(IAsteroidsFactory.SMALL_ASTEROID);
            } else if(size < 5) {  // Medium asteroid
                asteroid = factory.getAsteroid(IAsteroidsFactory.MEDIUM_ASTEROID);
            } else {                // Big asteroid
                asteroid = factory.getAsteroid(IAsteroidsFactory.LARGE_ASTEROID);
            }
            asteroid.setPosition(r.nextInt(game.getDisplayWidth()), r.nextInt(game.getDisplayHeight()));
            asteroids.add(asteroid);
            world.put(asteroid.getID(), asteroid);
        }
    }
    
    private void destroyAsteroid(Map<String, Entity> world, Entity asteroid) {
        world.remove(asteroid.getID());
        asteroids.remove(asteroid);
        if(asteroid.getHealth() > 1) {
            for(int i=0; i<2; i++) {
                Entity newAsteroid;
                if(asteroid.getHealth() == 3) {
                    newAsteroid = factory.getAsteroid(IAsteroidsFactory.MEDIUM_ASTEROID);
                } else {
                    newAsteroid = factory.getAsteroid(IAsteroidsFactory.SMALL_ASTEROID);
                }
                newAsteroid.setPosition(asteroid.getX(), asteroid.getY());
                world.put(newAsteroid.getID(), newAsteroid);
                asteroids.add(newAsteroid);
            }
        }
    }
    
    private void setShape(Entity asteroid) {
        float[] dists = asteroid.getAsteroidDists();
        float[] shapeX = new float[dists.length];
        float[] shapeY = new float[dists.length];
        float angle = 0;
        for(int i = 0; i < dists.length; i++) {
            shapeX[i] = asteroid.getX() + (float)Math.cos(angle + asteroid.getRadians()) * dists[i];
            shapeY[i] = asteroid.getY() + (float)Math.sin(angle + asteroid.getRadians()) * dists[i];
            angle += 2 * 3.1415f / dists.length;
        }
        asteroid.setShapeX(shapeX);
        asteroid.setShapeY(shapeY);
    }  
}
