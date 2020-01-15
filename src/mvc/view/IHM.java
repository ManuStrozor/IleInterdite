package mvc.view;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import game.Tuile;
import mvc.Observe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author turbetde
 */
public class IHM extends Observe {

    public final int WIDTH = 580;
    public final int HEIGHT = 800;
    private double screenWidth, screenHeight, frameWidth, frameHeight;

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

    public Vue getVue(String vue) {
        return vues.get(vue);
    }

    private void resizeFromSize(int w, int h) {
        this.setSize(w, h);
        frame.setVisible(true);

        double contentWidth = frame.getContentPane().getSize().getWidth();
        double contentHeight = frame.getContentPane().getSize().getHeight();

        this.setSize((int) (w + frameWidth - contentWidth), (int) (h + frameHeight - contentHeight));
        frame.setLocation((int) (screenWidth /2 - frameWidth /2.0), (int) (screenHeight /2 - frameHeight /2.0));
    }

    public void setSize(int w, int h) {
        if (h > screenHeight) {
            double ratio = screenHeight/h;
            h = (int) screenHeight;
            w = (int) (w * ratio);
        }
        if (w > screenWidth) {
            double ratio = screenWidth/w;
            w = (int) screenWidth;
            h = (int) (h * ratio);
        }
        frame.setSize(w, h);
        frameWidth = frame.getSize().getWidth();
        frameHeight = frame.getSize().getHeight();
    }

    public void setVue(String vue) {
        this.vue = getVue(vue);
        frame.setContentPane(this.vue);
        this.resizeFromSize(getVue(vue).getWidth(), getVue(vue).getHeight());
        frame.setVisible(true);
    }

    public void afficherTuilesAccessibles(ArrayList<Tuile> tuiles) {
    }
}
