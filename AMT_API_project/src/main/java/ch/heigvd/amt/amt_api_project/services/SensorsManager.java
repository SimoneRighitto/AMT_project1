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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class SensorsManager implements SensorsManagerLocal {

//    private Map<Long, Sensor> organizations = new HashMap<>();
    @PersistenceContext
    EntityManager em;
    
    public SensorsManager() {

//        organizations.put(1L, new Sensor(1L, "sensor1", "roof", "clima", "public", 2L));
//        organizations.put(1L, new Sensor(2L, "sensor2", "window", "clima", "public", 2L));
//        organizations.put(1L, new Sensor(3L, "sensor3", "1st floor", "clima", "public", 2L));

    }

    @Override
    public Sensor findSensorByID(long id) {
        return em.find(Sensor.class, id);
    }


    @Override
    public long createSensor(Sensor sensor) {
         em.persist(sensor);
        em.flush();
        em.detach(sensor);
        System.out.println("Fact id: " + sensor.getId());
        return sensor.getId();
    }

    @Override
    public void updateSensor(Sensor sensor) {
        em.merge(sensor);
    }

    @Override
    public void deleteSensor(long id) {
        em.remove(id);
    }
}
