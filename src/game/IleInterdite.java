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

import static enumerations.Tresor.*;
import static enumerations.Tresor.statueDeZephir;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;
    private List<Tresor> tresorsDispo;
    private List<Tresor> tresorsRecuperes;
    private int niveauEau = 0, nbJoueurs = 0, currentAventurier = 0;
    private ArrayList<CarteTresor> pileCartesTresor;
    private ArrayList<CarteTresor> defausseCartesTresor;
    private ArrayList<CarteInondation> pileCartesInondation;
    private ArrayList<CarteInondation> defausseCartesInondation;
    private ArrayList<Aventurier> aventuriers;
    private List<Roles> lesRoles;
    private ArrayList<Tuile> tuilesTresor;


    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        lesRoles = Arrays.asList(Roles.values());
        Collections.shuffle(lesRoles);
        tresorsDispo=Arrays.asList(Tresor.values()); //initialisation tresors
        tuilesTresor = new ArrayList<>();
        setTuilesTresor();
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
        initiateTresorCards();
        initiateAventuriers(nomJoueurs);

        Message m = new Message(TypeMessage.UPDATE_DASHBOARD);
        m.aventuriers = aventuriers;
        notifierObservateur(m);

        initiateInondation();
        tirerCartesIondation(6);

        m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    private void initiateTresorCards() {
        pileCartesTresor = new ArrayList<>();
        defausseCartesTresor = new ArrayList<>();
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

    private void initiateInondation() {
        pileCartesInondation = new ArrayList<>();
        defausseCartesInondation = new ArrayList<>();
        for(Tuile t : grille.getTuiles()){
            pileCartesInondation.add(new CarteInondation(t.getNom()));
        }
        Collections.shuffle(pileCartesInondation);
    }

    public void tirerCartesIondation(int nb) {
        for(int i = 0; i < nb; i++) {
            grille.getTuilesMap().get(pileCartesInondation.get(0).getNom()).innonder();
            defausseCartesInondation.add(pileCartesInondation.get(0));
            pileCartesInondation.remove(0);
        }
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
            aventuriers.add(newAventurier);
            distribuerCarteTresor(newAventurier);
        }
    }

    private void distribuerCarteTresor(Aventurier aventurier) {
        for (int k = 1; k <= 2; k++) {
            int i = pileCartesTresor.size() - 1;
            CarteTresor carte = pileCartesTresor.get(i);

            while(carte.getNom().equals("Montée des eaux")) {
                i--;
                carte = pileCartesTresor.get(i);
            }
            if(aventurier.ajouterCarte(carte)) {
                pileCartesTresor.remove(carte);
            }
        }
    }

    public void quitter(){
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier, Tuile tuileDest) {
        aventurier.seDeplacer(tuileDest);
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
        aventurier.moinsUneAction(aventurier);
    }

    public void assecher(Tuile tuile, Aventurier aventurier) {
        //if (aventurier.peutAcceder(tuile)== true ) { tuile.assecher();}
        tuile.assecher();

        double nbActions = aventurier.getNbActions();

        if (aventurier.getRole() == Roles.ingenieur){
            aventurier.setNbActions(nbActions - 0.5);
        }else{
            aventurier.moinsUneAction(aventurier);
        }
        Message m = new Message(TypeMessage.UPDATE_GRILLE);
        m.grille = grille;
        notifierObservateur(m);
    }

    public void donnerCarte(Aventurier donneur , Aventurier receveur, Carte carte ) {
        if (donneur.aventurierAccessibles(donneur).contains(receveur)){
            donneur.defausseCarte();
            receveur.ajouterCarte(carte);
        }
        donneur.moinsUneAction(donneur);
// il faudra completer la methode carte pour faire marcher les méthodes ajouterCarte et defausseCarte
    }

    public void recupererTresor(Aventurier aventurier, Tresor tresor) {

        tresorsDispo.remove(tresor);
        tresorsRecuperes.add(tresor);
        aventurier.moinsUneAction(aventurier);
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

    public boolean estRecuperable(Aventurier aventurier) {

        boolean conditionOK = false;
        int nbcarte = 0;
        Carte[] inventaire = aventurier.getInventaire();    //inventaire de l'aventurier
        Tresor tresorTuile = aventurier.getTuile().getTresor(); //Type de la tuile où est l'aventurier

        //si le tresor n'est pas deja recup
        for (Tresor t : tresorsDispo) {
            if (t == tresorTuile) {
                conditionOK = true;
                break;
            }
        }

        //si l'aventurier a le bon nombre de carte du meme type que la tuile où il se situe
        for (int i = 0; i < inventaire.length; i++) {
            if (inventaire[i].getTresor() == tresorTuile) {
                nbcarte++;
            }
        }
        if (nbcarte >= 4) {
            conditionOK = true;
        } else {
            conditionOK = false;
        }
        return conditionOK;
    }

    public void perdrePartie(Aventurier aventurier, Grille grille){



        if (aventurier.mort(aventurier, aventurier.getTuile(), grille) == true ) {
            System.out.println(" vous avez perdu ! ");
        }
        else if (grille.getTuilesMap().get("Heliport").getEtatTuile() == EtatTuile.coulee ){
            System.out.println(" vous avez perdu ! ");
        }
        else if (getNiveauEau() == 10){
            System.out.println(" vous avez perdu ! ");
        }
        int nbcartecoule = 0;

        for(Tresor t : Tresor.values()){    //pour tout les tresors
            for (int i = 0; i < tresorsDispo.size(); i++){  //pour les tresors disponibles
                if(tresorsDispo.get(i) == t){   // si le tresor t est disponible on verifie ses tuiles tresor
                    for (Tuile tuileT : tuilesTresor){  // pour toutes les tuiles tresor
                       if (tuileT.getTresor()== t ){
                           if(tuileT.getEtatTuile() == EtatTuile.coulee){  //si la tuile est coulée
                               nbcartecoule++;
                           }
                       }

                    }
                    if (nbcartecoule==2){
                        System.out.println(" vous avez perdu ! ");
                    }
                }
            }
        }



    }

    public void setTuilesTresor(){
        tuilesTresor.add( new Tuile("La caverne des ombres", cristalArdent));
        tuilesTresor.add( new Tuile("La caverne du brasier", cristalArdent));
        tuilesTresor.add( new Tuile("Le palais de corail", caliceDelombre));
        tuilesTresor.add( new Tuile("Le palais des marees", caliceDelombre));
        tuilesTresor.add( new Tuile("Le temple de la lune", pierreSacree));
        tuilesTresor.add( new Tuile("Le temple du soleil", pierreSacree));
        tuilesTresor.add( new Tuile("Le jardin des hurlements", statueDeZephir));
        tuilesTresor.add( new Tuile("Le jardin de murmures", statueDeZephir));
    }

}
