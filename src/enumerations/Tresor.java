package enumerations;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import game.Utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
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
}
