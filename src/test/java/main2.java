package test.java;

import game.Grille;
import game.IleInterdite;
import game.Tuile;
import mvc.controller.Controlleur;
import mvc.view.IHM;
import mvc.view.VueConfig;
import mvc.view.VueJeu;
import mvc.view.VueMenu;

import java.util.ArrayList;

public class main2 {
    public static void main(String[] args) {
         //Tuile[][] tuilesGrille = new Tuile[6][6];
         //ArrayList<Tuile> nomDesTuiles = new ArrayList<>();
         Grille grille = new Grille();
         grille.afficheGrille();


    }
}
