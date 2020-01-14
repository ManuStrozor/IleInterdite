package mvc.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
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
                ihm.getVue("jeu").initBoards(msg.nbJoueur);
                ileInterdite.commencerPartie(msg.nbJoueur, msg.niveauEau, msg.nomsJoueurs);
                break;
            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(ileInterdite.getGrille());
                break;
            case UPDATE_DASHBOARD:
                ihm.getVue("jeu").updateDashboard(ileInterdite.getAventuriers());
                break;
            case QUITTER:
                ileInterdite.quitter();
                break;
            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;
            case DEPLACEMENT:
                ArrayList<Tuile> tuiles = ileInterdite.getCurrentAventurier().getTuilesAccessibles(ileInterdite.getGrille());
                ihm.getVue("jeu").afficherTuilesAccessibles(ileInterdite.getGrille(), tuiles);
                break;
            case BOUGER:
                ileInterdite.seDeplacer(ileInterdite.getCurrentAventurier(), ileInterdite.getGrille().getTuile(msg.tuileIndex));
                ihm.getVue("jeu").updateGrille(ileInterdite.getGrille());
                ihm.getVue("jeu").updateDashboard(ileInterdite.getAventuriers());
                break;
            case ECHANGE_CARTE:
                System.out.println("echangerCarte()");
                ArrayList<Aventurier> aventuriers = ileInterdite.getCurrentAventurier().aventurierAccessibles(ileInterdite.getCurrentAventurier());
                ihm.getVue("jeu").afficherAventurierAccessibles(aventuriers);
                break;
            case ASSECHER_TUILE:
                //ileInterdite.assecher(ileInterdite.getCurrentAventurier().getTuile());
                //System.out.println(" assécher la tuile choisie par le joueur"); // ici la tuile ou se trouve le joueur

                ArrayList<Tuile> tuiless = ileInterdite.getCurrentAventurier().peutAssecher(ileInterdite.getGrille());
                ihm.getVue("jeu").afficherTuilesAccessibles(ileInterdite.getGrille(), tuiless);
                break;
            case RECUPERER_TRESOR:
                System.out.println("recupererTresor()");
                break;
            case NIVEAU_EAU:
                ileInterdite.setNiveauEau(msg.niveauEau);
        }
    }

}
