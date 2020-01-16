package mvc.view;

import aventuriers.Aventurier;
import game.Grille;
import game.Tuile;
import mvc.Message;
import enumerations.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class VueConfig extends Vue {

    private JComboBox<Integer> choixNbJoueurs;
    private JLabel [] labelNomJoueurs = new JLabel[4];
    private JTextField [] saisieNomJoueurs = new JTextField[4];
    private ButtonGroup groupeBouton;
    private final JButton inscrire = new JButton("OKAY");
    private final JButton retour = new JButton("RETOUR");
    private String[] nomJoueurs;
    private JLabel erreurJ1, erreurJ2, erreurJ3 , erreurJ4;

    public VueConfig(String name, IHM ihm) {

        super(name, ihm);

        erreurJ1 = new JLabel();
        erreurJ2 = new JLabel();
        erreurJ3 = new JLabel();
        erreurJ4 = new JLabel();

        this.initComponents();

        choixNbJoueurs.addItemListener(e -> {
            int nb = (Integer) choixNbJoueurs.getSelectedItem();

            for(int i = 0; i < saisieNomJoueurs.length; i++) {
                labelNomJoueurs[i].setEnabled(i < nb);
                saisieNomJoueurs[i].setEnabled(i < nb);
            }
        });

        retour.addActionListener(e -> {
            Message m = new Message(TypeMessage.CHANGER_VUE);
            m.vue = "menu";
            ihm.notifierObservateur(m);
        });

        inscrire.addActionListener(e -> {       // A OPTIMISER EN METTANT LES ERREURJ DANS UN TABLEAU
            erreurJ1.setText("");
            erreurJ2.setText("");
            erreurJ3.setText("");
            erreurJ4.setText("");

            if (entreeNomValide() < 5){

                if (entreeNomValide() == 0){
                    erreurJ1.setText("VIDE !");
                } else if ( entreeNomValide() == 1 ) {
                    erreurJ2.setText("VIDE !");
                } else if ( entreeNomValide() == 2 ) {
                    erreurJ3.setText("VIDE !");
                } else{
                    erreurJ4.setText("VIDE !");
                }

            }else{
                Message m = new Message(TypeMessage.JOUER);
                m.nbJoueur = (int) choixNbJoueurs.getSelectedItem();
                m.niveauEau = groupeBouton.getSelection().getActionCommand();

                int i = 0;
                nomJoueurs = new String[4];
                for(JTextField text : saisieNomJoueurs) {
                    nomJoueurs[i++] = text.getText();
                }
                m.nomsJoueurs = nomJoueurs;
                ihm.notifierObservateur(m);
            }

        });
    }
    @Override
    public void rendreBoutonsClicables(boolean bool){};

    public int entreeNomValide(){
        int indexErreur = 5;
        for (int j = 0 ; j < ((int) choixNbJoueurs.getSelectedItem()) ; j++) {
            if(saisieNomJoueurs[j].getText().equals("")){
                indexErreur = j;
                break;
            }
        }
        return indexErreur;
    }
    @Override
    public void updateNiveauEau(int niveau){}
    @Override
    public void updateJoueur(String nom) {}
    @Override
    public void updateDashboard(ArrayList<Aventurier> aventuriers) {}
    @Override
    public void updateGrille(Grille grille) {}
    @Override
    public void afficherTuilesAccessibles(Grille grille, ArrayList<Tuile> tuiles) {}
    @Override
    public void afficherCartesAccessibles(ArrayList<Aventurier> aventuriers, Aventurier joueur) {}
    @Override
    public void initBoards(int nbJoueur) {}
    @Override
    public void afficherAventurierAccessibles(ArrayList<Aventurier> aventuriers, ArrayList<Aventurier> aventuriersOK) {}
    @Override
    public void afficherCartes(int index, int nbCartes) {}
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
            if (i == 0){
                panelJoueur.add(erreurJ1);
            } else if ( i== 1 ) {
                panelJoueur.add(erreurJ2);
            } else if ( i== 2 ) {
                panelJoueur.add(erreurJ3);
            } else{
                panelJoueur.add(erreurJ4);
            }

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

        groupeBouton = new ButtonGroup();
        JLabel choixNiveau = new JLabel ("Difficulté : ");
        JRadioButton radio1 = new JRadioButton("Novice");
        radio1.setActionCommand("Novice");
        JRadioButton radio2 = new JRadioButton("Normal");
        radio2.setActionCommand("Normal");
        JRadioButton radio3 = new JRadioButton("Elite");
        radio3.setActionCommand("Elite");
        //4 niveaux de difficulté la team
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

        panelJoueur.add(retour);
        panelJoueur.add(new JLabel());
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
