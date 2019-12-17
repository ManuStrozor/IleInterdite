package views;

import game.IHM;

import javax.swing.*;
import java.awt.*;

public class VueMenu extends Vue {

    public VueMenu(IHM ihm) {
        super(ihm);
        this.initialisation();
    }

    @Override
    public void initialisation() {
        JPanel corps = new JPanel(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setBackground(Color.green);
        corps.add(menu, BorderLayout.CENTER);

        this.add(corps, BorderLayout.CENTER);
    }
}
