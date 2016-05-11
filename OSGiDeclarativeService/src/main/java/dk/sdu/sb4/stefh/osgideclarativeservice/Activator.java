package dk.sdu.sb4.stefh.osgideclarativeservice;

import dk.sdu.sb4.stefh.common.services.IEntityProcessingService;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;


public class Activator {
    
    private ServiceReference reference;
    private IEntityProcessingService service;
    
    protected void activate(ComponentContext cc) {
        if(reference != null) {
            service = (IEntityProcessingService) cc.locateService("IEntityProcessingService", reference);
        }
    }
    
    public void gotService(ServiceReference sr) {
        reference = sr;
    }
    
    public void lostService(ServiceReference sr) {
        reference = null;
    }
    
}
