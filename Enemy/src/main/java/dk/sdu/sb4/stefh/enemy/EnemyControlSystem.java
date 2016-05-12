/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.enemy;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author stefh
 */
public class EnemyControlSystem implements IEntityProcessingService {
    
    private float spawnTimer;
    private final float TIME_BETWEEN_SPAWN = 10;
    
    private Entity enemy;

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if(enemy == null) {
            if((spawnTimer += gameData.getDelta()) > TIME_BETWEEN_SPAWN) {
                Entity e = createEnemy(gameData);
                world.put(e.getID(), e);
                enemy = e;
                spawnTimer = 0;
            }
        }
        if(entity.getType() == EntityType.ENEMY) {
            handleHealth(entity);
            if(entity.getHealth() > 0) {
                update(gameData, entity);
                setShape(entity);
            } else {
                destoyEnemy(world, entity);
            }
        }
    }
    
    private void update(GameData g, Entity p) {
        double r = Math.random();
        if(r < 0.3) {
            // Go forward
            p.setDx((float)(p.getDx() + Math.cos(p.getRadians()) * p.getAcceleration() * g.getDelta()));
            p.setDy((float)(p.getDy() + Math.sin(p.getRadians()) * p.getAcceleration() * g.getDelta()));
        } else if(r < 0.5) {
            // Go left
            p.setRadians(p.getRadians() + p.getRotationSpeed() * g.getDelta());
        } else if(r < 0.7) {
            // Go right
            p.setRadians(p.getRadians() - p.getRotationSpeed() * g.getDelta());
        }
        
        // deceleration
        float vec = (float) Math.sqrt(p.getDx() * p.getDx() + p.getDy() * p.getDy());
        if(vec > 0) {
            p.setDx(p.getDx() - ((p.getDx() / vec)*p.getDeacceleration()*g.getDelta()));
            p.setDy(p.getDy() - ((p.getDy() / vec)*p.getDeacceleration()*g.getDelta()));
        }
        if(vec > p.getMaxSpeed()) {
            p.setDx((p.getDx()/vec)*p.getMaxSpeed());
            p.setDy((p.getDy()/vec)*p.getMaxSpeed());
        }
        
        // set position
        float x = p.getX() + p.getDx() * g.getDelta();
        float y = p.getY() + p.getDy() * g.getDelta();
        p.setPosition(x, y);
    }
    
    private void setShape(Entity e) {
        float[] shapeX = new float[4];
        float[] shapeY = new float[4];
        float radius = e.getWidth()/2;
        
        shapeX[0] = (float) (e.getX() + Math.cos(e.getRadians()) * radius);
        shapeY[0] = (float) (e.getY() + Math.sin(e.getRadians()) * radius);

        shapeX[1] = (float) (e.getX() + Math.cos(e.getRadians() - 4 * 3.1415f / 5) * radius);
        shapeY[1] = (float) (e.getY() + Math.sin(e.getRadians() - 4 * 3.1145f / 5) * radius);

        shapeX[2] = (float) (e.getX() + Math.cos(e.getRadians() + 3.1415f) * (radius*0.6f));
        shapeY[2] = (float) (e.getY() + Math.sin(e.getRadians() + 3.1415f) * (radius*0.6f));

        shapeX[3] = (float) (e.getX() + Math.cos(e.getRadians() + 4 * 3.1415f / 5) * radius);
        shapeY[3] = (float) (e.getY() + Math.sin(e.getRadians() + 4 * 3.1415f / 5) * radius);
        
        e.setShapeX(shapeX);
        e.setShapeY(shapeY);
    }
    
    private void handleHealth(Entity e) {
        if(e.isDestroyed()) {
            e.setHealth(e.getHealth()-1);
            e.setDestroyed(false);
        }
    }
    
    private Entity createEnemy(GameData g) {
        Random r = new Random();
        Entity e = new Entity();
        e.setType(EntityType.ENEMY);
        e.setPosition(r.nextInt(g.getDisplayWidth()), r.nextInt(g.getDisplayHeight()));
        e.setHealth(1);
        e.setMaxSpeed(300);
        e.setAcceleration(200);
        e.setDeacceleration(50);
        e.setSize(16, 16);
        e.setRadians(3.1415f / 2);
        e.setRotationSpeed(5);
        return e;
    }
    
    private void destoyEnemy(Map<String, Entity> world, Entity e) {
        world.remove(e.getID());
        enemy = null;
    }
    
}
