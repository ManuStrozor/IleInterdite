package game;

import enumerations.Tresor;

public class CarteInondation extends Carte {

    CarteInondation(String nom) {
        super(nom);
    }

    public void defausser() {

    }

    @Override
    Tresor getTresor() {
        return null;
    }
}
