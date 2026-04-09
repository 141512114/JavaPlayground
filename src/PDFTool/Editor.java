package PDFTool;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class Editor {

    private static void modifyFile ( File file, Consumer<PDDocument> callback ) {
        File output = new File( "output.pdf" );

        try ( PDDocument document = Loader.loadPDF( file ) ) {
            callback.accept( document );
            document.save( output );

            System.out.println( "Dokument bearbeitet und gespeichert." );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static void addWatermark ( File file ) {
        modifyFile( file, document -> {
            int pageCount = document.getPages().getCount();

            for ( int i = 0; i < pageCount; i++ ) {
                PDPage page = document.getPage( i );

                try ( PDPageContentStream contentStream = new PDPageContentStream( document, page, PDPageContentStream.AppendMode.APPEND, true ) ) {
                    contentStream.beginText();
                    contentStream.setFont( new PDType1Font( Standard14Fonts.FontName.HELVETICA_BOLD ), 24 );
                    contentStream.newLineAtOffset( 100, 700 );
                    contentStream.showText( "Mein Wasserzeichen" );
                    contentStream.endText();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }

            System.out.println( "Wasserzeichen hinzugefügt!" );
        } );
    }

    public static void deletePages ( File file, int[] pagesToDelete ) {
        modifyFile( file, document -> {
            // Sortiere die Seitenzahlen absteigend, um Indizes beim Entfernen nicht zu verschieben
            java.util.Arrays.sort( pagesToDelete );
            for ( int i = pagesToDelete.length - 1; i >= 0; i-- ) {
                int pageIndex = pagesToDelete[ i ] - 1; // 0-basiert
                if ( pageIndex >= 0 && pageIndex < document.getPages().getCount() ) {
                    document.getPages().remove( pageIndex );
                }
            }
            System.out.println( "Seiten gelöscht!" );
        } );
    }

    public static void reorderPages ( File file, int[] newOrder ) {
        modifyFile( file, document -> {
            int pageCount = document.getPages().getCount();
            if ( newOrder.length != pageCount ) {
                System.out.println( "Fehler: Die Anzahl der Seiten in der neuen Reihenfolge muss mit der Gesamtanzahl übereinstimmen." );
                return;
            }
            java.util.List<PDPage> pages = new java.util.ArrayList<>();
            for ( int i = 0; i < pageCount; i++ ) {
                pages.add( document.getPage( i ) );
                document.getPages().remove( i );
            }
            for ( int pageNum : newOrder ) {
                int index = pageNum - 1; // 0-basiert
                if ( index >= 0 && index < pageCount ) {
                    document.getPages().add( pages.get( index ) );
                }
            }
            System.out.println( "Seiten neu geordnet!" );
        } );
    }

    public static void combinePDFs ( File[] files, File output ) {
        try {
            org.apache.pdfbox.multipdf.PDFMergerUtility merger = new org.apache.pdfbox.multipdf.PDFMergerUtility();
            for ( File file : files ) {
                merger.addSource( file );
            }
            merger.setDestinationFileName( output.getAbsolutePath() );
            merger.mergeDocuments( null );
            System.out.println( "PDFs zusammengefügt!" );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
