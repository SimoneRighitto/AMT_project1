/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Simone Righitto
 */
@Table(name = "DailyFact")
@NamedQueries(
        {
            @NamedQuery(
                    name = "findFactBySensorIdAndDate",
                    query = "SELECT f FROM DailyFact f WHERE f.id = :sensorId AND f.dayDate = :date"
            )

        }
)

@Entity
public class DailyFact extends Fact implements Serializable {

    @ManyToOne
    private Sensor sensor;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dayDate;
    private List<Double> observedValues;
    private double averageValue;
    private double minValue;
    private double maxValue;

    public DailyFact() {
    }

    public DailyFact(Sensor sensor, Date date, String type, String visibility, Organization organizationOwner) {
        super(type, visibility, organizationOwner);
        this.sensor = sensor;
        this.dayDate = date;
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

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public List<Double> getObservedValues() {
        return observedValues;
    }

    public void setObservedValues(List<Double> observedValues) {
        this.observedValues = observedValues;
    }

    public void addValue(double value) {
        if (observedValues != null) {
            observedValues.add(value);
            checkMinMaxAvg();
        }
    }

    private void checkMinMaxAvg() {
        minValue = Collections.min(observedValues);
        maxValue = Collections.max(observedValues);

        double sum = 0.0;
        for (Double v : observedValues) {
            sum += v;
        }
        if (sum != 0) {
            averageValue = sum / observedValues.size();
        }
    }

}
