package mvc.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import game.IleInterdite;
import mvc.Message;
import mvc.view.IHM;

/**
 *
 * @author turbetde
 */
public class Controlleur implements IControlleur {

    private IHM ihm;
    private IleInterdite ileInterdite;
    
    public Controlleur(IHM ihm, IleInterdite ileInterdite) {
        this.ihm = ihm;
        ihm.setObservateur(this);
        this.ileInterdite = ileInterdite;
        ileInterdite.setObservateur(this);
    }
    
    @Override
    public void traiterMessage(Message msg) {

        switch (msg.type) {

            case START:
                ihm.setVue("menu");
                break;
            case CONFIG:
                ihm.setVue(msg.vue);
                break;
            case JOUER:
                ihm.setVue(msg.vue);
                ileInterdite.commencerPartie();

                break;
            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(msg.grille);
                break;
            case QUITTER:
                ileInterdite.quitter();
                break;
            case UPDATE_IHM:
                //A COMPLETER !!
                System.out.println("code à completer");
            case RETOUR:
                ihm.setVue(msg.vue);
        }
    }

}
