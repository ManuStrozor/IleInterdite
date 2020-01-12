package game;

import java.util.ArrayList;
import java.util.Collections;

import static enumerations.Tresor.*;

/**
 *
 * @author estevmat
 */
public class Grille {

    private ArrayList<Tuile> tuiles;

    public Grille() {
        tuiles = new ArrayList<>();
        creerTuiles();
    }

    public void melangerTuiles() {
        Collections.shuffle(tuiles);
        int t = 0;
        for(int l = 0; l < 6; l++) {
            for(int c = 0; c < 6; c++) {
                if((l==0 && (c==2 || c==3)) || (l==1 && (c>0 && c<5)) || l==2 || l==3 || (l==4 && (c>0 && c<5)) || (l==5 && (c==2 || c==3))) {
                    tuiles.get(t).setCoor(l, c);
                    t++;
                }
            }
        }
        Collections.sort(tuiles);
    }

    private void creerTuiles() {
        tuiles.add(new Tuile("La Porte de Bronze"));
        tuiles.add(new Tuile("La Porte d'Argent"));
        tuiles.add(new Tuile("La Porte de Fer"));
        tuiles.add(new Tuile("La porte d'or"));
        tuiles.add(new Tuile("Heliport"));
        tuiles.add(new Tuile("La porte de cuivre"));
        tuiles.add(new Tuile("Les falaises de l'oubli"));
        tuiles.add(new Tuile("Les dunes de l'illusion"));
        tuiles.add(new Tuile("Le pont des abimes"));
        tuiles.add(new Tuile("La foret pourpre"));
        tuiles.add(new Tuile("Le lagon perdu"));
        tuiles.add(new Tuile("Le marais brumeux"));
        tuiles.add(new Tuile("Observatoire"));
        tuiles.add(new Tuile("Le rocher fantome"));
        tuiles.add(new Tuile("Le val du crépuscule"));
        tuiles.add(new Tuile("La tour du guet"));
        tuiles.add(new Tuile("La caverne des ombres", cristalArdent));
        tuiles.add(new Tuile("La caverne du brasier", cristalArdent));
        tuiles.add(new Tuile("Le palais de corail", caliceDelombre));
        tuiles.add(new Tuile("Le palais des marees", caliceDelombre));
        tuiles.add(new Tuile("Le temple de la lune", pierreSacree));
        tuiles.add(new Tuile("Le temple du soleil", pierreSacree));
        tuiles.add(new Tuile("Le jardin des hurlements", statueDeZephir));
        tuiles.add(new Tuile("Le jardin de murmures", statueDeZephir));
    }

    public ArrayList<Tuile> getTuiles() {
        return tuiles;
    }

    public Tuile getTuile(int index) {
        return tuiles.get(index);
    }
}
