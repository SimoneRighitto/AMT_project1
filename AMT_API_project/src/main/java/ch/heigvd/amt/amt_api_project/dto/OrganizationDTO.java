/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.dto;

/**
 *
 * @author Simone Righitto
 */
public class OrganizationDTO {

    private long id;
    private String name;
    private long contactUserID;

    public OrganizationDTO() {
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

    public long getContactUserID() {
        return contactUserID;
    }

    public void setContactUserID(long contactUserID) {
        this.contactUserID = contactUserID;
    }

    
   
    
}
