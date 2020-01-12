package game;

import aventuriers.Aventurier;
import enumerations.EtatTuile;
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
    private void setImage(EtatTuile etat) {
        String filename = Utils.getImageFromName(this.nom);
        URL url = getClass().getClassLoader().getResource("images/tuiles/" + filename + ".png");
        if (url != null) {
            Image img = new ImageIcon(url).getImage();
            if(etat == EtatTuile.assechee) {
                this.image = img;
            } else if(etat == EtatTuile.innondee) {
                this.image = Utils.createColorImage( Utils.toBufferedImage(img), 0xFF0000FF);
            } else {
                this.image = Utils.createColorImage( Utils.toBufferedImage(img), 0xFFFF0000);
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
    public void couler() {
        setEtatTuile(EtatTuile.coulee);
        setImage(EtatTuile.coulee);
    }
    public void innonder() {
        setEtatTuile(EtatTuile.innondee);
        setImage(EtatTuile.innondee);
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

    @Override
    public int compareTo(Tuile o) {
        return (o.getLigne() > this.getLigne() || (o.getLigne() == this.getLigne() && o.getColonne() > this.getColonne())) ? 1 : 0;
    }

    public Tresor getTuileTresor() {
        return tresor;
    }
}
