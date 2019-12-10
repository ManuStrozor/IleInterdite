/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut.ileinterdite;

import javax.swing.JFrame;

/**
 *
 * @author turbetde
 */
public class IHM extends Observe {
    
    private JFrame frame;
    private ViewMain main;
    
    public IHM() {
        main = new ViewMain(this);
        
        frame = new JFrame("Ile Interdite");
    }
}
