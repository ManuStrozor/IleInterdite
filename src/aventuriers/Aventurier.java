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
    private ArrayList<CarteTresor> inventaire;
    private double nbActions;
    private Role role;
    private String nomJoueur;

    public Aventurier(String nomJoueur, Grille grille) {
        setNbActions(3);
        role = null;
        setNomJoueur(nomJoueur);
        inventaire = new ArrayList<>();
        tuile = getTuileSpawn(grille);
        tuile.addAventurier(this);
        couleurPion = null;
    }

    public ArrayList<CarteTresor> getInventaire() {
        return inventaire;
    }

    public Color getCouleurPion() {
        return couleurPion;
    }

    protected abstract Tuile getTuileSpawn(Grille grille);

    public int getNombreCarte() {
        int nb = 0;
        for (int i = 0; i < inventaire.size(); i++) {
            if (inventaire.get(i) != null) {
                nb++;
            }
        }
        return nb;
    }
    public CarteTresor getCarteSacDeSable(){
        CarteTresor carte = null;
        for(int i=0;i<=getInventaire().size();i++){
            if (getInventaire().get(i).getNom().equals("Sac de sable")){
                carte = getInventaire().get(i);
            } else {
                carte = null;
            }
        }
        return carte;
    }

    public CarteTresor getCarteHelico(){
        CarteTresor carte = null;
        for (Carte c : getInventaire()) {
            if(c.getTresor() == Tresor.Helicoptere) {
                carte = (CarteTresor) c;
            }
        }
        return carte;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) { this.role = role;  }

    public void setCouleurPion(Color couleur) { this.couleurPion = couleur;}

    public void setNbActions(double nbActions) { this.nbActions = nbActions;}

    public double getNbActions() {
        return nbActions;
    }

    public void ajouterCarte(CarteTresor carte){
        getInventaire().add(carte);
    }


    public void defausseCarte(){
        int i = inventaire.size();
        while (i>0 && getInventaire().get(i) != null){
            i--;
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

    public ArrayList<Aventurier> aventuriersAccessible(ArrayList<Aventurier> aventuriers) {
        ArrayList<Aventurier> av = new ArrayList<>(this.getRole() == Role.messager ? aventuriers : tuile.getAventuriers());
        av.remove(this);
        return av;
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

                newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()); // la tuile ou il est
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()+1);
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne()-1, tuile.getColonne());
                if(newTuile != null) tuiles.add(newTuile);
                break;
        }

        if (getRole() == Role.explorateur) {


            Tuile newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne()+1); // tuile en haut a droite
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne()); // la tuile ou il est
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() - 1, tuile.getColonne() - 1); //tuile en bas a gauche
            if (newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() - 1, tuile.getColonne() + 1); // tuile en bas a droite
            if (newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() + 1, tuile.getColonne() - 1); // tuile en haut à gauche
            if (newTuile != null) tuiles.add(newTuile);
        }

        switch (getRole()) {
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

        switch (getRole()) {
            case ingenieur:
            case messager:
            case navigateur:
            case explorateur:
            case plongeur:
                Tuile newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne() - 1);
                if (newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne(), tuile.getColonne() + 1);
                if (newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne() + 1, tuile.getColonne());
                if (newTuile != null) tuiles.add(newTuile);

                newTuile = grille.getTuile(tuile.getLigne() - 1, tuile.getColonne());
                if (newTuile != null) tuiles.add(newTuile);
                break;
            case pilote:
                tuiles.addAll(grille.getTuiles());
                break;
        }

        if (getRole() == Role.explorateur) {
            Tuile newTuile = grille.getTuile(tuile.getLigne()+1, tuile.getColonne()+1);
            if(newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() - 1, tuile.getColonne() - 1);
            if (newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() - 1, tuile.getColonne() + 1);
            if (newTuile != null) tuiles.add(newTuile);

            newTuile = grille.getTuile(tuile.getLigne() + 1, tuile.getColonne() - 1);
            if (newTuile != null) tuiles.add(newTuile);
        }

        switch (getRole()) {
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

    public void seDeplacer(Tuile tuile) {
        this.tuile.delAventurier(this);
        this.tuile = tuile;
        tuile.addAventurier(this);
    }

    public void consommerAction(double n) {
        nbActions -= n;
        // si plus d'actions....finir tour ou bloquer les boutons d'actions
    }

    public boolean mort(Grille grille) {
        return getRole() != Role.plongeur && tuile.getEtatTuile() == EtatTuile.coulee && getTuilesAccessibles(grille) == null;
    }

    public void defaussetoi(ArrayList<CarteTresor> cs){
        this.getInventaire().remove(cs);
    }
}
