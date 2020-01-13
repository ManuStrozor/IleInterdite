package aventuriers;


import enumerations.Couleur;
import enumerations.EtatTuile;
import enumerations.Roles;

/**
 *
 * @author estevmat
 */
public class Messager extends Aventurier {


    public Messager(String nomJoueur){
        super(nomJoueur);
        setRole(Roles.messager);
        setCouleurPion(Couleur.gris);
    }

    public boolean estAccessible(game.Tuile tuile){

        if (tuile.getEtatTuile() == EtatTuile.assechee){
            if (tuile.getColonne() == super.getTuile().getColonne() + 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() - 1 && tuile.getLigne() == super.getTuile().getLigne()) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() + 1) {
                return true;
            }
            else if (tuile.getColonne() == super.getTuile().getColonne() && tuile.getLigne() == super.getTuile().getLigne() - 1) {
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
