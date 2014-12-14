/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class UsersManager implements UsersManagerLocal {


    @PersistenceContext
    EntityManager em;

    public UsersManager() {
    }

    @Override
    public User findUserByID(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users;
        users = em.createNamedQuery("findAllUsers").getResultList();
        em.clear();// detach all entities
        return users;
    }

    @Override
    public long createUser(User user) {
        em.persist(user);
        em.flush();
        em.detach(user);
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(id);
    }
}
