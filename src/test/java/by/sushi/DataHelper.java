package by.sushi;

import com.github.javafaker.Faker;

import java.util.Locale;
public class DataHelper {

    public static Faker faker = new Faker(new Locale("ru"));

    public static String getRandomString(int length) {
        return faker.regexify(String.format("[a-zA-Z0-9]{%d}", length));
    }

    public static String getRandomLetters(int length) {
        return faker.regexify(String.format("[a-z]{%d}", length));
    }

    public static String getRandomNumber(int length) {
        return faker.regexify(String.format("[0-9]{%d}", length));
    }

    public static int getRandomInteger() {
        return faker.number().numberBetween(1, 100);
    }

    public static double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 999);

    }
    public static double getRandomDoubleV2(int integerLength, int decimalLength) {
        return faker.number().randomDouble(2, 1,999);

    }
}
