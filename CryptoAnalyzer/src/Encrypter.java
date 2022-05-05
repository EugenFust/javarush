import java.util.ArrayList;
import java.util.List;

public class Encrypter {
    public static List<String> formationEncryptedText(List<String> originalText, int key) {
        List<String> encryptedText = new ArrayList<>();
        //проход по всем пришедшим строкам
        for (String line : originalText) {
            char[] lineToChar = line.toCharArray();
            StringBuilder encryptedLine = new StringBuilder();
            //проход по всем символам в строке
            for (char symbol : lineToChar) {
                if (Alphabet.alphabet.contains(symbol)) {
                    //вычисляем позицию зашифрованного символа и добавляем символ в строку
                    int positionEncryptedSymbol = (Alphabet.alphabet.indexOf(symbol) + key) % Alphabet.alphabetSize;
                    char encryptedSymbol = Alphabet.alphabet.get(positionEncryptedSymbol);
                    encryptedLine.append(encryptedSymbol);
                }
            }
            encryptedText.add(encryptedLine.toString());
        }
        return encryptedText;
    }

}
