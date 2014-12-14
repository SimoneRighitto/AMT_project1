/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.CounterFact;
import ch.heigvd.amt.amt_api_project.model.DailyFact;
import ch.heigvd.amt.amt_api_project.model.Observation;
import ch.heigvd.amt.amt_api_project.model.Sensor;
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
        DailyFact dailyFact = (DailyFact) factManager.findFactBySensorIdAndDate(sourceSensor.getId(), observation.getObservedAt());
        CounterFact counterFact = (CounterFact) factManager.findCounterFactBySensorId(sourceSensor.getId());

        if (dailyFact == null) {
            factManager.createFact(new DailyFact(sourceSensor, observation.getObservedAt(), "daily", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner()));
        } else {
            dailyFact.addValue(observation.getObservedValue());
            factManager.updateFact(dailyFact);
        }

        if (counterFact == null) {
            factManager.createFact(new CounterFact(sourceSensor, "counter", sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner()));
        } else {
            counterFact.incrementNumberOfObservationsDone();
            factManager.updateFact(counterFact);
        }

        return observation.getId();

    }
}
