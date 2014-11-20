/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Simone Righitto
 */
@Local
public interface UsersManagerLocal {

    public User findUserByID(long id);

    public List<User> findAllUsers();

    public long createUser(User user);

    public void updateUser(User user);

    public void deleteUser(long id);
}
