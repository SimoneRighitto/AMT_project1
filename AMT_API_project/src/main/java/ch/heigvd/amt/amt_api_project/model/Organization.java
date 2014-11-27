/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Simone Righitto
 */

@Entity
public class Organization implements Serializable {

    @Id
    private long id;
    private String name;
    @OneToMany
    private List<Sensor> sensors;
    @OneToMany
    private List<User> users;
    @OneToOne
    private User contatUser;
    @OneToMany
    private List<Fact> facts;

    public Organization() {
    }

    public Organization(long id, String name, List<Sensor> sensors, List<User> users, User contatUser, List<Fact> facts) {
        this.id = id;
        this.name = name;
        this.sensors = sensors;
        this.users = users;
        this.contatUser = contatUser;
        this.facts = facts;
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
