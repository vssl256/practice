static final int BASE = 1_000;
static final int MAX_LAST_TWO = 100;
static final int SEX_COUNT = 2;
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
static final int PERIOD_COUNT = RAW_PERIODS.length;

static final String[][] PERIODS = new String[ PERIOD_COUNT ][ MAX_LAST_TWO ];
void initPeriods() {
    for ( int period = 0; period < PERIOD_COUNT; period++ ) {
        for ( int lastTwo = 0; lastTwo < MAX_LAST_TWO; lastTwo++ ) {
            int form = ( lastTwo >= 11 && lastTwo <= 19 ) ? 2 : switch ( lastTwo % 10 ) {
                case 1 -> 0;
                case 2, 3, 4 -> 1;
                default -> 2;
            };
            PERIODS[ period ][ lastTwo ] = RAW_PERIODS[ period ][ form ];
        }
    }
}

static final String[][] TRIPLETS = new String[ Sex.values().length ][ BASE ];
void initTriplets() {
    for ( int sex = 0; sex < SEX_COUNT; sex++ ) {
        boolean isFeminine = sex == Sex.FEMININE.ordinal();
        String[][] tensOnes = isFeminine ? tensOnesFeminine : tensOnesMasculine;

        for ( int value = 0; value < BASE; value++ ) {
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

static String[][] tensOnesMasculine;
static String[][] tensOnesFeminine;
enum Sex {
    MASCULINE,
    FEMININE
}
static final int DIGIT_COUNT = 10;
String[][] initTensArr( Sex sex ) {
    String[][] tensOnes = new String[ DIGIT_COUNT ][ DIGIT_COUNT ];
    String[] ones = switch ( sex ) {
        case FEMININE -> ONES_FEMININE;
        case MASCULINE -> ONES_MASCULINE;
    };
    for ( int tensDigit = 0; tensDigit < DIGIT_COUNT; tensDigit++ ) {
        for ( int onesDigit = 0; onesDigit < DIGIT_COUNT; onesDigit++ ){
            tensOnes[ tensDigit ][ onesDigit ] = switch ( tensDigit ) {
                case 0 -> ones[ onesDigit ];
                case 1 -> TEENS[ onesDigit ];
                default -> TENS[ tensDigit ] + " " + ones[ onesDigit ];
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