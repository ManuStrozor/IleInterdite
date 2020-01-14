package mvc.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import game.IleInterdite;
import game.Tuile;
import mvc.Message;
import mvc.view.IHM;

import java.util.ArrayList;

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
                ileInterdite.commencerPartie(msg.nbJoueur, msg.niveauEau, msg.nomsJoueurs);
                break;
            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(msg.grille);
                break;
            case UPDATE_DASHBOARD:
                ihm.getVue("jeu").updateDashboard(msg.aventuriers);
                break;
            case QUITTER:
                ileInterdite.quitter();
                break;
            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;
            case DEPLACEMENT:
                ArrayList<Tuile> tuiles = ileInterdite.getCurrentAventurier().getTuilesAccessibles(ileInterdite.getGrille());
                ihm.getVue("jeu").afficherTuilesAccessibles(tuiles);
                break;
            case ECHANGE_CARTE:
                System.out.println("echangerCarte()");
                break;
            case ASSECHER_TUILE:

                ileInterdite.assecher(ileInterdite.getCurrentAventurier().getTuile(), ileInterdite.getCurrentAventurier());
                System.out.println(" assécher la tuile choisie par le joueur"); // ici la tuile ou se trouve le joueur

                break;
            case RECUPERER_TRESOR:
                System.out.println("recupererTresor()");
                break;
            case NIVEAU_EAU:
                ileInterdite.setNiveauEau(msg.niveauEau);
        }
    }

}
