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
    //private boolean x =false;

    public Controlleur(IHM ihm, IleInterdite ile) {
        this.ihm = ihm;
        ihm.setObservateur(this);
        this.ile = ile;
        ile.setObservateur(this);
        ihm.setVue("menu");
    }

    @Override
    public void traiterMessage(Message msg) {

        Grille grille = ile.getGrille();

        if(lastAction == TypeMessage.CLIK_CARTE && msg.type != TypeMessage.CLIK_CARTE) {
            ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
        }

        switch (msg.type) {

            case JOUER:
                ihm.setVue("jeu");
                ihm.getVue("jeu").initBoards(msg.nbJoueur);
                ile.setNbJoueurs(msg.nbJoueur);
                ile.setNiveauEau(msg.niveauEau);
                ile.commencerPartie(msg.nomsJoueurs);
                break;

            case UPDATE_GRILLE:
                ihm.getVue("jeu").updateGrille(grille);
                break;

            case UPDATE_DASHBOARD:
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                break;

            case UPDATE_IHM:
                ihm.getVue("jeu").updateJoueur(ile.getJoueur().getRole().name() + " (" + ile.getJoueur().getNomJoueur() + ")");
                ihm.getVue("jeu").updateNiveauEau(ile.getNiveauEau());
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
            case HELICO: //Clic joueur
                //ArrayList<Aventurier> y = ile.getJoueur().peutDonnerA(ile.getAventuriers());
                //ihm.getVue("jeu").afficherAventurierAccessibles(ile.getAventuriers(),y);
                //System.out.println(" case HELICO 1");
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

            case DONNER_CARTE:
                ArrayList<Aventurier> a = ile.getJoueur().peutDonnerA(ile.getAventuriers());
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
                    case CLIK_JOUEUR:
                        //if (x==true){
                        //    System.out.println("case HELICO>JOUEUR>TUILE 3");
                        //    ile.seDeplacer(ile.getAventuriers().get(msg.indexAventurier),grille.getTuile(msg.index));
                        //}
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
                    ihm.getVue("jeu").afficherAventurierAccessibles(ile.getAventuriers(),ile.getAventuriers());
                    System.out.println("Helicoptere");
                    //ile.utiliserHelico();

                } else if(c.getTresor() == Tresor.Sac_De_Sable) {
                    System.out.println("Sac de sable");
                }

                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                ihm.getVue("jeu").afficherCartesAccessibles(ile.getAventuriers(), ile.getJoueur());
                break;

            case CLIK_JOUEUR:
                switch (lastAction) {
                    case DONNER_CARTE:
                        ihm.getVue("jeu").afficherCartesAccessibles(ile.getAventuriers(), ile.getJoueur());
                        break;
                    case HELICO:
                        //clic tuile
                    //x=true;
                    //System.out.println(" case HELICO>JOUEUR 2");
                    //ihm.getVue("jeu").afficherTuilesAccessibles(grille, grille.getTuiles());
                    //break;
                }
                break;

            case RECUPERER_TRESOR:
                ile.recupererTresor();
                break;

            case PASSER_TOUR:
                ile.passerTour();
                break;

            case CHANGER_VUE:
                ihm.setVue(msg.vue);
                break;

            case QUITTER:
                System.exit(0);
                break;

            case INVENTAIRE_PLEIN:
                System.out.println("message defausser");
                ihm.getVue("jeu").updateDashboard(ile.getAventuriers());
                ihm.getVue("jeu").afficherCartes(ile.getAventuriers().indexOf(ile.getJoueur()), ile.getJoueur().getInventaire().size());
                break;
            case DEFAUSSER_CARTE:
                ile.defausserTresor(ile.getAventuriers().get(msg.indexAventurier).getInventaire().get(msg.index), ile.getAventuriers().get(msg.indexAventurier));
        }
        lastAction = msg.type;
        indexCible = msg.indexAventurier;

        if(ile.getJoueur() != null && ile.getJoueur().getInventaire().size() <= 5 && ile.isVeutFinir()) {
            ile.finirTour();
        }
    }
}
