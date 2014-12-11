/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.FactDTO;
import ch.heigvd.amt.amt_api_project.model.Fact;
import ch.heigvd.amt.amt_api_project.services.FactsManagerLocal;
import ch.heigvd.amt.amt_api_project.services.OrganizationManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
 * @author Maillard
 */
public class FactResource {
    
    @EJB
    FactsManagerLocal factsManager;
    
    @EJB
    OrganizationManagerLocal organizationManager;

    @Context
    private UriInfo context;

    public FactResource() {

    }

    @GET
    @Produces("application/json")
    public List<FactDTO> getAllFacts() {

        List<Fact> facts = factsManager.findAllFacts();
        List<FactDTO> result = new ArrayList<>();

        for (Fact u : facts) {
            result.add(toDTO(u));
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactDTO getFactDetails(@PathParam("id") long id) {
        return toDTO(factsManager.findFactByID(id));
    }

    @POST
    @Consumes("application/json")
    public long createFact(FactDTO dtoFact) {
        Fact newFact = new Fact();
        long id = factsManager.createFact(toFact(dtoFact, newFact));
        return id;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateFact(@PathParam("id") long id, FactDTO dtoFact) {
        Fact existingFact = factsManager.findFactByID(id);
        factsManager.updateFact(toFact(dtoFact, existingFact));
    }

    @Path("/{id}")
    @DELETE
    public void deleteFact(@PathParam("id") long id) {
        factsManager.deleteFact(id);
    }

    /*
     * Conversion utility methods
     */
    private Fact toFact(FactDTO dtoFact, Fact originalFact) {
        originalFact.setId(dtoFact.getId());
        originalFact.setInformation(dtoFact.getInformation());
        originalFact.setType(dtoFact.getType());
        originalFact.setVisibility(dtoFact.getVisibility());
        originalFact.setOrganizationOwner(organizationManager.findOrganizationByID(dtoFact.getOrganizationOwnerId()));
        
        return originalFact;
    }

    private FactDTO toDTO(Fact fact) {
        FactDTO dtoFact=  new FactDTO();
        dtoFact.setId(fact.getId());
        dtoFact.setInformation(fact.getInformation());
        dtoFact.setType(fact.getType());
        dtoFact.setVisibility(fact.getVisibility());
        dtoFact.setOrganizationOwner(fact.getOrganizationOwner().getId());
        
        return dtoFact;
    }
    
}
