import java.util.ArrayList;
import java.util.List;

public class Decrypter {
    public static List<String> formationDecryptedText(List<String> encryptedText, int key) {
        List<String> decryptedText = new ArrayList<>();
        //проход по всем пришедшим строкам
        for (String line : encryptedText) {
            char[] lineToChar = line.toCharArray();
            StringBuilder decryptedLine = new StringBuilder();
            //проход по всем символам в строке
            for (char symbol : lineToChar) {
                if (Alphabet.alphabet.contains(symbol)) {
                    //вычисляем позицию расшифрованного символа и добавляем символ в строку
                    int positionDecryptedSymbol;
                    if (Alphabet.alphabet.indexOf(symbol) >= key) {
                        positionDecryptedSymbol = Alphabet.alphabet.indexOf(symbol) - key;
                    } else positionDecryptedSymbol = Alphabet.alphabetSize - (key - Alphabet.alphabet.indexOf(symbol));
                    char decryptedSymbol = Alphabet.alphabet.get(positionDecryptedSymbol);
                    decryptedLine.append(decryptedSymbol);
                }
            }
            decryptedText.add(decryptedLine.toString());
        }
        return decryptedText;
    }

}

