package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.Aventurier;
import aventuriers.Ingenieur;
import enumerations.NomsTuiles;
import enumerations.Roles;
import enumerations.Tresor;
import mvc.Message;
import mvc.Observe;
import enumerations.TypeMessage;

import java.util.ArrayList;
import java.util.Collections;

import static enumerations.EtatTuile.assechee;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;
    private int niveauEau = 0;

    private ArrayList<Tresor> tresorsRecup;
    private ArrayList<CarteTresor>cartesTresor;
    private ArrayList<NomsTuiles> pileCarteInnondation;
    private ArrayList<NomsTuiles> defausseCarteInnondation;
    private Aventurier[] aventuriers;
    private Roles[] roles ;
    private int nbJoueurs;


    public IleInterdite() {
        grille = new Grille();
        tresorsRecup = new ArrayList<>();
        aventuriers = new Aventurier[3];
        roles = Roles.values();
        for (int i = 0; i <= roles.length - 1 ; i++){
            System.out.println(roles[i]);
        }
    }

    public void start() {
        Message m = new Message(TypeMessage.CHANGER_VUE);
        m.vue = "menu";
        notifierObservateur(m);
    }

    public void commencerPartie() { // cette methode = appuyer sur start donc on
        grille.melangerTuiles();                 // a besoin du niveau d'eau en parametre ?
        initiateInondation();
        initiateTresorCards();
        //initiateAventuriers();
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.vue = "jeu";
        m.grille = grille;
        notifierObservateur(m);
    }

    public void initiateDifficulty(int niveauEau) {

    }
//    public void setNomJoueurs(String[] nomJoueurs){
//        for (int i=0;i<=nomJoueurs.length;i++){
//            this.
//        }
//    }
    private void initiateTresorCards() {
        cartesTresor=new ArrayList<>();


        for (int j = 0; j < 5; j++) {
            CarteTresor carte = new CarteTresor("Le Cristal ardent");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 5; j++) {
            CarteTresor carte = new CarteTresor("La Pierre sacrée");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 5; j++) {
            CarteTresor carte = new CarteTresor("La Statue du zéphyr");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 5; j++) {
            CarteTresor carte = new CarteTresor("Le Calice de l’onde");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 3; j++) {
            CarteTresor carte = new CarteTresor("Montée des eaux");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 2; j++) {
            CarteTresor carte = new CarteTresor("Sac de sable");
            cartesTresor.add(carte);
        }
        for (int j = 0; j < 3; j++) {
            CarteTresor carte = new CarteTresor("Helicoptere");
            cartesTresor.add(carte);
        }

        Collections.shuffle(cartesTresor);
    }


//    private void initiateAventuriers(String[] nomJoueurs) {
//        int random;
//
//        for (int i = 0; i <= nomJoueurs.length; i++){
//            Aventurier aventurier = new Aventurier(nomJoueurs[i]);
//        }
//    }

    private void initiateInondation() {

    }

    public void quitter(){
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier , Tuile nouvelle ){
       // nouvelle = getMessage(aventurier) ;

           if (aventurier.estAccessible()== true){
              aventurier.getTuile().getAventuriers().remove(aventurier);
              nouvelle.getAventuriers().add(aventurier);
           }
           else {
               System.out.println("cette tuile n'est pas accéssible ");
           }



        double nbActions = aventurier.getNbActions();
        aventurier.setNbActions(nbActions - 1);

    }
    public void assecher(Tuile tuile, Aventurier aventurier){
        //if (aventurier.peutAcceder(tuile)== true ) { tuile.assecher();}
        tuile.assecher();

        double nbActions = aventurier.getNbActions();

        if (aventurier.getRole() == Roles.ingenieur){
            aventurier.setNbActions(nbActions - 0.5);
        }else{
            aventurier.setNbActions(nbActions - 1);
        }
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    public void recupererTresor(Aventurier aventurier){
        Tresor tresor = aventurier.getTuile().getTuileTresor();
        tresorsRecup.add(tresor);

    }


    public void setNbJoueurs(int i){
        nbJoueurs = i;
    }

    public void setNiveauEau(int niveauEau){
        this.niveauEau=niveauEau;
    }
    public int getNiveauEau() {
        return this.niveauEau;
    }

}
