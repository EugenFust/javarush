package com.javarush.golf.fusteugen.cryptoanalyzer;

import java.util.*;

public class DecrypterStaticAnalysis {
    public static List<String> formationDecryptedText(List<String> textFromFile, List<String> exampleText) {
        //ArrayList<Character> alphabet = new ArrayList<>(mapStaticExampleText.keySet());
        //здесь хотелось бы создать алфавит из всех символов exampleText и на его основе делать расшифровку
        //какой будет алгоритм правильного выбора букв при одинаковых value ???
        Character symbolFromMaxValueMapTextFromFile = getSymbolWithMaxEntry(textFromFile);
        Character symbolFromMaxValueMapExampleText = getSymbolWithMaxEntry(exampleText);

        System.out.println(symbolFromMaxValueMapExampleText);
        System.out.println(symbolFromMaxValueMapTextFromFile);

        int key = Alphabet.ALPHABET.indexOf(symbolFromMaxValueMapTextFromFile)
                - Alphabet.ALPHABET.indexOf(symbolFromMaxValueMapExampleText);
        if (key < 0) key = Alphabet.ALPHABET_SIZE + key; //исправляем отрицательный ключ
        return Decrypter.formationDecryptedText(textFromFile, key);
    }

    //находит ключ по значению (1й в HashMap)
    private static Character getKeyFromValue(HashMap<Character, Integer> mapStaticTextFromFile, Integer value) {
        Character key = null;
        for (Map.Entry<Character, Integer> pair : mapStaticTextFromFile.entrySet()) {
            if (pair.getValue().equals(value)) {
                key = pair.getKey();
                break;
            }
        }
        return key;
    }

    //собираем статистику вхождений символов
    private static HashMap<Character, Integer> createMapStaticAnalysis(List<String> exampleText) {
        HashMap<Character, Integer> mapStatic = new HashMap<>();
        for (String line : exampleText) {
            if (line == null || line.isEmpty()) continue;
            for (char symbol : line.toCharArray()) {
                if (mapStatic.containsKey(symbol)) {
                    int value = mapStatic.get(symbol);
                    mapStatic.put(symbol, value + 1);
                } else mapStatic.put(symbol, 1);
            }
        }
        return mapStatic;
    }

    //находим символы с максимальным вхождением в текст
    private static Character getSymbolWithMaxEntry(List<String> text){
        HashMap<Character, Integer> mapStaticText = createMapStaticAnalysis(text);
        ArrayList<Integer> valuesMapText = new ArrayList<>(mapStaticText.values());
        Collections.sort(valuesMapText);
        int maxValueFromMap = valuesMapText.get(valuesMapText.size() - 1);
        return getKeyFromValue(mapStaticText, maxValueFromMap);
    }
}
