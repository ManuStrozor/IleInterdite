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
    private int niveauEau = 0, nbJoueurs = 0, currentAventurier = 0, cartesAPiocher = 2;
    private ArrayList<CarteTresor> pileCartesTresor;
    private ArrayList<CarteTresor> defausseCartesTresor;
    private ArrayList<CarteInondation> pileCartesInondation;
    private ArrayList<CarteInondation> defausseCartesInondation;
    private ArrayList<Aventurier> aventuriers;
    private List<Roles> lesRoles;
    private ArrayList<Tuile> tuilesTresor;

    Random random = new Random();

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
        notifierObservateur(m);

        initiateInondation();
        //tirerCartesIondation(6);

        m = new Message(TypeMessage.UPDATE_GRILLE);
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
        for (Tuile t : grille.getTuiles()) {
            pileCartesInondation.add(new CarteInondation(t.getNom()));
        }
        Collections.shuffle(pileCartesInondation);
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
           //piocherCarteTresor();
        }
    }

    private void distribuerCarteTresor(Aventurier aventurier) {
        for (int k = 1; k <= 2; k++) {
            int i = pileCartesTresor.size() - 1;
            CarteTresor carte = pileCartesTresor.get(i);

            while (carte.getNom().equals("Montée des eaux")) {
                i--;
                carte = pileCartesTresor.get(i);
            }
            aventurier.ajouterCarte(carte) ;
            pileCartesTresor.remove(carte);
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
        aventurier.consommerAction((aventurier.getRole() == Roles.ingenieur) ? 0.5 : 1);
    }

    public void donnerCarte(Aventurier donneur, Aventurier receveur, CarteTresor carte) {
        if (donneur.aventuriersAccessible(aventuriers).contains(receveur)) {
            donneur.defausseCarte();
            receveur.ajouterCarte(carte);
        }
        donneur.consommerAction(1);
        // il faudra completer la methode carte pour faire marcher les méthodes ajouterCarte et defausseCarte
    }

    public void recupererTresor(Tresor tresor) {
        tresorsDispo.remove(tresor);
        tresorsRecuperes.add(tresor);
        //aventurier.moinsUneAction(aventurier); erreur
    }

    public void passerTour(){ //passe le tour du joueur et fais piocher les cartes
            piocherCarteTresor();
            piocherCarteInnondation();
            if(currentAventurier==aventuriers.size()-1){
                currentAventurier=0;
            }
            else{
                currentAventurier+=1;
            }
    }

   public void piocherCarteTresor(){ //Fais piocher 2 carte trésor si carte = montée des eaux lance la méthode usecartemontteeau
        CarteTresor c;
        for(int i=0; i<2; i++){
            c = pileCartesTresor.get(random.nextInt(pileCartesTresor.size()-1));
            if(c.getNom() == "Montée des eaux" ){
                useCarteMonteeDesEaux();
            }
            else {
                getJoueur().ajouterCarte(c);
                pileCartesTresor.remove(c);
                defausseCartesTresor.add(c);
            }
        }

    }

    public void piocherCarteInnondation(){ // fais piocher le nombre de carte innondation en fonction du niveau eau
        CarteInondation c;
        for(int i=0; i<=cartesAPiocher; i++){
            c = pileCartesInondation.get(random.nextInt(pileCartesInondation.size()-1));
            pileCartesInondation.add(c);
            defausseCartesInondation.add(c);
            grille.getTuilesMap().get(c.getNom()).innonder();
        }
    }

    public void defausseCarteInnondation(CarteInondation c){
        defausseCartesInondation.add(c);
        if(defausseCartesInondation.size()==0){
            for(){
                //dezdezdzedzedzedze
            }
        };
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
        return aventuriers.get(currentAventurier);
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void useCarteMonteeDesEaux() { // Déclenché automatiquement...ne pas oublier de defausser !
        niveauEau = niveauEau + 1;

        if (niveauEau == 3 || niveauEau == 6 || niveauEau == 8) {
                cartesAPiocher+=1;
        }

        /*if (niveauEau = 10){
            perdrePartie();
        }*/
    }

    public void useCarteSacDeSable(Tuile tuile){ // Montrer les tuiles inondées AVANT quand carte cliquée !
        tuile.assecher();
        defausseCartesTresor.add(getJoueur().getCarteSacDeSable());
        getJoueur().getInventaire().remove(getJoueur().getCarteSacDeSable());
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

    public void gagnerPartie(){
        //mettre un truc qui fera en sorte que cette méthode se déclenche dès que quelqu'un utilise l'hélico'
        if (aventuriers.size()==grille.getTuilesMap().get("Heliport").getAventuriers().size() && tresorsRecuperes.size()==4){
            System.out.println("Vous avez gagné !");
        }
    }

    public void setTuilesTresor(){
        tuilesTresor.add( new Tuile("La caverne des ombres", Tresor.cristalArdent));
        tuilesTresor.add( new Tuile("La caverne du brasier", Tresor.cristalArdent));
        tuilesTresor.add( new Tuile("Le palais de corail", Tresor.caliceDelombre));
        tuilesTresor.add( new Tuile("Le palais des marees", Tresor.caliceDelombre));
        tuilesTresor.add( new Tuile("Le temple de la lune", Tresor.pierreSacree));
        tuilesTresor.add( new Tuile("Le temple du soleil", Tresor.pierreSacree));
        tuilesTresor.add( new Tuile("Le jardin des hurlements",Tresor.statueDeZephir));
        tuilesTresor.add( new Tuile("Le jardin de murmures", Tresor.statueDeZephir));
    }

}
