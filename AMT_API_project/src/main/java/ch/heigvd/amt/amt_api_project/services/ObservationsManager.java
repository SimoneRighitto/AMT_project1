/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.model.Sensor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Maillard
 */
@Stateless
public class ObservationsManager implements ObservationsManagerLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    FactsManagerLocal factManager;

    public ObservationsManager() {

    }

    @Override
    public Observation findObservationByID(long id) {

        return em.find(Observation.class, id);
    }

    @Override
    public long createObservation(Observation observation) {
        em.persist(observation);
        em.flush();
        em.detach(observation);

        Sensor sourceSensor = observation.getSourceSensor();

        //managing the CounterFacts
        Fact counterFact = factManager.findFactBySensorIdAndType(sourceSensor.getId(), "counter");
        
        if (counterFact != null) {
            //only update
            HashMap<String, Double> counterInfos = counterFact.getInfos();
            double counter = counterInfos.get("obsCounter");
            counter++;
            counterInfos.put("obsCounter", counter);
            counterFact.setInfos(counterInfos);

            factManager.updateFact(counterFact);

        } else {
            //create a new CounterFact
            HashMap<String, Double> counterInfos = new HashMap();
            counterInfos.put("obsCounter", 1.0);
            counterFact = new Fact("counter", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner(), sourceSensor, new Date());
            counterFact.setInfos(counterInfos);

            factManager.createFact(counterFact);
        }

        //managing DailyFacts
        Fact dailyFact = factManager.findFactBySensorIdAndTypeAndDate(sourceSensor.getId(), "daily", new Date());
        if (dailyFact != null) {
            //only update
            HashMap<String, Double> dailyInfos = dailyFact.getInfos();
            double oldMin = dailyInfos.get("min");
            double oldMax = dailyInfos.get("max");
            

            double newValue = observation.getObservedValue();

            dailyInfos.put("min", (oldMin < newValue) ? oldMin : newValue);
            dailyInfos.put("max", (newValue > oldMax) ? newValue : oldMax);
            dailyInfos.put("avg", findAverageObservationByDay(sourceSensor.getId()));
           
            
            dailyFact.setInfos(dailyInfos);
            factManager.updateFact(dailyFact);

        } else {
            //create a new DailyFact
            HashMap<String, Double> dailyInfos = new HashMap();
            //as it's a new dailyFact, min, max and avg values are the same
            dailyInfos.put("min", observation.getObservedValue());
            dailyInfos.put("max", observation.getObservedValue());
            dailyInfos.put("avg", observation.getObservedValue());
            dailyFact = new Fact("daily", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner(), sourceSensor, new Date());
            dailyFact.setInfos(dailyInfos);
            factManager.createFact(dailyFact);
        }

        return observation.getId();

    }

    @Override
    public Double findAverageObservationByDay(long sensorId) {
        Date start = getStartOfDay(new Date());
        Date end = getEndOfDay(new Date());
        return (Double) em.createNamedQuery("findAverageObservationByDay").setParameter("sensorId", sensorId).setParameter("start", start, TemporalType.TIMESTAMP).setParameter("end", end, TemporalType.TIMESTAMP).getSingleResult();
    }
    
        @Override
    public void deleteAll() {
        Query query = em.createNamedQuery("Observation.deleteAll");
        query.executeUpdate();
    }

    //utility functions to get the time space of one day in order to be able to obtain averages values for observations
    //http://stackoverflow.com/questions/10308356/how-to-obtain-the-start-time-and-end-time-of-a-day
    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
