/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.collision;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        colsys = new CollisionSystem();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkBullet method, of class CollisionSystem.
     */
    @Test
    public void testCheckBullet() {
        System.out.println("checkBullet");
        Entity bullet = new Entity();
        Entity player = new Entity();
        Map<String, Entity> world = new HashMap<>();
        bullet.setType(EntityType.BULLET);
        player.setType(EntityType.PLAYER);
        bullet.setSize(16, 16);
        player.setSize(16, 16);
        bullet.setPosition(0, 0);
        player.setPosition(0, 0);
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
        Entity e2 = new Entity();
        e1.setSize(16, 16);
        e2.setSize(16, 16);
        e1.setPosition(0, 0);
        e2.setPosition(0, 0);
        assertTrue(colsys.collides(e1, e2));
        
        e2.setPosition(20, 20);
        assertFalse(colsys.collides(e1, e2));
    }
    
}
