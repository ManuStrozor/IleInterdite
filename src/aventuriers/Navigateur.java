package aventuriers;


import enumerations.Couleur;
import enumerations.EtatTuile;
import enumerations.Roles;
import game.Grille;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public class Navigateur extends Aventurier {

    public Navigateur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.navigateur);
        setCouleurPion(Couleur.jaune);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La porte d'or");
    }
}
