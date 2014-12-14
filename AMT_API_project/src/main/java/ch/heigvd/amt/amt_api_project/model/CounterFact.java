/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *
 * @author Simone Righitto
 */
@Table(name = "CounterFact")
@NamedQueries(
        {
            @NamedQuery(
                    name = "getObservationsNumberForSensor",
                    query = "SELECT f.numberOfObservationsDone FROM CounterFact f WHERE f.id = :sensorId"
            ),
            @NamedQuery(
                    name = "findCounterFactBySensorId",
                    query = "SELECT f FROM CounterFact f WHERE f.id = :sensorId"
            )

        }
)
@Entity
public class CounterFact extends Fact implements Serializable {

    @ManyToOne
    private Sensor sensor;
    private int numberOfObservationsDone;

    public CounterFact() {
    }

    public CounterFact(Sensor sensor, String type, String visibility, Organization organizationOwner) {
        super(type, visibility, organizationOwner);
        this.sensor = sensor;
        numberOfObservationsDone=0;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getNumberOfObservationsDone() {
        return numberOfObservationsDone;
    }

    public void setNumberOfObservationsDone(int numberOfObservationsDone) {
        this.numberOfObservationsDone = numberOfObservationsDone;
    }

    public void incrementNumberOfObservationsDone(){
        numberOfObservationsDone++;
    }
    
   
   

}
