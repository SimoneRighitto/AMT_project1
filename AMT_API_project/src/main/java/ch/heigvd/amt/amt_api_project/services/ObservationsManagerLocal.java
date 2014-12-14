/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Observation;
import javax.ejb.Local;

/**
 *
 * @author Maillard
 */
@Local
public interface ObservationsManagerLocal {

    public Observation findObservationByID(long id);

    public long createObservation(Observation toObservation);

}
