package dk.sdu.sb4.stefh.wrapper;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author stefh
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class WrapperControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, Map<String, Entity> world, Entity entity) {
        wrap(gameData, entity);
    }
    
    private void wrap(GameData game, Entity entity) {
        float x = entity.getX();
        float y = entity.getY();
        if(x < 0) x = game.getDisplayWidth();
        if(x > game.getDisplayWidth()) x = 0;
        if(y < 0) y = game.getDisplayHeight();
        if(y > game.getDisplayHeight()) y = 0;
        entity.setPosition(x, y);
    }
    
}
