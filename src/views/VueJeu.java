/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import game.IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {
    
    public VueJeu(IHM ihm) {
        super(ihm);
        this.initialisation();
    }

    @Override
    public void initialisation() {
        JPanel header = new JPanel(new GridLayout(1, 3));
        header.add(new JLabel("GAUCHE"));
        header.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("ileinterdite_logo.jpg"))));
        header.add(new JLabel("DROIT"));
        header.setBackground(Color.red);

        JPanel corps = new JPanel(new BorderLayout());

        JPanel board = new JPanel();
        board.setBackground(Color.green);
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.blue);
        corps.add(board, BorderLayout.CENTER);
        corps.add(sideBar, BorderLayout.EAST);

        this.add(header, BorderLayout.NORTH);
        this.add(corps, BorderLayout.CENTER);
    }
}
