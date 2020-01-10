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

    public final int WIDTH = 580;
    public final int HEIGHT = 800;
    private double screenWidth, screenHeight, fW, fH;

    private JFrame frame;
    private JPanel vue = new JPanel();
    private HashMap<String, Vue> vues = new HashMap<>();

    public IHM() {
        frame = new JFrame("Ile Interdite");
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(vue);

        Rectangle ws = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        screenWidth = ws.width;
        screenHeight = ws.height;

        this.resizeFromSize(WIDTH, HEIGHT);
    }

    public void addVue(Vue vue) {
        vues.put(vue.name, vue);
    }

    public Vue getVue(String name) {
        return vues.get(name);
    }

    private void resizeFromSize(int w, int h) {
        this.setSize(w, h);
        frame.setVisible(true);

        double cW = frame.getContentPane().getSize().getWidth();
        double cH = frame.getContentPane().getSize().getHeight();

        this.setSize((int) (w + fW- cW), (int) (h + fH- cH));
        frame.setLocation((int) (screenWidth /2 - fW/2.0), (int) (screenHeight /2 - fH/2.0));
    }

    public void setSize(int w, int h) {
        frame.setSize(Math.min(w, (int) screenWidth), Math.min(h, (int) screenHeight));
        fW = frame.getSize().getWidth();
        fH = frame.getSize().getHeight();
    }

    public void setVue(String vue) {
        this.vue = getVue(vue);
        frame.setContentPane(this.vue);
        this.resizeFromSize(getVue(vue).getWidth(), getVue(vue).getHeight());
        frame.setVisible(true);
    }
}
