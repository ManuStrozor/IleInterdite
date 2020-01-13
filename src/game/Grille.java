package game;

import java.util.*;

import static enumerations.Tresor.*;

/**
 *
 * @author estevmat
 */
public class Grille {

    private HashMap<String, Tuile> tuiles;

    public Grille() {
        tuiles = new HashMap<>();
        creerTuiles();
    }

    public void melangerTuiles() {
        shuffleTuiles();
        int t = 0;
        for(int l = 0; l < 6; l++) {
            for(int c = 0; c < 6; c++) {
                if((l==0 && (c==2 || c==3)) || (l==1 && (c>0 && c<5)) || l==2 || l==3 || (l==4 && (c>0 && c<5)) || (l==5 && (c==2 || c==3))) {
                    getTuile(t).setCoor(l, c);
                    t++;
                }
            }
        }
        sortTuiles();
    }

    private void shuffleTuiles() {
        ArrayList<Tuile> list = new ArrayList<>(tuiles.values());
        Collections.shuffle(list);

        tuiles.clear();
        list.forEach(k->tuiles.put(k.getNom(), k));
    }

    private void sortTuiles() {
        ArrayList<Tuile> list = new ArrayList<>(tuiles.values());
        Collections.sort(list);

        tuiles.clear();
        list.forEach(k->tuiles.put(k.getNom(), k));
    }

    private void creerTuiles() {
        tuiles.put("La Porte de Bronze", new Tuile("La Porte de Bronze"));
        tuiles.put("La Porte d'Argent", new Tuile("La Porte d'Argent"));
        tuiles.put("La Porte de Fer", new Tuile("La Porte de Fer"));
        tuiles.put("La porte d'or", new Tuile("La porte d'or"));
        tuiles.put("Heliport", new Tuile("Heliport"));
        tuiles.put("La porte de cuivre", new Tuile("La porte de cuivre"));
        tuiles.put("Les falaises de l'oubli", new Tuile("Les falaises de l'oubli"));
        tuiles.put("Les dunes de l'illusion", new Tuile("Les dunes de l'illusion"));
        tuiles.put("Le pont des abimes", new Tuile("Le pont des abimes"));
        tuiles.put("La foret pourpre", new Tuile("La foret pourpre"));
        tuiles.put("Le lagon perdu", new Tuile("Le lagon perdu"));
        tuiles.put("Le marais brumeux", new Tuile("Le marais brumeux"));
        tuiles.put("Observatoire", new Tuile("Observatoire"));
        tuiles.put("Le rocher fantome", new Tuile("Le rocher fantome"));
        tuiles.put("Le val du crépuscule", new Tuile("Le val du crépuscule"));
        tuiles.put("La tour du guet", new Tuile("La tour du guet"));
        tuiles.put("La caverne des ombres", new Tuile("La caverne des ombres", cristalArdent));
        tuiles.put("La caverne du brasier", new Tuile("La caverne du brasier", cristalArdent));
        tuiles.put("Le palais de corail", new Tuile("Le palais de corail", caliceDelombre));
        tuiles.put("Le palais des marees", new Tuile("Le palais des marees", caliceDelombre));
        tuiles.put("Le temple de la lune", new Tuile("Le temple de la lune", pierreSacree));
        tuiles.put("Le temple du soleil", new Tuile("Le temple du soleil", pierreSacree));
        tuiles.put("Le jardin des hurlements", new Tuile("Le jardin des hurlements", statueDeZephir));
        tuiles.put("Le jardin de murmures", new Tuile("Le jardin de murmures", statueDeZephir));
    }

    public HashMap<String, Tuile> getTuilesMap() {
        return tuiles;
    }

    public ArrayList<Tuile> getTuiles() {
        return new ArrayList<>(tuiles.values());
    }

    public Tuile getTuile(int index) {
        return getTuiles().get(index);
    }

    public Tuile getTuile(int ligne, int colonne) { // A optimiser
        for (Tuile t : getTuiles()) {
            if (t.getLigne() == ligne && t.getColonne() == colonne) {
                return t;
            }
        }
        return null;
    }
}
