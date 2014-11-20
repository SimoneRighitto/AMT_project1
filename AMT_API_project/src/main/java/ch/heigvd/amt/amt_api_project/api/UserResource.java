/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.dto.UserDTO;
import ch.heigvd.amt.amt_api_project.model.User;
import ch.heigvd.amt.amt_api_project.services.UsersManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Simone Righitto
 */
@Path("users")
@Stateless
public class UserResource {

    @EJB
    UsersManagerLocal usersManager;

    @Context
    private UriInfo context;

    public UserResource() {

    }

    @GET
    @Produces("application/json")
    public List<UserDTO> getAllUsers() {

        List<User> users = usersManager.findAllUsers();
        List<UserDTO> result = new ArrayList<>();

        for (User u : users) {
            result.add(toDTO(u));
        }

        return result;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUserDetails(@PathParam("id") long id) {
        return toDTO(usersManager.findUserByID(id));
    }

    @POST
    @Consumes("application/json")
    public long createUser(UserDTO dtoUser) {
        User newUser = new User();
        long id = usersManager.createUser(toUser(dtoUser, newUser));
        return id;
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateUser(@PathParam("id") long id, UserDTO dtoUser) {
        User existingUser = usersManager.findUserByID(id);
        usersManager.updateUser(toUser(dtoUser, existingUser));
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") long id) {
        usersManager.deleteUser(id);
    }

    /*
     * Conversion utility methods
     */
    private User toUser(UserDTO dtoUser, User originalUser) {
        originalUser.setEmail(dtoUser.getEmail());
        originalUser.setName(dtoUser.getName());
        originalUser.setOrganizationID(dtoUser.getOrganizationID());
        
        return originalUser;
    }

    private UserDTO toDTO(User user) {
        UserDTO dtoUser=  new UserDTO();
        dtoUser.setId(user.getId());
        dtoUser.setEmail(user.getEmail());
        dtoUser.setName(user.getName());
        dtoUser.setOrganizationID(user.getOrganizationID());
        
        return dtoUser;
    }
}
