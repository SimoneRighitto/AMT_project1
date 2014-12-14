/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Organization;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class OrganizationManager implements OrganizationManagerLocal {


    @PersistenceContext
    EntityManager em;

    public OrganizationManager() {

    }

    @Override
    public Organization findOrganizationByID(long id) {

        return em.find(Organization.class, id);
    }

    @Override
    public List<Organization> findAllOrganization() {
        List<Organization> organizations;
        organizations = em.createNamedQuery("findAll").getResultList();
        em.clear();// detach all entities
        return organizations;
    }

    @Override
    public long createOrganization(Organization organization) {
        em.persist(organization);
        em.flush();
        return organization.getId();
    }

    @Override
    public void updateOrganization(Organization organization) {
        em.merge(organization);
    }

    @Override
    public void deleteOrganization(long id) {
        em.remove(id);
    }
}
