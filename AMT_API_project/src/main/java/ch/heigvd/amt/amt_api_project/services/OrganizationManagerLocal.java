/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Organization;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Simone Righitto
 */
@Local
public interface OrganizationManagerLocal {
    
    public Organization findOrganizationByID(long id);

    public List<Organization> findAllOrganization();

    public long createOrganization(Organization organization);

    public void updateOrganization(Organization organization);

    public void deleteOrganization(long id);
}
