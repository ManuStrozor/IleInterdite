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
public class Ingenieur extends Aventurier {

    public Ingenieur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Role.ingenieur);
        setCouleurPion(Color.RED);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.La_Porte_De_Bronze);
    }
}