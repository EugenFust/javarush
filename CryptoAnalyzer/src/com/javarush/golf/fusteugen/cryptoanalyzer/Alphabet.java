package com.javarush.golf.fusteugen.cryptoanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//задаем свой алфавит
public class Alphabet {

    private static final Character[] ALPHABET_CHAR = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о',
            'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О',
            'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
            '.', ',', '\"', ':', '-', '!', '?', ' '};
    public static final List<Character> ALPHABET = new ArrayList<>(Arrays.asList(ALPHABET_CHAR));
    public static final int ALPHABET_SIZE = ALPHABET.size();

}
