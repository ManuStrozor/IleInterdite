package mvc.view;

import game.Grille;
import mvc.Message;
import enumerations.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VueConfig extends Vue {

    private JComboBox<Integer> choixNbJoueurs;
    private JLabel [] labelNomJoueurs = new JLabel[4];
    private JTextField [] saisieNomJoueurs = new JTextField[4];
    private final JButton inscrire = new JButton("OKAY");
    private final JButton retour = new JButton("RETOUR");
    private String[] nomJoueurs;

    public VueConfig(String name, IHM ihm) {

        super(name, ihm);

        this.initComponents();


        choixNbJoueurs.addItemListener(e -> {
            int nb = (Integer) choixNbJoueurs.getSelectedItem();

            for(int i = 0; i < saisieNomJoueurs.length; i++) {
                labelNomJoueurs[i].setEnabled(i < nb);
                saisieNomJoueurs[i].setEnabled(i < nb);
            }
        });
    }

    @Override
    public void updateGrille(Grille grille) {}

    @Override
    public void initComponents() {
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundConfig.jpg"))).getImage());

        //JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelJoueur = new JPanel(new GridLayout(9,3));
        JPanel panelNord = new JPanel(new BorderLayout());
        JPanel panelOuest = new JPanel(new BorderLayout());
        JPanel panelEst = new JPanel(new BorderLayout());
        JPanel panelSud = new JPanel(new BorderLayout());

        // nombre de joueurs
        choixNbJoueurs = new JComboBox<>(new Integer[] { 2, 3, 4 });
        panelJoueur.add(new JLabel("Nombre de joueurs :"));
        panelJoueur.add(choixNbJoueurs);
        panelJoueur.add(new JLabel(""));

        // Saisie des noms de joueurs
        for(int i = 0; i < saisieNomJoueurs.length; i++) {
            saisieNomJoueurs[i] = new JTextField();
            saisieNomJoueurs[i].setPreferredSize(new Dimension(20,3));
            labelNomJoueurs[i] = new JLabel("Nom du joueur " + (i+1) + " :");
            panelJoueur.add(labelNomJoueurs[i]);
            panelJoueur.add(saisieNomJoueurs[i]);
            panelJoueur.add(new JLabel(""));
            labelNomJoueurs[i].setEnabled(i < 2);
            saisieNomJoueurs[i].setEnabled(i < 2);
        }



        panelJoueur.add(new JLabel());
        panelJoueur.add(new JLabel());
        panelJoueur.add(new JLabel());
        this.add(panelJoueur,BorderLayout.CENTER);
        this.add(panelNord,BorderLayout.NORTH);
        this.add(panelOuest,BorderLayout.WEST);
        this.add(panelEst,BorderLayout.EAST);
        this.add(panelSud,BorderLayout.SOUTH);

        ButtonGroup groupeBouton = new ButtonGroup();
        JLabel choixNiveau = new JLabel ("Difficulté : ");
        JRadioButton radio1 = new JRadioButton("Niveau 1");
        JRadioButton radio2 = new JRadioButton("Niveau 2");
        JRadioButton radio3 = new JRadioButton("Niveau 3");
        radio1.setSelected(true);

        groupeBouton.add(radio1);
        groupeBouton.add(radio2);
        groupeBouton.add(radio3);

        panelJoueur.add(choixNiveau);
        panelJoueur.add(new JLabel());
        panelJoueur.add(new JLabel());
        panelJoueur.add(radio1);
        panelJoueur.add(radio2);
        panelJoueur.add(radio3);

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message(TypeMessage.CHANGER_VUE);
                m.vue = "menu";
                ihm.notifierObservateur(m);
            }
        });
        panelJoueur.add(retour);

        panelJoueur.add(new JLabel());

        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message(TypeMessage.JOUER);
                m.vue = "jeu";
                ihm.notifierObservateur(m);
                m.nbJoueur = (int) choixNbJoueurs.getSelectedItem();

            }
        });
        panelJoueur.add(inscrire);






        panelNord.setPreferredSize(new Dimension(300,200));
        panelOuest.setPreferredSize(new Dimension(150,200));
        panelEst.setPreferredSize(new Dimension(100,200));
        panelSud.setPreferredSize(new Dimension(300,150));

        panelJoueur.setOpaque(false);
        panelNord.setOpaque(false);
        panelOuest.setOpaque(false);
        panelEst.setOpaque(false);
        panelSud.setOpaque(false);
        radio1.setOpaque(false);
        radio2.setOpaque(false);
        radio3.setOpaque(false);
    }
}
