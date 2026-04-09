package SwingTest;

import javax.swing.*;
import java.text.NumberFormat;

public class AppWindow extends JFrame {

    JTextField textfeldDm;
    JTextField textfeldEuro;

    JButton button;

    public AppWindow () {
        this.getContentPane().setLayout( null );
        this.setTitle( "App Window" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable( false );
        this.setLocationRelativeTo( null );

        this.initWindow();
    }

    protected void initWindow () {
        textfeldDm   = new JTextField();
        textfeldEuro = new JTextField();

        button = new JButton( "DM in Euro" );

        button.addActionListener( _ -> buttonBerechneClicked() );

        textfeldDm.setBounds( 5, 10, 400, 25 );
        textfeldEuro.setBounds( 5, 80, 400, 25 );

        button.setBounds( 300, 110, 100, 30 );

        this.getContentPane().add( textfeldDm );
        this.getContentPane().add( textfeldEuro );
        this.getContentPane().add( button );

        this.pack();
    }

    private void buttonBerechneClicked () {
        if ( textfeldDm.getText().isEmpty() ) return;
        double dm = Double.parseDouble( textfeldDm.getText() );

        if ( dm >= 0 ) {

            double euro = this.dm2euro( dm );

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits( 2 );
            String ausgabe = nf.format( euro );

            textfeldEuro.setText( ausgabe );

        } else {

            textfeldEuro.setText( "Eingabe ist nicht in Ordnung." );

        }
    }

    private double dm2euro ( double dm ) {
        return (dm / 1.95583);
    }

}