/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.dto;

import ch.heigvd.amt.amt_api_project.model.Sensor;
import java.util.Date;

/**
 *
 * @author Simone Righitto
 */
public class ObservationDTO {

    private long id;
    private Date time;
    private double value;
    private long sourceSensorId;
    

    public ObservationDTO() {
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

    public long getSourceSensor() {
        return sourceSensorId;
    }

    public void setSourceSensor(long sourceSensor) {
        this.sourceSensorId = sourceSensor;
    }
}
