package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class SQLDeadlineTest {
    //после всех тестов очищаем БД
    @AfterAll
    static void teardown() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Should successful Login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        //получаем страницу логина
        var loginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации
        var authInfo = DataHelper.getAuthInfoWithTestData();
        //verification заполнив поля и нажав на кнопку
        var verificationPage = loginPage.validLogin(authInfo);
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
        var loginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации генерируем рандомного пользователя
        var authInfo = DataHelper.generateRandomUser();
        //на странице логина вводим Random пользователя
        loginPage.validLogin(authInfo);
        //получаем видимую страницу ошибки
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error notification if login with random exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithRandomExistInBaseAndActiveUserAndRandomVerificationCode() {
        //получаем страницу логина
        var loginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации
        var authInfo = DataHelper.getAuthInfoWithTestData();
        //verification заполнив поля и нажав на кнопку
        var verificationPage = loginPage.validLogin(authInfo);
        //на странице верификации проверяем ее видимость
        verificationPage.verifyVerificationPageVisibility();
        //получаем рандомный код верификации
        var verificationCode = DataHelper.getRandomVerificationCode();
        //вводим полученный код
        verificationPage.verify(verificationCode.getCode());
        //получаем видимую страницу ошибки
        verificationPage.verifyErrorNotificationVisibility();

    }

    @Test
    @DisplayName("blocking after three attempts to enter with a random login and password")
    void systemLock() {
        //получаем страницу логина
        var loginPage = open("http://localhost:9999", LoginPage.class);
        //получаем данные аутентификации генерируем рандомного пользователя
        var authInfo2 = DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        loginPage.validLogin(authInfo2);
        //получаем видимую страницу ошибки
        loginPage.verifyErrorNotificationVisibility();
        //стираем данные
        loginPage.cleanAllField();
        //получаем данные аутентификации генерируем рандомного пользователя 2 раз
        DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        loginPage.validLogin(authInfo2);
        //получаем видимую страницу ошибки
        loginPage.verifyErrorNotificationVisibility();
        //стираем данные
        loginPage.cleanAllField();
        //получаем данные аутентификации генерируем рандомного пользователя 3 раз
        DataHelper.generateUserAndRandomPass();
        //на странице логина вводим Random пользователя
        loginPage.validLogin(authInfo2);
        //получаем видимую страницу ошибки, и сообщение "система заблокированаloginPage.errorLockSystem();
    }
}




