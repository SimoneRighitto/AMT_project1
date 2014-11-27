/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author Simone Righitto
 */
@Stateless
public class UsersManager implements UsersManagerLocal {

    private Map<Long, User> users = new HashMap<>();

    public UsersManager() {

//        users.put(1L, new User(1L, "User1", "password1", "user3@mail.com", 11L));
//        users.put(1L, new User(1L, "User2", "password2", "user3@mail.com", 11L));
//        users.put(1L, new User(1L, "User2", "password3", "user3@mail.com", 22L));

    }

    @Override
    public User findUserByID(long id) {
        return users.get(id);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList(users.values());
    }

    @Override
    public long createUser(User user) {
        user.setId(users.size()+1);
        users.put(user.getId(), user);
        
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(long id) {
        users.remove(id);
    }
}
