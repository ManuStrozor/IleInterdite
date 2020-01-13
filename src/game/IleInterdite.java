package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.*;
import enumerations.NomsTuiles;
import enumerations.Roles;
import enumerations.Tresor;
import mvc.Message;
import mvc.Observe;
import enumerations.TypeMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private List<Roles> lesRoles;


    public IleInterdite() {
        grille = new Grille();
        tresorsRecup = new ArrayList<>();
        aventuriers = new Aventurier[4];
        roles = new Roles[6];
        roles = Roles.values();
        lesRoles= Arrays.asList(roles);
        Collections.shuffle(lesRoles);

        for (int i = 0; i < 6 ; i++){
            System.out.println(lesRoles.get(i));
        }
    }

    public void start() {
        Message m = new Message(TypeMessage.CHANGER_VUE);
        m.vue = "menu";
        notifierObservateur(m);
    }

    public void commencerPartie(String[] nomJoueurs) {               // cette methode = appuyer sur start donc on
        grille.melangerTuiles();                                        // a besoin du niveau d'eau en parametre ?
        initiateInondation();
        initiateTresorCards();
        initiateAventuriers(nomJoueurs);
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.vue = "jeu";
        m.grille = grille;
        notifierObservateur(m);
    }

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


    private void initiateAventuriers(String[] nomJoueurs) {

        for (int i = 0; i <= nomJoueurs.length; i++){
            if(lesRoles.get(i).equals("ingenieur")){
                Ingenieur ingenieur = new Ingenieur(nomJoueurs[i]);
            }
            else if(lesRoles.get(i).equals("pilote")){
                Pilote pilote = new Pilote(nomJoueurs[i]);
            }
            else if(lesRoles.get(i).equals("navigateur")){
                Navigateur navigateur = new Navigateur(nomJoueurs[i]);
            }
            else if(lesRoles.get(i).equals("messager")){
                Messager messager = new Messager(nomJoueurs[i]);
            }
            else if(lesRoles.get(i).equals("plongeur")){
                Plongeur plongeur = new Plongeur(nomJoueurs[i]);
            }
            else if(lesRoles.get(i).equals("explorateur")){
                Explorateur explorateur = new Explorateur(nomJoueurs[i]);
            }
        }
    }

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

    public void donnerCarte(Aventurier donneur , Aventurier receveur, Carte carte ){
        if(donneur.getTuile()== receveur.getTuile()&& receveur.getNombreCarte()<4){
            donneur.defausseCarte();
            receveur.ajouterCarte(carte);
        }
// il faudra completer la methode carte pour faire marcher les méthodes ajouterCarte et defausseCarte
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
