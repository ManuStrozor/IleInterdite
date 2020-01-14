package aventuriers;


import enumerations.Roles;
import game.Grille;
import game.Tuile;

import java.awt.*;

/**
 *
 * @author estevmat
 */
public class Pilote extends Aventurier {

    public Pilote(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.pilote);
        setCouleurPion(Color.BLUE);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("Heliport");
    }
}
