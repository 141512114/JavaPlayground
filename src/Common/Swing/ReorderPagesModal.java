package Common.Swing;

import javax.swing.*;
import java.awt.*;

public class ReorderPagesModal extends BasicModal {

    private JTextField orderField;

    public ReorderPagesModal ( Frame owner ) {
        super( owner );
        this.setTitle( "Seiten neuordnen" );
    }

    @Override
    protected void initComponents () {
        orderField = new JTextField( 20 );
        JPanel inputPanel = new JPanel( new FlowLayout() );
        inputPanel.add( new JLabel( "Neue Reihenfolge (z.B. 3,1,2):" ) );
        inputPanel.add( orderField );

        JButton okButton     = new JButton( "OK" );
        JButton cancelButton = new JButton( "Abbrechen" );

        okButton.addActionListener( e -> {
            setOkPressed( true );
            this.setVisible( false );
        } );

        cancelButton.addActionListener( e -> {
            setOkPressed( false );
            this.setVisible( false );
        } );

        JPanel buttonPanel = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
        buttonPanel.add( okButton );
        buttonPanel.add( cancelButton );

        this.getContentPane().setLayout( new BorderLayout() );
        this.getContentPane().add( inputPanel, BorderLayout.CENTER );
        this.getContentPane().add( buttonPanel, BorderLayout.SOUTH );
    }

    public int[] getNewOrder () {
        String text = orderField.getText().trim();
        if ( text.isEmpty() ) return new int[ 0 ];
        String[] parts = text.split( "," );
        int[]    order = new int[ parts.length ];
        for ( int i = 0; i < parts.length; i++ ) {
            try {
                order[ i ] = Integer.parseInt( parts[ i ].trim() );
            } catch ( NumberFormatException e ) {
                return new int[ 0 ]; // Ungültig
            }
        }
        return order;
    }

}
