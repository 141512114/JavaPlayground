package Calculator;

import Common.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Program {

    private Processor processor;

    @Override
    protected void init () { this.processor = new Processor(); }

    @Override
    public void run () {
        System.out.println( "Hello and welcome!" );
        System.out.println( "This is a simple Java program." );

        try {

            List<String> inputsList    = new ArrayList<>();
            boolean      doCalculation = false;

            System.out.println( "Starting calculator...\n\n" );

            System.out.println( "Enter two numbers to add (or type 'exit' to quit):" );
            Scanner scanner = new Scanner( System.in );
            String  input;

            do {

                input = scanner.nextLine();

                System.out.println( "Your input was: " + input );

                if ( input.equalsIgnoreCase( "exit" ) ) {
                    System.out.println( "Exiting calculator. Goodbye!" );
                    break;
                }

                if ( input.equalsIgnoreCase( "calc" ) ) {
                    doCalculation = true;
                    break;
                }

                try {

                    if ( Service.isOperator( input ) ) {
                        inputsList.add( input );
                        System.out.println( "Adding " + input + " as an operator to the list." );
                    } else {
                        double num = Double.parseDouble( input );
                        inputsList.add( num + "" );
                    }

                } catch ( NumberFormatException e ) {
                    System.out.println( "Invalid input. Please enter a valid number." );
                } catch ( Exception e ) {
                    System.out.println( "Invalid input. Please enter a valid number or operator." );
                }

            } while ( true );

            if ( ! doCalculation ) return;

            System.out.println( "Calculating result ..." );
            System.out.println( "Input list size: " + inputsList.size() );

            for ( String s : inputsList ) {
                System.out.println( s );
            }

            double sum = processor.processInputs( inputsList );

            System.out.println( "The total sum is: " + sum );

        } catch ( Exception e ) {
            System.out.println( "An error occurred while waiting for input.\n\nError: " + e.getMessage() );
        }
    }

}
