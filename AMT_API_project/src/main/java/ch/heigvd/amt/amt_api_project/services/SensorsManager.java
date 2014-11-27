/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 27-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Sensor;
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
public class SensorsManager implements SensorsManagerLocal {

    private Map<Long, Sensor> organizations = new HashMap<>();

    public SensorsManager() {

        organizations.put(1L, new Sensor(1L, "sensor1", "roof", "clima", "public", 2L));
        organizations.put(1L, new Sensor(2L, "sensor2", "window", "clima", "public", 2L));
        organizations.put(1L, new Sensor(3L, "sensor3", "1st floor", "clima", "public", 2L));

    }

    @Override
    public Sensor findSensorByID(long id) {
        return organizations.get(id);
    }


    @Override
    public long createSensor(Sensor organization) {
        organization.setId(organizations.size() + 1);
        organizations.put(organization.getId(), organization);

        return organization.getId();
    }

    @Override
    public void updateSensor(Sensor organization) {
        organizations.put(organization.getId(), organization);
    }

    @Override
    public void deleteSensor(long id) {
        organizations.remove(id);
    }
}
