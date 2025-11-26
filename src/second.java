import java.util.Scanner;

public class second {

    final static String[] ones = {
            "один", "два", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять"
    };
    final static String[] teens = {
            "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    };
    final static String[] tens = {
            "десять", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
    };
    final static String[] hunds = {
            "сто", "двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот"
    };

    public static void main( String[] args ) {
        while (true) {
            int input = 0;
            Scanner sc = new Scanner( System.in );
            try {
                input = sc.nextInt();
            } catch ( Exception ignore ) {}

            StringBuilder str = new StringBuilder();
            for ( int i = 4; i >= 0; i-- ) {
                int triplet = input / ( int ) Math.pow( 1_000, i ) % 1_000;
                if ( triplet == 0 ) continue;
                str.append( spell( triplet, i ) ).append( " " );
            }

            IO.println( firstToUpper( str.toString().trim() ) + " рублей." );
        }
    }

    public static String spell( int triplet, int period ) {
        if ( triplet == 0 ) return "";

        int onesDigit = triplet % 10;
        int tensDigit = triplet / 10 % 10;
        int hundsDigit = triplet / 100 % 10;

        int length = Integer.toString( triplet ).length();

        String out;

        if ( length == 1 ) { out = ones[ onesDigit - 1 ]; }
        else if ( length == 2 ) {
            if ( onesDigit == 0 ) {
                out = tens[ tensDigit - 1 ];
            } else if ( tensDigit == 1 ) {
                out = teens[ onesDigit - 1 ];
            } else out = tens[ tensDigit - 1 ] + " " + ones[ onesDigit - 1 ];
        }
        else {
            if ( onesDigit == 0 && tensDigit != 0 ) {
                out = hunds[ hundsDigit - 1 ] + " " + tens[ tensDigit - 1 ];
            } else if ( tensDigit == 1 ) {
                out = hunds[ hundsDigit - 1 ] + " " + teens[ onesDigit - 1 ];
            } else if ( tensDigit != 0 ) {
                out = hunds[ hundsDigit - 1 ] + " " + tens[ tensDigit - 1 ] + " " + ones[ onesDigit - 1 ];
            } else if ( onesDigit != 0 ) {
                out = hunds[ hundsDigit - 1 ] + " " + ones[ onesDigit - 1 ];
            } else out = hunds[ hundsDigit - 1 ];
        }

        if ( period == 1 ) {
            if ( onesDigit == 1 && tensDigit != 1 ) out = out.substring( 0, out.length() - 2 ) + "на";
            else if ( onesDigit == 2 && tensDigit != 1 ) out = out.substring( 0, out.length() - 1 ) + "е";

            if ( onesDigit == 1 && tensDigit != 1 ) out += " тысяча";
            else if ( onesDigit < 5 && tensDigit != 1 && onesDigit != 0 ) out += " тысячи";
            else out += " тысяч";
        } else if ( period == 2 ) {
            if ( onesDigit == 1 && tensDigit != 1 ) out += " миллион";
            else if ( onesDigit < 5 && tensDigit != 1 && onesDigit != 0 ) out += " миллиона";
            else out += " миллионов";
        } else if ( period == 3 ) {
            if ( onesDigit == 1 && tensDigit != 1 ) out += " миллиард";
            else if ( onesDigit < 5 && tensDigit != 1 && onesDigit != 0 ) out += " миллиарда";
            else out += " миллиардов";
        }

        return out;
    }

    static String firstToUpper( String str ) {
        return str.substring( 0, 1 ).toUpperCase() + str.substring( 1 );
    }
}
