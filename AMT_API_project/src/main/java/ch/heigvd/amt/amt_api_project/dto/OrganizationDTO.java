/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.dto;

import ch.heigvd.amt.amt_api_project.model.Fact;
import ch.heigvd.amt.amt_api_project.model.Sensor;
import ch.heigvd.amt.amt_api_project.model.User;
import java.util.List;

/**
 *
 * @author Simone Righitto
 */
public class OrganizationDTO {

    private long id;
    private String name;
    private List<Sensor> sensors;
    private List<User> users;
    private User contatUser;
    private List<Fact> facts;

    public OrganizationDTO() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getContatUser() {
        return contatUser;
    }

    public void setContatUser(User contatUser) {
        this.contatUser = contatUser;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }


    
    

   
    
}
