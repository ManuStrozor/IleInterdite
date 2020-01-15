package aventuriers;


import enumerations.Nom;
import enumerations.Role;
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
        setRole(Role.pilote);
        setColor(Color.BLUE);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.Heliport);
    }
}
