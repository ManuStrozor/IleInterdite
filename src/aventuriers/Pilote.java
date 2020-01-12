package aventuriers;


import enumerations.EtatTuile;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public class Pilote extends Aventurier {

    public boolean estAccessible(Tuile tuile , Tuile tuileJoueur){
        // pour l'instant je le fais sans prendre en compte le nb d'actions
        if (tuile.getEtatTuile() == EtatTuile.assechee){
            return true;
        }
        else{
            return false;
        }

    }

}
