package aventuriers;


import enumerations.Roles;
import game.Grille;
import game.Tuile;

import java.awt.*;

/**
 *
 * @author estevmat
 */
public class Messager extends Aventurier {


    public Messager(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.messager);
        setCouleurPion(Color.GRAY);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte d'Argent");
    }
}
