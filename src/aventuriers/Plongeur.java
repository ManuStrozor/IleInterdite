package aventuriers;


import enumerations.Roles;
import game.Grille;
import game.Tuile;

import java.awt.*;

/**
 *
 * @author estevmat
 */
public class Plongeur extends Aventurier {

    public Plongeur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.plongeur);
        setCouleurPion(Color.BLACK);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte de Fer");
    }
}
