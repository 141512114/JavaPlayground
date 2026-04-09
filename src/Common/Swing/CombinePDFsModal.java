package Common.Swing;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CombinePDFsModal extends BasicModal {

    private final List<File>    selectedFiles = new ArrayList<>();
    private       JList<String> fileList;

    public CombinePDFsModal ( Frame owner ) {
        super( owner );
        this.setTitle( "PDFs zusammenfügen" );
    }

    @Override
    protected void initComponents () {
        JButton selectFilesButton = new JButton( "Dateien auswählen" );
        selectFilesButton.addActionListener( e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled( true );
            chooser.setFileFilter( new javax.swing.filechooser.FileNameExtensionFilter( "PDF Files", "pdf" ) );
            int result = chooser.showOpenDialog( this );
            if ( result == JFileChooser.APPROVE_OPTION ) {
                File[] files = chooser.getSelectedFiles();
                selectedFiles.clear();
                Collections.addAll( selectedFiles, files );
                updateFileList();
            }
        } );

        fileList = new JList<>();
        fileList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        JScrollPane scrollPane = new JScrollPane( fileList );

        JPanel inputPanel = new JPanel( new BorderLayout() );
        inputPanel.add( selectFilesButton, BorderLayout.NORTH );
        inputPanel.add( scrollPane, BorderLayout.CENTER );

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

    private void updateFileList () {
        String[] fileNames = new String[ selectedFiles.size() ];
        for ( int i = 0; i < selectedFiles.size(); i++ ) {
            fileNames[ i ] = selectedFiles.get( i ).getName();
        }
        fileList.setListData( fileNames );
    }

    public File[] getSelectedFiles () {
        return selectedFiles.toArray( new File[ 0 ] );
    }

}
