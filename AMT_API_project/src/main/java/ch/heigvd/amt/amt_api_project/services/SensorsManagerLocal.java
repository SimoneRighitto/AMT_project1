/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 27-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Sensor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Simone Righitto
 */
@Local
public interface SensorsManagerLocal {
    
    public Sensor findSensorByID(long id);

    public long createSensor(Sensor organization);

    public void updateSensor(Sensor organization);

    public void deleteSensor(long id);
}
