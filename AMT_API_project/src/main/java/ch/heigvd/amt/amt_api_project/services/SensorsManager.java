/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 27-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Sensor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class SensorsManager implements SensorsManagerLocal {


    @PersistenceContext
    EntityManager em;
    
    public SensorsManager() {


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
    
        @Override
    public void deleteAll() {
        Query query = em.createNamedQuery("Sensor.deleteAll");
        query.executeUpdate();
    }
}
