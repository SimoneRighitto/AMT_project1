/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Simone Righitto
 */
@NamedQueries(
        {
            @NamedQuery(
                    name = "findAll",
                    query = "SELECT o FROM Organization o"
            ),
            @NamedQuery(name="Organization.deleteAll", query="DELETE FROM Organization")
        }
)

@Entity
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "organizationOwner", orphanRemoval=true)
    private List<Sensor> sensors = new ArrayList<>();
    @OneToMany(mappedBy = "organization", orphanRemoval=true)
    private List<User> users = new ArrayList<>();
    @OneToOne(orphanRemoval=true)
    private User contactUser;
    @OneToMany(mappedBy = "organizationOwner", orphanRemoval=true)
    private List<Fact> facts = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
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

    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

}
