/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.asteroids;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.services.IGamePluginService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class EntityPluginTest {
    
    private IGamePluginService plugin;
    private Map<String, Entity> world;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {

    }
    
    @Before
    public void setUp() {
        plugin = new EntityPlugin();
        world = new ConcurrentHashMap<>();
        for(int i=0; i<10; i++) {
            Entity e = new Entity();
            e.setType(EntityType.ASTEROIDS);
            world.put(e.getID(), e);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of start method, of class EntityPlugin.
     */
    @Test
    public void testStart() {
        int worldObjects = world.size();
        plugin.start(null, world);
        // Assert that the amount of objects has not changed
        assertEquals(world.size(), worldObjects);
    }

    /**
     * Test of stop method, of class EntityPlugin.
     */
    @Test
    public void testStop() {
        plugin.start(null, world);
        plugin.stop(null);
        for(Entity e : world.values()) {
            if(e.getType() == EntityType.ASTEROIDS) {
                fail("Asteroids not cleaned up!");
            }
        }
        
    }
    
}
