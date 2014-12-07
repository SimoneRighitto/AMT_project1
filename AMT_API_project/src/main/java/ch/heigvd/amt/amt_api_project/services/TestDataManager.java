/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 4-dic-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Organization;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class TestDataManager implements TestDataManagerLocal {
    
    
    @EJB
    private OrganizationManagerLocal organizationManager;
    
   
    @Override
    public void generateTestData() {
        // Let's create a first test organization
        Organization org1 = new Organization("org1");
        organizationManager.createOrganization(org1);
    }

    
}
