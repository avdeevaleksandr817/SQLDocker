package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class LoginPage {
    @FindBy(css = "[data-test-id=login] input")
    private SelenideElement loginField;
    @FindBy(css = "[data-test-id=password] input")
    private SelenideElement passwordField;
    @FindBy(css = "[data-test-id=action-login]")
    private SelenideElement loginButton;
    @FindBy(css = "[data-test-id='error-notification']")
    private SelenideElement errorNotification;

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return page(VerificationPage.class);
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






