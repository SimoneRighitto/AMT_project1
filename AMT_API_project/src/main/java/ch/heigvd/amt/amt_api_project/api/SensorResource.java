/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.SensorDTO;
import ch.heigvd.amt.amt_api_project.model.Sensor;
import ch.heigvd.amt.amt_api_project.services.OrganizationManagerLocal;
import ch.heigvd.amt.amt_api_project.services.SensorsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Simone Righitto
 */
@Path("sensors")
@Stateless
public class SensorResource {

    @EJB
    SensorsManagerLocal sensorsManager;
    
    @EJB
    OrganizationManagerLocal organizationManager;

    @Context
    private UriInfo context;

    public SensorResource() {

    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensorDetails(@PathParam("id") long id) {
        return toDTO(sensorsManager.findSensorByID(id));
    }

    @POST
    @Consumes("application/json")
    public long createSensor(SensorDTO dtoSensor) {
        Sensor newSensor = new Sensor();
        long id = sensorsManager.createSensor(toSensor(dtoSensor, newSensor));
        return id;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateSensor(@PathParam("id") long id, SensorDTO dtoSensor) {
        Sensor existingSensor = sensorsManager.findSensorByID(id);
        sensorsManager.updateSensor(toSensor(dtoSensor, existingSensor));
    }

    @Path("/{id}")
    @DELETE
    public void deleteSensor(@PathParam("id") long id) {
        sensorsManager.deleteSensor(id);
    }

    /*
     * Conversion utility methods
     */
    private Sensor toSensor(SensorDTO dtoSensor, Sensor originalSensor) {
        originalSensor.setDescription(dtoSensor.getDescription());
        originalSensor.setId(dtoSensor.getId());
        originalSensor.setName(dtoSensor.getName());
        
        originalSensor.setOrganizationOwner(organizationManager.findOrganizationByID(dtoSensor.getOrganizationOwnerId()));
        originalSensor.setType(dtoSensor.getType());
        originalSensor.setVisibility(dtoSensor.getVisibility());

        return originalSensor;
    }

    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dtoSensor = new SensorDTO();

        dtoSensor.setDescription(sensor.getDescription());
        dtoSensor.setId(sensor.getId());
        dtoSensor.setName(sensor.getName());
        dtoSensor.setOrganizationOwnerId(sensor.getOrganizationOwner().getId());
        dtoSensor.setType(sensor.getType());
        dtoSensor.setVisibility(sensor.getVisibility());

        return dtoSensor;
    }
}
