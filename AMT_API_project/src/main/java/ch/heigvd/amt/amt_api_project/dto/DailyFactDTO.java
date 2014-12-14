/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.dto;

import java.util.Date;

/**
 *
 * @author Simone Righitto
 */
public class DailyFactDTO extends FactDTO {

    private long sensorId;
    private Date dayDate;


    public DailyFactDTO(long sensorId, Date dayDate, long id, String type, String visibility, long organizationOwnerId) {
        super(id, type, visibility, organizationOwnerId);
        this.sensorId = sensorId;
        this.dayDate = dayDate;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }


    


   

    
}
