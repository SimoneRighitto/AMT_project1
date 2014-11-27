/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

/**
 *
 * @author Simone Righitto
 */
public class Organization {

    private long id;
    private String name;
    private long contactUserID;

    public Organization() {
    }

    public Organization(long id, String name, long contactUserID) {
        this.id = id;
        this.name = name;
        this.contactUserID = contactUserID;
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
