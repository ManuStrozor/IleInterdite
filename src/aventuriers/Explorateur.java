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
public class Explorateur extends Aventurier {

    public Explorateur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Role.explorateur);
        setCouleurPion(Color.GREEN);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.La_Porte_De_Cuivre);
    }
}
