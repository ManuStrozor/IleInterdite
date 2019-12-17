/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import game.IHM;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author turbetde
 */
public abstract class Vue extends JPanel implements IVue {
    
    protected IHM ihm;
    
    Vue(IHM ihm) {
        this.ihm = ihm;
        this.setLayout(new BorderLayout());
    }

    public IHM getIHM() {
        return ihm;
    }
    
    public void show() {
        ihm.setContentPane(this);
    }
}
