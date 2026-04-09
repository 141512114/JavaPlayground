import Common.Program;

import java.util.Scanner;

public class Main {

    private static Program program;

    public static void main ( String[] args ) {
        System.out.println( "Hello and welcome!" );
        System.out.println( "This is Nolan's Java testing range.\n\n" );

        try {

            System.out.println( "Which program would you like to run? (type the keyword of a program)" );

            // Available programs to choose from
            String keywords = "> calc # Calculator app";
            System.out.print( keywords + "\n\n" );

            Scanner scanner = new Scanner( System.in );
            String  input   = scanner.nextLine();

            if ( input.equalsIgnoreCase( "exit" ) ) {
                System.out.println( "Terminating. Goodbye!" );
                return;
            }

            if ( input.equals( "calc" ) ) {
                setProgram( new Calculator.Main() );
            }

            program.run();

        } catch ( Exception e ) {
            System.out.println( "An error occurred while waiting for input.\n\nError: " + e.getMessage() );
        }
    }

    public static void setProgram ( Program program ) {
        Main.program = program;
    }

}