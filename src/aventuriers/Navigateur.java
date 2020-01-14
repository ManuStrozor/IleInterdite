package aventuriers;


import enumerations.Roles;
import game.Grille;
import game.Tuile;

import java.awt.*;

/**
 *
 * @author estevmat
 */
public class Navigateur extends Aventurier {

    public Navigateur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.navigateur);
        setCouleurPion(Color.YELLOW);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La porte d'or");
    }
}
