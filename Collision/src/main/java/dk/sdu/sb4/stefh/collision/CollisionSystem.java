package dk.sdu.sb4.stefh.collision;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.Map;

/**
 *
 * @author stefh
 */
public class CollisionSystem implements IEntityProcessingService {
    
    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        checkPlayer(world, entity);
        checkBullet(world, entity);
    }
    
    /**
     * Checks for player specific collision like collision with asteroids.
     * @param world
     * @param entity Player entity
     */
    public void checkPlayer(Map<String, Entity> world, Entity entity) {
        if(entity.getType() == EntityType.PLAYER) {
            for (Entity object : world.values()) {
                if (object.getType() == EntityType.ASTEROIDS) {
                    if (collides(object, entity)) {
                        entity.setDestroyed(true);
                    }
                }
            }
        }
    }
    
    /**
     * Checks for bullet specific collision like collision with player, enemy 
     * and asteroids
     * @param world
     * @param entity 
     */
    public void checkBullet(Map<String, Entity> world, Entity entity) {
        if(entity.getType() == EntityType.BULLET) {
            for(Entity object : world.values()) {
                if((entity.isEnemyBullet()
                        && object.getType() == EntityType.PLAYER 
                        && collides(object, entity)
                        ) || (
                        !entity.isEnemyBullet()
                        && (object.getType() == EntityType.ASTEROIDS 
                            || object.getType() == EntityType.ENEMY)
                        && collides(object, entity)
                        )) {
                    entity.setDestroyed(true);
                    object.setDestroyed(true);
                }
            }
        }
    }
    
    /**
     * Checks if the given entities collide with each other
     * @param e1
     * @param e2
     * @return True if they collide. Otherwise false
     */
    public boolean collides(Entity e1, Entity e2) {
        float dx = e1.getX() - e2.getX();
        float dy = e1.getY() - e2.getY();
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double minDistance = e1.getWidth() / 2 + e2.getWidth() / 2;
        return distance < minDistance;
    }

}
