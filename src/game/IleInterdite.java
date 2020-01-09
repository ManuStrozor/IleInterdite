package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc.Message;
import mvc.Observe;
import mvc.TypeMessage;

/**
 *
 * @author turbetde
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

    public void quitter() {
        System.exit(0);
    }
}
