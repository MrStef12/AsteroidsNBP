/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.weapon;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.data.GameKeys;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author stefh
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class WeaponControlSystem implements IEntityProcessingService {
    
    private final Map<String, Float> bulletLifetimes = new HashMap<>();
    
    private final float BULLET_LIFE_TIME = 1f;

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if(entity.getType() == EntityType.PLAYER) {
            if(!entity.isDestroyed() && gameData.getKeys().isPressed(GameKeys.SPACE)) {
                shootBullet(gameData, world, entity);
            }
        } else if(entity.getType() == EntityType.ENEMY) {
            if(Math.random() > 0.8) {
                shootBullet(gameData, world, entity);
            }
        } else if(entity.getType() == EntityType.BULLET) {
            update(gameData, entity);
            setShape(entity);
            if(bulletLifetimes.get(entity.getID()) > BULLET_LIFE_TIME) {
                entity.setDestroyed(true);
            }
            if(entity.isDestroyed()) {
                bulletLifetimes.remove(entity.getID());
                world.remove(entity.getID());
            }
        }
    }
    
    private void update(GameData g, Entity b) {
        double dx = Math.cos(b.getRadians()) * b.getMaxSpeed() * g.getDelta();
        double dy = Math.sin(b.getRadians()) * b.getMaxSpeed() * g.getDelta();
        
        b.setX((float)(b.getX() + dx * g.getDelta()));
        b.setY((float)(b.getY() + dy * g.getDelta()));
        
        // Add time to the bullet lifetime
        bulletLifetimes.put(b.getID(), bulletLifetimes.get(b.getID())+g.getDelta());
    }
    
    private void setShape(Entity b) {
        float[] shapeX = new float[2];
        float[] shapeY = new float[2];
        float radius = b.getHeight()/2;
        
        shapeX[0] = (float) (b.getX() + Math.cos(b.getRadians()) * radius);
        shapeY[0] = (float) (b.getY() + Math.sin(b.getRadians()) * radius);

        shapeX[1] = (float) (b.getX() + Math.cos(b.getRadians() + 3.1415f) * radius);
        shapeY[1] = (float) (b.getY() + Math.sin(b.getRadians() + 3.1415f) * radius);
        
        b.setShapeX(shapeX);
        b.setShapeY(shapeY);
    }
    
    private void shootBullet(GameData g, Map<String, Entity> world, Entity p) {
        Entity b = generateBullet(g, p);
        bulletLifetimes.put(b.getID(), 0f);
        world.put(b.getID(), b);
    }
    
    private Entity generateBullet(GameData g, Entity p) {
        Entity bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setEnemyBullet(p.getType() == EntityType.ENEMY);
        bullet.setX(p.getX());
        bullet.setY(p.getY());
        bullet.setRadians(p.getRadians());
        bullet.setMaxSpeed(20000);
        bullet.setSize(1, 10);
        return bullet;
    }
}
