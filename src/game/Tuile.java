package game;

import aventuriers.Aventurier;
import enumerations.EtatTuile;
import enumerations.Roles;
import enumerations.Tresor;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashSet;

/**
 *
 * @author estevmat
 */
public class Tuile implements Comparable<Tuile> {

    private String nom;
    private Image image;
    private Tresor tresor;
    private EtatTuile etatTuile;
    private int ligne, colonne;
    private HashSet<Aventurier> aventuriers;

    Tuile(String nom){
        setNom(nom);
        this.assecher();
        setImage(etatTuile);
        aventuriers = new HashSet<>();
        setTresor(null);
    }

    Tuile(String nom, Tresor tresor){
        setNom(nom);
        this.assecher();
        setImage(etatTuile);
        aventuriers = new HashSet<>();
        setTresor(tresor);
    }

    public String getNom() {
        return nom;
    }
    private void setNom(String nom) {
        this.nom = nom;
    }

    public Image getImage() {
        return this.image;
    }
    public void setImage(EtatTuile etat) {
        String filename = Utils.getImageFromName(this.nom);
        URL url = getClass().getClassLoader().getResource("images/tuiles/" + filename + ".png");
        if (url != null) {
            Image img = new ImageIcon(url).getImage();
            if(etat == EtatTuile.assechee) {
                this.image = img;
            } else if(etat == EtatTuile.innondee) {
                this.image = Utils.createColorImage( Utils.toBufferedImage(img), 0xFF0000FF);
            } else if(etat == EtatTuile.coulee) {
                this.image = Utils.createColorImage( Utils.toBufferedImage(img), 0xFFFF0000);
            } else {
                this.image = Utils.createColorImage( Utils.toBufferedImage(img), 0x66FFFFFF);
            }
        }
    }

    public Tresor getTresor() {
        return tresor;
    }
    private void setTresor(Tresor tresor) {
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

    public EtatTuile getEtatTuile() {
        return etatTuile;
    }
    private void setEtatTuile(EtatTuile etatTuile) {
        this.etatTuile = etatTuile;
    }

    public void assecher() {
        setEtatTuile(EtatTuile.assechee);
        setImage(EtatTuile.assechee);
    }

    public void innonder() {
        if (getEtatTuile() == EtatTuile.innondee){
            setEtatTuile(EtatTuile.coulee);
            setImage(EtatTuile.coulee);
        }
        else{
            setEtatTuile(EtatTuile.innondee);
            setImage(EtatTuile.innondee);
        }
    }

    public HashSet<Aventurier> getAventuriers() {
        return aventuriers;
    }
    public void addAventurier(Aventurier aventurier) {
        this.aventuriers.add(aventurier);
    }
    public void delAventurier(Aventurier aventurier) {
        this.aventuriers.remove(aventurier);
    }

    public Tresor getTuileTresor() {
        return tresor;
    }

    @Override
    public int compareTo(Tuile o) {
        return (o.getLigne() > this.getLigne() || (o.getLigne() == this.getLigne() && o.getColonne() > this.getColonne())) ? 1 : 0;
    }



}
