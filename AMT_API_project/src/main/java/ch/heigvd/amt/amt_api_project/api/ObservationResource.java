/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.ObservationDTO;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.services.ObservationsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
        long id = observationManager.createObservation(toObservation(dtoObservation, newObservation));
        return id;
    }
    
    
    
    /*
     * Conversion utility methods
     */
    private Observation toObservation(ObservationDTO dtoObservation, Observation originalObservation) {
        originalObservation.setId(dtoObservation.getId());
        originalObservation.setTime(dtoObservation.getTime());
        originalObservation.setValue(dtoObservation.getValue());
        originalObservation.setSourceSensor(dtoObservation.getSourceSensor());
        
        return originalObservation;
    }

    private ObservationDTO toDTO(Observation observation) {
        ObservationDTO dtoObservation =  new ObservationDTO();
        dtoObservation.setId(observation.getId());
        dtoObservation.setTime(observation.getTime());
        dtoObservation.setValue(observation.getValue());
        dtoObservation.setSourceSensor(observation.getSourceSensor());
        
        return dtoObservation;
    }
    
}
