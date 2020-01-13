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
public class Ingenieur extends Aventurier {

    public Ingenieur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.ingenieur);
        setCouleurPion(Couleur.rouge);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La Porte de Bronze");
    }

    public Roles getRole(){
        return Roles.ingenieur;
    }


}