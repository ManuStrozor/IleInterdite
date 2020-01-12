package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
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

    public IleInterdite() {
        grille = new Grille();
    tresorsRecup = new ArrayList<>();
    }

    public void start() {
        notifierObservateur(new Message(TypeMessage.START));
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

    public void assecher(Tuile tuile, Aventurier aventurier){
        tuile.setEtatTuile(assechee);

        double nbActions = aventurier.getNbActions();

        if (aventurier.getRole() == Roles.ingenieur){
            aventurier.setNbActions(nbActions - 0.5);
        }else{
            aventurier.setNbActions(nbActions - 1);
        }
        Message m = new Message(TypeMessage.UPDATE_IHM);
        notifierObservateur(m);
    }

    public void recupererTresor(Tuile tuile, Aventurier aventurier){
        Tresor tresor = tuile.getTuileTresor();
        tresorsRecup.add(tresor);
    }

}
