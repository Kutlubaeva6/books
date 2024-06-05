import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InfoApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата рождения номер телефона пол");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        try {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Ожидалось 6, получено: " + parts.length);
            }

            String lastName = parts[0];
            String firstName = parts[1];
            String middleName = parts[2];
            String birthDate = parts[3];
            String phoneNumber = parts[4];
            String gender = parts[5];

            validateData(lastName, firstName, middleName, birthDate, phoneNumber, gender);

            writeToFile(lastName, firstName, middleName, birthDate, phoneNumber, gender);

            System.out.println("Данные успешно записаны в файл.");

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void validateData(String lastName, String firstName, String middleName, String birthDate, String phoneNumber, String gender) {
        if (!Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", birthDate)) {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается dd.mm.yyyy.");
        }

        if (!Pattern.matches("\\d+", phoneNumber)) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Ожидается целое число.");
        }

        if (!(gender.equals("f") || gender.equals("m"))) {
            throw new IllegalArgumentException("Неверный формат пола. Ожидается 'f' или 'm'.");
        }
    }

    private static void writeToFile(String lastName, String firstName, String middleName, String birthDate, String phoneNumber, String gender) throws IOException {
        String fileName = lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.format("%s %s %s %s %s %s", lastName, firstName, middleName, birthDate, phoneNumber, gender));
            writer.newLine();    
        }
    }
}
