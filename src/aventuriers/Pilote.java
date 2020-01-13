package aventuriers;


import enumerations.Couleur;
import enumerations.EtatTuile;
import enumerations.Roles;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public class Pilote extends Aventurier {

    public Pilote(String nomJoueur){
        super(nomJoueur);
        setRole(Roles.pilote);
        setCouleurPion(Couleur.bleu);
    }

    public boolean estAccessible(Tuile tuile){
        // pour l'instant je le fais sans prendre en compte le nb d'actions
        if (tuile.getEtatTuile() == EtatTuile.assechee){
            return true;
        }
        else{
            return false;
        }

    }

}
