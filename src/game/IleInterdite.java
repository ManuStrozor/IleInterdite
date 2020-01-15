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
    private List<Tresor> tresorsDispo;
    private List<Tresor> tresorsRecuperes;
    private int niveauEau = 0, nbJoueurs = 0, joueur = 0, cartesAPiocher = 2;
    private ArrayList<CarteTresor> pileCartesTresor, defausseCartesTresor;
    private ArrayList<CarteInondation> pileCartesInondation, defausseCartesInondation;

    private ArrayList<Aventurier> aventuriers;
    private List<Role> lesRoles;
    private ArrayList<Tuile> tuilesTresor;
    private Aventurier joueurASauver;

    Random random = new Random();

    public IleInterdite() {
        grille = new Grille(); //initialisation grille
        aventuriers = new ArrayList<>(); //initialisation aventuriers
        lesRoles = Arrays.asList(Role.values());
        Collections.shuffle(lesRoles);
        tresorsDispo=Arrays.asList(Tresor.values()); //initialisation tresors
        tuilesTresor = new ArrayList<>();
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
        initiateInondation();
        tirerCartesIondation();
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
        //Collections.shuffle(pileCartesTresor);
    }

    private void initiateInondation() {
        pileCartesInondation = new ArrayList<>();
        defausseCartesInondation = new ArrayList<>();
        for (Tuile t : grille.getTuiles()) {
            pileCartesInondation.add(new CarteInondation(t.getNom()));
        }
        //Collections.shuffle(pileCartesInondation);
    }

    private void initiateAventuriers(String[] nomJoueurs) throws IllegalStateException {
        for (int i = 0; i < nbJoueurs; i++) {
            Aventurier newAventurier;
            switch (lesRoles.get(i)) {
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

    private void distribuerCarteTresor(Aventurier aventurier) { // Distribue 2 carte trésor au début sauf montée eaux
        CarteTresor c;
        for(int i=0; i<2; i++){
            c = pileCartesTresor.get(random.nextInt(pileCartesTresor.size()-1));
            if(c.getTresor() == Tresor.Montee_Des_Eaux) {
                DEFAUSSECARTETRESOR(c);
                i--;
            } else {
                aventurier.ajouterCarte(c);
                DEFAUSSECARTETRESOR(c);
            }
        }
    }

    private void tirerCartesIondation(){ // Distribue 6 carte inondation au début du jeu
        for(int i=0; i<6; i++) {
            CarteInondation c;
            c = pileCartesInondation.get(random.nextInt(pileCartesInondation.size()));
            grille.getTuilesMap().get(c.getNom()).innonder();
            DEFAUSSECARTEINONDATION(c);
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
        System.out.println(getJoueur().getInventaire() + " " + receveur.getInventaire());
        if (getJoueur().aventuriersAccessible(aventuriers).contains(receveur)) {
            getJoueur().defausseCarte();
            receveur.ajouterCarte(carte);
        }
        System.out.println(getJoueur().getInventaire() + " " + receveur.getInventaire());
        getJoueur().consommerAction(1);
        // il faudra completer la methode carte pour faire marcher les méthodes ajouterCarte et defausseCarte
    }

    public void recupererTresor(Tresor tresor) {
        tresorsDispo.remove(tresor);
        tresorsRecuperes.add(tresor);
        //aventurier.moinsUneAction(aventurier); erreur
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
                getJoueur().ajouterCarte(c);
                pileCartesTresor.remove(c);
                DEFAUSSECARTETRESOR(c);
            }
        }
    }

    public void piocherCarteInnondation(){ // fais piocher le nombre de carte innondation en fonction du niveau eau
        CarteInondation c;
        for(int i=0; i<cartesAPiocher; i++){
            c = pileCartesInondation.get(random.nextInt(pileCartesInondation.size()-1));
            pileCartesInondation.add(c);
            DEFAUSSECARTEINONDATION(c);
            grille.getTuilesMap().get(c.getNom()).innonder();
        }
    }

    public void DEFAUSSECARTEINONDATION(CarteInondation c){ // defausse la carte et si la pile est vide la re rempli innondation
        defausseCartesInondation.add(c);
        pileCartesInondation.remove(c);
        if(pileCartesInondation.isEmpty()){
            defausseCartesInondation.addAll(pileCartesInondation);
        }
    }

    public void DEFAUSSECARTETRESOR(CarteTresor c){ // defausse la carte et si la pile est vide la re rempli tresor
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

    public void useCarteSacDeSable(Tuile tuile){ // Montrer les tuiles inondées AVANT quand carte cliquée !
        tuile.assecher();
        defausseCartesTresor.add(getJoueur().getCarteSacDeSable());
        getJoueur().getInventaire().remove(getJoueur().getCarteSacDeSable());
    }

    public void utiliserHelico(Aventurier joueur, Tuile destination){
        if (destination.getEtatTuile()!=EtatTuile.coulee){
            //Ceci est la méthode temporaire, une fois la question de l'IHM réglée on pourra selectionner plusieurs joueurs
            joueur.getTuile().getAventuriers().remove(joueur);
            destination.addAventurier(joueur);
            getJoueur().getInventaire().remove(getJoueur().getCarteHelico());
            gagnerPartie();
        }
    }

    public boolean estRecuperable(Aventurier aventurier) { // Déplacer dans Tuile.java

        boolean conditionOK = false;
        int nbcarte = 0;
        ArrayList<CarteTresor> inventaire = aventurier.getInventaire(); //inventaire de l'aventurier
        Tresor tresorTuile = aventurier.getTuile().getTresor(); //Type de la tuile où est l'aventurier

        //si le tresor n'est pas deja recup
        for (Tresor t : tresorsDispo) {
            if (t == tresorTuile) {
                conditionOK = true;
                break;
            }
        }

        //si l'aventurier a le bon nombre de carte du meme type que la tuile où il se situe
        for (int i = 0; i < inventaire.size(); i++) {
            nbcarte++;
        }

        if (nbcarte >= 4) {
            conditionOK = true;
        } else {
            conditionOK = false;
        }
        return conditionOK;
    }

    public boolean perdrePartie(Grille grille) {

        for (Aventurier a : aventuriers) { //si un aventurier est mort, la partie est perdue
            return a.mort(grille);
        }

        for (Tresor t : tresorsDispo) { // pour chaque tresor que l'on a pas encore recuperé
            // Si les tuiles permettants de recuperer ce tresor sont toutes les deux coulées, on retourne true
            return getTuileTresor(t).get(0).getEtatTuile() == EtatTuile.coulee
                    && getTuileTresor(t).get(1).getEtatTuile() == EtatTuile.coulee;
        }

        return grille.getTuilesMap().get("Heliport").getEtatTuile() == EtatTuile.coulee || getNiveauEau() >= 10;
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
        //mettre un truc qui fera en sorte que cette méthode se déclenche dès que quelqu'un utilise l'hélico'
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
            DEFAUSSECARTETRESOR(c);
        }
    }
}
