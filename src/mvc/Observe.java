package mvc;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc.controller.IControlleur;

/**
 *
 * @author turbetde
 */
public class Observe {

    private IControlleur observateur;

    public void setObservateur(IControlleur o) {
        this.observateur = o;
    }

    public void notifierObservateur(Message m) {

        if (observateur != null) {
            observateur.traiterMessage(m);
        }
    }
}
