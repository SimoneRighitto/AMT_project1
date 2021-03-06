/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.dto;

import ch.heigvd.amt.amt_api_project.model.Organization;

/**
 *
 * @author Simone Righitto
 */
public class SensorDTO {

    private long id;
    private String name;
    private String description;
    private String type;
    private String visibility;
    private long organizationOwnerId;

    public SensorDTO() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

   
    
}
