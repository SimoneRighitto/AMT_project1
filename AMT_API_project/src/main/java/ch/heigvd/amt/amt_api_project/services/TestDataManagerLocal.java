/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 4-dic-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import javax.ejb.Local;

/**
 *
 * @author Simone Righitto
 */
@Local
public interface TestDataManagerLocal {

    void generateTestData();
    
    void resetTestData();
    
}
