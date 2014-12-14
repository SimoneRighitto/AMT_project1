/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
//    private Map<Long, Observation> observations = new HashMap<>();
    @PersistenceContext
    EntityManager em;
    
    @EJB
    FactsManagerLocal factManager;

    public ObservationsManager() {
        //observations.put(1L, new Observation(1L, new Date(), 1L, 11L));
    }

    @Override
    public Observation findObservationByID(long id) {
//        return observations.get(id);
        return em.find(Observation.class, id);
    }

    @Override
    public long createObservation(Observation observation) {
        em.persist(observation);
        em.flush();
        em.detach(observation);
        
        Sensor sourceSensor= observation.getSourceSensor();
        DailyFact dailyFact = (DailyFact) factManager.findFactBySensorIdAndDate(sourceSensor.getId(), observation.getObservedAt());
        CounterFact counterFact = (CounterFact) factManager.findCounterFactBySensorId(sourceSensor.getId());
        
        if(dailyFact== null){
            factManager.createFact(new DailyFact(sourceSensor,observation.getObservedAt(), sourceSensor.getType(), sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner()));
        }
        else{
            dailyFact.addValue(observation.getObservedValue());
            factManager.updateFact(dailyFact);
        }
        
        if(counterFact == null){
            factManager.createFact(new CounterFact(sourceSensor, sourceSensor.getType(), sourceSensor.getVisibility(), sourceSensor.getOrganizationOwner()));
        }
        else{
            counterFact.incrementNumberOfObservationsDone();
            factManager.updateFact(counterFact);
        }
        
        
        return observation.getId();
        
        
    }
}