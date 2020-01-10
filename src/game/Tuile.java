package game;

import aventuriers.Aventurier;
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

    Tuile(String nomTuile,Tresor tuileTresor){
        this.nomTuile=nomTuile;
        this.tuileTresor=tuileTresor;
        this.etatTuile = EtatTuile.assechee;
    }

    Tuile(int ligne, int colonne, String nomTuile) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nomTuile = nomTuile;
        this.etatTuile = EtatTuile.assechee;
        this.aventuriersSurTuile = new ArrayList<>();
        setTuileTresor(null);
    }

    Tuile(int ligne, int colonne, String nomTuile, Tresor tuileTresor) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nomTuile = nomTuile;
        this.etatTuile = EtatTuile.assechee;
        this.aventuriersSurTuile = new ArrayList<>();
        setTuileTresor(tuileTresor);

    }

    /*public ArrayList<Tuile> getTuileAdjacentes(Tuile tuile){

        ArrayList<Tuile> tuilesadjacentes = new ArrayList<>();





    }*/




















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
        this.etatTuile = EtatTuile.assechee;
    }

    public void setCoulée() {
        this.etatTuile = EtatTuile.coulee;
    }

    public void setInnondée() {
        this.etatTuile = EtatTuile.innondee;
    }

    public void setAventuriersSurTuile(ArrayList<Aventurier> aventuriersSurTuile) {
        this.aventuriersSurTuile = aventuriersSurTuile;
    }

}
