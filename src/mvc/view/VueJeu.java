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

    private JPanel grille, dashBoard;
    private JLabel joueur;
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
        int j = 0;
        for(Component dash : this.dashBoard.getComponents()) {
            JPanel d = (JPanel)dash;
            JPanel cartes = (JPanel)d.getComponent(2);

            Aventurier a = aventuriers.get(j);
            for (int i = 0; i < a.getInventaire().size(); i++) {

                TilePanel carte = (TilePanel)cartes.getComponent(i);
                JButton btn = (JButton)carte.getComponent(0);

                CarteTresor c = a.getInventaire().get(i);

                if(a == joueur && c.getTresor() != Tresor.Helicoptere && c.getTresor() != Tresor.Sac_De_Sable) {

                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);

                } else if(a != joueur && (c.getTresor() == Tresor.Helicoptere || c.getTresor() == Tresor.Sac_De_Sable)) {

                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);

                }

                carte.setBackground(c.getTresor().getImage());
            }
            j++;
        }
        this.updateUI();
    }

    @Override
    public void afficherCartes(int indexJoueur){

        this.dashBoard.getComponent(indexJoueur).getComponentAt(1,0).setEnabled(true);


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

    public void initBoards(int nb) {
        for (int i = 0; i < nb; i++) {
            int indexAventurier = i;
            JPanel dashPanel = new JPanel(new BorderLayout());
            dashPanel.setOpaque(false);

            TilePanel rolePanel = new TilePanel(new BorderLayout());
            JButton bouton = new JButton();
            bouton.setEnabled(false);
            bouton.setOpaque(false);
            rolePanel.add(bouton);

            bouton.addActionListener(e -> {
                Message m = new Message(TypeMessage.CLIK_JOUEUR);
                m.indexAventurier = indexAventurier;
                ihm.notifierObservateur(m);
            });

            rolePanel.setPreferredSize(new Dimension(100, 0));
            rolePanel.setOpaque(false);

            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.add(new JLabel());

            JPanel cartes = new JPanel(new GridLayout(1, 8));
            cartes.setPreferredSize(new Dimension(0, 80));
            cartes.setOpaque(false);

            for(int c = 0; c < 8; c++) {
                TilePanel carte = new TilePanel(new BorderLayout());
                JButton btn = new JButton();
                btn.setEnabled(false);
                btn.setOpaque(false);
                carte.add(btn);
                int index = c;
                btn.addActionListener(e -> {
                    Message m = new Message(TypeMessage.CLIK_CARTE);
                    m.indexAventurier = indexAventurier;
                    m.index = index;
                    ihm.notifierObservateur(m);
                });
                carte.setOpaque(false);
                cartes.add(carte);
            }

            dashPanel.add(rolePanel, BorderLayout.WEST);
            dashPanel.add(descPanel, BorderLayout.CENTER);
            dashPanel.add(cartes, BorderLayout.SOUTH);
            dashBoard.add(dashPanel);
        }
    }

    public void updateDashboard(ArrayList<Aventurier> aventuriers) {
        int i = 0;
        JButton btn;
        for(Component comp : dashBoard.getComponents()) {
            JPanel dashPanel = (JPanel)comp;

            TilePanel rolePanel = (TilePanel)dashPanel.getComponent(0);
            JPanel descPanel = (JPanel)dashPanel.getComponent(1);
            JPanel cartesPanel = (JPanel)dashPanel.getComponent(2);

            rolePanel.setBackground(aventuriers.get(i).getRole().getImage());
            btn = (JButton)rolePanel.getComponent(0);
            btn.setEnabled(false);
            btn.setOpaque(false);

            JLabel desc = (JLabel)descPanel.getComponent(0);
            desc.setText("Joueur: ["+aventuriers.get(i).getNomJoueur() + "]  Actions: " + aventuriers.get(i).getNbActions());

            int j = 0;
            for(Component c : cartesPanel.getComponents()) {
                TilePanel carte = (TilePanel)c;
                btn = (JButton)carte.getComponent(0);
                btn.setEnabled(false);
                btn.setOpaque(false);
                if(j < aventuriers.get(i).getInventaire().size()) {
                    carte.setBackground(aventuriers.get(i).getInventaire().get(j).getTresor().getImage());
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
        for (Component dash : this.dashBoard.getComponents()) {
            JPanel d = (JPanel)dash;
            TilePanel r = (TilePanel)d.getComponent(0);
            JButton btn = (JButton)r.getComponent(0);

            Aventurier a = aventuriers.get(i);

            if(aventuriersOK.contains(a)) {
                btn.setEnabled(true);
                btn.setContentAreaFilled(false);
            } else {
                // cacher pour update le background
            }

            r.setBackground(a.getRole().getImage());
            i++;
        }
        this.updateUI();
    }

    @Override
    public void afficherTitreJoueur(String nom) {
        joueur.setText(nom);
        this.updateUI();
    }

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundJeu.jpg"))).getImage());

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);

        JPanel parchemin = new JPanel(new BorderLayout(0, 10));
        parchemin.setOpaque(false);

        JPanel menu = new JPanel(new GridLayout(2,2, 20,20));
        menu.setOpaque(false);

        ////// MENU //////
        JPanel menuBar = new JPanel(new BorderLayout());
        menuBar.setPreferredSize(new Dimension(0, 50)); menuBar.setOpaque(false);

        JPanel title = new JPanel(new BorderLayout()); title.setOpaque(false);
        joueur = new JLabel();
        title.add(new JLabel("C'est au tour de "), BorderLayout.WEST);
        title.add(joueur, BorderLayout.CENTER);
        passertour = new JButton("passerTour");

        menuBar.add(title, BorderLayout.CENTER);
        menuBar.add(passertour, BorderLayout.EAST);

        deplacer = new JButton("Se déplacer");
        assecher = new JButton("Assécher la tuile");
        donnerCarteTresor = new JButton("Donner une carte");
        recupererTresor = new JButton("Récupérer le Trésor");

        menu.add(deplacer);
        menu.add(assecher);
        menu.add(donnerCarteTresor);
        menu.add(recupererTresor);
        ////// MENU //////

        parchemin.add(menuBar, BorderLayout.NORTH);
        parchemin.add(menu, BorderLayout.CENTER);

        JPanel marginLeft = new JPanel();
        marginLeft.setPreferredSize(new Dimension(100, 0)); marginLeft.setOpaque(false);
        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(0, 50)); marginTop.setOpaque(false);
        JPanel marginBot = new JPanel();
        marginBot.setPreferredSize(new Dimension(0, 100)); marginBot.setOpaque(false);

        menuPanel.add(marginTop, BorderLayout.NORTH);
        menuPanel.add(marginLeft, BorderLayout.WEST);
        menuPanel.add(marginBot, BorderLayout.SOUTH);
        menuPanel.add(parchemin, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(425,0));

        JPanel board = new JPanel(new BorderLayout());
        board.setOpaque(false);
        grille = new JPanel(new GridLayout(6, 6, 5, 5));
        grille.setOpaque(false);

        /////// GRILLE ///////
        for (int i = 0; i < 36; i++) {
            TilePanel tile = new TilePanel(new BorderLayout());
            JButton btn = new JButton();
            btn.setEnabled(false);
            btn.setOpaque(false);
            tile.add(new JLabel(""), BorderLayout.NORTH);
            tile.add(new JLabel(""), BorderLayout.WEST);
            tile.add(new JLabel(""), BorderLayout.EAST);
            tile.add(new JLabel(""), BorderLayout.SOUTH);
            tile.add(btn);
            tile.setOpaque(false);
            this.grille.add(tile);
        }
        /////// GRILLE ///////

        JPanel leftSide = new JPanel();
        leftSide.setPreferredSize(new Dimension(100, 0)); leftSide.setOpaque(false);
        JPanel rightSide = new JPanel();
        rightSide.setPreferredSize(new Dimension(100, 0)); rightSide.setOpaque(false);
        board.add(leftSide, BorderLayout.WEST);
        board.add(rightSide, BorderLayout.EAST);
        board.add(grille, BorderLayout.CENTER);

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.setOpaque(false);
        JPanel marginLeft2 = new JPanel();
        marginLeft2.setPreferredSize(new Dimension(10, 0)); marginLeft2.setOpaque(false);
        JPanel marginRight2 = new JPanel();
        marginRight2.setPreferredSize(new Dimension(10, 0)); marginRight2.setOpaque(false);
        JPanel marginTop2 = new JPanel();
        marginTop2.setPreferredSize(new Dimension(0, 10)); marginTop2.setOpaque(false);
        JPanel marginBot2 = new JPanel();
        marginBot2.setPreferredSize(new Dimension(0, 10)); marginBot2.setOpaque(false);

        boardPanel.add(marginTop2, BorderLayout.NORTH);
        boardPanel.add(marginLeft2, BorderLayout.WEST);
        boardPanel.add(marginRight2, BorderLayout.EAST);
        boardPanel.add(marginBot2, BorderLayout.SOUTH);
        boardPanel.add(board, BorderLayout.CENTER);

        dashBoard = new JPanel(new GridLayout(1, 4, 10, 0));
        dashBoard.setPreferredSize(new Dimension(0, 200));
        dashBoard.setOpaque(false);

        this.add(menuPanel, BorderLayout.WEST);
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(dashBoard, BorderLayout.SOUTH);
    }

}
