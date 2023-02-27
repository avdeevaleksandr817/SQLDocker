package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    //получить информацию об аутентификации с тестовыми данными
    public static AuthInfo getAuthInfoWithTestData() {

        return new AuthInfo("vasya", "qwerty123");
    }

    //создать рандомный логин
    private static String generateRandomLogin() {
        return faker.name().username();
    }

    //создать рандомный пароль
    private static String generateRandomPassword() {

        return faker.internet().password();
    }

    //создать рандомного пользователя
    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    //получить случайный проверочный код
    public static VerificationCode getRandomVerificationCode() {

        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class generateRandomUser {
        String generateRandomLogin;
        String generateRandomPassword;
    }

    @Value
    public static class AuthInfo {

        String testUserLogin;
        String testUserPassword;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthCode {
        private String id;
        private String user_id;
        private String code;
        private String created;
    }

}
