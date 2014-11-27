/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.model;

/**
 *
 * @author Simone Righitto
 */
public class User {

    private long id;
    private String name;
    private String password;
    private String email;
    private Organization organization;
    private boolean isContact;
    

    public User() {
    }

    public User(long id, String name, String password, String email, Organization organization, boolean isContact) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.organization = organization;
        this.isContact = isContact;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isIsContact() {
        return isContact;
    }

    public void setIsContact(boolean isContact) {
        this.isContact = isContact;
    }

    

   
}

