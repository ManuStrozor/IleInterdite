/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {
    
    public VueJeu(String name, IHM ihm, int width, int height) {
        super(name, ihm, width, height);
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundJeu.jpg"))).getImage());

        JPanel menu = new JPanel(null);
        menu.setOpaque(false);
        ////// ZONE DE MENU //////

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

        JPanel board = new JPanel(new BorderLayout(5,5));
        board.setOpaque(false);
        JPanel tilesZone = new JPanel(new GridLayout(6, 6));
        tilesZone.setOpaque(false);

        ////// ZONE DE JEU //////
        for (int i = 0; i < 36; i++) {
            TilePanel tile = new TilePanel(new BorderLayout());
            if(i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 11 && i != 24 && i != 29 && i != 30 && i != 31 && i != 34 && i != 35) {
                tile.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("emptyTile.png"))).getImage());
            }
            tile.setOpaque(false);
            tilesZone.add(tile);
        }
        ////// ZONE DE JEU //////

        JPanel leftSide = new JPanel();
        leftSide.setPreferredSize(new Dimension(100, 0)); leftSide.setOpaque(false);
        JPanel rightSide = new JPanel();
        rightSide.setPreferredSize(new Dimension(100, 0)); rightSide.setOpaque(false);
        board.add(leftSide, BorderLayout.WEST);
        board.add(rightSide, BorderLayout.EAST);
        board.add(tilesZone, BorderLayout.CENTER);

        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.setOpaque(false);
        JPanel marginLeft2 = new JPanel();
        marginLeft2.setPreferredSize(new Dimension(50, 0)); marginLeft2.setOpaque(false);
        JPanel marginRight2 = new JPanel();
        marginRight2.setPreferredSize(new Dimension(50, 0)); marginRight2.setOpaque(false);
        JPanel marginTop2 = new JPanel();
        marginTop2.setPreferredSize(new Dimension(0, 50)); marginTop2.setOpaque(false);
        JPanel marginBot2 = new JPanel();
        marginBot2.setPreferredSize(new Dimension(0, 50)); marginBot2.setOpaque(false);

        boardPanel.add(marginTop2, BorderLayout.NORTH);
        boardPanel.add(marginLeft2, BorderLayout.WEST);
        boardPanel.add(marginRight2, BorderLayout.EAST);
        boardPanel.add(marginBot2, BorderLayout.SOUTH);
        boardPanel.add(board, BorderLayout.CENTER);


        JPanel dashBoard = new JPanel(new GridLayout(1, 4, 10, 0));
        dashBoard.setPreferredSize(new Dimension(0, 200));
        dashBoard.setOpaque(false);

        for (int i = 0; i < 4; i++) {
            JPanel playerPanel = new JPanel(new BorderLayout());
            playerPanel.setOpaque(false);

            JPanel rolePlayer = new JPanel(null);

            ////// ZONE DE ROLE DU JOUEUR //////

            ////// ZONE DE ROLE DU JOUEUR //////

            rolePlayer.setPreferredSize(new Dimension(100,0));
            rolePlayer.setBackground(Color.RED);

            JPanel description = new JPanel(null);

            ////// ZONE DE DESCRIPTION DU JOUEUR //////

            ////// ZONE DE DESCRIPTION DU JOUEUR //////

            description.setBackground(Color.GREEN);

            JPanel cartes = new JPanel(new GridLayout(1, 5));

            ////// ZONE DES CARTES DU JOUEUR //////

            ////// ZONE DES CARTES DU JOUEUR //////

            cartes.setPreferredSize(new Dimension(0, 100));
            cartes.setBackground(Color.BLUE);

            playerPanel.add(rolePlayer, BorderLayout.WEST);
            playerPanel.add(description, BorderLayout.CENTER);
            playerPanel.add(cartes, BorderLayout.SOUTH);

            dashBoard.add(playerPanel);
        }

        this.add(menuPanel, BorderLayout.WEST);
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(dashBoard, BorderLayout.SOUTH);
    }
}
