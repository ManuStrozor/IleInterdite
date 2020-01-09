/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import javax.swing.JPanel;

/**
 *
 * @author turbetde
 */
public abstract class Vue extends JPanel implements IVue {
    
    protected IHM ihm;
    protected String name;
    
    Vue(String name, IHM ihm) {
        this.name = name;
        this.ihm = ihm;
        this.ihm.addVue(this);
    }
}
