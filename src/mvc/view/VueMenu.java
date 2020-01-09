package mvc.view;

import mvc.Message;
import mvc.TypeMessage;

import javax.swing.*;
import java.awt.*;

public class VueMenu extends Vue {

    private JPanel corps, menu;
    private JButton play, quit;

    public VueMenu(String name, IHM ihm) {
        super(name, ihm);

        corps = new JPanel(new BorderLayout());
        menu = new JPanel(new GridLayout(4,3));

        play = new JButton("Jouer");
        quit = new JButton("Quitter");

        this.initComponents();

        play.addActionListener(e -> {
            Message m = new Message();
            m.type = TypeMessage.JOUER;
            m.vue = "jeu";
            ihm.notifierObservateur(m);
        });

        quit.addActionListener(e -> {
            Message m = new Message();
            m.type = TypeMessage.QUITTER;
            ihm.notifierObservateur(m);
        });
    }

    @Override
    public void initComponents() {

        for (int i = 0; i < 12; i++) {
            if (i == 4) {
                menu.add(play);
            } else if (i == 7) {
                menu.add(quit);
            } else {
                menu.add(new JLabel());
            }
        }

        corps.add(menu, BorderLayout.CENTER);
        this.add(corps, BorderLayout.CENTER);
    }
}
