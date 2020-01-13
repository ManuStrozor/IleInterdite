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
public class Explorateur extends Aventurier {

    public Explorateur(String nomJoueur, Grille grille){
        super(nomJoueur, grille);
        setRole(Roles.explorateur);
        setCouleurPion(Couleur.vert);
    }

    @Override
    protected Tuile getTuileSpawn(Grille grille) {
        return grille.getTuilesMap().get("La porte de cuivre");
    }

    public boolean peutAssecher(game.Tuile tuileInnondee) {
        super.peutAssecher(tuileInnondee); //tuiles adjacentes

        if ( tuileInnondee.getEtatTuile() == EtatTuile.innondee){   // tuiles diagonales
            if (tuileInnondee.getColonne() == this.getTuile().getColonne() + 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() - 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() - 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() - 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuileInnondee.getColonne() == this.getTuile().getColonne() + 1 && tuileInnondee.getLigne() == this.getTuile().getLigne() - 1) {
                return true;
            } else {
                return false;
            }
        }
        else{
            return false;
        }
    }

}
