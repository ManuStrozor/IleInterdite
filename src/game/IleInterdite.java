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

import java.util.*;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;
    private ArrayList<Tresor> tresorsDispo, tresorsRecuperes;
    private int niveauEau = 0, nbJoueurs = 0, joueur = 0, cartesAPiocher = 2, tourjeu=0;
    private ArrayList<CarteTresor> pileCartesTresor, defausseCartesTresor;
    private ArrayList<CarteInondation> pileCartesInondation, defausseCartesInondation;

    private ArrayList<Aventurier> aventuriers;
    private ArrayList<Tuile> tuilesTresor;
    private Aventurier joueurASauver;

    Random random = new Random();

    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        tresorsDispo= new ArrayList<>();
        Collections.addAll(tresorsDispo, Tresor.values());
        tresorsRecuperes= new ArrayList<>(); //initialisation tresors
        tuilesTresor = new ArrayList<>();
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void commencerPartie(String[] names) {
        grille.melangerTuiles();
        initiateTresorCards();
        initiateInondation();
        initiateAventuriers(names);
        tirerCartesIondation();
        DEBUGILEINTERDITE();
        this.notifierObservateur(new Message(TypeMessage.UPDATE_IHM));
    }

    private void initiateTresorCards() {
        pileCartesTresor = new ArrayList<>();
        defausseCartesTresor = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pileCartesTresor.add(new CarteTresor(Tresor.Le_Cristal_Ardent));
            pileCartesTresor.add(new CarteTresor(Tresor.La_Pierre_Sacree));
            pileCartesTresor.add(new CarteTresor(Tresor.La_Statue_Du_Zephyr));
            pileCartesTresor.add(new CarteTresor(Tresor.Le_Calice_De_L_Onde));
        }
        for (int i = 0; i < 3; i++) {
            pileCartesTresor.add(new CarteTresor(Tresor.Montee_Des_Eaux));
            pileCartesTresor.add(new CarteTresor(Tresor.Helicoptere));
        }
        for (int i = 0; i < 2; i++) {
            pileCartesTresor.add(new CarteTresor(Tresor.Sac_De_Sable));
        }
    }

    private void initiateInondation() {
        pileCartesInondation = new ArrayList<>();
        defausseCartesInondation = new ArrayList<>();
        for (Tuile t : grille.getTuiles()) {
            pileCartesInondation.add(new CarteInondation(t.getNom()));
        }
    }

    private void initiateAventuriers(String[] names) throws IllegalStateException {
        ArrayList<Role> roles = new ArrayList<>(Arrays.asList(Role.values()));
        Collections.shuffle(roles);
        for (int i = 0; i < getNbJoueurs(); i++) {
            Aventurier newA;
            switch (roles.get(i)) {
                case explorateur:
                    newA = new Explorateur(names[i], grille);
                    break;
                case navigateur:
                    newA = new Navigateur(names[i], grille);
                    break;
                case plongeur:
                    newA = new Plongeur(names[i], grille);
                    break;
                case messager:
                    newA = new Messager(names[i], grille);
                    break;
                case pilote:
                    newA = new Pilote(names[i], grille);
                    break;
                case ingenieur:
                    newA = new Ingenieur(names[i], grille);
                    break;
                default:
                    throw new IllegalStateException("[InitiateAventuriers] Unexpected value: " + roles.get(i));
            }
            aventuriers.add(newA);
           distribuerCarteTresor(newA);
        }
    }

    private void distribuerCarteTresor(Aventurier aventurier) { // Distribue 2 carte trésor au début sauf montée eaux
        CarteTresor c;
        for(int i=0; i<2; i++){
            c = pileCartesTresor.get(random.nextInt(pileCartesTresor.size()-1));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                defausserCartesTresor(c);
                i--;
            } else {
                aventurier.getInventaire().add(c);
                defausserCartesTresor(c);
            }
        }
    }

    private void tirerCartesIondation(){ // Distribue 6 carte inondation au début du jeu
        for(int i = 0; i < 6; i++) {
            CarteInondation c;
            c = pileCartesInondation.get(random.nextInt(pileCartesInondation.size()));
            grille.getTuilesMap().get(c.getNom()).innonder();
            defausserCarteInnondation(c);
        }
    }

    public void quitter() {
        System.exit(0);
    }

    public void seDeplacer(Aventurier aventurier, Tuile tuileDest) {
        aventurier.seDeplacer(tuileDest);
        aventurier.consommerAction(1);
    }

    public void assecher(Aventurier aventurier, Tuile tuile) {
        tuile.assecher();
        aventurier.consommerAction((aventurier.getRole() == Role.ingenieur) ? 0.5 : 1);
    }

    public void donnerCarte(CarteTresor carte, Aventurier receveur) {
        getJoueur().getInventaire().remove(carte);
        receveur.getInventaire().add(carte);
        getJoueur().consommerAction(1);
    }

    public void recupererTresor(Tresor tresor) {
        if(estRecuperable(getJoueur())) { // methode avant

            tresorsDispo.remove(tresor);
            tresorsRecuperes.add(tresor);

            System.out.println(getJoueur().getInventaire().size());

            ArrayList<Carte> cartesADegager = new ArrayList<>();
            for (CarteTresor c : getJoueur().getInventaire()) {
                if(c.getTresor() == tresor) {
                    defausserCartesTresor(c);
                    cartesADegager.add(c);
                }
            }
            getJoueur().getInventaire().removeAll(cartesADegager);
            getJoueur().consommerAction(1);
            System.out.println(getJoueur().getNomJoueur()+ " recupere le tresor "+tresor.name());
        }
    }

    public void passerTour() { //passe le tour du joueur et fais piocher les cartes
        piocherCarteTresor();
        piocherCarteInnondation();
        getJoueur().setNbActions(3);
        if(joueur == aventuriers.size()-1){
            joueur = 0;
        } else {
            joueur++;
        }

        if(perdrePartie(grille)== true) {
            System.out.println("VOUS AVEZ PERDU");
            Message msg = new Message(TypeMessage.CHANGER_VUE);
            msg.vue = "perdu";
            this.notifierObservateur(msg);
        }
        tourjeu++;
        DEBUGILEINTERDITE();

        this.notifierObservateur(new Message(TypeMessage.UPDATE_IHM));
    }

    public void sauverJoueur() {
        for (Aventurier a : aventuriers) {

            if (a.getTuile().getEtatTuile() == EtatTuile.coulee && a.getTuilesAccessibles(grille) != null) {
                // si la tuile sur laquelle est l'aventurier coule et qu'il y a des tuiles accessibles autour de lui , alors il y va
               joueurASauver= a;
               Message m = new Message(TypeMessage.SAUVER);
               m.a=a;
               this.notifierObservateur(m);
            }
        }
    }

    public void piocherCarteTresor() { //Fais piocher 2 carte trésor si carte = montée des eaux lance la méthode usecartemontteeau
        CarteTresor c;
        for(int i=0; i<2; i++){
            c = pileCartesTresor.get(random.nextInt(pileCartesTresor.size()-1));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                useCarteMonteeDesEaux();
            } else {
                getJoueur().getInventaire().add(c);
                pileCartesTresor.remove(c);
                defausserCartesTresor(c);
            }
        }
    }

    public void piocherCarteInnondation(){ // fais piocher le nombre de carte innondation en fonction du niveau eau
        CarteInondation c;
        for(int i=0; i<cartesAPiocher; i++){
            c = pileCartesInondation.get(random.nextInt(pileCartesInondation.size()-1));
            pileCartesInondation.add(c);
            defausserCarteInnondation(c);
            grille.getTuilesMap().get(c.getNom()).innonder();
        }
    }

    public void defausserCarteInnondation(CarteInondation c){
        defausseCartesInondation.add(c);
        pileCartesInondation.remove(c);
        if(pileCartesInondation.isEmpty()){
            defausseCartesInondation.addAll(pileCartesInondation);
        }
    }

    public void defausserCartesTresor(CarteTresor c){
        defausseCartesTresor.add(c);
        pileCartesTresor.remove(c);
        if(pileCartesTresor.isEmpty()){
            defausseCartesTresor.addAll(pileCartesTresor);
        }
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public void setNiveauEau(String niveauEau) {
        switch (niveauEau) {
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

    public Aventurier getJoueur() {
        return !aventuriers.isEmpty() ? aventuriers.get(joueur) : null;
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void useCarteMonteeDesEaux() { // Déclenché automatiquement...ne pas oublier de defausser !
        niveauEau = niveauEau + 1;
        if (niveauEau == 3 || niveauEau == 6 || niveauEau == 8) {
                cartesAPiocher+=1;
        }
    }

    public void useCarteSacDeSable(Tuile tuile) { // Montrer les tuiles inondées AVANT quand carte cliquée !
        tuile.assecher();
        defausseCartesTresor.add(getJoueur().getCarteSacDeSable());
        getJoueur().getInventaire().remove(getJoueur().getCarteSacDeSable());
    }

    public void utiliserHelico() {
        gagnerPartie();
        Message m = new Message(TypeMessage.HELICO);
        this.notifierObservateur(m);
    }

    public boolean estRecuperable(Aventurier aventurier) { // Déplacer dans Tuile.java

        boolean conditionOK = false;
        int nbcarte = 0;
        ArrayList<CarteTresor> inventaire = aventurier.getInventaire(); //inventaire de l'aventurier
        Tresor tresorTuile = aventurier.getTuile().getTresor(); //Type de la tuile où est l'aventurier

        if (aventurier.getTuile().getTresor() != null) {

            //si le tresor n'est pas deja recup
            for (Tresor t : tresorsDispo) {
                if (t == tresorTuile) {
                    conditionOK = true;
                    break;
                }
            }

            //si l'aventurier a le bon nombre de carte du meme type que la tuile où il se situe
            for (int i = 0; i < inventaire.size(); i++) {
                if (inventaire.get(i).getTresor() == tresorTuile) {
                    nbcarte++;

                }
            }

            if (nbcarte >= 4) {
                conditionOK = true;
            } else {
                conditionOK = false;
            }
        }

        return conditionOK;
    }

    public boolean perdrePartie(Grille grille) {

        for (Aventurier a : aventuriers) { //si un aventurier est mort, la partie est perdue
            if ( a.mort(grille)){
                return a.mort(grille);
            }
        }

        for (Tresor t : tresorsDispo) { // pour chaque tresor que l'on a pas encore recuperé
            // Si les tuiles permettants de recuperer ce tresor sont toutes les deux coulées, on retourne true
            if (getTuileTresor(t).get(0).getEtatTuile() == EtatTuile.coulee
                    && getTuileTresor(t).get(1).getEtatTuile() == EtatTuile.coulee){
                return getTuileTresor(t).get(0).getEtatTuile() == EtatTuile.coulee
                        && getTuileTresor(t).get(1).getEtatTuile() == EtatTuile.coulee;
            }

        }

        return grille.getTuilesMap().get(Nom.Heliport).getEtatTuile() == EtatTuile.coulee || getNiveauEau() >= 10; // marche pas
    }

    public ArrayList<Tuile> getTuileTresor(Tresor t) {
        ArrayList<Tuile> tuiles = new ArrayList<>();
        Nom[] sp;
        if(t == Tresor.Le_Cristal_Ardent) {
            sp = new Nom[]{Nom.La_Caverne_Des_Ombres, Nom.La_Caverne_Du_Brasier};
        } else if(t == Tresor.Le_Calice_De_L_Onde) {
            sp = new Nom[]{Nom.Le_Palais_De_Corail, Nom.Le_Palais_Des_Marees};
        } else if(t == Tresor.La_Pierre_Sacree) {
            sp = new Nom[]{Nom.Le_Temple_De_La_Lune, Nom.Le_Temple_Du_Soleil};
        } else {
            sp = new Nom[]{Nom.Le_Jardin_Des_Hurlements, Nom.Le_Jardin_Des_Murmures};
        }
        tuiles.add(grille.getTuilesMap().get(sp[0]));
        tuiles.add(grille.getTuilesMap().get(sp[1]));
        return tuiles;
    }

    public void gagnerPartie(){
        if (aventuriers.size() == grille.getTuilesMap().get(Nom.Heliport).getAventuriers().size() && tresorsRecuperes.size()==4){
            System.out.println("Vous avez gagné !");
        }
    }

    public Aventurier getJoueurASauver() {
        return joueurASauver;
    }

    public void defaussetoi(ArrayList<CarteTresor> ct) { // en cours de codage
        getJoueur().defaussetoi(ct);
        for(CarteTresor c: ct){
            defausserCartesTresor(c);
        }
    }

    public void DEBUGILEINTERDITE() { //DEBUGGER CONSOLE
        System.out.print("\n--------------------------------------Tour de jeu numéro " + tourjeu + " : ------------------------------------\n");
        System.out.print("\nInformations joueur en cours : \n\n\t" +
                "Nom joueur : " + getJoueur().getNomJoueur() +
                "\n\tRole : " + getJoueur().getRole().name() +
                "\n\tActions; " + getJoueur().getNbActions() +
                "\n\tCartes Inventaire : ");
        for (int j = 0; j < getJoueur().getInventaire().size(); j++) {
            System.out.print(getJoueur().getInventaire().get(j).getName() + ", ");
        }

        System.out.print("\n\tTuiles accessibles : ");

        for (int i = 0; i < getJoueur().getTuilesAccessibles(grille).size(); i++) {
            System.out.print(getJoueur().getTuilesAccessibles(grille).get(i).getName() + ", ");
        }

        System.out.print("\n\nInformations pour la partie : \n\n\t" + "Le niveau d'eau est : " + niveauEau + "\n\tLe nombre de carte à piocher est : " + cartesAPiocher);

        System.out.print("\n\tTrésors dispo : ");
        for (int i = 0; i < tresorsDispo.size(); i++) {
            System.out.print(tresorsDispo.get(i).name() + " | ");
        }

        System.out.print("\n\tTrésors récupérés : ");
        for (int i = 0; i < tresorsRecuperes.size(); i++) {
            System.out.print(tresorsRecuperes.get(i).name() + " | ");
        }

        System.out.print("\n\tLes cartes de la pile innondation : ");
        for (int i = 0; i < pileCartesInondation.size(); i++) {
            System.out.print(pileCartesInondation.get(i).getName() + " | ");
        }

        System.out.print("\n\tLes cartes de la pile trésor : ");
        for (int i = 0; i < pileCartesTresor.size(); i++) {
            System.out.print(pileCartesTresor.get(i).getName() + " | ");
        }

        System.out.print("\n\tLes cartes de la defausse innondation : ");
        for (int i = 0; i < defausseCartesInondation.size(); i++) {
            System.out.print(defausseCartesInondation.get(i).getName() + " | ");
        }

        System.out.print("\n\tLes cartes de la defausse trésor : ");
        for (int i = 0; i < defausseCartesTresor.size(); i++) {
            System.out.print(defausseCartesTresor.get(i).getName() + " | ");
        }

        System.out.print("\n\nInformations sur les autres joueurs : \n\n");
        for (int i = 0; i < aventuriers.size(); i++) {
            if (aventuriers.get(i) == getJoueur()) {

            } else {
                System.out.print("\tNom du joueur : " + aventuriers.get(i).getNomJoueur() +
                        "\n\tRole du joueur : " + aventuriers.get(i).getRole() +
                        "\n\tTuile où se trouve le joueur : " + aventuriers.get(i).getTuile().getNom() +
                        "\n\tActions; " + getJoueur().getNbActions() +
                        "\n\tLes cartes du joueurs : ");
                for (int j = 0; j < aventuriers.get(i).getInventaire().size(); j++) {
                    System.out.print(aventuriers.get(i).getInventaire().get(j).getName() + ", ");
                }
                System.out.println("\n");
            }
        }
    }
}
