package Common.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Window extends JFrame {

    private final JPanel topPanel;
    private final JPanel centerPanel;

    public Window () {
        JFrame.setDefaultLookAndFeelDecorated( true );
        JDialog.setDefaultLookAndFeelDecorated( true );

        this.getContentPane().setLayout( new BorderLayout() );

        // Panel oben für Buttons/Textfelder (behält PreferredSize, skaliert nicht über gesamte Fläche)
        topPanel = new JPanel( new FlowLayout( FlowLayout.LEFT, 5, 5 ) );
        this.getContentPane().add( topPanel, BorderLayout.NORTH );

        // zentrales Panel für gruppierte Controls, vertikal gestapelt
        centerPanel = new JPanel();
        centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.Y_AXIS ) );
        centerPanel.setBorder( null );
        JScrollPane scroll = new JScrollPane( centerPanel );
        scroll.setBorder( null );
        scroll.setViewportBorder( null );
        this.getContentPane().add( scroll, BorderLayout.CENTER );

        this.setTitle( "App Window" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable( false );
        this.setLocationRelativeTo( null );
    }

    public void insertComponent ( JComponent component ) {
        component.setAlignmentX( Component.LEFT_ALIGNMENT );
        Dimension pref = component.getPreferredSize();
        component.setMaximumSize( new Dimension( Integer.MAX_VALUE, pref.height ) );
        centerPanel.add( component );
        this.pack();
    }

    private void insertComponent ( JPanel panel, Component component ) {
        panel.add( component );
        panel.revalidate();
        panel.repaint();
        this.pack();
    }

    //#####################################################//
    // Label
    //#####################################################//

    public JLabel addLabel ( String text, int panelId ) {
        JLabel label = new JLabel( text );
        label.setForeground( Color.BLACK );
        this.insertComponent( this.getPanelById( panelId ), label );

        return label;
    }

    public JLabel addLabel ( String text ) {
        return this.addLabel( text, 1 );
    }

    public void updateLabel ( JLabel label, String text ) {
        String shortText = text;
        int    maxLen    = 21;

        if ( text.length() > maxLen ) {
            shortText = text.substring( 0, maxLen - 3 ) + "...";
        }

        label.setText( shortText );
        label.setToolTipText( text );
        label.getParent().revalidate();
        label.getParent().repaint();
    }

    //#####################################################//
    // FileChooser
    // Textfeld
    // Knopf
    //#####################################################//

    public void addFileChooser ( Size size, String text, BiConsumer<ActionEvent, File> action ) {
        JButton button = new JButton( text );
        button.addActionListener( e -> {
            JFileChooser chooser     = new JFileChooser();
            int          returnValue = chooser.showOpenDialog( null );

            if ( returnValue == JFileChooser.APPROVE_OPTION ) {
                File selectedFile = chooser.getSelectedFile();
                action.accept( e, selectedFile );
            }
        } );
        button.setPreferredSize( new Dimension( size.width(), size.height() ) );
        this.insertComponent( topPanel, button );
    }

    public JTextField addTextField ( Size size ) {
        JTextField textfield = new JTextField();
        textfield.setPreferredSize( new Dimension( size.width(), size.height() ) );
        this.insertComponent( topPanel, textfield );

        return textfield;
    }

    public void addButton ( Size size, String text, Consumer<ActionEvent> action ) {
        JButton button = new JButton( text );
        button.addActionListener( action::accept );
        button.setPreferredSize( new Dimension( size.width(), size.height() ) );
        this.insertComponent( topPanel, button );
    }

    //#####################################################//
    // Dialog
    //#####################################################//

    public boolean showConfirmDialog () {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Willst du wirklich fortfahren?",
                "Bestätigung",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }

    //#####################################################//
    // Nützliche Funktionen
    //#####################################################//

    private JPanel getPanelById ( int id ) {
        if ( id == 0 ) return topPanel;
        else return centerPanel;
    }

}
