static final String[] ONES_M = {
        "", "один", "два", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
static final String[] ONES_F = {
        "", "одна", "две", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
static final String[] TEENS = {
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
};
static final String[] TENS = {
        "", "десять", "двадцать", "тридцать", "сорок", "пятьдесят",
        "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
};
static final String[] HUNDS = {
        "", "сто", "двести", "триста", "четыреста", "пятьсот",
        "шестьсот", "семьсот", "восемьсот", "девятьсот"
};
static final String[][] PERIODS = {
        { "", "", "" },
        { "тысяча", "тысячи", "тысяч" },
        { "миллион", "миллиона", "миллионов" },
        { "миллиард", "миллиарда", "миллиардов" }
};
static final int TRIPLET = 1_000;
static final String[] ROUBLE_FORMS = { "рубль.", "рубля.", "рублей." };
static int[] forms;
void initForms() {
    forms = new int[ 100 ];
    for ( int i = 0; i < forms.length; i++ ) {
        final int lastTwo = i % 100;
        final int lastDigit = i % 10;

        forms[ i ] = ( lastTwo >= 11 && lastTwo <= 19 ) ? 2 : switch ( lastDigit ) {
            case 1 -> 0;
            case 2, 3, 4 -> 1;
            default -> 2;
        };
    }
}
static String[][] tensOnesM;
static String[][] tensOnesF;
String[][] initTensArr( char sex ) {
    String[][] tensOnes = new String[ 10 ][ 10 ];
    String[] ones = switch ( sex ) {
        case 'F' -> ONES_F;
        default -> ONES_M;
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
    tensOnesM = initTensArr( 'M' );
    tensOnesF = initTensArr( 'F' );
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
        int triplet = inputValue % TRIPLET;
        triplets.add( triplet );
        inputValue /= TRIPLET;
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
        if ( triplets.get( i ) == 0 ) continue;
        int[] triplet = dissectTriplet( triplets.get( i ) );
        int lastTwo = triplet[ 1 ] * 10 + triplet[ 2 ];
        boolean isFemale = i == 1;
        String part = String.join( " ",
            spellTriplet( triplet, isFemale ),
            PERIODS[ i ][ forms[ lastTwo ] ]
        );
        sb.append( part.trim() ).append( " " );
    }
    sb.append( sb.isEmpty() ? "ноль " : "" );
    int lastTwo = triplets.getFirst() % 100;
    String roubles = ROUBLE_FORMS[ forms[ lastTwo ] ];
    sb.append( roubles );
    return capitalize( sb.toString().trim() );
}

String spellTriplet( final int[] triplet, final boolean isFemale ) {
    String out;

    int hundsDigit = triplet[0];
    int tensDigit = triplet[1];
    int onesDigit = triplet[2];

    String[][] tensOnes = isFemale ? tensOnesF : tensOnesM;
    out = String.join( " ",
            HUNDS[ hundsDigit ],
            tensOnes[ tensDigit ][ onesDigit ]
    );
    return out.trim();
}

String capitalize( String str ) {
    if ( str.isEmpty() ) return str;
    return Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
}