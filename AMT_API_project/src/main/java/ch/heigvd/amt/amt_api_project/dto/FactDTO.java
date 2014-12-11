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
public class FactDTO {

   private long id;
    private String information;
    private String type;
    private String visibility;
    private long organizationOwnerId;

    public FactDTO() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public void setOrganizationOwner(long organizationOwnerId) {
        this.organizationOwnerId = organizationOwnerId;
    }
    
   

   
    
}
