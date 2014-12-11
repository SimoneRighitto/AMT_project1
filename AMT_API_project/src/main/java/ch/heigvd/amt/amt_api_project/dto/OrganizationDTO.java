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
    private List<Long> sensors;
    private List<Long> users;
    private long contatUserId;
    private List<Long> facts;

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

    public List<Long> getSensors() {
        return sensors;
    }

    public void setSensors(List<Long> sensors) {
        this.sensors = sensors;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public long getContatUserId() {
        return contatUserId;
    }

    public void setContatUserId(long contatUserId) {
        this.contatUserId = contatUserId;
    }

    public List<Long> getFacts() {
        return facts;
    }

    public void setFacts(List<Long> facts) {
        this.facts = facts;
    }
}
