package aventuriers;


import enumerations.EtatTuile;
import enumerations.Roles;
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
        setRole(Roles.explorateur);
        setCouleurPion(Color.GREEN);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La porte de cuivre");
    }


}
