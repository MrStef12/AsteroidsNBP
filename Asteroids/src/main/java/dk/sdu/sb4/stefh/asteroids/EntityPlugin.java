package dk.sdu.sb4.stefh.asteroids;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.EntityType;
import dk.sdu.sb4.stefh.common.data.GameData;
import dk.sdu.sb4.stefh.common.services.IGamePluginService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
/**
 *
 * @author stefh
 */
@ServiceProvider(service = IGamePluginService.class)
public class EntityPlugin implements IGamePluginService {

    private Map<String, Entity> world;
    
    @Override
    public void start(GameData gameData, Map<String, Entity> world) {
        this.world = world;
    }

    @Override
    public void stop(GameData gameData) {
        for(Entity a : world.values()) {
            if(a.getType() == EntityType.ASTEROIDS) {
                world.remove(a.getID());
            }
        }
    }
}
