/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.dto;

/**
 *
 * @author Simone Righitto
 */
public class CounterFactDTO extends FactDTO {

    private long sensorId;
    private int numberOfObservationsDone;

    public CounterFactDTO(long sensorId, int numberOfObservationsDone) {
        this.sensorId = sensorId;
        this.numberOfObservationsDone = numberOfObservationsDone;
    }

    public CounterFactDTO(long sensorId, int numberOfObservationsDone, long id, String type, String visibility, long organizationOwnerId) {
        super(id, type, visibility, organizationOwnerId);
        this.sensorId = sensorId;
        this.numberOfObservationsDone = numberOfObservationsDone;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public int getNumberOfObservationsDone() {
        return numberOfObservationsDone;
    }

    public void setNumberOfObservationsDone(int numberOfObservationsDone) {
        this.numberOfObservationsDone = numberOfObservationsDone;
    }

}
