import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoAnalyzerRunner {
    static Scanner scanner = new Scanner(System.in);
    private static Alphabet myAlphabet = new Alphabet();

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в CryptoAnalyzer 0.1b");
        System.out.println("Программа умеет работать с шифром Цезаря на основе русского алфавита и символов пунктуации");
        System.out.println(Alphabet.alphabetSize);
        System.out.println("Для начала работы напишите путь к файлу:");
        String pathFile = scanner.nextLine();

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(pathFile), UTF_8);
        } catch (IOException e) {
            System.out.println("Файл по указанному пути не найден либо к нему нет доступа");
            System.out.println("Запустите программу заново");
            return;
//            e.printStackTrace();
        }

        if (lines.isEmpty()) {
            System.out.println("Файл пустой");
            System.out.println("Запустите программу заново и выберите файл");
            return;
        }

        System.out.println("Меню действий:");
        System.out.println("1 - зашифровать файл, используя заданный ключ");
        System.out.println("2 - расшифровать файл, используя заданный ключ");
        System.out.println("3 - расшифровать файл, подобрав ключ методом BruteForce");
        System.out.println("0 - выход");
        boolean isOption = false;
        int option;
        int key = 0;
        while (!isOption) {
            System.out.println("Пожалуйста, выберите один из вариантов:");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                switch (option) {
                    case 1: //encrypt
                        isOption = true;
                        key = getKey();
                        Encryption.encryption(lines, key);
                        break;
                    case 2: //decrypt
                        isOption = true;
                        break;
                    case 3: //decrypt BruteForce
                        isOption = true;
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
        System.out.println("It's OK");

    }

    private static int getKey() {
        boolean isOption = false;
        int key = 0;
        while (!isOption) {
            System.out.println("Пожалуйста, введите ключ шифра(число больше 0):");
            if (scanner.hasNextInt()) {
                key = scanner.nextInt();
                if (key % Alphabet.alphabetSize <= 0) {
                    System.out.println("Такой ключ не даст результат.");
                    continue;
                }
                isOption = true;
            } else {
                scanner.next(); //игнорируем введенные символы
                System.out.println("Введён неизвестный параметр.");
            }
        }
//        System.out.println("key: " + key + "  %: " + key % Alphabet.alphabetSize);
        return key % Alphabet.alphabetSize; // ключ не больше
    }
}
