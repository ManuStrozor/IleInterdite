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
public abstract class Vue extends JPanel implements IVue {
    
    protected IHM ihm;
    protected String name;
    private Image background;
    
    Vue(String name, IHM ihm) {
        this.name = name;
        this.ihm = ihm;
        this.ihm.addVue(this);
        //this.setLayout(new BorderLayout());
    }

    protected void setBackground(Image background) {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);
    }
}
