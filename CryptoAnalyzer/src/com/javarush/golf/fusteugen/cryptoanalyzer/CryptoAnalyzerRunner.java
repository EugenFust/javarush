package com.javarush.golf.fusteugen.cryptoanalyzer;

import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;


import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoAnalyzerRunner {
    static Scanner scanner = new Scanner(System.in);
    protected static boolean isError;

    public static void main(String[] args) {

        List<String> textFromFile;
        List<String> finishedText = null;

        System.out.println("Добро пожаловать в CryptoAnalyzer 0.1c");
        System.out.println("Программа умеет работать с шифром Цезаря на основе русского алфавита и символов пунктуации");
        System.out.println("Для начала работы напишите путь к файлу:");
        String pathFile = scanner.nextLine();

        //получаем строки из файла или ошибку
        textFromFile = getStringsFromFile(pathFile);
        if (textFromFile == null || isError) return;

        System.out.println("Меню действий:");
        System.out.println("1 - зашифровать файл, используя заданный ключ");
        System.out.println("2 - расшифровать файл, используя заданный ключ");
        System.out.println("3 - расшифровать файл, подобрав ключ методом BruteForce");
        System.out.println("4 - расшифровать файл, используя статический анализ");
        System.out.println("0 - выход");
        boolean isOption = false;
        int option = 0;
        int key;
        while (!isOption) {
            System.out.println("Пожалуйста, выберите один из вариантов:");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                switch (option) {
                    case 1: //encrypt
                        isOption = true;
                        key = getKey();
                        finishedText = Encrypter.formationEncryptedText(textFromFile, key);
                        break;
                    case 2: //decrypt
                        isOption = true;
                        key = getKey();
                        finishedText = Decrypter.formationDecryptedText(textFromFile, key);
                        break;
                    case 3: //decrypt BruteForce
                        isOption = true;
                        key = DecrypterBruteForce.getDecryptionKey(textFromFile);
                        System.out.println("Самый вероятный ключ, исходя из нашего алгоритма: " + key);
                        finishedText = Decrypter.formationDecryptedText(textFromFile, key);
                        break;
                    case 4: //decrypt staticAnalysis
                        isOption = true;
                        System.out.println("Выберите файл для анализа:");
                        scanner.nextLine(); //убираем \n после ввода nextInt()
                        String pathExampleFile = scanner.nextLine();
                        List<String> exampleText = getStringsFromFile(pathExampleFile);
                        if (exampleText == null || isError) return;
                        finishedText = DecrypterStaticAnalysis.formationDecryptedText(textFromFile, exampleText);
                        break;
                    case 0:
                        System.out.println("Выбран выход. Благодарим за выбор нашей программы!");
                        return;
                    default:
                        System.out.println("Введён неизвестный параметр. Необходимо ввести параметр из меню");
                }
            } else {
                scanner.next(); //игнорируем введенные символы
                System.out.println("Введён неизвестный параметр. Необходимо ввести параметр из меню");
            }
        }
        if(isError) {
            printLog("Смотрите log сообщения");
            return;
        }
        writeStringsToFile(finishedText, pathFile, option);

        System.out.println("Все действия успешно выполнены. Благодарим за выбор нашей программы!");
    }

    //метод пытается взять текст из указанного файла или диагностировать ошибку
    private static List<String> getStringsFromFile(String pathFile) {
        List<String> textFromFile;
        try {
            textFromFile = Files.readAllLines(Paths.get(pathFile), UTF_8);
        } catch (MalformedInputException charsetError) {
            printLog("Сохраните файл в кодировке UTF_8 и запустите программуу заново.");
            isError = true;
            return null;
        } catch (NoSuchFileException noFile) {
            printLog("Файл по указанному пути не найден.");
            printLog("Запустите программу заново.");
            isError = true;
            return null;
        } catch (AccessDeniedException accessDeniedException) {
            printLog("К файлу по указанному пути нет доступа.");
            printLog("Запустите программу заново.");
            isError = true;
            return null;
        } catch (Exception e) {
            printLog("Неизввестная ошибка.");
            printLog("Запустите программу заново.");
            isError = true;
            return null;
//            e.printStackTrace();
        }

        if (textFromFile.isEmpty()) {
            System.out.println("Файл пустой");
            System.out.println("Запустите программу заново и выберите файл");
            isError = true;
            return null;
        }
        return textFromFile;
    }

    //метод пытается записать текст в файл или диагностировать ошибку
    private static void writeStringsToFile(List<String> completeText, String pathOriginalFile, int option) {
        //в зависимости от ранее выбранного option добавляет постфикс
        String postfix = option == 1 ? "_encrypt" : "_decrypt";

        String pathForCompleteText = //в новом файле вставляем постфикс перед расширением файла
                pathOriginalFile.substring(0, pathOriginalFile.length() - 4) + postfix +
                        pathOriginalFile.substring(pathOriginalFile.length() - 4);
        //можно nameOriginalFile.split(".") чтобы точнее разделить название от расширения
        Path path = Paths.get(pathForCompleteText);
        try {
            Files.write(path, completeText);
        } catch (Exception e) {
            printLog("Неизввестная ошибка.");
            printLog("Запустите программу заново.");
            isError = true;
            return;
//            e.printStackTrace();
        }
        System.out.println("Создан новый файл: " + pathForCompleteText);
    }

    //получаем ключ шифра и приводим его к размеру 0..alphabetSize
    private static int getKey() {
        boolean isOption = false;
        int key = 0;
        while (!isOption) {
            System.out.println("Пожалуйста, введите ключ шифра(число):");
            if (scanner.hasNextInt()) {
                key = scanner.nextInt();
                if (key % Alphabet.ALPHABET_SIZE == 0) {
                    System.out.println("С таким ключом результат будет идентичен оригиналу");
                }
                isOption = true;
            } else {
                scanner.next(); //если ввели не число, то игнорируем введенные символы
                System.out.println("Введён неизвестный параметр.");
            }
        }
        if (key > 0) return key % Alphabet.ALPHABET_SIZE;
        else return Alphabet.ALPHABET_SIZE + (key % Alphabet.ALPHABET_SIZE);
    }

    private static void printLog(String log){
        System.err.println(log);
    }
}
