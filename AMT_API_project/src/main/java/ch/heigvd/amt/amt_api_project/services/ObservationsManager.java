/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Observation;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
//        observation.setId(observations.size() + 1);
//        observations.put(observation.getId(), observation);
//        return observation.getId();
        em.persist(observation);
        em.flush();
        em.detach(observation);
        System.out.println("Fact id: " + observation.getId());
        return observation.getId();
    }
}