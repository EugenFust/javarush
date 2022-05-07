package com.javarush.golf.fusteugen.cryptoanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecrypterBruteForce {
    private static final String[] TRUTH = {", ", ". ", "! ", ": ", "? ", " и "};
    private static final String[] MISTAKES = {",.", ",?", ",!", ":,", ":.", ":!", ":?", ".!", ".?", ".,"};
    private static final List<String> PATTERN_OF_TRUTH = new ArrayList<>(Arrays.asList(TRUTH));
    private static final List<String> PATTERN_OF_MISTAKES = new ArrayList<>(Arrays.asList(MISTAKES));

    public static int getDecryptionKey(List<String> encryptedText) {
        //delta для сбора статистики количество совпадений
        double[] delta = new double[Alphabet.ALPHABET_SIZE];
        int key;
        //проход по всем значениям ключа
        for (key = 0; key < Alphabet.ALPHABET_SIZE; key++) {
            List<String> decryptedText;
            decryptedText = Decrypter.formationDecryptedText(encryptedText, key);
            long sumTruth = getSumOfMatches(decryptedText, PATTERN_OF_TRUTH);
            long sumMistakes = getSumOfMatches(decryptedText, PATTERN_OF_MISTAKES);
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
