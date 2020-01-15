package game;

import java.util.*;
import aventuriers.*;
import enumerations.*;
import mvc.Message;
import mvc.Observe;

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
        grille = new Grille();
        aventuriers = new ArrayList<>();
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
        initiateInondCards();
        initiateAventuriers(names);
        tirerCartesInond(6);
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

    private void initiateInondCards() {
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

    private void distribuerCarteTresor(Aventurier aventurier) {
        for(int i = 0; i < 2; i++){
            CarteTresor c = pileTresor.get(random.nextInt(pileTresor.size()));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                i--;
            } else {
                aventurier.getInventaire().add(c);
                pileTresor.remove(c);
            }
        }
    } // Distribue 2 carte trésor au début sauf montée eaux

    private void tirerCartesInond(int n) {
        for(int i = 0; i < n; i++) {
            CarteInondation c = pileInond.get(random.nextInt(pileInond.size()));
            grille.getTuilesMap().get(c.getNom()).innonder();
            defausserInond(c);
        }
    }

    public void tirerCartesTresor(int n) {
        for(int i = 0; i < n; i++) {
            CarteTresor c = pileTresor.get(random.nextInt(pileTresor.size()));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                useCarteMonteeDesEaux(c);
            } else {
                getJoueur().getInventaire().add(c);
                pileTresor.remove(c);
            }

            if (getJoueur().getInventaire().size() > 5){ // Rajouter dans le message la carte que l'utilisateur veux supprimer

                //appeler une methode qui permettrait à l'utilisateur de cliquer sur la carte à defausser
                // et qui recupere l'index de cette carte dans inventaire
                Message msg = new Message(TypeMessage.INVENTAIRE_PLEIN);
                //msg.index = l'index de la carte qu'on recupere avec la fonction
                msg.nbCarteEnTrop = getJoueur().getInventaire().size()-5;
                notifierObservateur(msg);
            }

        }
    } // Fais piocher 2 carte trésor si carte = montée des eaux lance la méthode usecartemontteeau


    public void seDeplacer(Aventurier aventurier, Tuile tuileDest) {
        aventurier.seDeplacer(tuileDest);
        if(aventurier.getRole()== Role.plongeur && tuileDest.getEtat()== Etat.innondee){
            aventurier.consommerAction(0);
        }
        else{
        aventurier.consommerAction(1);}
    }

    public void assecher(Aventurier aventurier, Tuile tuile) {
        tuile.assecher();
        if (aventurier.getRole() == Role.ingenieur)
            aventurier.consommerAction(0.5);
        else
            aventurier.consommerAction(1);
    }

    public void donnerCarte(CarteTresor carte, Aventurier receveur) {
        getJoueur().getInventaire().remove(carte);
        receveur.getInventaire().add(carte);
        getJoueur().consommerAction(1);

//        if (receveur.getInventaire().size() > 5){ // Rajouter dans le message la carte que l'utilisateur veux supprimer
//            System.out.println("testXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//            Message msg = new Message(TypeMessage.INVENTAIRE_PLEIN);
//            msg.a = receveur;
//            msg.nbCarteEnTrop = receveur.getInventaire().size()-5;
//            notifierObservateur(msg);
//        }
    }

    public void recupererTresor() {
        ArrayList<CarteTresor> cartesADegager = getJoueur().peutRecupererTresor(tresorsDispo);
        if(!cartesADegager.isEmpty()) {
            tresorsDispo.remove(getJoueur().getTuile().getTresor());
            defausseTresor.addAll(cartesADegager);
            getJoueur().getInventaire().removeAll(cartesADegager);
            getJoueur().consommerAction(1);
        }
    } // condition à verifier avant !

    public void passerTour() {
        tirerCartesTresor(2);
        tirerCartesInond(cartesAPiocher);
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

    public void defausserInond(CarteInondation c){
        defausseInond.add(c);
        pileInond.remove(c);
        if(pileInond.isEmpty()) {
            pileInond.addAll(defausseInond);
            defausseInond.clear();
        }
    }

    public void defausserTresor(CarteTresor c){
        defausseTresor.add(c);
        pileTresor.remove(c);
        if(pileTresor.isEmpty()) {
            pileTresor.addAll(defausseTresor);
            defausseTresor.clear();
        }
    }

    public void defausserCartesTresorInventaire(CarteTresor c, Aventurier av){
        System.out.println("on defausse la carte " + c.getName());
        // TEST HAMZA
        defausseTresor.add(c);

        ArrayList<CarteTresor> carteAsupprimer = new ArrayList<>();
        for (CarteTresor carte : av.getInventaire() ) {
            if (carte == c ){
                carteAsupprimer.add(carte);
                break;
            }
        }
        av.getInventaire().removeAll(carteAsupprimer);
        Message m = new Message(TypeMessage.UPDATE_DASHBOARD);
        notifierObservateur(m);
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public void setNiveauEau(String niveauEau) {
        switch (niveauEau) {
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

    public void useCarteMonteeDesEaux(CarteTresor c) { // Déclenché automatiquement...ne pas oublier de defausser !
        niveauEau++;
        if (niveauEau == 3 || niveauEau == 6 || niveauEau == 8) cartesAPiocher++;
        defausserTresor(c);
    }

    public void useCarteSacDeSable(Tuile tuile) { // Montrer les tuiles inondées AVANT quand carte cliquée !
        tuile.assecher();
        defausseTresor.add(getJoueur().getCarte(Tresor.Sac_De_Sable));
        getJoueur().getInventaire().remove(getJoueur().getCarte(Tresor.Sac_De_Sable));
    }

    public void utiliserHelico() {
        if(isWon()) {
            Message msg = new Message(TypeMessage.CHANGER_VUE);
            msg.vue = "gagne";
            this.notifierObservateur(msg);
            System.out.println("Vous avez gagné !!!");
        } else {
            Message m = new Message(TypeMessage.HELICO);
            this.notifierObservateur(m);
        }
    }

    public boolean isGameover() {
        for (Aventurier aventurier : aventuriers) {
            if(aventurier.mort(grille)) {
                return true;
            }
        }
        for (Tresor tresor : tresorsDispo) {
            if(!tresor.isReachable(grille)) {
                return true;
            }
        }
        Tuile heliport = grille.getTuilesMap().get(Nom.Heliport);
        return heliport.getEtat() == Etat.coulee || getNiveauEau() >= 10;
    }

    public boolean isWon() {
        Tuile heliport = grille.getTuilesMap().get(Nom.Heliport);
        return aventuriers.size() == heliport.getAventuriers().size() && tresorsDispo.isEmpty();
    }

    public ArrayList<Tresor> getTresorsDispo() {
        return tresorsDispo;
    }

    private ArrayList<Tresor> getRecup() {
        ArrayList<Tresor> recup = new ArrayList<>();
        Collections.addAll(recup, Tresor.values());
        recup.remove(Tresor.Montee_Des_Eaux);
        recup.remove(Tresor.Helicoptere);
        recup.remove(Tresor.Sac_De_Sable);
        recup.removeAll(tresorsDispo);
        return recup;
    }

    public Aventurier getJoueurASauver() {
        return joueurASauver;
    }

    public void defaussetoi(ArrayList<CarteTresor> ct) { // en cours de codage
        getJoueur().defaussetoi(ct);
        for(CarteTresor c: ct){
            defausserTresor(c);
        }
    }

    public void DEBUGILEINTERDITE() { //DEBUGGER CONSOLE
        System.out.print("\n-------------------------------------- Tour de jeu numéro " + tourjeu + " : ------------------------------------\n");
        System.out.print("\nInformations joueur en cours : \n\n\t" +
                "Nom joueur : " + getJoueur().getNomJoueur() +
                "\n\tRole : " + getJoueur().getRole().name() +
                "\n\tActions; " + getJoueur().getNbActions() +
                "\n\tTuile où se trouve le joueur : " + getJoueur().getTuile().getName() +
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


        System.out.print("\n\n\tCartes pile inond : ");
        for (CarteInondation carteInondation : pileInond) {
            System.out.print(carteInondation.getName() + " | ");
        }
        System.out.print("\n\tNb " + pileInond.size());

        System.out.print("\n\tCartes defausse inond : ");
        for (CarteInondation carteInondation : defausseInond) {
            System.out.print(carteInondation.getName() + " | ");
        }

        int totalInond=pileInond.size()+defausseInond.size();
        System.out.print("\n\tNb " + defausseInond.size() + "\n\tTotal cartes inond : " + totalInond + "/24");

        System.out.print("\n\n\tCartes pile trésor : ");
        for (CarteTresor carteTresor : pileTresor) {
            System.out.print(carteTresor.getName() + " | ");
        }
        System.out.print("\n\tNb " + pileTresor.size());

        System.out.print("\n\tCartes defausse trésor : ");
        for (CarteTresor carteTresor : defausseTresor) {
            System.out.print(carteTresor.getName() + " | ");
        }

        int somme=0;
        for(Aventurier a: aventuriers){
            somme +=a.getInventaire().size();
        }
        int totalTresor=pileTresor.size()+defausseTresor.size()+somme;
        System.out.print("\n\tNb " + defausseTresor.size() + "\n\tTotal cartes tresor : " + totalTresor+ "/28");

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
