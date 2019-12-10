package iut.ileinterdite;

import static iut.ileinterdite.EtatTuile.assechée;
import static iut.ileinterdite.EtatTuile.coulée;
import static iut.ileinterdite.EtatTuile.innondée;
import java.util.ArrayList;

/**
 *
 * @author estevmat
 */
public class Tuile {

    private int ligne;
    private int colonne;
    private String nomTuile;
    private Tresor tuileTresor;
    private EtatTuile etatTuile;
    private ArrayList<Aventurier> aventuriersSurTuile;

    Tuile(int ligne, int colonne, String nomTuile) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nomTuile = nomTuile;
        this.etatTuile = assechée;
        this.aventuriersSurTuile = new ArrayList<>();
    }

    Tuile(int ligne, int colonne, String nomTuile, Tresor tuileTresor) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nomTuile = nomTuile;
        this.etatTuile = assechée;
        this.aventuriersSurTuile = new ArrayList<>();
        this.tuileTresor = tuileTresor;

    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getNomTuile() {
        return nomTuile;
    }

    public void setNomTuile(String nomTuile) {
        this.nomTuile = nomTuile;
    }

    public Tresor getTuileTresor() {
        return tuileTresor;
    }

    public void setTuileTresor(Tresor tuileTresor) {
        this.tuileTresor = tuileTresor;
    }

    public EtatTuile getEtatTuile() {
        return etatTuile;
    }

    public ArrayList<Aventurier> getAventuriersSurTuile() {
        return aventuriersSurTuile;
    }

    public void setEtatTuile(EtatTuile etatTuile) {
        this.etatTuile = etatTuile;
    }

    public void setAssechée() {
        this.etatTuile = assechée;
    }

    public void setCoulée() {
        this.etatTuile = coulée;
    }

    public void setInnondée() {
        this.etatTuile = innondée;
    }

    public void setAventuriersSurTuile(ArrayList<Aventurier> aventuriersSurTuile) {
        this.aventuriersSurTuile = aventuriersSurTuile;
    }

}
