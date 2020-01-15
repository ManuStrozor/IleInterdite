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
public class Plongeur extends Aventurier {

    public Plongeur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Role.plongeur);
        setColor(Color.BLACK);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.La_Porte_De_Fer);
    }
}
