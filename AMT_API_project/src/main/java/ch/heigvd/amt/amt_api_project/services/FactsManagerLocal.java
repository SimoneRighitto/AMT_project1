/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Maillard
 */
@Local
public interface FactsManagerLocal {

    public List<Fact> findAllFacts();
    
    public List<Fact> findFactsBySensorId(long sensorId);
       
    
    public Fact findFactBySensorIdAndType(long sensorId, String type);
    public Fact findFactBySensorIdAndTypeAndDate(long sensorId, String type, Date date);
    
    
    public Fact findFactByID(long id);

    public long createFact(Fact toFact);

    public void updateFact(Fact toFact);

    public void deleteFact(long id);
    
    void deleteAll();
    
}
