package aventuriers;

import enumerations.Couleur;
import enumerations.EtatTuile;
import enumerations.Roles;
import game.Carte;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public abstract class Aventurier {
    private Couleur couleurPion;
    private double actionsRestantes;
    private game.Tuile tuile;
    private game.Carte[] inventaire;
    private double nbActions;
    private Roles role;
    private String nomJoueur;

    public Aventurier(String nomJoueur) {
        actionsRestantes = 3;
        nbActions = 0;
        role = null;
        setNomJoueur(nomJoueur);
        inventaire = new Carte[4];
        //tuile = getTuileSpawn();
        //couleurPion = null;
    }

    public int getNombreCarte(){
        int nb = 0;
        for (int i = 0 ; i < inventaire.length; i++){
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

    public void setCouleurPion(Couleur couleur) { this.couleurPion = couleur;}

    public void setNbActions(double nbActions) {
        this.nbActions = nbActions;
    }

    public double getNbActions() {
        return nbActions;
    }

    public void ajouterCarte(game.Carte carte){

    }

    public void defausseCarte(){

    }

    public int[] getPosition(){         //Renvoie un tableau avec les coordonnées de la tuile où se trouve l'aventurier
        int[] position = new int[2];
        position[0] = tuile.getLigne();
        position[1] = tuile.getColonne();
        return position;
    }


    public Tuile getTuile() {
        return tuile;
    }

    public void setNomJoueur(String nomJoueur){
        this.nomJoueur = nomJoueur;
    }

    public boolean estAccessible(){

        return estAccessible();
    };
    
    public boolean peutAssecher(game.Tuile tuileInnondee){

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
}
