import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecrypterBruteForce {
    private static final String[] truth = {", ", ". ", "! ", ": ", "? ", " и "};
    private static final String[] mistakes = {",.", ",?", ",!", ":,", ":.", ":!", ":?", ".!", ".?", ".,"};
    private static final List<String> patternOfTruth = new ArrayList<>(Arrays.asList(truth));
    private static final List<String> patternOfMistakes = new ArrayList<>(Arrays.asList(mistakes));

    public static int getDecryptionKey(List<String> encryptedText) {
        //delta для сбора статистики количество совпадений
        double[] delta = new double[Alphabet.alphabetSize];
        int key;
        //проход по всем значениям ключа
        for (key = 1; key < Alphabet.alphabetSize; key++) {
            List<String> decryptedText;
            decryptedText = Decrypter.formationDecryptedText(encryptedText, key);
            long sumTruth = getSumOfMatches(decryptedText, patternOfTruth);
            long sumMistakes = getSumOfMatches(decryptedText, patternOfMistakes);
            delta[key] = (double) sumTruth /sumMistakes;
        }
        key = indexMaxValue(delta);
        return key;
    }

    //вычисляем индекс максимального значения в массиве
    private static int indexMaxValue(double[] delta) {
        int indexMax = 0;
        for (int i = 0; i < delta.length; i++) {
            if (delta[indexMax] < delta[i]) {
                indexMax = i;
            }
        }
        return indexMax;
    }

    private static long getSumOfMatches(List<String> text, List<String> signatures) {
        long sum = 0;
        for (String line : text) {
            for (String signature : signatures) {
                sum += getSumOfMatchesString(line, signature);
            }
        }
        if (sum == 0) return 1; //возврат 1 чтобы не было деления на 0
        return sum;
    }

    private static long getSumOfMatchesString(String line, String signature) {
        long sum = 0;
        int startIndex;
        startIndex = line.indexOf(signature);
        if (startIndex > 0) {
            sum += getSumOfMatchesString(line.substring(startIndex + 1), signature);
            sum++;
        }
        return sum;
    }
}
