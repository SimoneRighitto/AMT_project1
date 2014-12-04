/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Maillard
 */
@Stateless
public class FactsManager implements FactsManagerLocal {

//    private Map<Long, Fact> facts = new HashMap<>();

    @PersistenceContext
    EntityManager em;

    public FactsManager() {

        //facts.put(1L, new Fact(1L, "info", "type", "visibility", 11L));
    }

    @Override
    public List<Fact> findAllFacts() {
//        return new ArrayList<Fact>(facts.values());
        
        List<Fact> facts;
        facts = em.createNamedQuery("findAllFacts").getResultList();
        em.clear();// detach all entities
        return facts;
    }

    @Override
    public Fact findFactByID(long id) {
       return em.find(Fact.class, id);
//        return facts.get(id);
    }

    @Override
    public long createFact(Fact fact) {
        em.persist(fact);
        em.flush();
        em.detach(fact);
        System.out.println("Fact id: " + fact.getId());
        return fact.getId();

//        fact.setId(facts.size() + 1);
//        facts.put(fact.getId(), fact);
//        return fact.getId();
    }

    @Override
    public void updateFact(Fact fact) {
        
        em.merge(fact);
        
//        facts.put(fact.getId(), fact);
    }

    @Override
    public void deleteFact(long id) {
        em.remove(id);
//        facts.remove(id);
    }
}
