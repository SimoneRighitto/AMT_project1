/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.OrganizationDTO;
import ch.heigvd.amt.amt_api_project.model.Organization;
import ch.heigvd.amt.amt_api_project.services.OrganizationManagerLocal;
import java.util.ArrayList;
import java.util.List;
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
@Path("organizations")
@Stateless
public class OrganizationResource {

    @EJB
    OrganizationManagerLocal organizationsManager;

    @Context
    private UriInfo context;

    public OrganizationResource() {

    }

    @GET
    @Produces("application/json")
    public List<OrganizationDTO> getAllOrganizations() {

        List<Organization> organizations = organizationsManager.findAllOrganization();
        List<OrganizationDTO> result = new ArrayList<>();

        for (Organization u : organizations) {
            result.add(toDTO(u));
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public OrganizationDTO getOrganizationDetails(@PathParam("id") long id) {
        return toDTO(organizationsManager.findOrganizationByID(id));
    }

    @POST
    @Consumes("application/json")
    public long createOrganization(OrganizationDTO dtoOrganization) {
        Organization newOrganization = new Organization();
        long id = organizationsManager.createOrganization(toOrganization(dtoOrganization, newOrganization));
        return id;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateOrganization(@PathParam("id") long id, OrganizationDTO dtoOrganization) {
        Organization existingOrganization = organizationsManager.findOrganizationByID(id);
        organizationsManager.updateOrganization(toOrganization(dtoOrganization, existingOrganization));
    }

    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id) {
        organizationsManager.deleteOrganization(id);
    }

    /*
     * Conversion utility methods
     */
    private Organization toOrganization(OrganizationDTO dtoOrganization, Organization originalOrganization) {

        originalOrganization.setContactUserID(dtoOrganization.getContactUserID());
        originalOrganization.setId(dtoOrganization.getId());
        originalOrganization.setName(dtoOrganization.getName());
        
                
       return originalOrganization;
    }

    private OrganizationDTO toDTO(Organization organization) {
        OrganizationDTO dtoOrganization=  new OrganizationDTO();
        dtoOrganization.setContactUserID(organization.getContactUserID());
        dtoOrganization.setId(organization.getId());
        dtoOrganization.setName(organization.getName());
        
        return dtoOrganization;
    }
}
