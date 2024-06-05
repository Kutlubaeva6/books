import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserInfoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        System.out.println("Пример: Иванов Иван Иванович 15.04.1985 89101112233 m");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        try {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Ожидалось 6, получено: " + parts.length);
            }

            String lastName = parts[0];
            String firstName = parts[1];
            String middleName = parts[2];
            String birthDateStr = parts[3];
            String phoneNumberStr = parts[4];
            String gender = parts[5];

            long phoneNumber = Long.parseLong(phoneNumberStr);
            LocalDate birthDate = parseDate(birthDateStr);
            validateData(lastName, firstName, middleName, birthDate, phoneNumber, gender);

            // Создаем папку для хранения файлов
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdir();
            }

            writeToFile(lastName, firstName, middleName, birthDateStr, phoneNumber, gender, directory);

            System.out.println("Данные успешно записаны в файл.");

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    private static void validateData(String lastName, String firstName, String middleName, LocalDate birthDate, long phoneNumber, String gender) {

        if (!Pattern.matches("[A-Za-zА-Яа-яЁё]+", lastName)) {
            throw new IllegalArgumentException("Неверный формат фамилии. Ожидается строка.");
        }

        if (!Pattern.matches("[A-Za-zА-Яа-яЁё]+", firstName)) {
            throw new IllegalArgumentException("Неверный формат имени. Ожидается строка.");
        }

        if (!Pattern.matches("[A-Za-zА-Яа-яЁё]+", middleName)) {
            throw new IllegalArgumentException("Неверный формат отчества. Ожидается строка.");
        }
        
        if (phoneNumber <= 0) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Ожидается положительное целое число.");
        }

        if (!(gender.equals("f") || gender.equals("m"))) {
            throw new IllegalArgumentException("Неверный формат пола. Ожидается 'f' или 'm'.");
        }
    }

    private static void writeToFile(String lastName, String firstName, String middleName, String birthDate, long phoneNumber, String gender, File directory) throws IOException {
        String fileName = directory.getPath() + File.separator + lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.format("%s %s %s %s %d %s", lastName, firstName, middleName, birthDate, phoneNumber, gender));
            writer.newLine();
        }
    }
}
