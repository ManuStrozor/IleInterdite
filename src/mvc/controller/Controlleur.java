package mvc.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
import enumerations.TypeMessage;
import game.Grille;
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
    private IleInterdite ile;
    private TypeMessage lastAction = null;
    
    public Controlleur(IHM ihm, IleInterdite ile) {
        this.ihm = ihm;
        ihm.setObservateur(this);
        this.ile = ile;
        ile.setObservateur(this);
    }
    
    @Override
    public void traiterMessage(Message msg) {

        Grille grille = ile.getGrille();

        switch (msg.type) {

            case JOUER:
                ihm.setVue("jeu");
                ihm.getVue("jeu").initBoards(msg.nbJoueur);
                ile.commencerPartie(msg.nbJoueur, msg.niveauEau, msg.nomsJoueurs);
                break;

            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(grille);
                break;

            case UPDATE_DASHBOARD:
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;

            case DEPLACEMENT:
                ArrayList<Tuile> tuiles = ile.getJoueur().getTuilesAccessibles(grille);
                ihm.getVue("jeu").afficherTuilesAccessibles(grille, tuiles);
                break;

            case  SAUVER:
                ArrayList<Tuile> t =msg.a.getTuilesAccessibles(grille);
                ihm.getVue("jeu").afficherTuilesAccessibles(grille, t);
                break;

            case ASSECHER_TUILE:
                ArrayList<Tuile> tuiless = ile.getJoueur().peutAssecher(grille);
                ihm.getVue("jeu").afficherTuilesAccessibles(grille, tuiless);
                break;

            case CLIK_TUILE:
                switch (lastAction) {
                    case DEPLACEMENT:
                        ile.seDeplacer(ile.getJoueur(), grille.getTuile(msg.tuileIndex));
                        break;
                    case SAUVER:
                        ile.seDeplacer(ile.getJoueurASauver(), grille.getTuile(msg.tuileIndex));
                        break;
                    case ASSECHER_TUILE:
                        ile.assecher(ile.getJoueur(), grille.getTuile(msg.tuileIndex));
                        break;
                    case HELICO:
                        break;
                    case SABLE:
                        break;
                }
                ihm.getVue("jeu").updateGrille(grille);
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;

            case CLIK_CARTE:


            case ECHANGE_CARTE:
                System.out.println("echangerCarte()");
                ihm.getVue("jeu").afficherAventurierAccessibles(ile.getJoueur().aventuriersAccessible(ile.getAventuriers()));
                break;

            case RECUPERER_TRESOR:
                System.out.println("recupererTresor()");
                break;
            case PASSERTOUR:
                ile.passerTour();
                ihm.getVue("jeu").updateGrille(ile.getGrille());
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;

            case NIVEAU_EAU:
                ile.setNiveauEau(msg.niveauEau);
                break;

            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;

            case QUITTER:
                ile.quitter();
                break;
        }
        lastAction = msg.type;
    }

}
