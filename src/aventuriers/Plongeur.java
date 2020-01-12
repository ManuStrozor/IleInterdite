package aventuriers;


import enumerations.EtatTuile;

/**
 *
 * @author estevmat
 */
public class Plongeur extends Aventurier {

    public boolean estAccessible(game.Tuile tuileJoueur, game.Tuile tuile){


        if (tuile.getEtatTuile() == EtatTuile.assechee || tuile.getEtatTuile()== EtatTuile.innondee){
            if (tuile.getColonne() == tuileJoueur.getColonne() + 1 && tuile.getLigne() == tuileJoueur.getLigne()) {
                return true;
            }
            if (tuile.getColonne() == tuileJoueur.getColonne() - 1 && tuile.getLigne() == tuileJoueur.getLigne()) {
                return true;
            }
            if (tuile.getColonne() == tuileJoueur.getColonne() && tuile.getLigne() == tuileJoueur.getLigne() + 1) {
                return true;
            }
            if (tuile.getColonne() == tuileJoueur.getColonne() && tuile.getLigne() == tuileJoueur.getLigne() - 1) {
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
