package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
import aventuriers.Ingenieur;
import enumerations.NomsTuiles;
import enumerations.Roles;
import enumerations.Tresor;
import mvc.Message;
import mvc.Observe;
import enumerations.TypeMessage;

import java.util.ArrayList;

import static enumerations.EtatTuile.assechee;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;

    private ArrayList<Tresor> tresorsRecup;
    private ArrayList<NomsTuiles> pileCarteInnondation;
    private ArrayList<NomsTuiles> defausseCarteInnondation;
    private Aventurier[] aventuriers;
    private int nbJoueurs;


    public IleInterdite() {
        grille = new Grille();
        tresorsRecup = new ArrayList<>();
    }

    public void start() {
        Message m = new Message(TypeMessage.CHANGER_VUE);
        m.vue = "menu";
        notifierObservateur(m);
    }

    public void commencerPartie() {
        grille.melangerTuiles();
        initiateInondation();
        initiateAventuriers();
        initiateTresorCards();
        initiateDifficulty();
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.vue = "jeu";
        m.grille = grille;
        notifierObservateur(m);
    }

    private void initiateDifficulty() {

    }

    private void initiateTresorCards() {

    }

    private void initiateAventuriers() {

    }

    private void initiateInondation() {

    }

    public void quitter(){
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier , Tuile nouvelle ){
       // nouvelle = getMessage(aventurier) ;

           if (aventurier.estAccessible()== true){
              aventurier.getTuile().getAventuriers().remove(aventurier);
              nouvelle.getAventuriers().add(aventurier);
           }
           else {
               System.out.println("cette tuile n'est pas accéssible ");
           }



        double nbActions = aventurier.getNbActions();
        aventurier.setNbActions(nbActions - 1);

    }
    public void assecher(Tuile tuile, Aventurier aventurier){
        //if (aventurier.peutAcceder(tuile)== true ) { tuile.assecher();}
        tuile.assecher();

        double nbActions = aventurier.getNbActions();

        if (aventurier.getRole() == Roles.ingenieur){
            aventurier.setNbActions(nbActions - 0.5);
        }else{
            aventurier.setNbActions(nbActions - 1);
        }
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    public void recupererTresor(Aventurier aventurier){
        Tresor tresor = aventurier.getTuile().getTuileTresor();
        tresorsRecup.add(tresor);

    }


    public void setNbJoueurs(int i){
        nbJoueurs = i;
    }



}
