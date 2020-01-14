package aventuriers;


import enumerations.*;
import game.*;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author estevmat
 */
public abstract class Aventurier {
    private Color couleurPion;
    private Tuile tuile;
    private Carte[] inventaire;
    private double nbActions;
    private Roles role;
    private String nomJoueur;

    public Aventurier(String nomJoueur, Grille grille) {
        setNbActions(3);
        role = null;
        setNomJoueur(nomJoueur);
        inventaire = new Carte[5];
        tuile = getTuileSpawn(grille);
        couleurPion = null;
    }

    public Carte[] getInventaire() {
        return inventaire;
    }

    public Color getCouleurPion() {
        return couleurPion;
    }

    protected abstract Tuile getTuileSpawn(Grille grille);

    public int getNombreCarte(){
        int nb = 0;
        for (int i = 0 ; i < inventaire.length; i++) {
            if (inventaire[i] != null){
                nb++;
            }
        }
        return nb;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) { this.role = role;  }

    public void setCouleurPion(Color couleur) { this.couleurPion = couleur;}

    public void setNbActions(double nbActions) {
        this.nbActions = nbActions;
    }

    public double getNbActions() {
        return nbActions;
    }

    public boolean ajouterCarte(Carte carte){
        int i = 0;
        while (i < inventaire.length && inventaire[i] != null ){
            i++;
        }
        if (i >= inventaire.length){
            System.out.println("L'inventaire de l'aventurier est plein");
            return false;
        }else{
            this.inventaire[i] = carte;
            return true;
        }
    }

    public void defausseCarte(){
        int i = inventaire.length;
        while (i>0 && inventaire[i] != null){
            i= i-1;
        }

    }

    public Tuile getTuile() {
        return tuile;
    }

    public String getNomJoueur(){
        return this.nomJoueur;
    }

    public void setNomJoueur(String nomJoueur){
        this.nomJoueur = nomJoueur;
    }

    public ArrayList<Aventurier> aventurierAccessibles(Aventurier donneur) {
        if (donneur.getRole() == Roles.messager) {
            //aventurierAccessibles(donneur).addAll(aventuriers);
        }
        else {
            aventurierAccessibles(donneur).addAll(donneur.getTuile().getAventuriers());
        }
        return aventurierAccessibles(donneur);
    }
    
    public boolean peutAssecher(Tuile tuileInnondee){

        if ( tuileInnondee.getEtatTuile() == EtatTuile.innondee){
            if (tuileInnondee.getColonne() == this.getTuile().getColonne() + 1 && tuileInnondee.getLigne() == this.getTuile().getLigne()) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() - 1 && tuileInnondee.getLigne() == this.getTuile().getLigne()) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() && tuileInnondee.getLigne() == this.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() && tuileInnondee.getLigne() == this.getTuile().getLigne() - 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public ArrayList<Tuile> getTuilesAccessibles(Grille grille) {
        ArrayList<Tuile> tuiles = new ArrayList<>();

        switch(getRole()) {
            case ingenieur:
            case messager:
            case navigateur:
            case explorateur:
            case plongeur:
                Tuile newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()-1);
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()+1);
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()-1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);
                break;
            case pilote:
                tuiles.addAll(grille.getTuiles());
                break;
        }

        if (getRole() == Roles.explorateur) {
            Tuile newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne()+1);
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne()-1, tuile.getColonne()-1);
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne()-1, tuile.getColonne()+1);
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne()-1);
            if(newTuile != null) tuiles.add(newTuile);
        }

        switch(getRole()) {
            case ingenieur:
            case messager:
            case navigateur:
            case pilote:
            case explorateur:
                tuiles.removeIf(t -> t.getEtatTuile() == EtatTuile.coulee);
                break;
        }
        return tuiles;
    }

    public void seDeplacer(Tuile nouvelle) {
        this.getTuile().getAventuriers().remove(this);
        nouvelle.getAventuriers().add(this);
        this.setNbActions(this.getNbActions() - 1);
    }

    public void moinsUneAction(Aventurier aventurier){
        double nbActions = aventurier.getNbActions();
        aventurier.setNbActions(nbActions - 1);
    }

    public boolean mort(Aventurier aventurier, Tuile tuile, Grille grille) {
        if (aventurier.getRole() != Roles.plongeur){
            if ( tuile.getEtatTuile()== EtatTuile.coulee && aventurier.getTuilesAccessibles(grille)== null){
                return true;
            }
            else { return false;}
        }
        else {return false ; }

    }
}
