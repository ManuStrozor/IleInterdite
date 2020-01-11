package mvc.view;

import mvc.Message;
import mvc.TypeMessage;

import javax.swing.*;
import java.util.Objects;

public class VueMenu extends Vue {

    private JButton jouer, quitter;

    public VueMenu(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();

        jouer.addActionListener(e -> {
            Message m = new Message();
            m.type = TypeMessage.CONFIG;
            m.vue = "config";
            ihm.notifierObservateur(m);
        });

        quitter.addActionListener(e -> {
            Message m = new Message();
            m.type = TypeMessage.QUITTER;
            ihm.notifierObservateur(m);
        });
    }

    @Override
    public void initComponents() {
        this.setLayout(null);
        this.setBackground(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("backgroundMenu.png"))).getImage());

        jouer = new JButton("JOUER");
        quitter = new JButton("QUITTER");

        int w = 160;
        int h = 50;
        jouer.setBounds(this.getWidth()/2 - w/2, (int)(0.78*this.getHeight()), w, h);
        quitter.setBounds(this.getWidth()/2 - w/2, (int)(0.88*this.getHeight()), w, h);

        this.add(jouer);
        this.add(quitter);
    }
}
