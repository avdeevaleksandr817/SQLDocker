package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDataBase;

public class SQLDeadlineTest {

    @AfterAll
    static void clean() {
        cleanDataBase();
    }


    @Test
    @DisplayName("Should successful Login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        //получаем страницу логина
        var LoginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации
        var authInfo = DataHelper.getAuthInfoWithTestData();
        //verification заполнив поля и нажав на кнопку
        var verificationPage = LoginPage.validLogin(authInfo);
        //на странице верификации проверяем ее видимость
        verificationPage.verifyVerificationPageVisibility();
        //получаем код верификации через Helper
        var verificationCode = SQLHelper.getVerificationCode();
        //полученный код вводим получаем страницу дашборда с проверкой на видимость
        assert verificationCode != null;
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingBase() {
        //получаем страницу логина
        var LoginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации генерируем рандомного пользователя
        var authInfo = DataHelper.generateRandomUser();
        //на странице логина вводим Random пользователя
        LoginPage.validLogin(authInfo);
        //получаем видимую страницу ошибки
        LoginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error notification if login with random exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithRandomExistInBaseAndActiveUserAndRandomVerificationCode() {
        //получаем страницу логина
        var LoginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации
        var authInfo = DataHelper.getAuthInfoWithTestData();
        //verification заполнив поля и нажав на кнопку
        var verificationPage = LoginPage.validLogin(authInfo);
        //на странице верификации проверяем ее видимость
        verificationPage.verifyVerificationPageVisibility();
        //получаем рандомный код верификации
        var verificationCode = DataHelper.getRandomVerificationCode();
        //вводим полученный код
        verificationPage.validVerify(verificationCode.getCode());
        //получаем видимую страницу ошибки
        verificationPage.verifyErrorNotificationVisibility();

    }

    @Test
    @DisplayName("blocking after three attempts to enter with a random login and password")
    void systemLock() {
        //получаем страницу логина
        var LoginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации генерируем рандомного пользователя
        var authInfo2 = DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        LoginPage.validLoginAndRandomPass(authInfo2);
        //получаем видимую страницу ошибки
        LoginPage.verifyErrorNotificationVisibility();
        //стираем данные
        LoginPage.cleanAllField();
        //получаем данные аутентификации генерируем рандомного пользователя 2 раз
        DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        LoginPage.validLoginAndRandomPass(authInfo2);
        //получаем видимую страницу ошибки
        LoginPage.verifyErrorNotificationVisibility();
        //стираем данные
        LoginPage.cleanAllField();
        //получаем данные аутентификации генерируем рандомного пользователя 3 раз
        DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        LoginPage.validLoginAndRandomPass(authInfo2);
        //получаем видимую страницу ошибки, и сообщение "система заблокирована"
        LoginPage.errorLockSystem();
    }
}




