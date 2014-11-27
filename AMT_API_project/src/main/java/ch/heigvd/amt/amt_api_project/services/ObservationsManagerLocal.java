/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
