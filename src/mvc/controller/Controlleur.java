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

            case JOUER:
                ihm.setVue(msg.vue);
                ileInterdite.commencerPartie();
                ileInterdite.setNbJoueurs(msg.nbJoueur);
                break;
            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(msg.grille);
                break;
            case QUITTER:
                ileInterdite.quitter();
                break;
            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;
            case DEPLACEMENT:
                System.out.println("sedeplacer()");
                break;
            case ECHANGE_CARTE:
                System.out.println("echangerCarte()");
                break;
            case ASSECHER_TUILE:
                System.out.println("assecher()");
                break;
            case RECUPERER_TRESOR:
                System.out.println("recupererTresor()");
                break;
        }
    }

}
