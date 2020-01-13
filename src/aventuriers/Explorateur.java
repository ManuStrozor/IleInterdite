package aventuriers;


import enumerations.EtatTuile;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public class Explorateur extends Aventurier {

    public boolean estAccessible( game.Tuile tuile){
        if (tuile.getEtatTuile()== EtatTuile.assechee) {
            if (tuile.getColonne() == super.getTuile().getColonne() + 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            }
           else  if (tuile.getColonne() == super.getTuile().getColonne() - 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() - 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() + 1 && tuile.getLigne() == super.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() - 1 && tuile.getLigne() == super.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() + 1 && tuile.getLigne() == super.getTuile().getLigne() - 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() - 1 && tuile.getLigne() == super.getTuile().getLigne() - 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false ;
        }

    }

    public boolean peutAssecher(game.Tuile tuileInnondee) {
        super.peutAssecher(tuileInnondee); //tuiles adjacentes

        if ( tuileInnondee.getEtatTuile() == EtatTuile.innondee){   // tuiles diagonales
            if (tuileInnondee.getColonne() == this.getTuile().getColonne() + 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() - 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() - 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() - 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() + 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() - 1) {
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
