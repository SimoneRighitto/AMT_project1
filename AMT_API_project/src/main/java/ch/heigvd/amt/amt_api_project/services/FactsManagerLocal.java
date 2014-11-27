/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Maillard
 */
@Local
public interface FactsManagerLocal {

    public List<Fact> findAllFacts();

    public Fact findFactByID(long id);

    public long createFact(Fact toFact);

    public void updateFact(Fact toFact);

    public void deleteFact(long id);
    
}
