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
    private List<Tresor> tresorsDispo;
    private List<Tresor> tresorsRecuperes;
    private int niveauEau = 0, nbJoueurs = 0, currentAventurier = 0;
    private ArrayList<CarteTresor> cartesTresor;
    private ArrayList<NomsTuiles> pileCarteInnondation;
    private ArrayList<NomsTuiles> defausseCarteInnondation;
    private ArrayList<Aventurier> aventuriers;
    private List<Roles> lesRoles;


    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        lesRoles= Arrays.asList(Roles.values());
        Collections.shuffle(lesRoles);
        tresorsDispo=Arrays.asList(Tresor.values()); //initialisation tresors
    }

    public void start() {
        Message m = new Message(TypeMessage.CHANGER_VUE);
        m.vue = "menu";
        notifierObservateur(m);
    }

    public void commencerPartie(int nbJoueurs, String level, String[] nomJoueurs) {
        setNbJoueurs(nbJoueurs);
        setNiveauEau(level);
        grille.melangerTuiles();
        initiateInondation();
        initiateTresorCards();
        initiateAventuriers(nomJoueurs);
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    private void initiateTresorCards() {
        cartesTresor = new ArrayList<>();

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
        for (int i = 0; i <= nbJoueurs-1; i++){
            if(lesRoles.get(i).equals(Roles.ingenieur)){
                aventuriers.add(new Ingenieur(nomJoueurs[i], grille));
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }
            else if(lesRoles.get(i).equals(Roles.pilote)){
                Pilote pilote = new Pilote(nomJoueurs[i], grille);
                aventuriers.add(pilote);
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }
            else if(lesRoles.get(i).equals(Roles.navigateur)){
                Navigateur navigateur = new Navigateur(nomJoueurs[i], grille);
                aventuriers.add(navigateur);
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }
            else if(lesRoles.get(i).equals(Roles.messager)){
                Messager messager = new Messager(nomJoueurs[i], grille);
                aventuriers.add(messager);
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }
            else if(lesRoles.get(i).equals(Roles.plongeur)){
                Plongeur plongeur = new Plongeur(nomJoueurs[i], grille);
                aventuriers.add(plongeur);
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }
            else if(lesRoles.get(i).equals(Roles.explorateur)){
                Explorateur explorateur = new Explorateur(nomJoueurs[i], grille);
                aventuriers.add(explorateur);
                System.out.println(aventuriers.get(i).getRole() + " " +aventuriers.get(i).getNomJoueur());
            }

        }
    }

    private void initiateInondation() {

    }

    public void quitter(){
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier, Tuile tuileDest){
        aventurier.seDeplacer(tuileDest);
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
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

    public void recupererTresor(Tresor tresor){
        tresorsDispo.remove(tresor);
        tresorsRecuperes.add(tresor);

    }

    public void setNbJoueurs(int i){
        nbJoueurs = i;
    }

    public void setNiveauEau(String niveauEau){
        switch(niveauEau) {
            case "Novice":
                this.niveauEau = 1;
                break;
            case "Normal":
                this.niveauEau = 2;
                break;
            case "Elite":
                this.niveauEau = 3;
                break;
            case "Legendaire":
                this.niveauEau = 4;
                break;
            default:
                this.niveauEau = 1;
        }
    }

    public int getNiveauEau() {
        return this.niveauEau;
    }

    public Grille getGrille() {
        return grille;
    }

    public Aventurier getCurrentAventurier() {
        return aventuriers.get(currentAventurier);
    }
}
