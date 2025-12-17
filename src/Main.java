import java.util.Scanner;

public class Main {
    static final int BASE = 1_000;
    static final String[] RAW_ONES_MASCULINE = {
            "ноль", "один", "два", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять"
    };
    static final String[] RAW_ONES_FEMININE = {
            "ноль", "одна", "две", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять"
    };
    static final String[] RAW_TEENS = {
            "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    };
    static final String[] RAW_TENS = {
            "", "", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
    };
    static final String[] HUNDREDS = {
            "", "сто", "двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот"
    };
    static final String[][] PERIODS = {
            { "", "", "" },
            { "тысяча", "тысячи", "тысяч" },
            { "миллион", "миллиона", "миллионов" },
            { "миллиард", "миллиарда", "миллиардов" }
    };
    static final String[] ROUBLE_FORMS = { "рубль.", "рубля.", "рублей." };

    enum Gender {
        MASCULINE,
        FEMININE
    }
    static final Gender[] CLASS_GENDERS = { Gender.MASCULINE, Gender.FEMININE, Gender.MASCULINE, Gender.MASCULINE };

    static final String[] CARDINALS_MASCULINE = { RAW_ONES_MASCULINE[ 0 ], RAW_ONES_MASCULINE[ 1 ], RAW_ONES_MASCULINE[ 2 ], RAW_ONES_MASCULINE[ 3 ], RAW_ONES_MASCULINE[ 4 ], RAW_ONES_MASCULINE[ 5 ], RAW_ONES_MASCULINE[ 6 ], RAW_ONES_MASCULINE[ 7 ], RAW_ONES_MASCULINE[ 8 ], RAW_ONES_MASCULINE[ 9 ], RAW_TEENS[ 0 ], RAW_TEENS[ 1 ], RAW_TEENS[ 2 ], RAW_TEENS[ 3 ], RAW_TEENS[ 4 ], RAW_TEENS[ 5 ], RAW_TEENS[ 6 ], RAW_TEENS[ 7 ], RAW_TEENS[ 8 ], RAW_TEENS[ 9 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 2 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 3 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 4 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 5 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 6 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 7 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 8 ] + " " + RAW_ONES_MASCULINE[ 9 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 0 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 1 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 2 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 3 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 4 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 5 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 6 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 7 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 8 ], RAW_TENS[ 9 ] + " " + RAW_ONES_MASCULINE[ 9 ] };
    static final String[] CARDINALS_FEMININE = { RAW_ONES_FEMININE[ 0 ], RAW_ONES_FEMININE[ 1 ], RAW_ONES_FEMININE[ 2 ], RAW_ONES_FEMININE[ 3 ], RAW_ONES_FEMININE[ 4 ], RAW_ONES_FEMININE[ 5 ], RAW_ONES_FEMININE[ 6 ], RAW_ONES_FEMININE[ 7 ], RAW_ONES_FEMININE[ 8 ], RAW_ONES_FEMININE[ 9 ], RAW_TEENS[ 0 ], RAW_TEENS[ 1 ], RAW_TEENS[ 2 ], RAW_TEENS[ 3 ], RAW_TEENS[ 4 ], RAW_TEENS[ 5 ], RAW_TEENS[ 6 ], RAW_TEENS[ 7 ], RAW_TEENS[ 8 ], RAW_TEENS[ 9 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 2 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 3 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 4 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 5 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 6 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 7 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 8 ] + " " + RAW_ONES_FEMININE[ 9 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 0 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 1 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 2 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 3 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 4 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 5 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 6 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 7 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 8 ], RAW_TENS[ 9 ] + " " + RAW_ONES_FEMININE[ 9 ] };
    static final String[][] CARDINALS_BY_GENDER = {
            CARDINALS_MASCULINE,
            CARDINALS_FEMININE
    };

    static final int[] PLURAL_FORMS = { 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2 };

    static void main() {
        Scanner sc = new Scanner( System.in );
        while ( true ) {
            String inputString = sc.nextLine();
            if ( inputString.equals( "exit" ) ) break;
            try {
                int inputValue = Integer.parseInt( inputString );
                boolean isNegative = inputValue < 0;
                spell( inputValue, isNegative );
            } catch ( NumberFormatException ignore ) {
                System.out.println( "Введите корректное целое число" );
            }
        }
    }

    static void spell( int inputValue, boolean isNegative ) {
        StringBuilder output = new StringBuilder();
        int[] triplets = new int[ 4 ];
        int count = dissect( inputValue, triplets );
        if ( isNegative ) output.append( "минус " );
        for ( int i = count - 1; i >= 0; i-- ) {
            int triplet = triplets[ i ];
            if ( triplet == 0 ) continue;
            int lastTwo = triplet % 100;
            Gender gender = CLASS_GENDERS[ i ];
            String part = HUNDREDS[ triplet / 100 ] + " " +
                    CARDINALS_BY_GENDER[ gender.ordinal() ][ lastTwo ] + " " +
                    PERIODS[ i ][ PLURAL_FORMS[ lastTwo ] ];
            output.append( part.trim() ).append( " " );
        }
        output.append( output.isEmpty() ? "ноль " : "" );
        int lastTwo = triplets[ 0 ] % 100;
        output.append( ROUBLE_FORMS[ PLURAL_FORMS[ lastTwo ] ] );
        System.out.println( capitalize( output.toString().trim() ) );
    }

    static int dissect( int inputValue, int[] output ) {
        int count = 0;
        int temp = Math.abs( inputValue );

        while ( temp > 0 ) {
            output[ count ] = temp % BASE;
            temp /= BASE;
            count++;
        }

        return count;
    }

    static String capitalize( String str ) {
        if ( str.isEmpty() ) return str;
        return Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
    }
}
