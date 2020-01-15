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
public class Messager extends Aventurier {

    public Messager(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Role.messager);
        setCouleurPion(Color.GRAY);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get(Nom.La_Porte_D_Argent);
    }
}
