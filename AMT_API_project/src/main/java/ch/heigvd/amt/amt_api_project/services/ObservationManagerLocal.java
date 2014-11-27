/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.amt_api_project.services;

import ch.heigvd.amt.amt_api_project.model.Observation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Maillard
 */
@Local
public interface ObservationManagerLocal {

    public Observation findObservationByID(long id);

    public long createObservation(Observation toObservation);

}
