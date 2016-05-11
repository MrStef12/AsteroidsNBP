/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author stefh
 */
public class AsteroidControlSystemTest {
    
    private IEntityProcessingService sys;
    private Map<String, Entity> world;
    private GameData gameData;
    private Entity player;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sys = new AsteroidControlSystem();
        world = new HashMap<>();
        gameData = new GameData();
        player = new Entity();
        gameData.setDisplayHeight(100);
        gameData.setDisplayWidth(100);
        gameData.setDelta(0.2f);
        player.setType(EntityType.PLAYER);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class AsteroidControlSystem.
     */
    @Test
    public void testSpawnOfNewAsteroids() {
        sys.process(gameData, world, player);
        assertTrue(!world.isEmpty());
    }
    
    @Test
    public void testDestructionOfAsteroid() {
        sys.process(gameData, world, player);
        Entity asteroid = getAsteroid();
        asteroid.setHealth(3);
        asteroid.setDestroyed(true);
        int numEntities = world.size();
        int asteroidWith3Health = countAsteroidsWithHealth(3);
        int asteroidWith2Health = countAsteroidsWithHealth(2);
        sys.process(gameData, world, asteroid);
        
        // Assert that there are now one more asteroid
        assertEquals(world.size(), 1+numEntities); 
        // Assert that the asteroid has been deleted
        assertNull(world.get(asteroid.getID())); 
        // Assert that the amount of asteroids with health 3 are one less
        // than before the update
        assertEquals(countAsteroidsWithHealth(3), asteroidWith3Health-1);
        // Assert that the amount of asteroids with health 2 are two more
        // than before the update
        assertEquals(countAsteroidsWithHealth(2), asteroidWith2Health+2);
    }
    
    private int countAsteroidsWithHealth(int health) {
        int count = 0;
        for(Entity e : world.values()) {
            if(e.getHealth() == health)
                count++;
        }
        return count;
    }
    
    private Entity getAsteroid() {
        for(Entity e : world.values()) {
            if(e.getType() == EntityType.ASTEROIDS) {
                return e;
            }
        } 
        return null;
    }
    
}
