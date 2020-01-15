/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import aventuriers.Aventurier;
import enumerations.EtatTuile;
import enumerations.Role;
import enumerations.TypeMessage;
import game.Carte;
import game.CarteTresor;
import game.Grille;
import game.Tuile;
import mvc.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {

    private JPanel grille, dashBoard;
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
            //m.currentplayer = currentplayer();
            ihm.notifierObservateur(m);
        });

        donnerCarteTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.ECHANGE_CARTE);
            ihm.notifierObservateur(m);

        });

        recupererTresor.addActionListener(e -> {
            Message m = new Message(TypeMessage.RECUPERER_TRESOR);
            ihm.notifierObservateur(m);
        });

        passertour.addActionListener(e -> {
            Message m = new Message(TypeMessage.PASSERTOUR);
            ihm.notifierObservateur(m);
        });

        /*
         * LES BOUTONS DE LA GRILLE
         */

        int pos = 0;
        for (int i = 0; i < this.grille.getComponentCount(); i++) {
            TilePanel t = (TilePanel)this.grille.getComponent(i);
            JButton btn = (JButton)t.getComponent(0);
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                int finalPos = pos;
                btn.addActionListener(e -> {
                    Message m = new Message(TypeMessage.CLIK_TUILE);
                    m.tuileIndex = finalPos;
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

            JButton btn = (JButton)t.getComponent(0);
            btn.setEnabled(false);
            btn.setOpaque(false);

            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                t.setBackground(grille.getTuile(pos).getImage());

                pos++;
            }
            i++;
        }
        this.updateUI();
    }
    public void rendreBoutonsClicables(boolean bool){
        recupererTresor.setEnabled(bool);
    }
    @Override
    public void afficherTuilesAccessibles(Grille grille, ArrayList<Tuile> tuiles) {
        int i = 0, pos = 0;
        for (Component tile : this.grille.getComponents()) {
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                TilePanel t = (TilePanel)tile;
                JButton btn = (JButton)t.getComponent(0);
                if(!tuiles.contains(grille.getTuile(pos))) {
                    grille.getTuile(pos).setImage(EtatTuile.cachee);
                } else {
                    btn.setEnabled(true);
                    btn.setContentAreaFilled(false);
                }

                t.setBackground(grille.getTuile(pos).getImage());
                grille.getTuile(pos).setImage(grille.getTuile(pos).getEtatTuile());
                pos++;
            }
            i++;
        }
        this.updateUI();
    }

    public void initBoards(int nb) {
        for (int i = 0; i < nb; i++) {
            JPanel dashPanel = new JPanel(new BorderLayout());
            dashPanel.setOpaque(false);

            JPanel rolePanel = new JPanel(new BorderLayout());
            rolePanel.setPreferredSize(new Dimension(100, 0));
            rolePanel.add(new JLabel());

            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.add(new JLabel());

            JPanel cartesPanel = new JPanel(new GridLayout(1, 8)); // 8 = Un joueur n'aura jamais plus de 8 cartes en main
            cartesPanel.setPreferredSize(new Dimension(0, 100));
            for(int c = 0; c < 8; c++) {
                cartesPanel.add(new JLabel());
            }

            /*cartesPanel.addActionListener(e -> {
                Message m = new Message(TypeMessage.CLIK_CARTE);
                ihm.notifierObservateur(m);
            });*/

            dashPanel.add(rolePanel, BorderLayout.WEST);
            dashPanel.add(descPanel, BorderLayout.CENTER);
            dashPanel.add(cartesPanel, BorderLayout.SOUTH);
            dashBoard.add(dashPanel);
        }
    }

    public void updateDashboard(ArrayList<Aventurier> aventuriers) {
        int i = 0;
        for(Component comp : dashBoard.getComponents()) {
            JPanel dashPanel = (JPanel)comp;

            JPanel rolePanel = (JPanel)dashPanel.getComponent(0);
            JPanel descPanel = (JPanel)dashPanel.getComponent(1);
            JPanel cartesPanel = (JPanel)dashPanel.getComponent(2);

            rolePanel.setBackground(aventuriers.get(i).getCouleurPion());
            JLabel role = (JLabel)rolePanel.getComponent(0);
            role.setText(String.valueOf(aventuriers.get(i).getRole()));
            role.setForeground((aventuriers.get(i).getCouleurPion() == Color.YELLOW) ? Color.BLACK : Color.WHITE);

            JLabel desc = (JLabel)descPanel.getComponent(0);
            desc.setText("Joueur: ["+aventuriers.get(i).getNomJoueur() + "]  Actions: " + aventuriers.get(i).getNbActions());

            int j = 0;
            for(Carte c : aventuriers.get(i).getInventaire()){
                if(c != null) {
                    JLabel carte = (JLabel)cartesPanel.getComponent(j);
                    carte.setText(c.getName());
                }
                j++;
            }

            i++;
        }
        this.updateUI();
    }

    @Override
    public void afficherAventurierAccessibles(ArrayList<Aventurier> aventurierAccessibles) {
        System.out.println("Aventuriers qui peuvent recevoir :");
        for(Aventurier a : aventurierAccessibles) {
            System.out.println("\t" + a.getNomJoueur());
        }
    }

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundJeu.jpg"))).getImage());

        JPanel menu = new JPanel( new GridLayout(4,1, 5,10));
        menu.setOpaque(false);

        ////// MENU //////
        deplacer = new JButton("Se déplacer");
        assecher = new JButton("Assécher la tuile");
        donnerCarteTresor = new JButton("Donner une carte");
        recupererTresor = new JButton("Récupérer le Trésor");
        passertour = new JButton("passerTouur");

        menu.add(deplacer);
        menu.add(assecher);
        menu.add(donnerCarteTresor);
        menu.add(recupererTresor);
        menu.add(passertour);
        ////// MENU //////

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);
        JPanel marginLeft = new JPanel();
        marginLeft.setPreferredSize(new Dimension(100, 0)); marginLeft.setOpaque(false);
        JPanel marginTop = new JPanel();
        marginTop.setPreferredSize(new Dimension(0, 50)); marginTop.setOpaque(false);
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

        /////// GRILLE ///////
        for (int i = 0; i < 36; i++) {
            TilePanel tile = new TilePanel(new BorderLayout());
            JButton btn = new JButton();
            btn.setEnabled(false);
            btn.setOpaque(false);
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
