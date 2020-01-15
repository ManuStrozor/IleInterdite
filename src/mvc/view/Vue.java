/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import aventuriers.Aventurier;
import game.Grille;
import game.Tuile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author turbetde
 */
public abstract class Vue extends JPanel implements IVue {
    
    protected IHM ihm;
    protected String name;
    protected int width, height;
    private Image background;
    
    Vue(String name, IHM ihm) {
        this.name = name;
        this.ihm = ihm;
        setWidth(ihm.WIDTH);
        setHeight(ihm.HEIGHT);
        this.ihm.addVue(this);
        this.setLayout(new BorderLayout());
    }

    Vue(String name, IHM ihm, int width, int height) {
        this.name = name;
        this.ihm = ihm;
        setWidth(width);
        setHeight(height);
        this.ihm.addVue(this);
        this.setLayout(new BorderLayout());
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected void setBackground(Image background) {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, (int)getSize().getWidth(), (int)getSize().getHeight(), null);
    }

    public abstract void afficherTitreJoueur(String nom);
    public abstract void updateDashboard(ArrayList<Aventurier> aventuriers);
    public abstract void updateGrille(Grille grille);
    public abstract void afficherTuilesAccessibles(Grille grille, ArrayList<Tuile> tuiles);
    public abstract void afficherCartesAccessibles(ArrayList<Aventurier> aventuriers, Aventurier joueur);
    public abstract void initBoards(int nbJoueur);
    public abstract void rendreBoutonsClicables(boolean b);
    public abstract void afficherAventurierAccessibles(ArrayList<Aventurier> aventuriers, ArrayList<Aventurier> aventuriersOK);
}
