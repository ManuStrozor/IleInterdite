/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import game.IHM;
import views.VueJeu;
import views.VueMenu;

/**
 *
 * @author turbetde
 */
public class Main {
    
    public static void main(String[] args) {
        IHM ihm = new IHM();
        VueJeu jeu = new VueJeu(ihm);
        VueMenu menu = new VueMenu(ihm);
        menu.show();
    }
}
