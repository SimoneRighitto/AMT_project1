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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Simone Righitto
 */
@Table(name="Fact")
@NamedQueries(
        {
            @NamedQuery(
                    name = "findAll",
                    query = "SELECT f FROM Fact f"
            )
        }
)

@Entity
public class Fact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String information;
    private String type;
    private String visibility;
    @ManyToOne
    private Organization organizationOwner;

    public Fact() {
    }

    public Fact(String information, String type, String visibility, Organization organizationOwner) {
     
        this.information = information;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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
