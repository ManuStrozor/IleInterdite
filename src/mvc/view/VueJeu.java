/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author turbetde
 */
public class VueJeu extends Vue {
    
    public VueJeu(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("backgroundJeu.jpg"))));
    }
}
