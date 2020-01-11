package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc.Message;
import mvc.Observe;
import mvc.TypeMessage;
import static game.EtatTuile.*;
import static game.Tresor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;
import java.util.ArrayList;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    public IleInterdite() {

    }

    public void start() {
        notifierObservateur(new Message(TypeMessage.START));
    }

    public void commencerPartie() {
        // melanger Tuiles
        initiateInondation();
        initiateAventuriers();
        initiateTresorCards();
        initiateDifficulty();
        Message m = new Message(TypeMessage.UPDATE_IHM);
        m.vue = "jeu";
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
}
