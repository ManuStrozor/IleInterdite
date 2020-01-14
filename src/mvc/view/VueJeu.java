/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import aventuriers.Aventurier;
import enumerations.TypeMessage;
import game.Grille;
import game.Tuile;
import mvc.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {

    private JPanel grille, dashBoard;
    private JButton deplacer, assecher, donnerCarteTresor, recupererTresor;

    public VueJeu(String name, IHM ihm, int width, int height) {
        super(name, ihm, width, height);
        this.initComponents();

        deplacer.addActionListener(e -> {
            Message m = new Message(TypeMessage.DEPLACEMENT);
            ihm.notifierObservateur(m);
        });

        assecher.addActionListener(e -> {
            Message m = new Message(TypeMessage.ASSECHER_TUILE);
            //m.currentplayer = currentplayer();
            ihm.notifierObservateur(m);
            for (int i = 0; i < 5 ; i++){
                grille.add(new JButton("test"));
            }

        });

        donnerCarteTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.ECHANGE_CARTE);
            ihm.notifierObservateur(m);

        });

        recupererTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.RECUPERER_TRESOR);
            ihm.notifierObservateur(m);
        });
    }

    public void updateGrille(Grille grille) {
        int pos = 0;
        for (int i = 0; i < 36; i++) {
            TilePanel tile = new TilePanel(null);
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                tile.setBackground(grille.getTuile(pos).getImage());
                pos++;
            }
            tile.setOpaque(false);
            this.grille.add(tile);
        }
    }

    public void updateDashboard(ArrayList<Aventurier> aventuriers) {
        for (Aventurier aventurier : aventuriers) {
            JPanel playerPanel = new JPanel(new BorderLayout());
            playerPanel.setOpaque(false);

            JPanel rolePlayer = new JPanel(new BorderLayout());

            ////// ZONE DE ROLE DU JOUEUR //////
            JLabel role = new JLabel(String.valueOf(aventurier.getRole()));
            rolePlayer.add(role);
            ////// ZONE DE ROLE DU JOUEUR //////

            rolePlayer.setPreferredSize(new Dimension(100, 0));
            rolePlayer.setBackground(aventurier.getCouleurPion());
            if(aventurier.getCouleurPion() == Color.YELLOW) {
                role.setForeground(Color.BLACK);
            } else {
                role.setForeground(Color.WHITE);
            }

            JPanel description = new JPanel(new BorderLayout());

            ////// ZONE DE DESCRIPTION DU JOUEUR //////
            description.add(new JLabel("Joueur: ["+aventurier.getNomJoueur() + "]  Actions: " + aventurier.getNbActions()));
            ////// ZONE DE DESCRIPTION DU JOUEUR //////

            JPanel cartes = new JPanel(new GridLayout(1, 5));

            ////// ZONE DES CARTES DU JOUEUR //////

            ////// ZONE DES CARTES DU JOUEUR //////

            cartes.setPreferredSize(new Dimension(0, 100));

            playerPanel.add(rolePlayer, BorderLayout.WEST);
            playerPanel.add(description, BorderLayout.CENTER);
            playerPanel.add(cartes, BorderLayout.SOUTH);

            dashBoard.add(playerPanel);
        }
    }

    @Override
    public void afficherTuilesAccessibles(ArrayList<Tuile> tuiles) {
        System.out.println("Tuiles accessibles :");
        for(Tuile t : tuiles) {
            System.out.println("\t" + t.getNom());
        }
    }

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundJeu.jpg"))).getImage());

        JPanel menu = new JPanel( new GridLayout(4,1, 0 ,10));
        menu.setOpaque(false);

        ////// ZONE DE MENU //////
        deplacer = new JButton("Se déplacer");
        assecher = new JButton("Dessécher");
        donnerCarteTresor = new JButton("Donner une carte Trésor");
        recupererTresor = new JButton("Recuperer le Tresor");
        menu.add(deplacer);
        menu.add(assecher);
        menu.add(donnerCarteTresor);
        menu.add(recupererTresor);
        ////// ZONE DE MENU //////

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);

        JPanel marginLeft = new JPanel();
        marginLeft.setPreferredSize(new Dimension(100, 0)); marginLeft.setOpaque(false);
        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(0, 100)); marginTop.setOpaque(false);
        JPanel marginBot = new JPanel();
        marginBot.setPreferredSize(new Dimension(0, 100)); marginBot.setOpaque(false);

        menuPanel.add(marginTop, BorderLayout.NORTH);
        menuPanel.add(marginLeft, BorderLayout.WEST);
        menuPanel.add(marginBot, BorderLayout.SOUTH);
        menuPanel.add(menu, BorderLayout.CENTER);
        menuPanel.setPreferredSize(new Dimension(450,0));

        JPanel board = new JPanel(new BorderLayout());
        board.setOpaque(false);
        grille = new JPanel(new GridLayout(6, 6, 5, 5));
        grille.setOpaque(false);

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
