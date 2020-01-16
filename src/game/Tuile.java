package game;

import aventuriers.Aventurier;
import enumerations.Etat;
import enumerations.Nom;
import enumerations.Tresor;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author estevmat
 */
public class Tuile implements Comparable<Tuile> {

    private Nom nom;
    private Image image;
    private Tresor tresor;
    private Etat etat;
    private int ligne, colonne;
    private ArrayList<Aventurier> aventuriers;

    Tuile(Nom nom){
        setNom(nom);
        this.assecher();
        setImage(etat);
        aventuriers = new ArrayList<>();
        setTresor(null);
    }

    public Nom getNom() {
        return nom;
    }
    public String getName() {
        return nom.name();
    }
    private void setNom(Nom nom) {
        this.nom = nom;
    }

    public Image getImage() {
        return this.image;
    }
    public void setImage(Etat etat) {
        String filename = Utils.toCamelCase(getName());
        if(etat == Etat.innondee) {
            filename += "2";
        } else if(etat == Etat.coulee) {
            filename = "EauSombree";
        }
        URL url = getClass().getClassLoader().getResource("tuiles/" + filename + ".png");
        if (url != null) {
            Image img = new ImageIcon(url).getImage();
            this.image = etat == Etat.cachee ? Utils.createColorImage(Utils.toBufferedImage(img), 0x66FFFFFF) : img;
        } else {
            System.out.println("Erreur nom image : " + getName() + " " + filename);
        }
    }

    public Tresor getTresor() {
        return tresor;
    }
    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }

    public int getLigne() {
        return ligne;
    }
    public int getColonne() {
        return colonne;
    }
    private void setLigne(int ligne) {
        this.ligne = ligne;
    }
    private void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public void setCoor(int ligne, int colonne) {
        setLigne(ligne);
        setColonne(colonne);
    }

    public Etat getEtat() {
        return etat;
    }
    private void setEtat(Etat etat) {
        this.etat = etat;
    }

    public void assecher() {
        setEtat(Etat.assechee);
        setImage(Etat.assechee);
    }

    public void innonder() {
        if (getEtat() == Etat.innondee){
            setEtat(Etat.coulee);
            setImage(Etat.coulee);
        }
        else if (getEtat() == Etat.assechee){
            setEtat(Etat.innondee);
            setImage(Etat.innondee);
        }
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }
    public void addAventurier(Aventurier aventurier) {
        this.aventuriers.add(aventurier);
    }
    public void delAventurier(Aventurier aventurier) {
        this.aventuriers.remove(aventurier);
    }

    @Override
    public int compareTo(Tuile o) {
        return (o.getLigne() > this.getLigne() || (o.getLigne() == this.getLigne() && o.getColonne() > this.getColonne())) ? 1 : 0;
    }
}
