/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.model.Sensor;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
//            List<Double> oldValue = f1.getInfo();
//            List<Double> newValue = new LinkedList<>();
//            Double min = oldValue.get(0);
//            Double max = oldValue.get(1);
//            Double avg = oldValue.get(2);
//            if (observation.getValue() < min) {
//                min = observation.getValue();
//            } else if (observation.getValue() > max) {
//                max = observation.getValue();
//            }
//            avg = findAverageObservationForOneDay(observation.getSensor());
//            newValue.add(min);
//            newValue.add(max);
//            newValue.add(avg);
//            f1.setInfo(newValue);
            
            HashMap<String, Double> dailyInfos = dailyFact.getInfos();
            double oldMin= dailyInfos.get("min");
            double oldMax= dailyInfos.get("max");
            double oldAvg= dailyInfos.get("avg");
            
            double newValue = observation.getObservedValue();
            
            dailyInfos.put("min", (oldMin < newValue) ? oldMin : newValue);
            dailyInfos.put("max", (newValue > oldMax) ? newValue: oldMax);
            
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
}
