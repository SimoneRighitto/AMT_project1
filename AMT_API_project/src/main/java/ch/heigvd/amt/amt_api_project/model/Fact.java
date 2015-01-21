/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Simone Righitto
 */
@NamedQueries(
        {
            @NamedQuery(
                    name = "findAllFacts",
                    query = "SELECT f FROM Fact f"
            ),
           
            @NamedQuery(
                    name = "findFactBySensorIdAndType",
                    query = "SELECT f FROM Fact f WHERE f.sensor.id = :sensorId AND f.type = :type"),
            @NamedQuery(
                    name = "findFactBySensorIdAndTypeAndDate",
                    query = "SELECT f FROM Fact f WHERE f.sensor.id = :sensorId AND f.type = :type AND f.dayDate = :date"),
            @NamedQuery(name="Fact.deleteAll", query="DELETE FROM Fact")
        })

@Entity
public class Fact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private String visibility;
    @ManyToOne
    private Organization organizationOwner;
    
    private HashMap infos;
    @ManyToOne
    private Sensor sensor;
    @Temporal(TemporalType.DATE)
    private Date dayDate;

    public Fact() {
    }

    public Fact(String type, String visibility, Organization organizationOwner) {

        this.type = type;
        this.visibility = visibility;
        this.organizationOwner = organizationOwner;
    }

    public Fact(String type, String visibility, Organization organizationOwner, Sensor sensor, Date dayDate) {
        this.type = type;
        this.visibility = visibility;
        this.organizationOwner = organizationOwner;
        this.sensor = sensor;
        this.dayDate = dayDate;
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public HashMap getInfos() {
        return infos;
    }

    public void setInfos(HashMap infos) {
        this.infos = infos;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

   
}
