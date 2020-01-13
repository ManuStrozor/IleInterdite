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
public class Pilote extends Aventurier {

    public Pilote(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.pilote);
        setCouleurPion(Couleur.bleu);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("Heliport");
    }
}
