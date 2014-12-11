/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 4-dic-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.*;
import java.util.ArrayList;
import java.util.List;
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
    
    @EJB
    private UsersManagerLocal userManger;
    
    @EJB
    private SensorsManagerLocal sensorManager;
    
   
    @Override
    public void generateTestData() {
        // Let's create a first test organization
        Organization org1 = new Organization("org1");
        long org1Id=organizationManager.createOrganization(org1);
        
        //now let's create some users for the organizations:
        List<Long> org1Users= new ArrayList();
        for (int i = 0; i < 5; i++) {
            org1Users.add(userManger.createUser(new User("user"+i, "user"+i, "user"+i+"@org1.com", org1.getId(), false)));
            
        }
        
        User contactUser = userManger.findUserByID(org1Users.get(0));
        contactUser.setIsContact(true);
        userManger.updateUser(contactUser);

        
        //We create some sensors for our organization:
//        List<Long> org1Sensors= new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            org1Sensors.add(sensorManager.createSensor(new Sensor("s"+i, "testSensor"+i, "meteo", "public", org1.getId())));
//        }
    }

    
}
