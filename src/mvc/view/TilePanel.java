package mvc.view;

import javax.swing.*;
import java.awt.*;

public class TilePanel extends JPanel {

    private Image background = null;

    public TilePanel(LayoutManager layout) {
        super(layout);
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, (int)getSize().getWidth(), (int)getSize().getHeight(), null);
    }
}
