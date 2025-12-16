static final String[] ONES_MASCULINE = {
        "", "один", "два", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
static final String[] ONES_FEMININE = {
        "", "одна", "две", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
static final String[] TEENS = {
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
};
static final String[] TENS = {
        "", "", "двадцать", "тридцать", "сорок", "пятьдесят",
        "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
};
static final String[] HUNDREDS = {
        "", "сто", "двести", "триста", "четыреста", "пятьсот",
        "шестьсот", "семьсот", "восемьсот", "девятьсот"
};
static final String[][] RAW_PERIODS = {
        { "рубль.", "рубля.", "рублей." },
        { "тысяча", "тысячи", "тысяч" },
        { "миллион", "миллиона", "миллионов" },
        { "миллиард", "миллиарда", "миллиардов" }
};
static final String[][] PERIODS = new String[4][100];
void initPeriods() {
    for ( int period = 0; period < 4; period++ ) {
        for ( int lastTwo = 0; lastTwo < 100; lastTwo++ ) {
            int form = ( lastTwo >= 11 && lastTwo <= 19 ) ? 2 : switch ( lastTwo % 10 ) {
                case 1 -> 0;
                case 2, 3, 4 -> 1;
                default -> 2;
            };
            PERIODS[ period ][ lastTwo ] = RAW_PERIODS[ period ][ form ];
        }
    }
}
static final String[][] TRIPLETS = new String[ Sex.values().length ][ 1_000 ];
void initTriplets() {
    for ( int sex = 0; sex < 2; sex++ ) {
        boolean isFeminine = sex == Sex.FEMININE.ordinal();
        String[][] tensOnes = isFeminine ? tensOnesFeminine : tensOnesMasculine;

        for ( int value = 0; value < 1000; value++ ) {
            int hundreds = value / 100;
            int tens = value / 10 % 10;
            int ones = value % 10;

            TRIPLETS[ sex ][ value ] = String.join( " ",
                    HUNDREDS[ hundreds ],
                    tensOnes[ tens ][ ones ]
            ).trim();
        }
    }
}
static final boolean[] PERIOD_IS_FEMALE = { false, true, false, false };
static final boolean[] PERIOD_CAN_BE_ZERO = { true, false, false, false };
static final int BASE = 1_000;
static String[][] tensOnesMasculine;
static String[][] tensOnesFeminine;
enum Sex {
    MASCULINE,
    FEMININE
}
String[][] initTensArr( Sex sex ) {
    String[][] tensOnes = new String[ 10 ][ 10 ];
    String[] ones = switch ( sex ) {
        case FEMININE -> ONES_FEMININE;
        case MASCULINE -> ONES_MASCULINE;
    };
    for ( int t = 0; t < 10; t++ ) {
        for ( int o = 0; o < 10; o++ ){
            tensOnes[ t ][ o ] = switch ( t ) {
                case 0 -> ones[ o ];
                case 1 -> TEENS[ o ];
                default -> TENS[ t ] + " " + ones[ o ];
            };
        }
    }
    return tensOnes;
}
void initTens() {
    tensOnesMasculine = initTensArr( Sex.MASCULINE );
    tensOnesFeminine = initTensArr( Sex.FEMININE );
}
void initTables() {
    initPeriods();
    initTens();
    initTriplets();
}

void main() {
    initTables();
    Scanner sc = new Scanner( System.in );
    while ( true ) {
        String inputString = sc.nextLine();
        if ( inputString.equals( "exit" ) ) break;
        try {
            int inputValue = Integer.parseInt( inputString );
            boolean isNegative = inputValue < 0;
            System.out.println( spell ( dissect( Math.abs( inputValue ) ), isNegative ) );
        } catch ( NumberFormatException ignore ) {
            System.out.println( "Введите корректное целое число" );
        }
    }
}

List<Integer> dissect( int inputValue ) {
    List<Integer> triplets = new ArrayList<>();
    do {
        int triplet = inputValue % BASE;
        triplets.add( triplet );
        inputValue /= BASE;
    } while ( inputValue > 0 );
    return triplets;
}

String spell( final List<Integer> triplets, final boolean isNegative ) {
    StringBuilder sb = new StringBuilder();
    if ( isNegative ) sb.append( "минус " );
    for ( int i = triplets.size() - 1; i >= 0; i-- ) {
        if ( triplets.get( i ) == 0 && !PERIOD_CAN_BE_ZERO[ i ] ) continue;
        int value = triplets.get( i );
        Sex sex = PERIOD_IS_FEMALE[ i ] ? Sex.FEMININE : Sex.MASCULINE;
        String part = String.join( " ",
                TRIPLETS[ sex.ordinal() ][ value ],
                PERIODS[ i ][ value % 100 ]
        );
        sb.append( part.trim() ).append( " " );
    }
    sb.append( sb.isEmpty() ? "ноль " : "" );
    return capitalize( sb.toString().trim() );
}

String capitalize( String str ) {
    if ( str.isEmpty() ) return str;
    return Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
}