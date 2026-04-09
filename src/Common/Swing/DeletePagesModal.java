package Common.Swing;

import javax.swing.*;
import java.awt.*;

public class DeletePagesModal extends BasicModal {

    private JTextField pagesField;

    public DeletePagesModal ( Frame owner ) {
        super( owner );
        this.setTitle( "Seiten löschen" );
    }

    @Override
    protected void initComponents () {
        pagesField = new JTextField( 20 );
        JPanel inputPanel = new JPanel( new FlowLayout() );
        inputPanel.add( new JLabel( "Seiten zum Löschen (z.B. 1,3,5):" ) );
        inputPanel.add( pagesField );

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

    public int[] getPagesToDelete () {
        String text = pagesField.getText().trim();
        if ( text.isEmpty() ) return new int[ 0 ];
        String[] parts = text.split( "," );
        int[]    pages = new int[ parts.length ];
        for ( int i = 0; i < parts.length; i++ ) {
            try {
                pages[ i ] = Integer.parseInt( parts[ i ].trim() );
            } catch ( NumberFormatException e ) {
                return new int[ 0 ]; // Ungültig
            }
        }
        return pages;
    }

}
