import java.util.Scanner;

public class Old {

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
        System.out.println( System.getProperty( "java.version" ) + "\nВведите число" );
        while ( true ) {
            Scanner sc = new Scanner( System.in );
            System.out.print("> ");
            String input = sc.nextLine();
            long start = System.nanoTime();
            if ( input.equals( "exit" ) || input.equals( "выход" ) ) System.exit( 0 );

            int inputVal;
            try {
                inputVal = Integer.parseInt( input );
            } catch ( NumberFormatException ignore ) {
                System.out.println( "Введите ЧИСЛО" );
                continue;
            }

            if ( inputVal <= 0 ) {
                System.out.println( "Введите число > 0" );
                continue;
            }

            StringBuilder str = new StringBuilder();
            for ( int i = 3; i >= 0; i-- ) {
                int triplet = inputVal / ( int ) Math.pow( 1_000, i ) % 1_000;
                if ( triplet == 0 ) continue;
                str.append( spell( triplet, i ) ).append( " " );
            }

            System.out.println( firstToUpper( str.toString() ).trim() + " " + rubles( inputVal ) );
            IO.println( (System.nanoTime() - start) / 1e6 );
        }
    }

    static String rubles( int value ) {
        int lastTwo = value % 100;

        if ( lastTwo >= 11 && lastTwo <= 19 ) return "рублей.";

        switch ( value % 10 ) {
            case 1: return "рубль.";
            case 2:
            case 3:
            case 4: return "рубля.";
            default: return "рублей.";
        }
    }

    public static String spell( int triplet, int period ) {
        if ( triplet == 0 ) return "";

        int onesDigit = triplet % 10;
        int tensDigit = triplet / 10 % 10;
        int hundsDigit = triplet / 100 % 10;

        String out = "";

        if ( hundsDigit > 0 ) out += hunds[ hundsDigit - 1 ] + " ";

        if ( tensDigit == 1 ) {
            if ( onesDigit == 0 ) out += tens[ 0 ] + " ";
            else out += teens[ onesDigit - 1 ] + " ";
        } else {
            if ( tensDigit > 1 ) out += tens[ tensDigit - 1 ] + " ";
            if ( onesDigit > 0 ) out += ones[ onesDigit - 1 ] + " ";
        }

        if ( period == 1 ) {
            if ( onesDigit == 1 && tensDigit != 1 ) out = out.substring( 0, out.length() - 3 ) + "на ";
            else if ( onesDigit == 2 && tensDigit != 1 ) out = out.substring( 0, out.length() - 2 ) + "е ";

            if ( onesDigit == 1 && tensDigit != 1 ) out += "тысяча";
            else if ( onesDigit < 5 && tensDigit != 1 && onesDigit != 0 ) out += "тысячи";
            else out += "тысяч";
        } else if ( period == 2 ) {
            if ( onesDigit == 1 && tensDigit != 1 ) out += "миллион";
            else if ( onesDigit < 5 && tensDigit != 1 && onesDigit != 0 ) out += "миллиона";
            else out += "миллионов";
        } else if ( period == 3 ) {
            out = "один миллиард";
        }

        out += " ";
        return out.trim();
    }

    static String firstToUpper( String str ) {
        return str.substring( 0, 1 ).toUpperCase() + str.substring( 1 );
    }
}
