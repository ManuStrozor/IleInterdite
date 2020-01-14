package aventuriers;


import enumerations.Roles;
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
        setRole(Roles.ingenieur);
        setCouleurPion(Color.RED);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte de Bronze");
    }

    public Roles getRole(){
        return Roles.ingenieur;
    }


}