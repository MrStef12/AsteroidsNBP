/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sb4.stefh.collision;

import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author stefh
 */
public class Activator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(IEntityProcessingService.class, new CollisionSystem(), null);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        
    }
    
}
