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
public class Navigateur extends Aventurier {

    public Navigateur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Role.navigateur);
        setCouleurPion(Color.YELLOW);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.La_Porte_D_Or);
    }
}
