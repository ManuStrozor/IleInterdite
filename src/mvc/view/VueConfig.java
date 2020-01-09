package mvc.view;

import mvc.Message;
import mvc.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class VueConfig extends Vue {

    public VueConfig(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                if (x > 0 && x < 580 && y > 0 && y < 840) {
                    Message m = new Message();
                    m.type = TypeMessage.JOUER;
                    m.vue = "jeu";
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
        this.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("backgroundConfig.jpg"))));
    }
}
