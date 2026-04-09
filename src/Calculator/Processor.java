package Calculator;

import java.util.List;

class Processor {

    public double processInputs ( List<String> inputsList ) {
        if ( inputsList.isEmpty() ) return 0;

        double result = 0;

        for ( int i = 0; i < inputsList.size(); i += 3 ) {

            String[] termItems = {
                    inputsList.get( i ),
                    inputsList.get( i + 1 ),
                    inputsList.get( i + 2 )
            };

            System.out.println( "\nTerm " + ((i + 3) / 3) + ":" );
            double termResult = processNextTerm( termItems );
            System.out.println( "Result: " + termResult + "\n" );
            result += termResult;

        }

        return result;
    }

    private double processNextTerm ( String[] termItems ) {
        double num1     = 0;
        double num2     = 0;
        String operator = "";

        for ( String termItem : termItems ) {
            try {

                if ( Service.isOperator( termItem ) ) {
                    operator = termItem;
                    continue;
                }

                double num = Double.parseDouble( termItem );
                if ( num1 == 0 ) num1 = num;
                else if ( num2 == 0 ) num2 = num;

            } catch ( Exception _ ) {
            }
        }

        System.out.println( "Term: " + num1 + " " + operator + " " + num2 );

        return calculateResult( num1, num2, operator );
    }

    private double calculateResult ( double num1, double num2, String operator ) {
        return switch ( operator ) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            default -> num1 / num2;
        };
    }

}
