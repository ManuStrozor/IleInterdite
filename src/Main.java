/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import mvc.controller.Controlleur;
import mvc.view.IHM;
import game.IleInterdite;
import mvc.view.VueConfig;
import mvc.view.VueJeu;
import mvc.view.VueMenu;

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
        ihm.addVue(new VueJeu("jeu", ihm, 1318, 800));

        new Controlleur(ihm, ileInterdite); // Controlleur

        ileInterdite.start();
    }
}
