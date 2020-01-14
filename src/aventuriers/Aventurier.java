package aventuriers;


import enumerations.*;
import game.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author estevmat
 */
public abstract class Aventurier {
    private Color couleurPion;
    private Tuile tuile;
    private ArrayList<CarteTresor> inventaire;
    private double nbActions;
    private Roles role;
    private String nomJoueur;
    private ArrayList<Aventurier> aventurierAccessibles;

    public Aventurier(String nomJoueur, Grille grille) {
        setNbActions(3);
        role = null;
        setNomJoueur(nomJoueur);
        inventaire = new ArrayList<>();
        tuile = getTuileSpawn(grille);
        couleurPion = null;
        aventurierAccessibles = new ArrayList<>();
    }

    public ArrayList<CarteTresor> getInventaire() {
        return inventaire;
    }

    public Color getCouleurPion() {
        return couleurPion;
    }

    protected abstract Tuile getTuileSpawn(Grille grille);

    public int getNombreCarte(){
        int nb = 0;
        for (int i = 0 ; i < inventaire.size(); i++) {
            if (inventaire.get(i) != null){
                nb++;
            }
        }
        return nb;
    }
    public CarteTresor getCarteSacDeSable(){
        CarteTresor carte = null;
        for(int i=0;i<=getInventaire().size();i++){
            if (getInventaire().get(i).getNom()=="Sac de sable"){
                carte = getInventaire().get(i);
            }
            else {
                carte = null;
            }
        }
        return carte;
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

    public void ajouterCarte(CarteTresor carte){
        getInventaire().add(carte);

        }


    public void defausseCarte(){
        int i = inventaire.size();
        while (i>0 && getInventaire().get(i) != null){
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
            aventurierAccessibles.addAll(donneur.getTuile().getAventuriers());
        }
        return aventurierAccessibles;
    }
    
    public ArrayList<Tuile> peutAssecher(Grille grille){
        ArrayList<Tuile> tuiles = new ArrayList<>();

        switch(getRole()) {
            case ingenieur:
            case messager:
            case navigateur:
            case explorateur:
            case plongeur:
            case pilote:
                Tuile newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()-1);
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()+1);
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()-1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);
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
            case plongeur:
                tuiles.removeIf(t -> t.getEtatTuile() == EtatTuile.coulee);
                tuiles.removeIf(t -> t.getEtatTuile() == EtatTuile.assechee);
                break;
        }
        return tuiles;
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
        tuile.getAventuriers().remove(this);
        nouvelle.getAventuriers().add(this);
        tuile = nouvelle;
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
