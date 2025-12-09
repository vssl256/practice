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
static final String[][] PERIODS = {
        { "рубль.", "рубля.", "рублей." },
        { "тысяча", "тысячи", "тысяч" },
        { "миллион", "миллиона", "миллионов" },
        { "миллиард", "миллиарда", "миллиардов" }
};
static final boolean[] PERIOD_IS_FEMALE = { false, true, false, false };
static final boolean[] PERIOD_CAN_BE_ZERO = { true, false, false, false };
static final int BASE = 1_000;
static int[] pluralForms;
void initForms() {
    pluralForms = new int[ 100 ];
    for ( int i = 0; i < pluralForms.length; i++ ) {
        final int lastTwo = i % 100;
        final int lastDigit = i % 10;

        pluralForms[ i ] = ( lastTwo >= 11 && lastTwo <= 19 ) ? 2 : switch ( lastDigit ) {
            case 1 -> 0;
            case 2, 3, 4 -> 1;
            default -> 2;
        };
    }
}
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
    initForms();
    initTens();
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

int[] dissectTriplet( final int triplet ) {
    return new int[] {
            triplet / 100,
            triplet / 10 % 10,
            triplet % 10
    };
}

String spell( final List<Integer> triplets, final boolean isNegative ) {
    StringBuilder sb = new StringBuilder();
    if ( isNegative ) sb.append( "минус " );
    for ( int i = triplets.size() - 1; i >= 0; i-- ) {
        if ( triplets.get( i ) == 0 && !PERIOD_CAN_BE_ZERO[ i ] ) continue;
        int[] triplet = dissectTriplet( triplets.get( i ) );
        int lastTwo = triplet[ 1 ] * 10 + triplet[ 2 ];
        boolean isFemale = PERIOD_IS_FEMALE[ i ];
        String part = String.join( " ",
            spellTriplet( triplet, isFemale ),
                PERIODS[ i ][ pluralForms[ lastTwo ] ]
        );
        sb.append( part.trim() ).append( " " );
    }
    sb.append( sb.isEmpty() ? "ноль " : "" );
    return capitalize( sb.toString().trim() );
}

String spellTriplet( final int[] triplet, final boolean isFemale ) {
    String out;

    int hundredsDigit = triplet[0];
    int tensDigit = triplet[1];
    int onesDigit = triplet[2];

    String[][] tensOnes = isFemale ? tensOnesFeminine : tensOnesMasculine;
    out = String.join( " ",
            HUNDREDS[ hundredsDigit ],
            tensOnes[ tensDigit ][ onesDigit ]
    );
    return out.trim();
}

String capitalize( String str ) {
    if ( str.isEmpty() ) return str;
    return Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
}