package dk.sdu.sb4.stefh.player;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.data.GameKeys;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author jcs
 */

@ServiceProvider(service = IEntityProcessingService.class)
public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        if(entity.getType() == EntityType.PLAYER) {
            if(entity.getHealth() > 0) {
                update(gameData, entity);
                setShape(entity);
            }
        }
    }
    
    private void setShape(Entity player) {
        float[] shapeX = new float[4];
        float[] shapeY = new float[4];
        float radius = player.getWidth()/2;
        
        shapeX[0] = (float) (player.getX() + Math.cos(player.getRadians()) * radius);
        shapeY[0] = (float) (player.getY() + Math.sin(player.getRadians()) * radius);

        shapeX[1] = (float) (player.getX() + Math.cos(player.getRadians() - 4 * 3.1415f / 5) * radius);
        shapeY[1] = (float) (player.getY() + Math.sin(player.getRadians() - 4 * 3.1145f / 5) * radius);

        shapeX[2] = (float) (player.getX() + Math.cos(player.getRadians() + 3.1415f) * (radius*0.6f));
        shapeY[2] = (float) (player.getY() + Math.sin(player.getRadians() + 3.1415f) * (radius*0.6f));

        shapeX[3] = (float) (player.getX() + Math.cos(player.getRadians() + 4 * 3.1415f / 5) * radius);
        shapeY[3] = (float) (player.getY() + Math.sin(player.getRadians() + 4 * 3.1415f / 5) * radius);
        
        player.setShapeX(shapeX);
        player.setShapeY(shapeY);
    }
    
    private void update(GameData g, Entity p) {
        handleDestoryed(g, p);
        if(g.getKeys().isDown(GameKeys.LEFT)) {
            p.setRadians(p.getRadians() + p.getRotationSpeed() * g.getDelta());
        } else if(g.getKeys().isDown(GameKeys.RIGHT)) {
            p.setRadians(p.getRadians() - p.getRotationSpeed() * g.getDelta());
        }
        // acceleration
        if(g.getKeys().isDown(GameKeys.UP)) {
            p.setDx((float)(p.getDx() + Math.cos(p.getRadians()) * p.getAcceleration() * g.getDelta()));
            p.setDy((float)(p.getDy() + Math.sin(p.getRadians()) * p.getAcceleration() * g.getDelta()));
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
    
    private void handleDestoryed(GameData g, Entity p) {
        if(p.isDestroyed()) {
            p.setHealth(p.getHealth()-1);
            if(p.getHealth()<1) {
                System.out.println("YOU ARE DEAD!");
            } else {
                System.out.println("You lost a life! Health: "+p.getHealth());
                p.setX(g.getDisplayWidth() / 2);
                p.setY(g.getDisplayHeight() / 2);
                p.setDx(0);
                p.setDy(0);
            }
            p.setDestroyed(false);
        }
    }
}
