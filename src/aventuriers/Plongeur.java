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
public class Plongeur extends Aventurier {

    public Plongeur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.plongeur);
        setCouleurPion(Couleur.noir);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte de Fer");
    }
}
