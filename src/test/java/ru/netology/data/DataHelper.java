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
        return new AuthInfo("vasya", "qwerty123", null, null);
    }

    //тестовый пользователь и случайный пароль
    public static AuthInfo generateUserAndRandomPass() {
        return new AuthInfo("vasya", generateRandomPassword(), null, null);
    }

    //создать рандомный логин
    public static String generateRandomLogin() {
        return faker.name().username();
    }

    //создать рандомный пароль
    public static String generateRandomPassword() {
        return faker.internet().password();
    }

    //создать рандомного пользователя
    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword(), null, null);
    }

    //получить случайный проверочный код
    public static VerificationCode getRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
        String generateRandomLogin;
        String generateRandomPassword;

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
