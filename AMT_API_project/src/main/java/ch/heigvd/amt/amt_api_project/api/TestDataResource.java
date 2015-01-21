/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */

package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.services.TestDataManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Simone Righitto
 */
@Path("data")
@Stateless
public class TestDataResource {
    @EJB
    private TestDataManagerLocal testDataManager;

    @GET
    @Path("generate")
    public String generateTestData() {
        testDataManager.generateTestData();
        return "Data generated.";
    }
    
    @POST
    @Path("reset")
    public String resetTestData(){
        testDataManager.resetTestData();
        return "Reset done";
    }
    
}
