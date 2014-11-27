/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

import java.util.Date;

/**
 *
 * @author Simone Righitto
 */
public class Observation {

    private long id;
    private Date time;
    private double value;
    private Sensor sourceSensor;
    

    public Observation() {
    }

    public Observation(long id, Date time, double value, Sensor sourceSensor) {
        this.id = id;
        this.time = time;
        this.value = value;
        this.sourceSensor = sourceSensor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Sensor getSourceSensor() {
        return sourceSensor;
    }

    public void setSourceSensor(Sensor sourceSensor) {
        this.sourceSensor = sourceSensor;
    }
    
   
    
}
