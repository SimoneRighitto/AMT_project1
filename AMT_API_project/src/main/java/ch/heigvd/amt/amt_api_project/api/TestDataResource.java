package ch.heigvd.amt.amt_api_project.api;

import ch.heigvd.amt.amt_api_project.services.TestDataManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Simone Righitto
 */
@Path("generate")
@Stateless
public class TestDataResource implements TestDataResourceLocal {
    @EJB
    private TestDataManagerLocal testDataManager;

    @GET
    public String generateTestData() {
        testDataManager.generateTestData();
        return "Data generated.";
    }
    
}
