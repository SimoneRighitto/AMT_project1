/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.ObservationDTO;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.services.ObservationsManagerLocal;
import ch.heigvd.amt.amt_api_project.services.SensorsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Maillard
 */
@Path("observations")
@Stateless
public class ObservationResource {

    @EJB
    private ObservationsManagerLocal observationManager;

    @EJB
    SensorsManagerLocal sensorsManager;

    @Context
    private UriInfo context;

    public ObservationResource() {

    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public ObservationDTO getObservationDetails(@PathParam("id") long id) {
        return toDTO(observationManager.findObservationByID(id));
    }

    @POST
    @Consumes("application/json")
    public long createObservation(ObservationDTO dtoObservation) {
        
        Observation newObservation = new Observation();
        int numberOfAttempts = 100;
 
        
        while (numberOfAttempts > 0) {
            try {
                numberOfAttempts--;
                long id = observationManager.createObservation(toObservation(dtoObservation, newObservation));
                
                return id;
            } catch (Exception e) {
                System.out.println("OptimisticLockException : remaining attempts: " + numberOfAttempts);
                try {
                    Thread.sleep((long) Math.random() * 200);
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted Exception : "+ ex.getStackTrace());
                }
            }
        }
        throw new WebApplicationException("ERROR: concurrente update");
    }

    /*
     * Conversion utility methods
     */
    private Observation toObservation(ObservationDTO dtoObservation, Observation originalObservation) {
        originalObservation.setId(dtoObservation.getId());
        originalObservation.setObservedAt(dtoObservation.getTime());
        originalObservation.setObservedValue(dtoObservation.getValue());
        originalObservation.setSourceSensor(sensorsManager.findSensorByID(dtoObservation.getSourceSensorId()));

        return originalObservation;
    }

    private ObservationDTO toDTO(Observation observation) {
        ObservationDTO dtoObservation = new ObservationDTO();
        dtoObservation.setId(observation.getId());
        dtoObservation.setTime(observation.getObservedAt());
        dtoObservation.setValue(observation.getObservedValue());
        dtoObservation.setSourceSensorId(observation.getSourceSensor().getId());

        return dtoObservation;
    }

}
