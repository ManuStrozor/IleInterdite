/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import aventuriers.Aventurier;
import enumerations.Etat;
import enumerations.Tresor;
import enumerations.TypeMessage;
import game.CarteTresor;
import game.Grille;
import game.Tuile;
import mvc.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {

    private TilePanel[] roles = new TilePanel[4];
    private JPanel[] dashs = new JPanel[4];
    private JPanel grille;
    private JLabel joueur,niveaueau;
    private JButton deplacer, assecher, donnerCarteTresor, recupererTresor, passertour;

    public VueJeu(String name, IHM ihm, int width, int height) {
        super(name, ihm, width, height);
        this.initComponents();

        /*
        * LES 4 BOUTONS DU MENU
        */

        deplacer.addActionListener(e -> {
            Message m = new Message(TypeMessage.DEPLACEMENT);
            ihm.notifierObservateur(m);
        });

        assecher.addActionListener(e -> {
            Message m = new Message(TypeMessage.ASSECHER_TUILE);
            ihm.notifierObservateur(m);
        });

        donnerCarteTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.DONNER_CARTE);
            ihm.notifierObservateur(m);

        });

        recupererTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.RECUPERER_TRESOR);
            ihm.notifierObservateur(m);
        });

        passertour.addActionListener(e -> {
            Message m = new Message(TypeMessage.PASSER_TOUR);
            ihm.notifierObservateur(m);
        });

        /*
         * LES BOUTONS DE LA GRILLE
         */

        int pos = 0;
        for (int i = 0; i < this.grille.getComponentCount(); i++) {
            TilePanel t = (TilePanel)this.grille.getComponent(i);
            JButton btn = (JButton)t.getComponent(4);
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                int index = pos;
                btn.addActionListener(e -> {
                    Message m = new Message(TypeMessage.CLIK_TUILE);
                    m.index = index;
                    ihm.notifierObservateur(m);
                });
                pos++;
            }
        }
    }

    public void updateGrille(Grille grille) {
        int i = 0, pos = 0;
        for (Component tile : this.grille.getComponents()) {
            TilePanel t = (TilePanel)tile;

            for(int a = 0; a < 4; a++) {
                JLabel j = (JLabel)t.getComponent(a);
                j.setText("");
            }

            JButton btn = (JButton)t.getComponent(4);
            btn.setEnabled(false);
            btn.setOpaque(false);

            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {

                ArrayList<Aventurier> joueurs = grille.getTuile(pos).getAventuriers();
                for(int a = 0; a < joueurs.size(); a++) {
                    JLabel j = (JLabel)t.getComponent(a);
                    j.setText(joueurs.get(a).getRole().name());
                }

                t.setBackground(grille.getTuile(pos).getImage());
                pos++;
            }
            i++;
        }
        this.updateUI();
    }

    public void rendreBoutonsClicables(boolean bool) {
        recupererTresor.setEnabled(bool);
    }

    @Override
    public void afficherTuilesAccessibles(Grille grille, ArrayList<Tuile> tuiles) {
        int i = 0, pos = 0;
        for (Component tile : this.grille.getComponents()) {
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                TilePanel t = (TilePanel)tile;
                JButton btn = (JButton)t.getComponent(4);

                if(!tuiles.contains(grille.getTuile(pos))) {
                    grille.getTuile(pos).setImage(Etat.cachee);
                } else {
                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);
                }

                t.setBackground(grille.getTuile(pos).getImage());
                grille.getTuile(pos).setImage(grille.getTuile(pos).getEtat());
                pos++;
            }
            i++;
        }
        this.updateUI();
    }

    @Override
    public void afficherCartesAccessibles(ArrayList<Aventurier> aventuriers, Aventurier joueur) {
        int i = 0;
        for(Aventurier a : aventuriers) {

            JPanel cartes = (JPanel)dashs[i].getComponent(1);

            for (int j = 0; j < a.getInventaire().size(); j++) {

                TilePanel carte = (TilePanel)cartes.getComponent(j);
                JButton btn = (JButton)carte.getComponent(0);

                CarteTresor c = a.getInventaire().get(j);

                if(a == joueur && c.getTresor() != Tresor.Helicoptere && c.getTresor() != Tresor.Sac_De_Sable) {

                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);

                } else if(a != joueur && (c.getTresor() == Tresor.Helicoptere || c.getTresor() == Tresor.Sac_De_Sable)) {

                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);

                }

                carte.setBackground(c.getTresor().getImage());
            }
            i++;
        }
        this.updateUI();
    }

    @Override
    public void afficherCartes(int indexJoueur){

        System.out.println("afficherCartes");

        JPanel lesCartes = (JPanel)dashs[indexJoueur].getComponent(2);
        for (Component carte : lesCartes.getComponents()){
            TilePanel uneCarte = (TilePanel) (carte);
            JButton btn = (JButton) uneCarte.getComponent(0);
            btn.setEnabled(true);
            btn.setContentAreaFilled(true);
            Message msg = new Message(TypeMessage.UPDATE_DASHBOARD);
            ihm.notifierObservateur(msg);
            System.out.println("for");
            this.updateUI();
        }


//        for (Component dash : this.dashBoard.getComponents()) {
//            dash.setEnabled(false);
//            System.out.println("test");
//        }

//            dashBoard.getComponent(0);
//        System.out.println("test" + dashBoard.getComponent(0).getName());
//
//        for(Component comp : dashBoard.getComponents()) {
//            for ( )
//        }
    }

    public void updateDashboard(ArrayList<Aventurier> aventuriers) {
        int i = 0;
        JButton btn;
        for(Aventurier a : aventuriers) {

            JPanel descPanel = (JPanel)dashs[i].getComponent(0);
            JPanel cartesPanel = (JPanel)dashs[i].getComponent(1);

            roles[i].setBackground(a.getRole().getImage());
            btn = (JButton)roles[i].getComponent(0);
            btn.setEnabled(false);
            btn.setOpaque(false);

            JLabel desc = (JLabel)descPanel.getComponent(0);
            desc.setText("Joueur: "+a.getNomJoueur() + " NbActions: " + a.getNbActions());

            int j = 0;
            for(Component c : cartesPanel.getComponents()) {
                TilePanel carte = (TilePanel)c;
                btn = (JButton)carte.getComponent(0);
                btn.setEnabled(false);
                btn.setOpaque(false);
                if(j < a.getInventaire().size()) {
                    carte.setBackground(a.getInventaire().get(j).getTresor().getImage());
                } else {
                    carte.delBackground();
                }
                j++;
            }
            i++;
        }
        this.updateUI();
    }

    @Override
    public void afficherAventurierAccessibles(ArrayList<Aventurier> aventuriers, ArrayList<Aventurier> aventuriersOK) {
        int i = 0;
        for(Aventurier a : aventuriers) {
            JButton btn = (JButton)roles[i].getComponent(0);

            if(aventuriersOK.contains(a)) {
                btn.setEnabled(true);
                btn.setContentAreaFilled(false);
            } else {
                // cacher pour update le background
            }

            roles[i].setBackground(a.getRole().getImage());

            i++;
        }
        this.updateUI();
    }

    @Override
    public void updateJoueur(String nom) {
        joueur.setText(nom);
        this.updateUI();
    }

    @Override
    public void updateNiveauEau(int niveau){
        niveaueau.setText(Integer.toString(niveau));
        this.updateUI();
    }

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundJeu.jpg"))).getImage());

        /////////// ROLES + DASHS ///////////

        for(int i = 0; i < 4; i++) {
            roles[i] = new TilePanel(new BorderLayout()); roles[i].setOpaque(false);
            roles[i].setPreferredSize(new Dimension(0, 200));

            dashs[i] = new JPanel(new BorderLayout()); dashs[i].setOpaque(false);
            dashs[i].setPreferredSize(new Dimension(500, 0));
        }

        /////////// ROLES + DASHS ///////////

        JPanel TOP = new JPanel(new BorderLayout()); TOP.setOpaque(false);
        TOP.setPreferredSize(new Dimension(0, 200));
        JPanel LEFT = new JPanel(new BorderLayout()); LEFT.setOpaque(false);
        JPanel RIGHT = new JPanel(new BorderLayout()); RIGHT.setOpaque(false);
        JPanel BOT = new JPanel(new BorderLayout()); BOT.setOpaque(false);
        BOT.setPreferredSize(new Dimension(0, 200));

        TOP.add(dashs[0], BorderLayout.WEST);
        TOP.add(dashs[1], BorderLayout.EAST);

        BOT.add(dashs[2], BorderLayout.WEST);
        BOT.add(dashs[3], BorderLayout.EAST);

        /////////////////////////// MENU_LEFT ///////////////////////////

        JPanel menu_left = new JPanel(new BorderLayout()); menu_left.setBackground(Color.lightGray);
        menu_left.setPreferredSize(new Dimension(200,0));

        ////// TITLE

        JPanel title = new JPanel(); title.setOpaque(false);

        joueur = new JLabel();

        title.add(new JLabel("C'est au tour de "));
        title.add(joueur);

        ////// TITLE

        //////////// MENU

        JPanel menu = new JPanel(); menu.setOpaque(false); menu.setOpaque(false);

        deplacer = new JButton("Se déplacer");
        assecher = new JButton("Assécher la tuile");
        donnerCarteTresor = new JButton("Donner une carte");
        recupererTresor = new JButton("Récupérer le Trésor");

        menu.add(deplacer);
        menu.add(assecher);
        menu.add(donnerCarteTresor);
        menu.add(recupererTresor);

        //////////// MENU

        menu_left.add(title, BorderLayout.NORTH);
        menu_left.add(menu, BorderLayout.CENTER);

        LEFT.add(roles[0], BorderLayout.NORTH);
        LEFT.add(menu_left, BorderLayout.CENTER);
        LEFT.add(roles[2], BorderLayout.SOUTH);

        /////////////////////////// MENU_LEFT ///////////////////////////

        /////////////////// GRILLE /////////////////

        grille = new JPanel(new GridLayout(6, 6, 5, 5)); grille.setOpaque(false);

        for (int i = 0; i < 36; i++) {
            TilePanel tile = new TilePanel(new BorderLayout()); tile.setOpaque(false);

            JButton btn = new JButton(); btn.setOpaque(false); btn.setEnabled(false);

            tile.add(new JLabel(""), BorderLayout.NORTH);
            tile.add(new JLabel(""), BorderLayout.WEST);
            tile.add(new JLabel(""), BorderLayout.EAST);
            tile.add(new JLabel(""), BorderLayout.SOUTH);

            tile.add(btn);

            grille.add(tile);
        }

        /////////////////// GRILLE /////////////////

        /////////////////////////// MENU_RIGHT ///////////////////////////

        JPanel menu_right = new JPanel(new BorderLayout()); menu_right.setOpaque(false);
        menu_right.setPreferredSize(new Dimension(200,0));

        //////////// MENU

        JPanel menu2 = new JPanel(); menu2.setOpaque(false);

        passertour = new JButton("passerTour");

        menu2.add(passertour);

        //////////// MENU

        ////// NIVEAU

        JPanel niveaudeau = new JPanel(); niveaudeau.setOpaque(false);

        niveaueau = new JLabel();

        niveaudeau.add(new JLabel("Niveau d'eau : "));
        niveaudeau.add(niveaueau);

        ////// NIVEAU

        menu_right.add(menu2, BorderLayout.CENTER);
        menu_right.add(niveaudeau, BorderLayout.SOUTH);

        RIGHT.add(roles[1], BorderLayout.NORTH);
        RIGHT.add(menu_right, BorderLayout.CENTER);
        RIGHT.add(roles[3], BorderLayout.SOUTH);

        /////////////////////////// MENU_RIGHT ///////////////////////////

        this.add(TOP, BorderLayout.NORTH);
        this.add(LEFT, BorderLayout.WEST);
        this.add(grille, BorderLayout.CENTER);
        this.add(RIGHT, BorderLayout.EAST);
        this.add(BOT, BorderLayout.SOUTH);
    }

    public void initBoards(int nb) {
        for (int i = 0; i < nb; i++) {
            int indexAventurier = i;

            JButton btnRole = new JButton(); btnRole.setOpaque(false); btnRole.setEnabled(false);

            roles[i].add(btnRole);

            btnRole.addActionListener(e -> {
                Message m = new Message(TypeMessage.CLIK_JOUEUR);
                m.indexAventurier = indexAventurier;
                ihm.notifierObservateur(m);
            });


            JPanel descPanel = new JPanel(new BorderLayout()); descPanel.setOpaque(false);
            descPanel.add(new JLabel());

            JPanel cartes = new JPanel(); cartes.setOpaque(false);

            for(int j = 0; j < 8; j++) {
                TilePanel carte = new TilePanel(new BorderLayout()); carte.setOpaque(false);
                carte.setPreferredSize(new Dimension(80, 80));

                JButton btnCard = new JButton(); btnCard.setOpaque(false); btnCard.setEnabled(false);

                carte.add(btnCard);

                int index = j;
                btnCard.addActionListener(e -> {
                    Message m = new Message(TypeMessage.CLIK_CARTE);
                    m.indexAventurier = indexAventurier;
                    m.index = index;
                    ihm.notifierObservateur(m);
                });

                cartes.add(carte);
            }

            dashs[i].add(descPanel, BorderLayout.NORTH);
            dashs[i].add(cartes, BorderLayout.CENTER);
            dashs[i].setOpaque(true); dashs[i].setBackground(Color.darkGray);
        }
    }
}
