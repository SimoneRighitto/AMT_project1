/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.model.Sensor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            List<Double> counterInfos = counterFact.getInfos();
            double counter = counterInfos.get(0);
            counter++;
            counterInfos.set(0, counter);
            counterFact.setInfos(counterInfos);

            factManager.updateFact(counterFact);

        } else {
            //create a new CounterFact
            List<Double> counterInfos = new ArrayList<>();
            counterInfos.add(1.0);
            counterFact = new Fact("counter", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner(), sourceSensor, observation.getObservedAt());
            counterFact.setInfos(counterInfos);

            factManager.createFact(counterFact);
        }

        //managing DailyFacts
        Fact dailyFact = factManager.findFactBySensorIdAndTypeAndDate(sourceSensor.getId(), "daily", observation.getObservedAt());
        if (dailyFact != null) {
            //only update
            List<Double> oldValue = dailyFact.getInfos();
            List<Double> newValue = new ArrayList<>();
            Double min = oldValue.get(0);
            Double max = oldValue.get(1);
            Double avg = oldValue.get(2);
            if (observation.getObservedValue() < min) {
                min = observation.getObservedValue();
            } else if (observation.getObservedValue() > max) {
                max = observation.getObservedValue();
            }
            avg = findAverageObservationByDay(observation.getSourceSensor().getId());
            newValue.add(min);
            newValue.add(max);
            newValue.add(avg);
            dailyFact.setInfos(newValue);

        } else {
            //create a new DailyFact
            //as it's a new dailyFact, min, max and avg values are the same
            List<Double> dailyInfos = new ArrayList<>();
            dailyInfos.add(observation.getObservedValue()); 
            dailyInfos.add(observation.getObservedValue()); 
            dailyInfos.add(observation.getObservedValue()); 
            
            dailyFact = new Fact("daily", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner(), sourceSensor, observation.getObservedAt());
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
