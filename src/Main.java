final String[] onesM = {
        "", "один", "два", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
final String[] onesF = {
        "", "одна", "две", "три", "четыре", "пять", "шесть",
        "семь", "восемь", "девять"
};
final String[] teens = {
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
};
final String[] tens = {
        "", "десять", "двадцать", "тридцать", "сорок", "пятьдесят",
        "шестьдесят", "семьдесят", "восемьдесят", "девяносто"
};
final String[] hunds = {
        "", "сто", "двести", "триста", "четыреста", "пятьсот",
        "шестьсот", "семьсот", "восемьсот", "девятьсот"
};
final String[][] periods = {
        { "", "", "" },
        { "тысяча", "тысячи", "тысяч" },
        { "миллион", "миллиона", "миллионов" },
        { "миллиард", "миллиарда", "миллиардов" }
};
int[] forms;
void initForms() {
    forms = new int[ 100 ];
    for ( int i = 0; i < forms.length; i++ ) {
        int lastTwo = i % 100;
        int lastDigit = i % 10;

        forms[ i ] = ( lastTwo >= 11 && lastTwo <= 19 ) ? 2 : switch ( lastDigit ) {
            case 1 -> 0;
            case 2, 3, 4 -> 1;
            default -> 2;
        };
    }
}
String[][] tensOnesM;
String[][] tensOnesF;
String[][] initTensArr( char sex ) {
    String[][] tensOnes = new String[ 10 ][ 10 ];
    String[] ones = switch ( sex ) {
        case 'F' -> onesF;
        default -> onesM;
    };
    for ( int t = 0; t < 10; t++ ) {
        for ( int o = 0; o < 10; o++ ){
            tensOnes[ t ][ o ] = switch ( t ) {
                case 0 -> ones[ o ];
                case 1 -> teens[ o ];
                default -> tens[ t ] + " " + ones[ o ];
            };
        }
    }
    return tensOnes;
}
void initTens() {
    tensOnesM = initTensArr( 'M' );
    tensOnesF = initTensArr( 'F' );
}
String[][] roubleForms;
void initRoubleForms() {
    roubleForms = new String[ 10 ][ 10 ];
    for ( int t = 0; t < 10; t++ ) {
        for ( int o = 0; o < 10; o++ ) {
            roubleForms[ t ][ o ] = switch ( t ) {
                case 1 -> "рублей.";
                default -> switch ( o ) {
                    case 1 -> "рубль.";
                    case 2, 3, 4 -> "рубля.";
                    default -> "рублей.";
                };
            };
        }
    }
}
void initTables() {
    initForms();
    initTens();
    initRoubleForms();
}

void main() {
    initTables();
    Scanner sc = new Scanner( System.in );
    while ( true ) {
        String inputString = sc.nextLine();
        int inputValue = Integer.parseInt( inputString );
        spell( dissect( inputValue ) );
    }
}

int[] dissect( int inputValue ) {
    int tripletsCount = 1;
    for ( int i = inputValue; i != 0; i /= 1_000 ) {
        tripletsCount++;
    }
    int[] triplets = new int[ tripletsCount ];
    for ( int i = 0; i < tripletsCount; i++ ) {
        int triplet = inputValue / ( int ) Math.pow( 1_000, i ) % 1_000;
        triplets[ i ] = triplet;
    }
    return triplets;
}

int[] dissectTriplet( int triplet ) {
    return new int[] {
            triplet / 100,
            triplet / 10 % 10,
            triplet % 10
    };
}

void spell( int[] triplets ) {
    StringBuilder sb = new StringBuilder();
    for ( int i = triplets.length - 1; i >= 0; i-- ) {
        if ( triplets[ i ] == 0 ) continue;
        int[] triplet = dissectTriplet( triplets[ i ] );
        int lastTwo = triplet[ 1 ] * 10 + triplet[ 2 ];
        boolean isFemale = i == 1;
        sb.append( spellTriplet( triplet, isFemale ) ).append( " " );
        sb.append( periods[ i ][ forms[ lastTwo ] ] ).append( " " );
    }
    sb.append( sb.isEmpty() ? "ноль" : "" );
    int last = triplets[ 0 ] % 10;
    int preLast = triplets[ 0 ] / 10 % 10;
    String roubles = roubleForms[ preLast ][ last ];
    IO.println( firstToUpper( sb.toString().trim() + " " + roubles ) );
}

String spellTriplet( int[] triplet, boolean isFemale ) {
    StringBuilder sb = new StringBuilder();

    int hundsDigit = triplet[0];
    int tensDigit = triplet[1];
    int onesDigit = triplet[2];

    String[][] tensOnes = isFemale ? tensOnesF : tensOnesM;

    sb.append( hunds[ hundsDigit ] ).append( " " );
    sb.append( tensOnes[ tensDigit ][ onesDigit ] );
    return sb.toString().trim();
}

static String firstToUpper( String str ) {
    return str.substring( 0, 1 ).toUpperCase() + str.substring( 1 );
}