/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import enumerations.TypeMessage;
import game.IleInterdite;
import mvc.Message;
import mvc.controller.Controlleur;
import mvc.view.*;

/**
 *
 * @author turbetde
 */
public class Main {
    
    public static void main(String[] args) {

        IleInterdite ileInterdite = new IleInterdite(); // Modèle

        IHM ihm = new IHM(); // Vues

        ihm.addVue(new VueMenu("menu", ihm));
        ihm.addVue(new VueConfig("config", ihm));
        ihm.addVue(new VueJeu("jeu", ihm, 1250, 800));
        ihm.addVue(new VueGameOver("perdu", ihm));
        ihm.addVue(new VueWin("gagne", ihm, 880, 586));

        new Controlleur(ihm, ileInterdite); // Controleur
    }
}
