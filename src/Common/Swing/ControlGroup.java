package Common.Swing;

import javax.swing.*;
import java.awt.*;

public class ControlGroup extends JPanel {

    public ControlGroup ( String title ) {
        setBorder( BorderFactory.createTitledBorder( title ) );
    }

    public void insertComponent ( JComponent component ) {
        this.add( component );
    }

    public void setGroupEnabled ( boolean enabled ) {
        Component[] components = this.getComponents();
        for ( Component c : components ) {
            c.setEnabled( enabled );
        }
    }

}
