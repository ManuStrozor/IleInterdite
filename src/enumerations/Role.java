package enumerations;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public enum Role {
    ingenieur,
    messager,
    navigateur,
    explorateur,
    pilote,
    plongeur,
    ;

    public Image getImage() {
        URL url = getClass().getClassLoader().getResource("aventuriers/"+this.name()+".png");
        return new ImageIcon(Objects.requireNonNull(url)).getImage();
    }
}
