package aventuriers;


import enumerations.Couleur;
import enumerations.EtatTuile;
import enumerations.Roles;
import game.Grille;
import game.Tuile;

/**
 *
 * @author estevmat
 */
public class Messager extends Aventurier {


    public Messager(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.messager);
        setCouleurPion(Couleur.gris);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte d'Argent");
    }
}
