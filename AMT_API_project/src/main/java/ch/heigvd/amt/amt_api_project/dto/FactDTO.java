/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Simone Righitto
 */
public class FactDTO {

    private long id;
    private String type;
    private String visibility;
    private long organizationOwnerId;

    private List<Double> infos;
    private long sensorId;
    private Date dayDate;

    public FactDTO() {
    }

    public FactDTO(long id, String type, String visibility, long organizationOwnerId, long sensorId) {
        this.id = id;
        this.type = type;
        this.visibility = visibility;
        this.organizationOwnerId = organizationOwnerId;
        this.sensorId = sensorId;
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

    public long getOrganizationOwnerId() {
        return organizationOwnerId;
    }

    public void setOrganizationOwnerId(long organizationOwnerId) {
        this.organizationOwnerId = organizationOwnerId;
    }

    public List<Double> getInfos() {
        return infos;
    }

    public void setInfos(List<Double> infos) {
        this.infos = infos;
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
