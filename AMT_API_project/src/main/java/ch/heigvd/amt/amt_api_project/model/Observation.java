/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author Simone Righitto
 */
@NamedQueries({
    @NamedQuery(
            name = "findAllObservations",
            query = "SELECT o FROM Observation o"),
    @NamedQuery(
            name = "findAverageObservationByDay",
            query = "SELECT AVG(o.observedValue) FROM Observation o WHERE o.sourceSensor.id = :sensorId AND o.observedAt BETWEEN :start AND :end")
})
@Entity
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date observedAt;
    private double observedValue;
    @ManyToOne
    private Sensor sourceSensor;

    public Observation() {
    }

    public Observation(Date time, double value, Sensor sourceSensor) {

        this.observedAt = time;
        this.observedValue = value;
        this.sourceSensor = sourceSensor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(Date observedAt) {
        this.observedAt = observedAt;
    }

    public double getObservedValue() {
        return observedValue;
    }

    public void setObservedValue(double observedValue) {
        this.observedValue = observedValue;
    }

    public Sensor getSourceSensor() {
        return sourceSensor;
    }

    public void setSourceSensor(Sensor sourceSensor) {
        this.sourceSensor = sourceSensor;
    }

}
