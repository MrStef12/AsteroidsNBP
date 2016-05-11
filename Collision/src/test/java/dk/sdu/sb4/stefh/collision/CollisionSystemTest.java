/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.collision;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
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
public class CollisionSystemTest {
    
    private CollisionSystem colsys;
    Map<String, Entity> world;
    private Entity player;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        colsys = new CollisionSystem();
        world = new HashMap<>();
        player = new Entity();
        player.setType(EntityType.PLAYER);
        player.setSize(16, 16);
        player.setPosition(0, 0);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of checkPlayer method, of class CollisionSystem.
     */
    @Test
    public void testCheckPlayer() {
        System.out.println("checkPlayer");
        Entity asteroid = new Entity();
        asteroid.setType(EntityType.ASTEROIDS);
        asteroid.setSize(16, 16);
        asteroid.setPosition(50, 50);
        world.put(asteroid.getID(), asteroid);
        
        colsys.checkPlayer(world, player);
        assertFalse(player.isDestroyed());
        
        asteroid.setPosition(0, 0);
        colsys.checkPlayer(world, player);
        assertTrue(player.isDestroyed());
    }

    /**
     * Test of checkBullet method, of class CollisionSystem.
     */
    @Test
    public void testCheckBullet() {
        System.out.println("checkBullet");
        Entity bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setSize(16, 16);
        bullet.setPosition(0, 0);
        world.put(player.getID(), player);
        
        bullet.setEnemyBullet(false);
        colsys.checkBullet(world, bullet);
        assertFalse(bullet.isDestroyed());
        
        bullet.setEnemyBullet(true);
        colsys.checkBullet(world, bullet);
        assertTrue(bullet.isDestroyed());
    }

    /**
     * Test of collides method, of class CollisionSystem.
     */
    @Test
    public void testCollides() {
        System.out.println("collides");
        Entity e1 = new Entity();
        e1.setSize(16, 16);
        e1.setPosition(0, 0);
        assertTrue(colsys.collides(e1, player));
        
        e1.setPosition(20, 20);
        assertFalse(colsys.collides(e1, player));
    }
    
}
