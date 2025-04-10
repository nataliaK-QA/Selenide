package ru.netology.delivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {
    private static final String[] CITIES = {
            "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
            "Нижний Новгород", "Челябинск", "Самара", "Омск", "Ростов-на-Дону",
            "Уфа", "Красноярск", "Пермь", "Воронеж", "Волгоград"
    };

    private static final String[] LAST_NAMES = {
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Попов", "Васильев", "Лебедев", "Новиков", "Федоров"
    };

    private static final String[] FIRST_NAMES = {
            "Александр", "Алексей", "Андрей", "Дмитрий", "Михаил",
            "Елена", "Ольга", "Наталья", "Ирина", "Мария"
    };

    private DataGenerator() {
    }

    public static String generateCity() {
        return CITIES[new Random().nextInt(CITIES.length)];
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName() {
        return LAST_NAMES[new Random().nextInt(LAST_NAMES.length)] + " " +
                FIRST_NAMES[new Random().nextInt(FIRST_NAMES.length)];
    }

    public static String generatePhone() {
        return "+7" + String.format("%010d", new Random().nextInt(1_000_000_000));
    }
}