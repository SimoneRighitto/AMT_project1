/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Maillard
 */
@Stateless
public class FactsManager implements FactsManagerLocal {

    @PersistenceContext
    EntityManager em;

    public FactsManager() {

    }

    @Override
    public List<Fact> findAllFacts() {

        List<Fact> facts;
        facts = em.createNamedQuery("findAllFacts").getResultList();
        em.clear();// detach all entities
        return facts;
    }

    @Override
    public Fact findFactByID(long id) {
        return em.find(Fact.class, id);

    }

    @Override
    public long createFact(Fact fact) {
        em.persist(fact);
        em.flush();
        em.detach(fact);
        return fact.getId();

    }

    @Override
    public void updateFact(Fact fact) {

        em.merge(fact);

    }

    @Override
    public void deleteFact(long id) {
        em.remove(id);

    }

    @Override
    public List<Fact> findFactsBySensorId(long sensorId) {

        return em.createNamedQuery("findFactsBySensorId").setParameter("sensorId", sensorId).getResultList();

    }

    @Override
    public Fact findFactBySensorIdAndType(long sensorId, String type) {
        try {
            return (Fact) em.createNamedQuery("findFactBySensorIdAndType").setParameter("sensorId", sensorId).setParameter("type", type).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Fact findFactBySensorIdAndTypeAndDate(long sensorId, String type, Date date) {
        try {
            return (Fact) em.createNamedQuery("findFactBySensorIdAndTypeAndDate").setParameter("sensorId", sensorId).setParameter("type", type).setParameter("date", date, TemporalType.DATE).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
        @Override
    public void deleteAll() {
        Query query = em.createNamedQuery("Fact.deleteAll");
        query.executeUpdate();
    }

}
