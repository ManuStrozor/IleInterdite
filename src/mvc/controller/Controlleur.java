package mvc.controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
import enumerations.Role;
import enumerations.Tresor;
import enumerations.TypeMessage;
import game.CarteTresor;
import game.Grille;
import game.IleInterdite;
import game.Tuile;
import mvc.Message;
import mvc.view.IHM;
import mvc.view.VueGameOver;

import java.util.ArrayList;

/**
 *
 * @author turbetde
 */
public class Controlleur implements IControlleur {

    private IHM ihm;
    private IleInterdite ile;
    private TypeMessage lastAction = null;
    private int indexCible;
    private VueGameOver VueGameOver;

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

            case UPDATE_IHM:
                ihm.getVue("jeu").afficherTitreJoueur(ile.getJoueur().getNomJoueur());
                ihm.getVue("jeu").updateGrille(grille);
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;
            case DEPLACEMENT:
                if(ile.getJoueur().getNbActions() < 1) { // Helico...verifier lastAction
                    ile.passerTour();
                } else {
                    ArrayList<Tuile> tuiles = ile.getJoueur().getTuilesAccessibles(grille);
                    ihm.getVue("jeu").afficherTuilesAccessibles(grille, tuiles);
                }
                break;

            case SAUVER:
                ArrayList<Tuile> t = msg.a.getTuilesAccessibles(grille);
                ihm.getVue("jeu").afficherTuilesAccessibles(grille, t);
                break;

            case ASSECHER_TUILE:
                if(ile.getJoueur().getNbActions() < 1 && ile.getJoueur().getRole() != Role.ingenieur) { // Sace de sable Verifier lastAction
                    ile.passerTour();
                } else {
                    ArrayList<Tuile> tuiless = ile.getJoueur().peutAssecher(grille);
                    ihm.getVue("jeu").afficherTuilesAccessibles(grille, tuiless);
                }
                break;

            case ECHANGE_CARTE:
                ArrayList<Aventurier> a = ile.getJoueur().aventuriersAccessible(ile.getAventuriers());
                ihm.getVue("jeu").afficherAventurierAccessibles(ile.getAventuriers(), a);
                break;

            case CLIK_TUILE:
                switch (lastAction) {
                    case DEPLACEMENT:
                        ile.seDeplacer(ile.getJoueur(), grille.getTuile(msg.index));
                        break;
                    case SAUVER:
                        ile.seDeplacer(ile.getJoueurASauver(), grille.getTuile(msg.index));
                        break;
                    case ASSECHER_TUILE:
                        ile.assecher(ile.getJoueur(), grille.getTuile(msg.index));
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
                Aventurier av = ile.getAventuriers().get(msg.indexAventurier);
                CarteTresor c = av.getInventaire().get(msg.index);

                if(c.getTresor() != Tresor.Helicoptere && c.getTresor() != Tresor.Sac_De_Sable) {
                    ile.donnerCarte(ile.getJoueur().getInventaire().get(msg.index), ile.getAventuriers().get(indexCible));

                } else if(c.getTresor() == Tresor.Helicoptere) {
                    System.out.println("Helicoptere");
                } else if(c.getTresor() == Tresor.Sac_De_Sable) {
                    System.out.println("Sac de sable");
                } else {

                }
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;

            case CLIK_JOUEUR:
                ihm.getVue("jeu").afficherCartesAccessibles(ile.getAventuriers(), ile.getJoueur());
                break;

            case RECUPERER_TRESOR:
                ile.recupererTresor(ile.getJoueur().getTuile().getTresor());
                break;

            case PASSERTOUR:
                ile.passerTour();
                break;

            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;

            case QUITTER:
                ile.quitter();
                break;
        }
        lastAction = msg.type;
        indexCible = msg.indexAventurier;

        if(ile.getJoueur() !=null&&ile.getJoueur().getNbActions()>0){
            if(ile.estRecuperable(ile.getJoueur())){
                ihm.getVue("jeu").rendreBoutonsClicables(true);
            }
            else {
                ihm.getVue("jeu").rendreBoutonsClicables(false);
            }
        }
        if (ile.getJoueur() != null && ile.getJoueur().getNbActions() == 0) {
            ile.passerTour();
        }

    }

}
