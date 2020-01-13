package aventuriers;


import enumerations.EtatTuile;
import enumerations.Roles;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public abstract class Ingenieur extends Aventurier {

    public boolean estAccessible( game.Tuile tuile) {

        if (tuile.getEtatTuile() == EtatTuile.assechee) {
            if (tuile.getColonne() == super.getTuile().getColonne() + 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            } else if (tuile.getColonne() == super.getTuile().getColonne() - 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            } else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() + 1) {
                return true;
            } else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() - 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Roles getRole(){
        return Roles.ingenieur;
    }


}