/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

/**
 *
 * @author Simone Righitto
 */
public class Fact {

   private long id;
    private String information;
    private String type;
    private String visibility;
    private Organization organizationOwner;

    public Fact() {
    }

    public Fact(long id, String information, String type, String visibility, Organization organizationOwner) {
        this.id = id;
        this.information = information;
        this.type = type;
        this.visibility = visibility;
        this.organizationOwner = organizationOwner;
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

    public Organization getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(Organization organizationOwner) {
        this.organizationOwner = organizationOwner;
    }
    
   

   
    
}
