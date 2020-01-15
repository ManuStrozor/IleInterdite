package game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import aventuriers.*;
import enumerations.*;
import mvc.Message;
import mvc.Observe;

/**
 *
 * @author turbetde,estevmat
 */
public class IleInterdite extends Observe {

    private Grille grille;
    private ArrayList<Tresor> tresorsDispo;
    private int niveauEau = 0, nbJoueurs = 0, joueur = 0, cartesAPiocher = 2, tourjeu = 0;
    private ArrayList<CarteTresor> pileTresor, defausseTresor;
    private ArrayList<CarteInondation> pileInond, defausseInond;

    private ArrayList<Aventurier> aventuriers;
    private Aventurier joueurASauver;

    Random random = new Random();

    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        tresorsDispo = new ArrayList<>();
        Collections.addAll(tresorsDispo, Tresor.values());
        tresorsDispo.remove(Tresor.Montee_Des_Eaux);
        tresorsDispo.remove(Tresor.Helicoptere);
        tresorsDispo.remove(Tresor.Sac_De_Sable);
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void commencerPartie(String[] names) {
        grille.melangerTuiles();
        initiateTresorCards();
        initiateInondation();
        initiateAventuriers(names);
        tirerCartesIondation(6);
        DEBUGILEINTERDITE();
        this.notifierObservateur(new Message(TypeMessage.UPDATE_IHM));
    }

    private void initiateTresorCards() {
        pileTresor = new ArrayList<>();
        defausseTresor = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pileTresor.add(new CarteTresor(Tresor.Le_Cristal_Ardent));
            pileTresor.add(new CarteTresor(Tresor.La_Pierre_Sacree));
            pileTresor.add(new CarteTresor(Tresor.La_Statue_Du_Zephyr));
            pileTresor.add(new CarteTresor(Tresor.Le_Calice_De_L_Onde));
        }
        for (int i = 0; i < 3; i++) {
            pileTresor.add(new CarteTresor(Tresor.Montee_Des_Eaux));
            pileTresor.add(new CarteTresor(Tresor.Helicoptere));
        }
        for (int i = 0; i < 2; i++) {
            pileTresor.add(new CarteTresor(Tresor.Sac_De_Sable));
        }
    }

    private void initiateInondation() {
        pileInond = new ArrayList<>();
        defausseInond = new ArrayList<>();
        for (Tuile t : grille.getTuiles()) {
            pileInond.add(new CarteInondation(t.getNom()));
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
            c = pileTresor.get(random.nextInt(pileTresor.size()-1));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                defausserCartesTresor(c);
                i--;
            } else {
                aventurier.getInventaire().add(c);
                defausserCartesTresor(c);
            }
        }
    }

    private void tirerCartesIondation(int n) {
        for(int i = 0; i < n; i++) {
            CarteInondation c = pileInond.get(random.nextInt(pileInond.size()));
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

            System.out.println(getJoueur().getInventaire().size());

            ArrayList<CarteTresor> cartesADegager = new ArrayList<>();
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

    public void passerTour() {
        piocherCarteTresor();
        tirerCartesIondation(cartesAPiocher);
        getJoueur().setNbActions(3);

        if(joueur == aventuriers.size()-1){
            joueur = 0;
        } else {
            joueur++;
        }

        if(isGameover()) {
            Message msg = new Message(TypeMessage.CHANGER_VUE);
            msg.vue = "perdu";
            this.notifierObservateur(msg);
        } else {
            tourjeu++;
            DEBUGILEINTERDITE();
            this.notifierObservateur(new Message(TypeMessage.UPDATE_IHM));
        }
    }

    public void sauverJoueur() {
        for (Aventurier a : aventuriers) {
            if (a.getTuile().getEtat() == Etat.coulee && a.getTuilesAccessibles(grille) != null) {
                // si la tuile sur laquelle est l'aventurier coule et qu'il y a des tuiles accessibles autour de lui , alors il y va
               joueurASauver = a;
               Message m = new Message(TypeMessage.SAUVER);
               m.a = a;
               this.notifierObservateur(m);
            }
        }
    }

    public void piocherCarteTresor() { //Fais piocher 2 carte trésor si carte = montée des eaux lance la méthode usecartemontteeau
        CarteTresor c;
        for(int i=0; i<2; i++){
            c = pileTresor.get(random.nextInt(pileTresor.size()-1));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                useCarteMonteeDesEaux();
            } else {
                getJoueur().getInventaire().add(c);
                pileTresor.remove(c);
                defausserCartesTresor(c);
            }
        }
    }

    public void defausserCarteInnondation(CarteInondation c){
        defausseInond.add(c);
        pileInond.remove(c);
        if(pileInond.isEmpty()){
            defausseInond.addAll(pileInond);
            defausseInond.clear();
        }
    }

    public void defausserCartesTresor(CarteTresor c){
        defausseTresor.add(c);
        pileTresor.remove(c);
        if(pileTresor.isEmpty()){
            defausseTresor.addAll(pileTresor);
            defausseTresor.clear();
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
        niveauEau++;
        if (niveauEau == 3 || niveauEau == 6 || niveauEau == 8) cartesAPiocher++;
    }

    public void useCarteSacDeSable(Tuile tuile) { // Montrer les tuiles inondées AVANT quand carte cliquée !
        tuile.assecher();
        defausseTresor.add(getJoueur().getCarteSacDeSable());
        getJoueur().getInventaire().remove(getJoueur().getCarteSacDeSable());
    }

    public void utiliserHelico() {
        if(isWon()) {
            System.out.println("Vous avez gagné !!!");
        } else {
            Message m = new Message(TypeMessage.HELICO);
            this.notifierObservateur(m);
        }
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

    public boolean isGameover() {
        for (Aventurier a : aventuriers) {
            // si un aventurier est mort
            if(a.mort(grille)) {
                return true;
            }
        }
        for (Tresor t : tresorsDispo) {
            ArrayList<Tuile> tuiles = getTuileTresor(t);
            // Si deux tuiles d'un même trésor sont coulées
            if (tuiles.get(0).getEtat() == Etat.coulee && tuiles.get(1).getEtat() == Etat.coulee) {
                return true;
            }
        }
        Tuile heliport = grille.getTuilesMap().get(Nom.Heliport);
        return heliport.getEtat() == Etat.coulee || getNiveauEau() >= 10;
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

    public boolean isWon() {
        Tuile heliport = grille.getTuilesMap().get(Nom.Heliport);
        if (aventuriers.size() == heliport.getAventuriers().size() && tresorsDispo.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private ArrayList<Tresor> getRecup() {
        ArrayList<Tresor> recup = new ArrayList<>();
        Collections.addAll(recup, Tresor.values());
        recup.removeAll(tresorsDispo);
        return recup;
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

        System.out.print("\n\nInformations pour la partie : \n\n\t"
                + "Le niveau d'eau est : " + niveauEau
                + "\n\tLe nombre de carte à piocher est : " + cartesAPiocher);

        System.out.print("\n\tTrésors dispo : ");
        for (Tresor tresor : tresorsDispo) {
            System.out.print(tresor.name() + " | ");
        }

        System.out.print("\n\tTrésors récupérés : ");
        for (Tresor tresorsRecupere : getRecup()) {
            System.out.print(tresorsRecupere.name() + " | ");
        }

        System.out.print("\n\n\tLes cartes de la pile innondation : ");
        for (CarteInondation carteInondation : pileInond) {
            System.out.print(carteInondation.getName() + " | ");
        }

        System.out.print("\n\tLes cartes de la defausse innondation : ");
        for (CarteInondation carteInondation : defausseInond) {
            System.out.print(carteInondation.getName() + " | ");
        }

        System.out.print("\n\n\tLes cartes de la pile trésor : ");
        for (CarteTresor carteTresor : pileTresor) {
            System.out.print(carteTresor.getName() + " | ");
        }

        System.out.print("\n\tLes cartes de la defausse trésor : ");
        for (CarteTresor carteTresor : defausseTresor) {
            System.out.print(carteTresor.getName() + " | ");
        }

        System.out.print("\n\nInformations sur les autres joueurs : \n\n");
        for (Aventurier aventurier : aventuriers) {
            if (aventurier != getJoueur()) {
                System.out.print("\tNom du joueur : " + aventurier.getNomJoueur() +
                        "\n\tRole du joueur : " + aventurier.getRole() +
                        "\n\tTuile où se trouve le joueur : " + aventurier.getTuile().getNom() +
                        "\n\tActions; " + getJoueur().getNbActions() +
                        "\n\tLes cartes du joueurs : ");
                for (int j = 0; j < aventurier.getInventaire().size(); j++) {
                    System.out.print(aventurier.getInventaire().get(j).getName() + ", ");
                }
                System.out.println("\n");
            }
        }
    }
}
