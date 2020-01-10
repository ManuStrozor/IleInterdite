package mvc.view;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc.Observe;

import java.awt.*;
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

    private int[] screenSize = new int[2];

    public IHM() {
        frame = new JFrame("Ile Interdite");
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        screenSize[0] = (int) tk.getScreenSize().getWidth();
        screenSize[1] = (int) tk.getScreenSize().getHeight();

        this.setSize(580, 840);
    }

    public void addVue(Vue vue) {
        vues.put(vue.name, vue);
    }

    public Vue getVue(String name) {
        return vues.get(name);
    }

    public void setSize(int w, int h) {
        frame.setSize(Math.min(w, screenSize[0]), Math.min(h, screenSize[1]));
    }

    public void setVue(String vue) {
        this.vue = getVue(vue);
        frame.setContentPane(this.vue);
        frame.setVisible(true);
    }
}
