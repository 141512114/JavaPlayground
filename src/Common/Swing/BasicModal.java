package Common.Swing;

import javax.swing.*;
import java.awt.*;

public abstract class BasicModal extends JDialog {

    private boolean okPressed = false;

    public BasicModal ( Frame owner ) {
        super( owner, "Modal", true );
        initComponents();
        this.pack();
        setLocationRelativeTo( owner );
    }

    protected abstract void initComponents ();

    public boolean isOkPressed () {
        return okPressed;
    }

    protected void setOkPressed ( boolean okPressed ) {
        this.okPressed = okPressed;
    }

}
