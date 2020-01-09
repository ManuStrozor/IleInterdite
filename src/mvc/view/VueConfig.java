package mvc.view;

import mvc.Message;
import mvc.TypeMessage;

import javax.swing.*;

public class VueConfig extends Vue {

    public VueConfig(String name, IHM ihm) {
        super(name, ihm);

        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("backgroundConfig.jpg"))));
    }
}
