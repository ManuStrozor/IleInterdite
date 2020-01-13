package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aventuriers.*;
import enumerations.*;
import mvc.Message;
import mvc.Observe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;
    private List<Tresor> tresorsDispo;
    private List<Tresor> tresorsRecuperes;
    private int niveauEau = 0, nbJoueurs = 0, currentAventurier = 0;
    private ArrayList<CarteTresor> defausseCartesTresor = new ArrayList<>();
    private ArrayList<CarteTresor> pileCartesTresor;
    private ArrayList<NomsTuiles> pileCarteInnondation;
    private ArrayList<NomsTuiles> defausseCarteInnondation;
    private ArrayList<Aventurier> aventuriers;
    private List<Roles> lesRoles;


    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        lesRoles = Arrays.asList(Roles.values());
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
        pileCartesTresor = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            pileCartesTresor.add(new CarteTresor("Le Cristal ardent"));
            pileCartesTresor.add(new CarteTresor("La Pierre sacrée"));
            pileCartesTresor.add(new CarteTresor("La Statue du zéphyr"));
            pileCartesTresor.add(new CarteTresor("Le Calice de l’onde"));
        }

        for (int i = 0; i < 3; i++) {
            pileCartesTresor.add(new CarteTresor("Montée des eaux"));
            pileCartesTresor.add(new CarteTresor("Helicoptere"));
        }

        for (int i = 0; i < 2; i++) {
            pileCartesTresor.add(new CarteTresor("Sac de sable"));
        }

        Collections.shuffle(pileCartesTresor);

    }


    private void initiateAventuriers(String[] nomJoueurs) throws IllegalStateException {
        for (int i = 0; i < nbJoueurs; i++) {
            Aventurier newAventurier;
            switch(lesRoles.get(i)) {
                case explorateur:
                    newAventurier = new Explorateur(nomJoueurs[i], grille);
                    break;
                case navigateur:
                    newAventurier = new Navigateur(nomJoueurs[i], grille);
                    break;
                case plongeur:
                    newAventurier = new Plongeur(nomJoueurs[i], grille);
                    break;
                case messager:
                    newAventurier = new Messager(nomJoueurs[i], grille);
                    break;
                case pilote:
                    newAventurier = new Pilote(nomJoueurs[i], grille);
                    break;
                case ingenieur:
                    newAventurier = new Ingenieur(nomJoueurs[i], grille);
                    break;
                default:
                    throw new IllegalStateException("[InitiateAventuriers] Unexpected value: " + lesRoles.get(i));
            }
            distribuerCarteTresor(newAventurier);
            aventuriers.add(newAventurier);
            System.out.println("Role: " + aventuriers.get(i).getRole() + " Nom: " + aventuriers.get(i).getNomJoueur());
        }
    }


    private void distribuerCarteTresor(Aventurier aventurier){
        for (int k = 1; k <= 2 ; k ++){
            int i = pileCartesTresor.size() - 1;
            CarteTresor carte = pileCartesTresor.get(i);

            while(carte.getNomCarteTresor().equals("Montée des eaux")){
                i--;
                carte = pileCartesTresor.get(i);
            }
            boolean ajoutok;
            ajoutok = aventurier.ajouterCarte(pileCartesTresor.get(pileCartesTresor.size()-1)); // On ajoute à l'inventaire de l'aventurier la derniere carte de l'arraylist pilecarteTresor
            if (ajoutok){
                pileCartesTresor.remove(pileCartesTresor.get(pileCartesTresor.size()-1)); //et on l'enleve de la pile de carte tresor
                System.out.println("une carte a ete ajouté à : " + aventurier.getNomJoueur());
            }
        }
    }

    private void initiateInondation() {

    }

    public void quitter(){
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier, Tuile tuileDest) {
        aventurier.seDeplacer(tuileDest);
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    public void assecher(Tuile tuile, Aventurier aventurier) {
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

    public void donnerCarte(Aventurier donneur , Aventurier receveur, Carte carte ) {
        if(donneur.getTuile()== receveur.getTuile()&& receveur.getNombreCarte()<4){
            donneur.defausseCarte();
            receveur.ajouterCarte(carte);
        }
// il faudra completer la methode carte pour faire marcher les méthodes ajouterCarte et defausseCarte
    }

    public void recupererTresor(Tresor tresor) {
        tresorsDispo.remove(tresor);
        tresorsRecuperes.add(tresor);

    }

    public void setNbJoueurs(int nbJoueurs){
        this.nbJoueurs = nbJoueurs;
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
