package dk.sdu.sb4.stefh.enemy;

import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import dk.sdu.sb4.stefh.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class, new EntityPlugin(), null);
        context.registerService(IEntityProcessingService.class, new EnemyControlSystem(), null);
    }

    public void stop(BundleContext context) throws Exception {
        // TODO add deactivation code here
    }

}
