/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Simone Righitto
 */
@Table(name="sensor")

@Entity
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private String type;
    private String visibility;
    @ManyToOne
    private Organization organizationOwner;

    public Sensor() {
    }

    public Sensor(long id, String name, String description, String type, String visibility, Organization organizationOwner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.visibility = visibility;
        this.organizationOwner = organizationOwner;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Organization getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(Organization organizationOwner) {
        this.organizationOwner = organizationOwner;
    }

   
    
}
