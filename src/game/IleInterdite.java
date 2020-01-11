package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
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
        Message m = new Message();
        m.type = TypeMessage.START;
        notifierObservateur(m);
    }

    public void jouer() {
        System.out.println("IleInterdite : jouer()");
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
        Message m = new Message();


    }
}
