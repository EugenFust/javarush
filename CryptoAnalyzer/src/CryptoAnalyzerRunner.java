import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;


import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoAnalyzerRunner {
    static Scanner scanner = new Scanner(System.in);
    static List<String> textFromFile;
    static List<String> finishedText;

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в CryptoAnalyzer 0.1b");
        System.out.println("Программа умеет работать с шифром Цезаря на основе русского алфавита и символов пунктуации");
        System.out.println("Для начала работы напишите путь к файлу:");
        String pathFile = scanner.nextLine();

        boolean isError;
        //получаем строки из файла или ошибку
        isError = getStringsFromFile(pathFile);
        if (textFromFile == null || !isError) return;

        System.out.println("Меню действий:");
        System.out.println("1 - зашифровать файл, используя заданный ключ");
        System.out.println("2 - расшифровать файл, используя заданный ключ");
        System.out.println("3 - расшифровать файл, подобрав ключ методом BruteForce");
        System.out.println("0 - выход");
        boolean isOption = false;
        int option = 0;
        int key = 0;
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
        writeStringsToFile(pathFile, option);

        System.out.println("Все действия успешно выполнены. Благодарим за выбор нашей программы!");

    }

    //метод пытается взять текст из указанного файла или диагностировать ошибку
    private static boolean getStringsFromFile(String pathFile) {
        boolean isError = false;
        try {
            textFromFile = Files.readAllLines(Paths.get(pathFile), UTF_8);
        } catch (MalformedInputException charsetError) {
            System.out.println("Сохраните файл в кодировке UTF_8 и запустите программуу заново.");
            return isError;
        } catch (NoSuchFileException noFile) {
            System.out.println("Файл по указанному пути не найден.");
            System.out.println("Запустите программу заново.");
            return isError;
        } catch (AccessDeniedException accessDeniedException) {
            System.out.println("К файлу по указанному пути нет доступа.");
            System.out.println("Запустите программу заново.");
            return isError;
        } catch (Exception e) {
            System.out.println("Неизввестная ошибка.");
            System.out.println("Запустите программу заново.");
            return isError;
//            e.printStackTrace();
        }

        if (textFromFile.isEmpty()) {
            System.out.println("Файл пустой");
            System.out.println("Запустите программу заново и выберите файл");
            return isError;
        }
        return !isError; //текст получен, return true
    }

    //метод пытается записать текст в файл или диагностировать ошибку
    private static boolean writeStringsToFile(String pathFile, int option) {
        //в зависимости от ранее выбранного option добавляет постфикс
        String postfix = option == 1 ? "_encrypt" : "_decrypt";

        String newPath = pathFile.substring(0, pathFile.length() - 4) + postfix + ".txt";
        Path path = Paths.get(newPath);
        boolean isError = false;
        try {
//            Files.createFile(path);
            Files.write(path, finishedText);
        }  catch (Exception e) {
            System.out.println("Неизввестная ошибка.");
            System.out.println("Запустите программу заново.");
            return isError;
//            e.printStackTrace();
        }
        System.out.println("Создан новый файл: " + newPath);
        return !isError; //текст записан в файл, return true
    }

    //получаем ключ шифра и приводим его к размеру 0..alphabetSize
    private static int getKey() {
        boolean isOption = false;
        int key = 0;
        while (!isOption) {
            System.out.println("Пожалуйста, введите ключ шифра(число):");
            if (scanner.hasNextInt()) {
                key = scanner.nextInt();
                if (key % Alphabet.alphabetSize == 0) {
                    System.out.println("С таким ключом результат будет идентичен оригиналу");
                }
                isOption = true;
            } else {
                scanner.next(); //если ввели не число, то игнорируем введенные символы
                System.out.println("Введён неизвестный параметр.");
            }
        }
        if (key > 0) return key % Alphabet.alphabetSize;
        else return Alphabet.alphabetSize + (key % Alphabet.alphabetSize);
    }
}
