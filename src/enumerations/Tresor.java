package enumerations;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import game.Grille;
import game.Tuile;
import game.Utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author estevmat
 */
public enum Tresor {
    Le_Cristal_Ardent,
    La_Pierre_Sacree,
    La_Statue_Du_Zephyr,
    Le_Calice_De_L_Onde,
    Montee_Des_Eaux,
    Helicoptere,
    Sac_De_Sable,
    ;

    public Image getImage() {
        URL url = getClass().getClassLoader().getResource("cartes/tresor/"+ Utils.toCamelCase(this.name()) +".png");
        return new ImageIcon(Objects.requireNonNull(url)).getImage();
    }

    public boolean isReachable(Grille grille) {

        ArrayList<Tuile> tuiles = new ArrayList<>();
        Nom[] sp;
        switch (this) {
            case Le_Cristal_Ardent:
                sp = new Nom[]{Nom.La_Caverne_Des_Ombres, Nom.La_Caverne_Du_Brasier};
                break;
            case Le_Calice_De_L_Onde:
                sp = new Nom[]{Nom.Le_Palais_De_Corail, Nom.Le_Palais_Des_Marees};
                break;
            case La_Pierre_Sacree:
                sp = new Nom[]{Nom.Le_Temple_De_La_Lune, Nom.Le_Temple_Du_Soleil};
                break;
            default:
                sp = new Nom[]{Nom.Le_Jardin_Des_Hurlements, Nom.Le_Jardin_Des_Murmures};
        }

        tuiles.add(grille.getTuilesMap().get(sp[0]));
        tuiles.add(grille.getTuilesMap().get(sp[1]));

        return tuiles.get(0).getEtat() != Etat.coulee || tuiles.get(1).getEtat() != Etat.coulee;
    }
}
