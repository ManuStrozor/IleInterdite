package game;

import enumerations.Tresor;

/**
 *
 * @author estevmat
 */
public abstract class Carte {

    protected Tresor tresor;

    protected String nom;

    Carte(String nom){
        this.nom = nom;
    }

    abstract void defausser();

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    abstract Tresor getTresor();
}
