import java.util.ArrayList;

public class Alphabet {

    public static ArrayList<Character> alphabet = new ArrayList<>();
    public static int alphabetSize;

    public Alphabet() {
        for (char symbol = 'а'; symbol <= 'я'; symbol++) {
            alphabet.add(symbol);
        }
        for (char symbol = 'А'; symbol <= 'Я'; symbol++) {
            alphabet.add(symbol);
        }
        alphabet.add('.');
        alphabet.add(',');
        alphabet.add('!');
        alphabet.add('?');
        alphabet.add('-');
        alphabet.add('"');
        alphabet.add(':');
        alphabet.add(' ');
        alphabetSize = alphabet.size();
    }


}
