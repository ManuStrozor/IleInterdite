package aventuriers;

import game.Roles;

/**
 *
 * @author estevmat
 */
public abstract class Aventurier {
    private game.Couleur couleurPion;
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

}
