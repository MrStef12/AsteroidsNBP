package dk.sdu.sb4.stefh.common.services;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.GameData;
import java.util.Map;

public interface IEntityProcessingService {

    /**
     * TODO: Describe the contract using pre- and post-conditions.
     *
     * @param gameData
     * @param world
     * @param entity
     */
    void process(GameData gameData, Map<String, Entity> world, Entity entity);
}
