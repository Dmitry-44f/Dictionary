import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class MyDictionary {
    private final Scanner sc = new Scanner(System.in);
    private DictionaryService currentDictionary;

    public void start() {
        System.out.println("Консольный словарь");

        boolean running = true;
        while (running) {
            try {
                String filePath = forFilePath();
                initDictionary(filePath);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            boolean runningMenu = true;
            while (runningMenu) {
                try {
                    System.out.println("1. Посмотреть содержимое");
                    System.out.println("2. Найти запись по ключу");
                    System.out.println("3. Добавить запись");
                    System.out.println("4. Удалить запись по ключу");
                    System.out.println("5. Сменить словарь");
                    System.out.println("0. Выйти из словаря!");
                    System.out.print("Выберте действие: ");

                    String choice = sc.nextLine().trim();

                    switch (choice) {
                        case "1" -> showAll();
                        case "2" -> findEntry();
                        case "3" -> addEntry();
                        case "4" -> removeEntry();
                        case "5" -> {
                            System.out.println("\nВыбираем другой словарь...");
                            runningMenu = false;
                        }
                        case "0" -> {
                            running = false;
                            runningMenu = false;
                        }
                        default -> System.out.println("Неверная команда!");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
        System.out.println("Вы вышли из словаря!");
        sc.close();
    }

    private String forFilePath() {
        while (true) {
            System.out.println("Введите путь к файлу: ");
            String path = sc.nextLine().trim();

            File file = new File(path);
            if (file.exists() && file.isFile()) {
                return path;
            } else {
                System.out.println("Ошибка: Файл не найден. Попробуйте еще раз.");
            }
        }
    }

    private void initDictionary(String filePath) {
        System.out.println("1. Латинский (4 буквы)");
        System.out.println("2. Цифровой (5 цифр)");
        System.out.println("Выберите тип словарь:");

        String type = sc.nextLine();

        if (type.equals("1")) {
            currentDictionary = new LatinDictionary(filePath);
        } else if (type.equals("2")) {
            currentDictionary = new NumericDictionary(filePath);
        } else {
            throw new IllegalArgumentException("Несуществующий тип словаря!");
        }
    }

    private void showAll() {
        String all = currentDictionary.getAll();
        if (all.isEmpty()) {
            System.out.println("Словарь пуст!");
        } else {
            System.out.println("Содержимое словаря:");
            System.out.println(all);
        }
    }

    private void findEntry() {
        System.out.print("Введите ключ: ");
        String key = sc.nextLine().trim();

        try{
            String value = currentDictionary.find(key);
            System.out.println("Перевод: " + value);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void addEntry() {
        System.out.print("Введите ключ: ");
        String key = sc.nextLine().trim();

        System.out.print("Введите значение: ");
        String value = sc.nextLine().trim();

        try {
            currentDictionary.add(key, value);
            System.out.println("Запись добавлена!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void removeEntry() {
        System.out.print("Введите ключ: ");
        String key = sc.nextLine().trim();

        try{
            if (currentDictionary.remove(key)) {
                System.out.println("Запись удалена!");
            } else {
                System.out.println("Ошибка: запись не найдена!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка:" + e.getMessage());
        }

    }
}
