package Calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Service {

    private Service () {}

    static boolean isOperator ( String operator ) {
        String  patternString = "[+\\-*/]";
        Pattern pattern       = Pattern.compile( patternString );
        Matcher matcher       = pattern.matcher( operator );
        return matcher.matches();
    }

}
