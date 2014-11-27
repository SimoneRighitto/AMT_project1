/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Simone Righitto
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.heigvd.amt.amt_api_project.api.ObservationResourc        resources.add(ch.heigvd.amt.amt_api_project.api.OrganizationResource.class);
        resources.add(ch.heigvd.amt.amt_api_project.api.SensorResource.class);
        resources.add(ch.heigvd.amt.amt_api_project.api.UserResource.class);
    }

}
