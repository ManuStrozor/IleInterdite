package aventuriers;

import enumerations.Couleur;
import enumerations.Roles;

/**
 *
 * @author estevmat
 */
public abstract class Aventurier {
    private Couleur couleurPion;
    private String actionsRestantes;
    private game.Tuile tuile;
    private game.Carte[] inventaire;
    private double nbActions;
    private Roles role;


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

}
