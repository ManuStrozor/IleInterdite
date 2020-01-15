package game;

import enumerations.Nom;
import enumerations.Tresor;

import java.util.*;

/**
 *
 * @author estevmat
 */
public class Grille {

    private HashMap<Nom, Tuile> tuiles;

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
        for (Nom nom : Nom.values()) {
            Tuile newTuile = new Tuile(nom);
            tuiles.put(nom, newTuile);
            if(newTuile.getName().contains("caverne")) {
                newTuile.setTresor(Tresor.Le_Cristal_Ardent);
            } if(newTuile.getName().contains("palais")) {
                newTuile.setTresor(Tresor.Le_Calice_De_L_Onde);
            } if(newTuile.getName().contains("temple")) {
                newTuile.setTresor(Tresor.La_Pierre_Sacree);
            } if(newTuile.getName().contains("jardin")) {
                newTuile.setTresor(Tresor.La_Statue_Du_Zephyr);
            }
        }
    }

    public HashMap<Nom, Tuile> getTuilesMap() {
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
