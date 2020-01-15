package game;

import enumerations.Nom;
import enumerations.Tresor;

public class CarteInondation extends Carte {

    CarteInondation(Nom nom) {
        super(nom);
    }

    public void defausser() {

    }

    @Override
    public Tresor getTresor() {
        return null;
    }

    @Override
    public String getName() {
        return nom.name();
    }
}
