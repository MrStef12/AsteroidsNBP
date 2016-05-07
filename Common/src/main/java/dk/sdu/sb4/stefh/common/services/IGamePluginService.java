package dk.sdu.sb4.stefh.common.services;

import dk.sdu.sb4.stefh.common.data.Entity;
import dk.sdu.sb4.stefh.common.data.GameData;
import java.util.Map;

public interface IGamePluginService {

    /**
     * TODO: Describe the contract using pre- and post-conditions.
     * Starts the component
     * Pre: 
     * Post: 
     * 
     * @param gameData
     * @param world
     */
    void start(GameData gameData, Map<String, Entity> world);

    /**
     * TODO: Describe the contract using pre- and post-conditions.
     * Pre: Component is running
     * Post: Data from the component has been cleaned up
     * 
     * @param gameData
     */
    void stop(GameData gameData);
}
