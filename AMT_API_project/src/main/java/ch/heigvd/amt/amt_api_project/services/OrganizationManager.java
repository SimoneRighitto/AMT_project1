/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Organization;
import ch.heigvd.amt.amt_api_project.model.Organization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class OrganizationManager implements OrganizationManagerLocal {

    private Map<Long, Organization> organizations = new HashMap<>();

    public OrganizationManager() {

        organizations.put(1L, new Organization(1L, "Organization1", 2L));
        organizations.put(1L, new Organization(1L, "Organization1", 2L));
        organizations.put(1L, new Organization(1L, "Organization1", 2L));

    }

    @Override
    public Organization findOrganizationByID(long id) {
        return organizations.get(id);
    }

    @Override
    public List<Organization> findAllOrganization() {
        return new ArrayList(organizations.values());
    }

    @Override
    public long createOrganization(Organization organization) {
        organization.setId(organizations.size()+1);
        organizations.put(organization.getId(), organization);
        
        return organization.getId();
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizations.put(organization.getId(), organization);
    }

    @Override
    public void deleteOrganization(long id) {
        organizations.remove(id);
    }
}
