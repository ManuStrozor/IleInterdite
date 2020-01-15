package game;

import enumerations.Nom;
import enumerations.Tresor;

/**
 *
 * @author estevmat
 */
public abstract class Carte {

    protected Tresor tresor;
    protected Nom nom;

    Carte(Nom nom){
        this.nom = nom;
    }

    Carte(Tresor tresor){
        this.tresor = tresor;
    }

    abstract void defausser();
    public abstract Tresor getTresor();

    public Nom getNom() {
        return nom;
    }
    public abstract String getName();
    public void setNom(Nom nom) {
        this.nom = nom;
    }
}
