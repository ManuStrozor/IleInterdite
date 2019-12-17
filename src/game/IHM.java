package game;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import views.Vue;

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author turbetde
 */
public class IHM extends Observe {
    
    private JFrame frame;
    private JPanel content;
    
    public IHM() {
        frame = new JFrame("Ile Interdite");
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        int w = (int) tk.getScreenSize().getWidth();
        int h = (int) tk.getScreenSize().getHeight();
        frame.setSize(w, h);
        
        frame.setVisible(true);
    }
    
    public void setContentPane(Vue vue) {
        content = vue;
        frame.setContentPane(content);
    }
}
