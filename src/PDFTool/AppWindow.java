package PDFTool;

import Common.Swing.*;
import Common.Swing.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class AppWindow {

    Window window;

    File   chosenFile;
    JLabel fileLabel;

    public AppWindow () {
        this.window = new Window();

        this.window.addFileChooser( new Size( 200, 30 ), "Datei auswählen", this::chooseFile );
        this.fileLabel = this.window.addLabel( "Keine Datei ausgewählt ...", 0 );

        JButton pageDeleteButton = new JButton( "Seite(n) löschen" );
        pageDeleteButton.addActionListener( e -> {
            if ( chosenFile == null ) return;
            DeletePagesModal modal = new DeletePagesModal( this.window );
            modal.setVisible( true );
            if ( modal.isOkPressed() ) {
                int[] pages = modal.getPagesToDelete();
                Editor.deletePages( this.chosenFile, pages );
            }
        } );
        JButton pageReorderButton = new JButton( "Seiten neuordnen" );
        pageReorderButton.addActionListener( e -> {
            if ( chosenFile == null ) return;
            ReorderPagesModal modal = new ReorderPagesModal( this.window );
            modal.setVisible( true );
            if ( modal.isOkPressed() ) {
                int[] order = modal.getNewOrder();
                Editor.reorderPages( this.chosenFile, order );
            }
        } );
        ControlGroup pageGroup = new ControlGroup( "Seiten" );
        pageGroup.insertComponent( pageDeleteButton );
        pageGroup.insertComponent( pageReorderButton );
        this.window.insertComponent( pageGroup );

        JButton watermarkButton = new JButton( "Wasserzeichen einfügen" );
        watermarkButton.addActionListener( this::addWatermark );
        JButton combineButton = new JButton( "PDFs zusammenfügen" );
        combineButton.addActionListener( e -> {
            CombinePDFsModal modal = new CombinePDFsModal( this.window );
            modal.setVisible( true );
            if ( modal.isOkPressed() ) {
                File[] files = modal.getSelectedFiles();
                if ( files.length > 0 ) {
                    Editor.combinePDFs( files, new File( "output.pdf" ) );
                }
            }
        } );
        ControlGroup manipulateGroup = new ControlGroup( "Allgemein" );
        manipulateGroup.insertComponent( watermarkButton );
        manipulateGroup.insertComponent( combineButton );
        this.window.insertComponent( manipulateGroup );
    }

    //#####################################################//
    // Datei auswählen
    // Anzeigen aktualisieren
    //#####################################################//

    private void chooseFile ( ActionEvent actionEvent, File file ) {
        this.chosenFile = file;
        this.updateFileLabel( file );
    }

    private void updateFileLabel ( File file ) {
        String fileName = file.getName();
        String text     = fileName.isBlank() ? "Keine Datei ausgewählt ..." : fileName;
        this.window.updateLabel( this.fileLabel, text );
    }

    //#####################################################//
    // Aktionen
    //#####################################################//

    private void addWatermark ( ActionEvent actionEvent ) {
        if ( chosenFile == null ) return;
        Editor.addWatermark( this.chosenFile );
    }

}