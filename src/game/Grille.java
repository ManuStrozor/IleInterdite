package game;

import java.util.ArrayList;
import java.util.Collections;

import static enumerations.Tresor.*;
import static enumerations.Tresor.statueDeZephir;

/**
 *
 * @author estevmat
 */
public class Grille {


    private Tuile[][] tuilesGrille;
    private ArrayList<Tuile> nomDesTuiles;

     public Grille(Tuile[][] tuiles, ArrayList<Tuile> nomDesTuiles){
       tuilesGrille=tuiles;
       this.nomDesTuiles=nomDesTuiles;
    }
     public Grille (){
            tuilesGrille=new Tuile[6][6];
            nomDesTuiles=new ArrayList<>();
     }

    public void initTuiles() {

        nomDesTuiles = new ArrayList<>();
        Tuile t2 = new Tuile("La Porte de Bronze",null);
        nomDesTuiles.add(t2);
        Tuile t3 = new Tuile("La Porte d’Argent",null);
        nomDesTuiles.add(t3);
        Tuile t4 = new Tuile("La Porte de Fer",null);
        nomDesTuiles.add(t4);
        Tuile t5 = new Tuile("La porte d'or",null);
        nomDesTuiles.add(t5);
        Tuile t6 = new Tuile("Heliport",null);
        nomDesTuiles.add(t6);
        Tuile t7 = new Tuile("La porte de cuivre",null);
        nomDesTuiles.add(t7);
        Tuile t8 = new Tuile("Les falaises de l'oubli",null);
        nomDesTuiles.add(t8);
        Tuile t9 = new Tuile("Les dunes de l'illusion",null);
        nomDesTuiles.add(t9);
        Tuile t10 = new Tuile("Le pont des abimes",null);
        nomDesTuiles.add(t10);
        Tuile t11 = new Tuile("La foret pourpre",null);
        nomDesTuiles.add(t11);
        Tuile t12 = new Tuile("Le lagon perdu",null);
        nomDesTuiles.add(t12);
        Tuile t13 = new Tuile("Le marais brumeux",null);
        nomDesTuiles.add(t13);
        Tuile t14 = new Tuile("Observatoire",null);
        nomDesTuiles.add(t14);
        Tuile t15 = new Tuile("Le rocher fantome",null);
        nomDesTuiles.add(t15);
        Tuile t16 = new Tuile("Le val du crépuscule",null);
        nomDesTuiles.add(t16);
        Tuile t17 = new Tuile("La tour du guet",null);
        nomDesTuiles.add(t17);
        Tuile t18 = new Tuile("La caverne des ombres",cristalArdent);
        nomDesTuiles.add(t18);
        Tuile t19 = new Tuile("La caverne du brasier",cristalArdent);
        nomDesTuiles.add(t19);
        Tuile t20 = new Tuile("Le palais de corail",caliceDelombre);
        nomDesTuiles.add(t20);
        Tuile t25 = new Tuile("Le palais des marees",caliceDelombre);
        nomDesTuiles.add(t25);
        Tuile t21 = new Tuile("Le temple de la lune",pierreSacree);
        nomDesTuiles.add(t21);
        Tuile t22 = new Tuile("Le temple du soleil",pierreSacree);
        nomDesTuiles.add(t22);
        Tuile t23 = new Tuile("Le jardin des hurlements",statueDeZephir);
        nomDesTuiles.add(t23);
        Tuile t24 = new Tuile("Le jardin de murmures",statueDeZephir);
        nomDesTuiles.add(t24);


        Collections.shuffle(this.nomDesTuiles);
    }

    public void initGrille() {
        initTuiles();

        for (int t=0;t<24;t++) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    getTuilesGrille()[i][j] = getNomDesTuiles().get(t);
                    getTuilesGrille()[i][j].setLigne(i);
                    getTuilesGrille()[i][j].setColonne(j);
                }
            }
        }
        tuilesGrille[0][0] = null;
        tuilesGrille[0][1] = null;
        tuilesGrille[0][4] = null;
        tuilesGrille[0][5] = null;
        tuilesGrille[1][0] = null;
        tuilesGrille[1][5] = null;
        tuilesGrille[4][0] = null;
        tuilesGrille[4][5] = null;
        tuilesGrille[5][0] = null;
        tuilesGrille[5][1] = null;
        tuilesGrille[5][4] = null;
        tuilesGrille[5][5] = null;


    }

    public void afficheGrille(){
        initGrille();
        for(int i=0; i<6;i++){
            for(int j=0; j<6;j++){
                System.out.println("helloworld2");
                if (getTuilesGrille()[i][j] != null){
                    System.out.println("hello world");
                    getTuilesGrille()[i][j].afficheTuile();
                }
            }
        }
    }

    public Tuile[][] getTuilesGrille() {
        return tuilesGrille;
    }

    public void setTuilesGrille(Tuile[][] tuilesGrille) {
        this.tuilesGrille = tuilesGrille;
    }

    /*public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }*/

    public ArrayList<Tuile> getNomDesTuiles() {
        return nomDesTuiles;
    }

    public void setNomDesTuiles(ArrayList<Tuile> nomDesTuiles) {
        this.nomDesTuiles = nomDesTuiles;
    }
}
