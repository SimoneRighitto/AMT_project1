/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 4-dic-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

    @EJB
    private ObservationsManagerLocal observationManger;
    
    @EJB
    private FactsManagerLocal factManager;

    @Override
    public void generateTestData() {

        /**
         * Heig-Vd organization test
         */
        // Let's create a first test organization
        Organization heigVd = new Organization("heigVd");
        long heigVdId = organizationManager.createOrganization(heigVd);

        //now let's create some users for the organizations:
        List<Long> heigVdUsers = new ArrayList();
        for (int i = 0; i < 5; i++) {
            heigVdUsers.add(userManger.createUser(new User("user" + i, "user" + i, "user" + i + "@heigVd.com", heigVd, false)));

        }

        User heigVdContactUser = userManger.findUserByID(heigVdUsers.get(0));
        heigVdContactUser.setIsContact(true);
        userManger.updateUser(heigVdContactUser);
        heigVd.setContactUser(heigVdContactUser);
        organizationManager.updateOrganization(heigVd);

        //We create some sensors for our organization:
        List<Long> heigVdSensors = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            heigVdSensors.add(sensorManager.createSensor(new Sensor("s" + i, "testSensor" + i, "meteo", "public", heigVd)));
        }

        //finally let's generate some observations:
        Date d = new Date(); //just for test we let the date empty
        Random r = new Random();
        double randomValue;
        int randomNbrOfObservations;
        for (Long s : heigVdSensors) {
            randomNbrOfObservations = r.nextInt(5);
            for (int i = 0; i < randomNbrOfObservations; i++) {
                randomValue = -15 + (40 - 15) * r.nextDouble();
                observationManger.createObservation(new Observation(d, randomValue, sensorManager.findSensorByID(s)));
            }
        }

        /**
         * Police organization test
         */
        // Let's create a second test organization
        Organization policeVd = new Organization("policeVd");
        long policeVdId = organizationManager.createOrganization(policeVd);

        //now let's create some users for the organizations:
        List<Long> policeVdUsers = new ArrayList();
        for (int i = 0; i < 10; i++) {
            policeVdUsers.add(userManger.createUser(new User("officer" + i, "officer" + i, "officer" + i + "@policeVd.com", policeVd, false)));

        }

        User policeVdContactUser = userManger.findUserByID(policeVdUsers.get(0));
        policeVdContactUser.setIsContact(true);
        userManger.updateUser(policeVdContactUser);
        policeVd.setContactUser(policeVdContactUser);
        organizationManager.updateOrganization(policeVd);

        //We create some sensors for our organization:
        List<Long> policeVdSensors = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            policeVdSensors.add(sensorManager.createSensor(new Sensor("r" + i, "radar" + i, "speed", "private", policeVd)));
        }

        //Just flashing some people on the highway ;-)
        Observation o1 = new Observation(d, 150.0, sensorManager.findSensorByID(policeVdSensors.get(0)));
        Observation o2 = new Observation(d, 180.0, sensorManager.findSensorByID(policeVdSensors.get(0)));
        observationManger.createObservation(o1);
        observationManger.createObservation(o2);
    }

    @Override
    public void resetTestData() {
       
        factManager.deleteAll();
        observationManger.deleteAll();
        sensorManager.deleteAll();
        userManger.deleteAll();
        organizationManager.deleteAll();
       
    }

}
