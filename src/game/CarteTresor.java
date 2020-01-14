package game;

import enumerations.*;

/**
 *
 * @author estevmat
 */
public class CarteTresor extends Carte {


    CarteTresor(String nom){
        super(nom);
    }

    public void defausser() {
    }

    public Tresor getTresor(){
        return tresor;
    }
}
