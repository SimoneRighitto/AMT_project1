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

/**
 *
 * @author Maillard
 */
@Stateless
public class FactsManager implements FactsManagerLocal {
    
    private Map<Long, Fact> facts = new HashMap<>();

    public FactsManager() {

        //facts.put(1L, new Fact(1L, "info", "type", "visibility", 11L));

    }

    @Override
    public List<Fact> findAllFacts() {
        return new ArrayList<Fact>(facts.values());
    }

    @Override
    public Fact findFactByID(long id) {
        return facts.get(id);
    }

    @Override
    public long createFact(Fact fact) {
        fact.setId(facts.size() + 1);
        facts.put(fact.getId(), fact);
        return fact.getId();
    }

    @Override
    public void updateFact(Fact fact) {
        facts.put(fact.getId(), fact);
    }

    @Override
    public void deleteFact(long id) {
        facts.remove(id);
    }
}
