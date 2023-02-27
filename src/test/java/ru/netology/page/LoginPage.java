package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }


    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getTestUserLogin());
        passwordField.setValue(info.getTestUserPassword());
        loginButton.click();
        return new VerificationPage();
    }

    private void cleanLoginField() {
        loginField.sendKeys(Keys.CONTROL + "A");
        loginField.sendKeys(BACK_SPACE);
    }

    private void cleanPasswordField() {
        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(BACK_SPACE);
    }

    public void cleanAllField() {
        cleanPasswordField();
        cleanLoginField();
    }

    public void errorLockSystem() {
        errorNotification.shouldBe(visible);
        errorNotification.shouldHave(Condition.text("Система заблокирована"));
    }

}





