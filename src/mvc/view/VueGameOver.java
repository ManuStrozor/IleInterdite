package mvc.view;

import aventuriers.Aventurier;
import game.Grille;
import game.Tuile;
import mvc.Message;
import enumerations.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class VueGameOver extends Vue {

    private JButton  quitter;

    public VueGameOver(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();



        quitter.addActionListener(e -> {
            Message m = new Message(TypeMessage.QUITTER);
            ihm.notifierObservateur(m);
        });
    }
    @Override
    public void updateNiveauEau(int niveau){}
    @Override
    public void rendreBoutonsClicables(boolean bool) {}
    @Override
    public void updateJoueur(String nom) {}
    @Override
    public void updateDashboard(ArrayList<Aventurier> aventuriers) {}
    @Override
    public void updateGrille(Grille grille) {}
    @Override
    public void afficherAventurierAccessibles(ArrayList<Aventurier> aventuriers, ArrayList<Aventurier> aventuriersOK) {}
    @Override
    public void afficherCartes(int index, int nbCartes, Aventurier a) {}
    @Override
    public void afficherTuilesAccessibles(Grille grille, ArrayList<Tuile> tuiles) {}
    @Override
    public void afficherCartesAccessibles(ArrayList<Aventurier> aventuriers, Aventurier joueur) {}
    @Override
    public void initBoards(int nbJoueur) {}
    @Override
    public void desactiverCartes(int index, int nbCartes, Aventurier a){}
    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("gameOver.png"))).getImage());




        quitter = new JButton("QUITTER");

        JPanel panel = new JPanel(new BorderLayout()); panel.setOpaque(false);

        JPanel buttonsPanel = new JPanel(new BorderLayout(0, 10)); buttonsPanel.setOpaque(false);
        JPanel marginLeft = new JPanel(); marginLeft.setPreferredSize(new Dimension(200, 0)); marginLeft.setOpaque(false);
        JPanel marginRight = new JPanel(); marginRight.setPreferredSize(new Dimension(200, 0)); marginRight.setOpaque(false);
        JPanel marginBot = new JPanel(); marginBot.setPreferredSize(new Dimension(0, 50)); marginBot.setOpaque(false);

        JLabel perdu = new JLabel("VOUS AVEZ PERDU!  ", SwingConstants.CENTER);
        Font font = new Font("Arial",Font.BOLD,48);
        perdu.setFont(font);
        perdu.setForeground(Color.white);




        buttonsPanel.add(quitter, BorderLayout.SOUTH);

        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(marginBot, BorderLayout.SOUTH);
        panel.add(marginLeft, BorderLayout.WEST);
        panel.add(marginRight, BorderLayout.EAST);
        this.add(perdu, BorderLayout.CENTER);

        this.add(panel, BorderLayout.SOUTH);
    }
}
