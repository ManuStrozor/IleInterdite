package mvc.view;

import mvc.Message;
import mvc.TypeMessage;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VueMenu extends Vue {

    public VueMenu(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                if (x > 217 && x < 357 && y > 628 && y < 681) { // Bouton Jouer
                    Message m = new Message();
                    m.type = TypeMessage.JOUER;
                    m.vue = "config";
                    ihm.notifierObservateur(m);
                } else if (x > 217 && x < 355 && y > 714 && y < 769) { // Bouton Quitter
                    Message m = new Message();
                    m.type = TypeMessage.QUITTER;
                    ihm.notifierObservateur(m);
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

    @Override
    public void initComponents() {
        this.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("backgroundMenu.png"))));
    }
}
