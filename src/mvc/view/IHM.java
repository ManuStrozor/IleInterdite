package mvc.view;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc.Observe;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author turbetde
 */
public class IHM extends Observe {
    
    private JFrame frame;
    private JPanel vue;
    private HashMap<String, Vue> vues = new HashMap<>();
    
    public IHM() {
        frame = new JFrame("Ile Interdite");
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Toolkit tk = Toolkit.getDefaultToolkit();
        // int w = (int) tk.getScreenSize().getWidth();
        // int h = (int) tk.getScreenSize().getHeight();

        int w = 580;
        int h = 840;

        frame.setSize(w, h);
    }

    public void addVue(Vue vue) {
        vues.put(vue.name, vue);
    }

    public Vue getVue(String name) {
        return vues.get(name);
    }

    public void setVue(String vue) {
        this.vue = getVue(vue);
        frame.setContentPane(this.vue);
        frame.setVisible(true);
    }
}
