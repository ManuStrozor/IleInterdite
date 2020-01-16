
import game.IleInterdite;
import mvc.controller.Controlleur;
import mvc.view.*;

public class Main {
    
    public static void main(String[] args) {

        IleInterdite ileInterdite = new IleInterdite(); // Modèle

        IHM ihm = new IHM(); // Vues

        ihm.addVue(new VueMenu("menu", ihm));
        ihm.addVue(new VueConfig("config", ihm));
        ihm.addVue(new VueJeu("jeu", ihm, 1080, 1080));
        ihm.addVue(new VueGameOver("perdu", ihm));
        ihm.addVue(new VueWin("gagne", ihm, 880, 586));

        new Controlleur(ihm, ileInterdite); // Controleur
    }
}
